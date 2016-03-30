package jp.co.withlinkage.sato.s10_04;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader fr = new FileReader
				("C:\\Users\\masahiko satoh\\Desktop\\rpgdata.properties");
		Properties p = new Properties();
		p.load(fr);
		String name = p.getProperty("heroName");
		String strHp = p.getProperty("heroHp");
		int hp = Integer.parseInt(strHp) + 10;
		System.out.println("勇者の名前:" + name);
		System.out.println("勇者のHP:" + hp);
		fr.close();
	}
}
