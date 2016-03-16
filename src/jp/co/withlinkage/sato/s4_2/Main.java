package jp.co.withlinkage.sato.s4_2;

public class Main {
	public static void main(String[] args){
		Account a1 = new Account("123");
		Account a2 = new Account("123");
		boolean b = a1.equals(a2);
		System.out.println(b);
	}
}
