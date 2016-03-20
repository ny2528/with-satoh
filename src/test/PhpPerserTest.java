package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import jp.co.withlinkage.naokit.common.util.FileUtil;
import jp.co.withlinkage.naokit.sugarcrm.php.PhpParser;

public class PhpPerserTest {

	@Test
	public void test() {
		try {

		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Test
	public void test03() {
		try {
			File dir = new File("C:\\naoki\\application\\SugarCRM\\SugarCE-JLP-master\\SugarCE-JLP-master");
			PrintWriter p = null;
			try {
				p = new PrintWriter(new FileOutputStream(new File("output.txt")));
				Map<File, Map<String, Object>> map = new PhpParser().getAllVariables(dir);
				for(File f : map.keySet()) {
					String path = f.getAbsolutePath();
					Map<String, Object> m = map.get(f);
					printArray(m, p, new String[]{path});
				}
			} finally {
				p.close();
			}
			System.out.println(FileUtil.DEFAULT_CHAR_SET);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	private void printArray(Map<String, Object> map, PrintWriter p, String...parents) {
		final int len = parents.length;
		String[] args = new String[len + 1];
		System.arraycopy(parents, 0, args, 0, len);
		for(String key : map.keySet()) {
			Object value = map.get(key);
			for(int i=0; i<len; i++) {
				p.print(parents[i]);
				p.print("\t");
			}
			p.print(key);
			if(value instanceof String) {
				p.print("\t");
				p.println(value);
			} else {
				args[len] = key;
				p.println();
				printArray((Map<String,Object>)value, p, args);
			}
		}
	}


	@Test
	public void test02() {
		try {
			File dir = new File("C:\\naoki\\application\\SugarCRM\\SugarCE-JLP-master\\SugarCE-JLP-master");
			File dir2 = new File(dir, "modules\\Accounts\\language");
//			File dir2 = new File(dir, "include\\language");
			File file = new File(dir2, "ja.lang.php");
			PrintWriter p = null;
			try {
				p = new PrintWriter(new FileOutputStream(new File("output.txt")));
				Map<String, Object> vars = new PhpParser().getArrayVariables(file);
				printArray(vars, p, 0);
			} finally {
				p.close();
			}
			System.out.println(FileUtil.DEFAULT_CHAR_SET);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	private void printArray(Map<String, Object> map, PrintWriter p, int depth) {
		for(String key : map.keySet()) {
			Object value = map.get(key);
			for(int i=0; i<depth; i++)
				p.print("\t");
			p.print(key);
			if(value instanceof String) {
				p.print("\t");
				p.println(value);
			} else {
				p.println();
				printArray((Map<String,Object>)value, p, depth + 1);
			}
		}
	}

	@Test
	public void test01() {
		try {
			File dir = new File("C:\\naoki\\application\\SugarCRM\\SugarCE-JLP-master\\SugarCE-JLP-master");
//			File dir2 = new File(dir, "modules\\Accounts\\language");
			File dir2 = new File(dir, "include\\language");
			File file = new File(dir2, "ja.lang.php");
			PrintWriter p = null;
			try {
				p = new PrintWriter(new FileOutputStream(new File("output.txt")));
				Map<String, String> vars = new PhpParser().getVariables(file);
				for(String key : vars.keySet()) {
					System.out.println(key);
					String s = vars.get(key);
					System.out.println(s);

					p.print(key);
					p.print("=");
					p.println(s);
				}
			} finally {
				p.close();
			}
			System.out.println(FileUtil.DEFAULT_CHAR_SET);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
