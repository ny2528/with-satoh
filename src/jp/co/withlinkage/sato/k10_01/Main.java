package jp.co.withlinkage.sato.k10_01;
import java.io.FileReader;
import java.io.Reader;
import java.util.Properties;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader fr = new FileReader
				("C:\\Users\\masahiko satoh\\Desktop\\pref.properties");
		//プロパティファイル形式の読み書きのためのクラス（java.utilパッケージに用意されている。）
		Properties p = new Properties();
		p.load(fr);   //ファイル内容を読み込む
		String capital = p.getProperty("aichi.capital");
		String food = p.getProperty("aichi.food");
		System.out.println(capital + ":" + food);
		fr.close();
	}
}
