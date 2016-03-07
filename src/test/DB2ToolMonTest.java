package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
import jp.co.withlinkage.naokit.db2.toolmon.csv.PkgCacheStmt;
import jp.co.withlinkage.naokit.db2.toolmon.csv.Workload;

public class DB2ToolMonTest {

	@Test
	public void test03() {
		PrintWriter writer = null;
		try {
			File base = new File("C:\\naoki\\IBM\\project\\FTS\\P問題2015年末");
			DB2ToolMon t = new DB2ToolMon();
			DateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

			writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(base,"pkgCacheStmt.tsv"))));

			writer.print("TIMESTAMP");
			writer.print("\t");
			writer.print("TOTAL_ACT_TIME");
			writer.print("\t");
			writer.print("TOTAL_ACT_WAIT_TIME");
			writer.print("\t");
			writer.print("ROWS_MODIFIED");
			writer.print("\t");
			writer.print("ROWS_READ");
			writer.print("\t");
			writer.print("ROWS_RETURNED");
			writer.print("\t");
			writer.print("HIT_RATE");
			writer.print("\t");
			writer.print("NUM_EXECUTIONS");
			writer.print("\t");
			writer.print("EXECUTABLE_ID");
			writer.print("\t");
			writer.print("STMT_TEXT");
			writer.print("\t");
			writer.print("TIME_PER_EXECUTION");
			writer.print("\t");
			writer.print("LOCK_WAIT_TIME");
			writer.println();

//			File file = new File(base, "201602\\02\\20160205_000005\\20160205_000005.vmhost05.db2inst1.IRESDB.mon_get_pkg_cache_stmt.csv");
//			File file = new File(base, "201602\\02\\20160204_000002\\20160204_000002.vmhost05.db2inst1.IRESDB.mon_get_pkg_cache_stmt.csv");
//			File file = new File(base, "201601\\01\\20160107_000004\\20160107_000004.vmhost05.db2inst1.IRESDB.mon_get_pkg_cache_stmt.csv");
			File file = new File(base, "201601\\01\\20160108_000003\\20160108_000003.vmhost05.db2inst1.IRESDB.mon_get_pkg_cache_stmt.csv");

			for(PkgCacheStmt p : t.getDailyPkgCacheStmt(file).values()) {
				writer.print(fmt.format(p.getTimestamp()));
				writer.print("\t");
				writer.print(p.getTotalActTime());
				writer.print("\t");
				writer.print(p.getTotalActWaitTime());
				writer.print("\t");
				writer.print(p.getRowsModified());
				writer.print("\t");
				writer.print(p.getRowsRead());
				writer.print("\t");
				writer.print(p.getRowsReturned());
				writer.print("\t");
				writer.print(((double)p.getRowsReturned()) / ((double)p.getRowsRead()));
				writer.print("\t");
				writer.print(p.getNumExecution());
				writer.print("\t");
				writer.print(p.getId());
				writer.print("\t");
				writer.print(p.getStmtText().replaceAll("\n", " "));
				writer.print("\t");
//				writer.print(p.getNumExecution()>0 ?(((double)p.getTotalActTime())/((double)p.getNumExecution())) : 0);
				writer.print(((double)p.getTotalActTime())/((double)p.getNumExecution()));
				writer.print("\t");
				writer.print(p.getLockWaitTime());
				writer.println();
			}

		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		} finally {
			try { writer.close(); } catch(Exception e) {}
		}
	}

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
