package jp.co.withlinkage.sato.k10_02;
import java.io.Serializable;

public class Employee implements Serializable{    //社員クラス
	String name;
	int age;
	
	//Employeeコンストラクタ
	public Employee(String name, int age){
		this.name = name;
		this.age = age;
	}
}
