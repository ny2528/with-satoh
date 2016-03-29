package jp.co.withlinkage.sato.s09_02;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		FileReader fw = new FileReader("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat");
		System.out.println("すべてのデータを読んで表示します");
		int i = fw.read();
		while(i != -1){
			char c = (char) i;
			System.out.print(c);
			i = fw.read();
		}
		System.out.println("ファイルの末尾に到達しました");
		fw.close();
	}
}
