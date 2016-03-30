package jp.co.withlinkage.sato.s10_02;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Hero {
	String name;
	String hp;
	String mp;

//	Heroインスタンスの属性情報をrpgsave.datに書き込むメソッド
	public void saveHeroToFile() throws IOException {
		Writer w = new BufferedWriter(new FileWriter
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.csv"));
		w.write(this.name + ",");  // 区切りに用いる文字をデリミタ(delimiter)という
		w.write(this.hp + ",");
		w.write(this.mp);
		w.flush();
		w.close();
	}
	
//	Heroインスタンス作成時にrpgread.datファイルから読込、属性設定をするコンストラクタ
	public Hero() throws IOException{
		BufferedReader r = new BufferedReader(new FileReader
				("C:\\Users\\masahiko satoh\\Desktop\\rpgread.dat"));
		this.name = r.readLine();
		this.hp = r.readLine();
		this.mp = r.readLine();
		r.close();
//		hpとmpはString型として読み込まれ、加減算に用いる場合扱いづらい。
//		String → intのキャストも出来ず、どう後処理すればいいか。
	}
}
