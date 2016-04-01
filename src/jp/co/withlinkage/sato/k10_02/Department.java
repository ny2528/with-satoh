package jp.co.withlinkage.sato.k10_02;
import java.io.Serializable;

public class Department implements Serializable{    //部署クラス
	String name;
	Employee leader;

	public Department(String name){
		this.name = name;
	}
}
