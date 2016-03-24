package jp.co.withlinkage.sato.s14_01;

public class Account {
	String owner;     //口座名義人
	int balance;      //口座残高
	//引数を２つもつコンストラクタ
	public Account(String owner, int balance){
		this.owner = owner;
		this.balance = balance;
	}
	//送金を行うメソッド
	public void transfer(Account dest, int amount){
		dest.balance += amount;
		this.balance -= amount;
	}
}
