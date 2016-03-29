package jp.co.withlinkage.sato.s12_01;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		System.out.println("なにか入力してください");
		new Scanner(System.in).nextLine();
		for(int i = 0; i < 10; i++){
			System.out.print(i);
		}
	}
}
