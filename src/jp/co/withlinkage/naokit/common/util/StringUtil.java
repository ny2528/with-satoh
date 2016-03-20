package jp.co.withlinkage.naokit.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtil {
	/**
	 * null または空文字列であればtrue
	 * @param s 対象文字列
	 * @return null または空文字列であればtrue
	 */
	public static final boolean isEmpty(String s) {
		return (s == null) ? true : s.length() == 0;
	}
	/**
	 * null でも安全な比較。ただしお互いnullでもfalse
	 * @param s1 文字列1
	 * @param s2 文字列2
	 * @return 等しければtrue
	 */
	public static final boolean isEqual(String s1, String s2) {
		if(s1 == null || s2 == null) return false;
		return s1.equals(s2);
	}
	/**
	 * お互いnullであればtrueという条件を追加したequals
	 * @param s1 文字列1
	 * @param s2 文字列2
	 * @return 等しければtrue
	 */
	public static final boolean isEqualIgnoreNull(String s1, String s2) {
		if(s1 == null) return s2 == null;
		if(s2 == null) return false;
		return s1.equals(s2);
	}
	/**
	 * 複数文字列のうちに等しいものがあればtrue
	 * @param target 検査文字列
	 * @param matcher 比較文字列（複数）
	 * @return target に等しい matcherがあれば <p>true</p>
	 */
	public static final boolean in(String target, String... matcher) {
		if(target == null) {
			for(String s : matcher)
				if(s == null) return true;
			return false;
		}
		for(String s : matcher)
			if(s != null && s.equals(target))
				return true;
		return false;
	}
	/**
	 * 複数文字列のうちに等しいものがあればtrue
	 * @param target 検査文字列
	 * @param matcher 比較文字列（複数）
	 * @return target に等しい matcherがあれば <p>true</p>
	 */
	public static final boolean inIgnoreCase(String target, String... matcher) {
		if(target == null) {
			for(String s : matcher)
				if(s == null) return true;
			return false;
		}
		for(String s : matcher)
			if(s != null && s.equalsIgnoreCase(target))
				return true;
		return false;
	}
	/**
	 * 完ぺきではない
	 * @param s
	 * @return
	 */
	public static final int nonProportionalWidth(String s) {
		try {
			return s.getBytes("MS932").length;
		} catch (UnsupportedEncodingException e) {
			// ありえない
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * Delimiter連続の場合間なしのSplit
	 * 例) linux コマンドの戻りなど
	 * @param source source string
	 * @param delimiter delimiter
	 * @return list of split string
	 */
	public static final List<String> splitTrim(String source, char delimiter) {
		List<String> result = new ArrayList<String>();
		int pos = 0;
		boolean flag = true;
		char[] array = source.toCharArray();
		for(int i=0; i<array.length; i++) {
			if(array[i] == delimiter) {
				if(flag) {
					pos++;
				} else {
					result.add(new String(array, pos, i - pos));
					pos = i + 1;
					flag = true;
				}
			} else {
				flag = false;
			}
		}
		if(pos < array.length)
			result.add(new String(array, pos, array.length - pos));
		return result;
	}
	/*
	 * java keywords
	 */
	private static final String[] _java_keyword;
	static {
		String[] tmp = {
		"class","interface",
		"static","final","native","implements","extends","synchronized",
		"null","return","new",
		"throws","throw",
		"if","else","switch","case","default",
		"try","catch","finally",
		"public","protected","private",
		"void","boolean","char","byte","int","float","double","short",
		"enum",
		"volatile"
		};
		Arrays.sort(tmp);
		//TODO: add java.lang Classes??
		_java_keyword = tmp;
	}
	/**
	 * Javaのキーワードか判定
	 * @param s 対象文字列
	 * @return
	 */
	public static final boolean isJavaKeyword(String s) {
		return Arrays.binarySearch(_java_keyword, s) >= 0;
	}
	/**
	 * 繰り返し文字列を返します
	 * @param s 対象文字列
	 * @param count 繰り返し数
	 * @return 引数文字列の繰り返し文字列
	 */
	public static final String dupeStr(String s, int count) {
		final char[] src = s.toCharArray();
		final int srcLen = src.length;
		final int resLen = srcLen * count;
		final char[] result = new char[resLen];
		for(int i=0; i<resLen; i+=srcLen)
			System.arraycopy(src, 0, result, i, srcLen);
		return new String(result);
	}
	/**
	 * 文字列を左埋めします
	 * @param source 元文字列
	 * @param c 左埋めする文字
	 * @param length 結果の長さ
	 * @return 引数の長さまで文字を左埋めした文字列
	 */
	public static final String fillLeft(String source, char c, int length) {
		final char[] src = source.toCharArray();
		final int srcLen = src.length;
		final char[] result = new char[length];
		int pos = length - srcLen;
		System.arraycopy(src, 0, result, pos, srcLen);
		while(pos > 0)
			result[--pos] = c;
		return new String(result);
	}
	/**
	 * 文字列を右埋めします
	 * @param source 元文字列
	 * @param c 右埋めする文字
	 * @param length 結果の長さ
	 * @return 引数の長さまで文字を右埋めした文字列
	 */
	public static final String fillRight(String source, char c, int length) {
		final char[] src = source.toCharArray();
		final int srcLen = src.length;
		final char[] result = new char[length];
		System.arraycopy(src, 0, result, 0, srcLen);
		int pos = length;
		while(pos > srcLen)
			result[--pos] = c;
		return new String(result);
	}
	/**
	 * 数字文字列か判定
	 * @param source 対象文字列
	 * @return 数字変換できれば、true
	 */
	public static final boolean isNumeric(String source) {
		try {
			new BigDecimal(source);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 文字列配列の結合
	 * @param source 対象文字列配列
	 * @param delimiter 区切り文字
	 * @param quote 括り文字
	 * @param escape エスケープ文字
	 * @param escapeTarget エスケープ対象文字配列
	 * @return 結合された文字列
	 */
	public static final String toString(String[] source, String delimiter, String quote, String escape, String[] escapeTarget) {
		StringBuffer buf = new StringBuffer();
		for(String s : source) {
			buf.append(delimiter);
			if(s != null) {
				for(String e : escapeTarget)
					s = s.replaceAll(e, escape+e);
				buf.append(quote).append(s).append(quote);
			}
		}
		return buf.substring(delimiter.length());
	}
	/**
	 * 文字列配列の結合
	 * @param source 対象文字列配列
	 * @param delimiter 区切り文字
	 * @param quote 括り文字
	 * @return 結合された文字列
	 */
	public static final String toString(String[] source, String delimiter, String quote) {
		String[] escapeTarget = quote.length() > 0 ? new String[]{quote} : new String[0];
		return toString(source, delimiter, quote, quote, escapeTarget);
	}
	/**
	 * 文字列配列の結合
	 * @param source 対象文字列配列
	 * @param quote 括り文字
	 * @return 結合された文字列
	 */
	public static final String toString(String[] source, String quote) {
		return toString(source, "", quote);
	}
	/**
	 * 文字列配列のカンマ結合
	 * @param source 対象文字列配列
	 * @param quote 括り文字
	 * @return 結合された文字列
	 */
	public static final String toCommaString(String[] source, String quote) {
		return toString(source, ",", quote);
	}
	/**
	 * 文字列配列のカンマ結合
	 * @param source 対象文字列配列
	 * @return 結合された文字列
	 */
	public static final String toCommaString(String... source) {
		return toString(source, ",", "");
	}
	/**
	 * 重複除去.ソートされます
	 * @param source 対象文字列配列
	 * @return
	 */
	public static final String[] trimRepeat(String... source) {
		final int length = source.length;
		String[] array = new String[length];
		System.arraycopy(source, 0, array, 0, length);
		if(length <= 1)
			return array;
		Arrays.sort(array);
//		int pos = 0;
//		for(int i=1; i<length; i++,pos++)
//			if(!array[i].equals(array[pos]))
//				break;
		int pos = 1;
		for(int i=pos; i<length; i++)
			if(!array[i].equals(array[pos]))
				array[++pos] = array[i];
		if(++pos == length)
			return array;
		String[] result = new String[pos];
		System.arraycopy(array, 0, result, 0, pos);
		return result;
	}

	/**
	 * 1次元文字列配列に結合します.配列の配列（の配列の...）も1次元にします。
	 * String以外の引数は {@link Object#toString()} の結果を格納します。
	 * @param array
	 * @return
	 */
	public static final String[] concat(Object... array) {
		List<Object> list = new ArrayList<Object>();
		for(Object o : array)
			_addArray(o, list);
		String[] result = new String[list.size()];
		int pos = 0;
		for(Object o : list) {
			if(o == null)
				result[pos] = null;
			else if(o instanceof String)
				result[pos] = (String)o;
			else
				result[pos] = o.toString();
			pos++;
		}
		return result;
	}

	private static final void _addArray(Object o, List<Object> list) {
		if(o == null)
			list.add(null);
		else if(o instanceof Object[])
			for(Object o1 : (Object[])o)
				_addArray(o1, list);
		else
			list.add(o);
	}

	/**
	 * Trim charactors
	 * @param source
	 * @param c charactors to trim
	 * @return trimed string
	 */
	public static String trim(String source, char... c) {
		Arrays.sort(c);
		int begin = 0;
		try {
			begin = beginPosWithout(source, c);
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
		int end = endPosWithout(source, c);
		return (begin==0 && end==source.length()-1)
				? source
				: source.substring(begin, end);
	}
	/**
	 * Left Trim charactors
	 * @param source
	 * @param c charactors to trim
	 * @return trimed string
	 */
	public static String trimLeft(String source, char... c) {
		Arrays.sort(c);
		int begin = 0;
		try {
			begin = beginPosWithout(source, c);
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
		return (begin==0)
				? source
				: source.substring(begin);
	}
	/**
	 * Right Trim charactors
	 * @param source
	 * @param c charactors to trim
	 * @return trimed string
	 */
	public static String trimRight(String source, char... c) {
		Arrays.sort(c);
		int end = 0;
		try {
			end = endPosWithout(source, c);
		} catch(IndexOutOfBoundsException e) {
			return "";
		}
		return (end==source.length()-1)
				? source
				: source.substring(0, end);
	}

	/**
	 * @param source
	 * @param sortedChars
	 * @throws IndexOutOfBoundsException
	 * @return
	 */
	private static final int beginPosWithout(String source, char[] sortedChars) {
		int p = 0;
		while(Arrays.binarySearch(sortedChars, source.charAt(p)) >= 0)
			p++;
		return p;
	}
	/**
	 * @param source
	 * @param sortedChars
	 * @throws IndexOutOfBoundsException
	 * @return
	 */
	private static final int endPosWithout(String source, char[] sortedChars) {
		int p = source.length() - 1;
		while(Arrays.binarySearch(sortedChars, source.charAt(p)) >= 0)
			p--;
		return p;
	}
}
