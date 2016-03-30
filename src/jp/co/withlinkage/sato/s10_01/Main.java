package jp.co.withlinkage.sato.s10_01;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
//		Heroインスタンスを作成
		Hero h = new Hero();
//		Heroインスタンスのhpを"10"に変更
		h.hp = "10";
//		ファイルに属性情報を書き込むメソッドの起動
		h.saveHeroToFile(h);
	}
}
