package jp.co.withlinkage.sato.s5_1;

public class Main {
	public static void prints(Object a, Object b){
		for(int i = 0; i < (Integer) b; i++){
			System.out.println(a);
		}
	}
	
	public static void main(String[] args){
		Object s = "こんにちは";
		// s = new Hero();
		Object n = 3;
		prints(s, n);
	}
}
