package jp.co.withlinkage.sato.s10_08;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	public static void main(String[] args) throws Exception {
		Hero hero1 = new Hero("ミナト", 75, 18);
		//①インスタンスの直列化と保存
		FileOutputStream fos = new FileOutputStream
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos); //シリアライズ処理を受入れる型
		oos.writeObject(hero1);   //インスタンス→バイト列
		oos.flush();
		oos.close();
		//②ファイルからインスタンスを復元
		FileInputStream fis = new FileInputStream
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Hero hero2 = (Hero) ois.readObject();  //バイト列→インタンス、Objectのためキャスト処理
		ois.close();
		
		System.out.println(hero2.getName());
		System.out.println(hero2.getHp());
		System.out.println(hero2.getMp());
	}
}
