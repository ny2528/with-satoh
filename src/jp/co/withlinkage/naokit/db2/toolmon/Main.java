package jp.co.withlinkage.naokit.db2.toolmon;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import jp.co.withlinkage.naokit.common.io.CsvReaderEx;
import jp.co.withlinkage.naokit.db2.toolmon.csv.Workload;

public class Main {

	@Test
	public void executeWorkload() {
		try {
			File file = new File("./data/20151101_000006.vmhost05.db2inst1.IRESDB.mon_get_workload.csv");
			CsvReaderEx<Workload> reader = new CsvReaderEx<>(Workload.class, file);
			Workload bean = null;
			while((bean = reader.readLine()) != null) {
				System.out.print(bean.getTimestamp());
				System.out.print("\t");
				System.out.print(bean.getTotalWaitTime());
				System.out.println();
			}

		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
