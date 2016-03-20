package jp.co.withlinkage.naokit.sugarcrm.php;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import jp.co.withlinkage.naokit.common.util.FileUtil;
import jp.co.withlinkage.naokit.common.util.StringUtil;

public class PhpParser {

	public Map<File, Map<String, Object>> getAllVariables(File dir) throws IOException {
		Map<File, Map<String, Object>> result = new TreeMap<>();

		for(File file : FileUtil.findR(dir, "*.php")) {
			Map<String, Object> vars = getArrayVariables(file);
			if(vars.size() > 0)
				result.put(file, vars);
		}
		return result;
	}

	public Map<String, String> getVariables(File source) throws IOException {
		return getVariables(FileUtil.read(source));
	}

	public Map<String, String> getVariables(String source) {
		Map<String, String> result = new TreeMap<>();
		int p = 0;
		boolean quote = false;
		String varName = null;
		char[] buf = new char[source.length()];
		for(int i=0; i<source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '$':
				if(quote) {
					if(varName != null)
						buf[p++] = c;
					break;
				}
				p = source.indexOf('=', i);
				if(p < 0)
					return result;
				varName = source.substring(i, p-1);
				varName = StringUtil.trim(varName, ' ', '\t', '\n', '\r');
				i = ++p;
				p = 0;
				break;
			case '/':
				if(quote) {
					if(varName != null)
						buf[p++] = c;
					break;
				}
				if(++i >= source.length())
					break;
				c = source.charAt(i);
				if(c == '/') {
					i = source.indexOf('\n', i);
					if(i < 0)
						return result;
				} else if(c == '*') {
					i = source.indexOf("*/", i);
					if(i < 0)
						return result;
					i++;
				}
				break;
			case '\\':
				i++;
				if(varName != null) {
					buf[p++] = c;
					buf[p++] = source.charAt(i);
				}
				break;
			case '\'':
				quote = !quote;
				if(varName != null)
					buf[p++] = c;
				break;
			case ';':
				if(quote || varName == null)
					break;
				String value = new String(buf, 0, p);
				StringUtil.trim(value, ' ', '\n', '\t', '\r');
				result.put(varName, value);
				varName = null;
				p = 0;
				break;
			case ' ':
			case '\n':
			case '\r':
			case '\t':
				if(quote && varName != null)
					buf[p++] = c;
				break;
			default:
				if(varName != null)
					buf[p++] = c;
				break;
			}
		}

		return result;
	}

	public Map<String, Object> getArrayVariables(File source) throws IOException {
		return getArrayVariables(FileUtil.read(source));
	}

	public Map<String, Object> getArrayVariables(String source) {
		Map<String, String> vars = getVariables(source);
		Map<String, Object> result = new TreeMap<>();
		for(String key : vars.keySet()) {
			String var = vars.get(key);
			if(var.startsWith("array(")) {
				result.put(key, array2map(var));
			} else {
				if(var.startsWith("'"))
					var = var.substring(1, var.length()-1);
				result.put(key, var);
			}
		}
		return result;
	}

	public Map<String, Object> array2map(String source) {
		Map<String, Object> result = new TreeMap<>();
		_array2map(source, 0, result);
		return result;
	}

	private int _array2map(String source, int pos, Map<String, Object> map) {
		if(pos >= source.length())
			return pos;
		int p = pos;
		String name = null;
		boolean quote = false;
		for(int i=pos; i<source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '\\':
				i++;
				break;
			case '\'':
				quote = !quote;
				if(!quote) {
					if(name == null) {
						name = p >= i ? "" : source.substring(p, i);
					} else {
						map.put(name, p >= i ? "" : source.substring(p, i));
						name = null;
					}
				}
				p = i + 1;
				break;
			case '(':
				if(quote || name == null)
					break;
				Map<String, Object> child = new TreeMap<>();
				map.put(name, child);
				i = _array2map(source, i, child);
				name = null;
				p = i;
				break;
			case ')':
				if(quote)
					break;
				return i+1;
			default:
				break;
			}
		}
		return p;
	}
}
