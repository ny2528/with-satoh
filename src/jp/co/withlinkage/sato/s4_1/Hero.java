package jp.co.withlinkage.sato.s4_1;

public class Hero {
	private String name;
	private int hp = 50;
	private int mp = 10;
	/* public String toString(){
		return "勇者（名前=" + this.name + "/HP" + this.hp + "/MP=" + this.mp + "）";
	}*/
	public void setName(String name){
		this.name = name;
	}
}
