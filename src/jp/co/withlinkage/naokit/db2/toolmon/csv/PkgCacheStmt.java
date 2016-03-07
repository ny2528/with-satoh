package jp.co.withlinkage.naokit.db2.toolmon.csv;

import java.util.Date;

import jp.co.withlinkage.naokit.common.io.CsvCol;
import jp.co.withlinkage.naokit.common.io.CsvColFormat;
import jp.co.withlinkage.naokit.common.io.CsvColLabel;
import jp.co.withlinkage.naokit.common.io.CsvFile;

@CsvFile(hasHeader=true)
public class PkgCacheStmt {

	@CsvCol(1)
	@CsvColLabel("TIMESTAMP")
	@CsvColFormat("yyyy-MM-dd HH:mm:ss.SSS")
	private Date timestamp;
	@CsvCol(5)
	@CsvColLabel("EXECUTABLE_ID")
	private String id;
	@CsvCol(11)
	@CsvColLabel("NUM_EXECUTIONS")
	private long numExecution;
	@CsvCol(14)
	@CsvColLabel("TOTAL_ACT_TIME")
	private long totalActTime;
	@CsvCol(15)
	@CsvColLabel("TOTAL_ACT_WAIT_TIME")
	private long totalActWaitTime;
	@CsvCol(21)
	@CsvColLabel("LOCK_WAIT_TIME")
	private long lockWaitTime;
	@CsvCol(27)
	@CsvColLabel("ROWS_MODIFIED")
	private long rowsModified;
	@CsvCol(28)
	@CsvColLabel("ROWS_READ")
	private long rowsRead;
	@CsvCol(29)
	@CsvColLabel("ROWS_RETURNED")
	private long rowsReturned;
	@CsvCol(83)
	@CsvColLabel("STMT_TEXT")
	private String stmtText;

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
	 * @return id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return numExecution
	 */
	public final long getNumExecution() {
		return numExecution;
	}

	/**
	 * @param numExecution セットする numExecution
	 */
	public final void setNumExecution(long numExecution) {
		this.numExecution = numExecution;
	}

	/**
	 * @return totalActTime
	 */
	public final long getTotalActTime() {
		return totalActTime;
	}

	/**
	 * @param totalActTime セットする totalActTime
	 */
	public final void setTotalActTime(long totalActTime) {
		this.totalActTime = totalActTime;
	}

	/**
	 * @return totalActWaitTime
	 */
	public final long getTotalActWaitTime() {
		return totalActWaitTime;
	}

	/**
	 * @param totalActWaitTime セットする totalActWaitTime
	 */
	public final void setTotalActWaitTime(long totalActWaitTime) {
		this.totalActWaitTime = totalActWaitTime;
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
	 * @return stmtText
	 */
	public final String getStmtText() {
		return stmtText;
	}

	/**
	 * @param stmtText セットする stmtText
	 */
	public final void setStmtText(String stmtText) {
		this.stmtText = stmtText;
	}
}
