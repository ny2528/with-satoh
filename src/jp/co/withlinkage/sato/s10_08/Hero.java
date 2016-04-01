package jp.co.withlinkage.sato.s10_08;
// 直接化するためにjava.io.Serializableを実装する
import java.io.Serializable;

public class Hero implements Serializable {
	private String name;
	private int hp;
	private int mp;

	//Heroコンストラクタ
	public Hero(String name, int hp, int mp){
		this.name = name;
		this.hp = hp;
		this.mp = mp;
	}

	//getterメソッド
	public String getName(){ return this.name; }
	public int getHp(){ return this.hp; }
	public int getMp(){ return this.mp; }
}
