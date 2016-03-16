package jp.co.withlinkage.sato.s4_6;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	public static void main(String[] args){
		List<Account> list = new ArrayList<Account>();
		Account a1 = new Account(1234);
		list.add(a1.accountNo);
		Account a2 = new Account(1235);
		list.add(a2.accountNo);
		Collections.sort(list);
	}
}
