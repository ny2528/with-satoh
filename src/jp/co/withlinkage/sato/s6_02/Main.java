package jp.co.withlinkage.sato.s6_02;
import java.util.function.IntBinaryOperator;

public class Main {
	public static int sub(int a, int b){
		return a - b;
	}
	public static void main(String[] args){
		IntBinaryOperator func = Main::sub;
		
		int c = func.applyAsInt(5, 3);
		System.out.println("5-3は" + c);
	}
}
