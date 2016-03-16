package jp.co.withlinkage.sato.s4_2;

public class Account {
	String accountNo;

	public boolean equals(Object o){
		if(o == this) return true;  // oが自分と等値ならtrueを返す
		if(o == null) return false; // oの中身がnullならfalseを返す
		// oがAccountクラスのインスタンスでなければfalseを返す
		if(!(o instanceof Account)) return false;
		Account r = (Account) o; // ObjectクラスのoをAccountクラスにキャスト化
		if(!this.accountNo.trim().equals(r.accountNo.trim())){
			return false;
		}
		return true;
	}
	public Account(String accountNo){
		this.accountNo = accountNo;
	}
}
