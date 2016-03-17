package jp.co.withlinkage.sato.s4_8;

public class Sword {
	String name;
	
	public Sword(String name) {
		this.name = name;
	}
	// getName(), setName()
	public String getName(){ return this.name; }
	public void setName(String name){ this.name = name; }
	
	public Sword clone(){
		Sword result = new Sword(this.name);   // this.nameを変更したらコピー先のnameも変わらないと思われる。
		return result;
	}
}