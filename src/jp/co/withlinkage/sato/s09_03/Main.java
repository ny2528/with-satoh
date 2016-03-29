package jp.co.withlinkage.sato.s09_03;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException{
		FileOutputStream fos = new FileOutputStream
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.dat", true);
		fos.write(65);
		fos.flush();
		fos.close();
	}
}
