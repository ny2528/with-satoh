package jp.co.withlinkage.naokit.db2.toolmon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.withlinkage.naokit.common.io.CsvReaderEx;
import jp.co.withlinkage.naokit.common.util.FileUtil;
import jp.co.withlinkage.naokit.db2.toolmon.csv.Workload;

public class DB2ToolMon {

	public List<Workload> getDailyWorkloads(File dir) throws IOException {
		List<Workload> list = new ArrayList<>();
		for(File f : FileUtil.find(dir, "*\\*.mon_get_workload.csv"))
			list.add(getDailyWorkload(f));
		return list;
	}

	public Workload getDailyWorkload(File csv) throws IOException {
		CsvReaderEx<Workload> reader = null;
		final String name = "SYSDEFAULTUSERWORKLOAD";
		try {
			reader = new CsvReaderEx<>(Workload.class, csv);
			Workload min = null;
			while((min = reader.readLine()) != null) {
				if(name.equals(min.getName()))
					break;
			}
			Workload max = null;
			Workload w = null;
			while((w = reader.readLine()) != null)
				if(name.equals(w.getName()))
					max = w;

			if(max == null)
				return min;

			w = new Workload();
			w.setName(name);
			w.setTimestamp(min.getTimestamp());
			w.setTotalWaitTime(max.getTotalWaitTime() - min.getTotalWaitTime());
			w.setRowsModified(max.getRowsModified() - min.getRowsModified());
			w.setRowsRead(max.getRowsRead() - min.getRowsRead());
			w.setRowsReturned(max.getRowsReturned() - min.getRowsReturned());
			w.setLockWaitTime(max.getLockWaitTime() - min.getLockWaitTime());
			return w;
		} finally {
			try { reader.close(); } catch(Exception ignore) {}
		}

	}
}
