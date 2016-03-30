package jp.co.withlinkage.sato.s10_05;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Properties;

public class Main {
	public static void main(String[] args) throws Exception {
		Writer fw = new FileWriter
				("C:\\Users\\masahiko satoh\\Desktop\\rpgsave.properties");
		Properties p = new Properties();
		p.setProperty("heroName", "アサカ");
		p.setProperty("heroHp", "62");
		p.setProperty("heroMp", "45");
		p.store(fw, "Infomations of Hero");
		fw.close();
	}
}
