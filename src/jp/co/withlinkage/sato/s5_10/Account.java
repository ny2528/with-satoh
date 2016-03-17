package jp.co.withlinkage.sato.s5_10;

public class Account {
	private String accountNo;
	private int balance;
	private String accountType;
	
	// コンストラクタ
	public Account(String aNo, String aType){
		this.accountNo = aNo;
		this.accountType = aType;
	}
}
