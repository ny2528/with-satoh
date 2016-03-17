package jp.co.withlinkage.sato.s5_9;

public class Account {
	private String accountNo;
	private int balance;
	private AccountType accountType;

	public Account(String aNo, AccountType aType){
		this.accountNo = aNo;
		this.accountType = aType;
	}
}
