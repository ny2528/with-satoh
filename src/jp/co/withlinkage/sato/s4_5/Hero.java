package jp.co.withlinkage.sato.s4_5;

public class Hero {
	public String name;
	public int hp;
	public int hashCode(){
		int result = 37;
		result = result * 31 + name.hashCode();
		result = result * 31 + hp;
		return result;
	}


	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		if(!(o instanceof Hero)) return false;
		Hero h = (Hero) o;
		if(!this.name.trim().equals(h.name.trim())){
			return false;
		}
		return true;
	}
}
