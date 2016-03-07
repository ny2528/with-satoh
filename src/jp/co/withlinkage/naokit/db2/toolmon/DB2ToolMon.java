package jp.co.withlinkage.naokit.db2.toolmon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.withlinkage.naokit.common.io.CsvReaderEx;
import jp.co.withlinkage.naokit.common.util.FileUtil;
import jp.co.withlinkage.naokit.db2.toolmon.csv.PkgCacheStmt;
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

	public Map<String, PkgCacheStmt> getDailyPkgCacheStmt(File csv) throws IOException {
		Map<String, PkgCacheStmt> min = new HashMap<>();
		Map<String, PkgCacheStmt> max = new HashMap<>();
		getDailyPkgCacheStmt(csv, min, max);
		return mergePkgCacheStmt(min, max);
	}

	private Map<String, PkgCacheStmt> mergePkgCacheStmt(Map<String, PkgCacheStmt> min, Map<String, PkgCacheStmt> max) {
		Map<String, PkgCacheStmt> result = new HashMap<>();
		for(String id : min.keySet()) {
			PkgCacheStmt p1 = min.get(id);
			PkgCacheStmt p2 = max.get(id);
			PkgCacheStmt p3 = new PkgCacheStmt();

			p3.setId(id);
			p3.setLockWaitTime(p2.getLockWaitTime() - p1.getLockWaitTime());
//			p3.setLockWaitTime(p1.getLockWaitTime());
			p3.setNumExecution(p2.getNumExecution() - p1.getNumExecution());
			p3.setRowsModified(p2.getRowsModified() - p1.getRowsModified());
			p3.setRowsRead(p2.getRowsRead() - p1.getRowsRead());
			p3.setRowsReturned(p2.getRowsReturned() - p1.getRowsReturned());
			p3.setStmtText(p1.getStmtText());
			p3.setTimestamp(p1.getTimestamp());
			p3.setTotalActTime(p2.getTotalActTime() - p1.getTotalActTime());
			p3.setTotalActWaitTime(p2.getTotalActWaitTime() - p1.getTotalActWaitTime());

			result.put(id, p3);
		}

		return result;
	}
	private void getDailyPkgCacheStmt(File csv, Map<String, PkgCacheStmt> min, Map<String, PkgCacheStmt> max) throws IOException {
		CsvReaderEx<PkgCacheStmt> reader = null;

		try {
			reader = new CsvReaderEx<>(PkgCacheStmt.class, csv);
			PkgCacheStmt p = null;
			while((p = reader.readLine()) != null) {
				String id = p.getId();
				if(!min.containsKey(id))
					min.put(id, p);
				max.put(id, p);
			}
		} finally {
			try { reader.close(); } catch(Exception ignore) {}
		}
	}
}
