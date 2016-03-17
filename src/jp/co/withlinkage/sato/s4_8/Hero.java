package jp.co.withlinkage.sato.s4_8;

public class Hero implements Cloneable { // Cloneableインターフェースを実装する
	String name;
	int hp;
	Sword sword;

	public Hero(String name){
		this.name = name;
	}

	public void setSword(Sword s){
		this.sword = s;
	}

	public Sword getSword(){ return this.sword; }

	// Objectクラスで定義してあるcloneメソッドはprotectedのため外部から呼び出すためにpublicでオーバーライドする
	public Hero clone(){
		Hero result = new Hero(this.name);   // this.nameを変更したらコピー先のnameも変わらないと思われる。
		result.hp = this.hp;
		result.sword = this.sword.clone();
		return result;
	}
}