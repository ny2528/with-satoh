package jp.co.withlinkage.sato.k10_02;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	public static void main(String[] args) throws Exception {
		Employee e = new Employee("田中太郎",41);
		Department d = new Department("総務部");
		d.leader = e;
		//インスタンスの直列化と保存
		FileOutputStream fos = new FileOutputStream
				("C:\\Users\\masahiko satoh\\Desktop\\company.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(d);
		oos.flush();
		oos.close();
		//②ファイルからインスタンスを復元
		FileInputStream fis = new FileInputStream
				("C:\\Users\\masahiko satoh\\Desktop\\company.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Department d2 = (Department) ois.readObject();
		ois.close();
		System.out.println
		(d2.leader.name + "(" +d2.leader.age + ")\n" + "所属：" + d2.name);
	}
}
