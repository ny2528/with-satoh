package test;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import jp.co.withlinkage.naokit.db2.toolmon.DB2ToolMon;
import jp.co.withlinkage.naokit.db2.toolmon.csv.Workload;

public class DB2ToolMonTest {

	@Test
	public void test02() {
		try {
			File base = new File("C:\\naoki\\IBM\\project\\FTS\\P問題2015年末");
			List<String> subdir = new ArrayList<>();
			subdir.add("201511/11");
			subdir.add("201512");
			subdir.add("201601/01");
			subdir.add("201602/02");
			subdir.add("20160306/03");

			DB2ToolMon t = new DB2ToolMon();
			DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			NumberFormat fmtd = new DecimalFormat("0.000000");

			for(String sub : subdir) {
				File dir = new File(base, sub);
				for(Workload w : t.getDailyWorkloads(dir)) {
					System.out.print(fmt.format(w.getTimestamp()));
					System.out.print("\t");
					System.out.print(w.getTotalWaitTime());
					System.out.print("\t");
					System.out.print(w.getRowsModified());
					System.out.print("\t");
					System.out.print(w.getRowsRead());
					System.out.print("\t");
					System.out.print(w.getRowsReturned());
					System.out.print("\t");
					System.out.print(fmtd.format(((double)w.getRowsReturned()) / ((double)w.getRowsRead())));
					System.out.print("\t");
					System.out.print(w.getLockWaitTime());
					System.out.println();
				}
			}


		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Test
	public void test01() {
		try {
			File base = new File("C:\\naoki\\IBM\\project\\FTS\\P問題2015年末");
			DB2ToolMon t = new DB2ToolMon();
			DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
			for(Workload w : t.getDailyWorkloads(new File(base, "201511/11"))) {
				System.out.print(fmt.format(w.getTimestamp()));
				System.out.print("\t");
				System.out.print(w.getTotalWaitTime());
				System.out.print("\t");
				System.out.print(w.getRowsModified());
				System.out.print("\t");
				System.out.print(w.getRowsRead());
				System.out.print("\t");
				System.out.print(w.getRowsReturned());
				System.out.print("\t");
				System.out.print(((double)w.getRowsReturned()) / ((double)w.getRowsRead()));
				System.out.println();
			}

		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Test
	public void test00() {
		try {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			System.out.println(fmt.parse("2016-01-01 15:34:59.119"));

			DateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			System.out.println(fmt2.parse("2016-01-01 15:34:59.119000"));
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
