package jp.co.withlinkage.naokit.db2.toolmon.csv;

import java.util.Date;

import jp.co.withlinkage.naokit.common.io.CsvCol;
import jp.co.withlinkage.naokit.common.io.CsvColFormat;
import jp.co.withlinkage.naokit.common.io.CsvColLabel;
import jp.co.withlinkage.naokit.common.io.CsvFile;

@CsvFile(hasHeader=true)
public class Workload {

	@CsvCol(1)
	@CsvColLabel("TIMESTAMP")
	@CsvColFormat("yyyy-MM-dd HH:mm:ss.SSS")
	private Date timestamp;
	@CsvCol(2)
	@CsvColLabel("WORKLOAD_NAME")
	private String name;
	@CsvCol(70)
	@CsvColLabel("TOTAL_WAIT_TIME")
	private long totalWaitTime;
	@CsvCol(56)
	@CsvColLabel("ROWS_MODIFIED")
	private long rowsModified;
	@CsvCol(57)
	@CsvColLabel("ROWS_READ")
	private long rowsRead;
	@CsvCol(58)
	@CsvColLabel("ROWS_RETURNED")
	private long rowsReturned;
	@CsvCol(49)
	@CsvColLabel("LOCK_WAIT_TIME")
	private long lockWaitTime;
	/**
	 * @return timestamp
	 */
	public final Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp セットする timestamp
	 */
	public final void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public final void setName(String name) {
		this.name = name;
	}
	/**
	 * @return totalWaitTime
	 */
	public final long getTotalWaitTime() {
		return totalWaitTime;
	}
	/**
	 * @param totalWaitTime セットする totalWaitTime
	 */
	public final void setTotalWaitTime(long totalWaitTime) {
		this.totalWaitTime = totalWaitTime;
	}
	/**
	 * @return rowsModified
	 */
	public final long getRowsModified() {
		return rowsModified;
	}
	/**
	 * @param rowsModified セットする rowsModified
	 */
	public final void setRowsModified(long rowsModified) {
		this.rowsModified = rowsModified;
	}
	/**
	 * @return rowsRead
	 */
	public final long getRowsRead() {
		return rowsRead;
	}
	/**
	 * @param rowsRead セットする rowsRead
	 */
	public final void setRowsRead(long rowsRead) {
		this.rowsRead = rowsRead;
	}
	/**
	 * @return rowsReturned
	 */
	public final long getRowsReturned() {
		return rowsReturned;
	}
	/**
	 * @param rowsReturned セットする rowsReturned
	 */
	public final void setRowsReturned(long rowsReturned) {
		this.rowsReturned = rowsReturned;
	}
	/**
	 * @return lockWaitTime
	 */
	public final long getLockWaitTime() {
		return lockWaitTime;
	}
	/**
	 * @param lockWaitTime セットする lockWaitTime
	 */
	public final void setLockWaitTime(long lockWaitTime) {
		this.lockWaitTime = lockWaitTime;
	}
}
