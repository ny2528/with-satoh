package jp.co.withlinkage.sato.s07_05;

// Java Virtual Machineが利用できる残りのメモリ容量
public class Main {
	public static void main(String[] args){
		long f = Runtime.getRuntime().freeMemory()/1024/1024;
		System.out.println(f + "MB");
	}
}
