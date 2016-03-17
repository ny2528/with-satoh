package jp.co.withlinkage.sato.s5_8;

public class Account {
	private String accountNo;
	private int balance;
	private int accountType;

	// コンストラクタ
	public Account(String aNo, int aType){
		this.accountNo = aNo;
		this.accountType = aType;
	}

	public static final int FUTSU = 1;
	public static final int TOUZA = 2;
	public static final int TEIKI = 3;
}
