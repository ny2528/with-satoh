package jp.co.withlinkage.sato.s09_01;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		FileWriter fw = new FileWriter
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat",false);
		
		fw.write("ABCDEFGHIJK");
		fw.flush();
		fw.close();
	}
}
