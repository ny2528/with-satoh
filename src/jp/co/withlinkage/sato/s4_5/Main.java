package jp.co.withlinkage.sato.s4_5;

import java.util.HashSet;
import java.util.Set;

public class Main {
	public static void main(String[] args){
		Set<Hero> list = new HashSet<Hero>();
		Hero h1 = new Hero();
		h1.name = "ミナト";
		h1.hp = 50;
		list.add(h1);
		System.out.println("要素数=" + list.size());
		System.out.println(h1.hashCode());
		h1 = new Hero();
		h1.name = "ミナト";
		h1.hp = 50;
		list.remove(h1);
		System.out.println("要素数=" + list.size());
		System.out.println(h1.hashCode());
	}
}
