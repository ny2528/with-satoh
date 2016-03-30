package jp.co.withlinkage.sato.s10_01;
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
	public void saveHeroToFile(Hero h) throws IOException {
		Writer w = new BufferedWriter(new FileWriter
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat"));
		w.write(h.name + "\n");
		w.write(h.hp + "\n");
		w.write(h.mp + "\n");
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
//		hpとmpはString型として読み込まれ、加減算に用いる場合扱い。
//		String → intのキャストも出来ず、どう後処理すればいいか。
	}
}
