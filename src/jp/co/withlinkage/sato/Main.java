package jp.co.withlinkage.sato;

import java.util.Date;
import java.util.List;

public class Main {

	private static String name = "Me";

	private Class2 _c;

	public Main() {
		super();
		_c = new Class2();
		_c.setName("properties name at " + new Date());
	}

	public static final void main(String[] args) {
//		Main m = new Main();
//		System.out.println(m.test2().getC2().getName());
//		Class3 c3 = m.test2();
//		Class2 c2 = c3.getC2();
//		String name = c2.getName();
//		System.out.println(name);
		ListTest lt = new ListTest();
//		List<If1> l = lt.list2();
		List<If1> l = lt.list5();

		for(int i=0; i<l.size(); i++) {
			If1 c = l.get(i);
			System.out.println(c.getName());
		}
//			System.out.println(l.get(i).getName());
	}


	public Class3 test2() {
		Class2 c2 = new Class2();
		c2.setName("test2");
		Class3 c3 = new Class3();
		c3.setC2(c2);
		return c3;
	}

	public String getHello() {
		return "Hellow world!" + this.name;
	}
}
