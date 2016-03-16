package jp.co.withlinkage.sato.s4_6;


public class Account implements Comparable<Account>{
	int accountNo;
	public Account(int accountNo){
		this.accountNo = accountNo;
	}
	
	public int compareTo(Account obj){
		if(this.accountNo < obj.accountNo){
			return -1;
		}
		if(this.accountNo > obj.accountNo){
			return 1;
		}
		return 0;
	}
}
