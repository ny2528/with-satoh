package jp.co.withlinkage.sato;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
	public List<If1> list2() {
		List<If1> list = new ArrayList<If1>();
		for(int i=0; i<5; i++) {
			Class2 t = new Class2();
			t.setName("name #" + i);
			list.add(t);
		}
		return list;
	}
	public List<If1> list4() {
		List<If1> list = new ArrayList<If1>();
		for(int i=0; i<7; i++) {
			list.add(new Class4());
		}
		return list;
	}


	public List<If1> list5() {
		List<If1> list = new ArrayList<If1>();
		int i=0;
		for(; i<5; i++) {
			Class2 t = new Class2();
			t.setName("name #" + i);
			list.add(t);
		}
		for(; i<10; i++) {
			list.add(new Class4());
		}
		for(; i<15; i++) {
			Class2 t = new Class2();
			t.setName("name #" + i);
			list.add(t);
		}
		return list;
	}
}
