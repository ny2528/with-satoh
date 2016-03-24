package jp.co.withlinkage.sato.s5_11;

class Outer {
	int outerField;
	static int outerStaticField;
	static class Inner{
		void innerMethod(){
			outerStaticField = 10;
		}
	}
	void outerMethod(){
		Inner ic = new Inner();
	}
}

public class Main {
	public static void main(String[] args){
		Outer.Inner ic = new Outer.Inner();
	}
}