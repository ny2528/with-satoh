package jp.co.withlinkage.naokit.common.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Simple CSV writer
 *
 * @author naoky@with-linkage.co.jp
 */
public class CsvWriter implements CsvCommon {

	/*
	 * private fields.
	 */
	/** writer */
	private final BufferedWriter _writer;
	/** line separator */
	private final String _lineSeparator;
	/** columns to string line formatter */
	private final Formatter _formatter;

	/*
	 * Constructors.
	 */
	/**
	 * @param writer writer
	 * @param delimiter delimiter
	 * @param quote quote
	 * @param escape escape char
	 * @param lineSeparator
	 */
	public CsvWriter(BufferedWriter writer, final char delimiter, char quote, char escape, String lineSeparator) {
		super();
		this._writer = writer;
		this._lineSeparator = lineSeparator;
		final char _delimiter = delimiter;
		final char _quote = quote;
		final char _escape = escape;
		Formatter f = null;

		if(_quote == NULL_CHAR) {
			if(_escape == NULL_CHAR) {
				f = new Formatter() {
					@Override
					public String toLine(List<String> cols) {
						StringBuffer buf = new StringBuffer();
						for(String c : cols)
							buf.append(_quote).append(c);
						return buf.substring(1);
					}
				};
			} else {
				f = new Formatter() {
					char[] _buf = new char[2048];
					@Override
					public String toLine(List<String> cols) {
						int p = 0;
						for(String col : cols) {
							if(_buf.length <= p + (col.length() << 1) + 1) {
								int len = _buf.length << 1;
								while(len <=  p + (col.length() << 1) + 1)
									len <<= 1;
								char[] tmp = new char[len];
								System.arraycopy(_buf, 0, tmp, 0, p);
								_buf =tmp;
							}
							_buf[p++] = _delimiter;
							for(int i=0; i<col.length(); i++) {
								char c = col.charAt(i);
								if(c == _delimiter)
									_buf[p++] = _escape;
								_buf[p++] = c;
							}
						}
						return new String(_buf, 1, p);
					}
				};
			}
		} else {
			if(_escape == NULL_CHAR) {
				f = new Formatter() {
					char[] _buf = new char[2048];
					@Override
					public String toLine(List<String> cols) {
						int p = 0;
						for(String col : cols) {
							if(_buf.length <= p + col.length() + 3) {
								int len = _buf.length << 1;
								while(len <=  p + col.length() + 3)
									len <<= 1;
								char[] tmp = new char[len];
								System.arraycopy(_buf, 0, tmp, 0, p);
								_buf =tmp;
							}
							_buf[p++] = _delimiter;
							_buf[p++] = _quote;
							for(int i=0; i<col.length(); i++) {
								_buf[p++] = col.charAt(i);
							}
							_buf[p++] = _quote;
						}
						return new String(_buf, 1, p);
					}
				};
			} else {
				f = new Formatter() {
					char[] _buf = new char[2048];
					@Override
					public String toLine(List<String> cols) {
						int p = 0;
						for(String col : cols) {
							if(_buf.length <= p + (col.length() << 1) + 3) {
								int len = _buf.length << 1;
								while(len <=  p + (col.length() << 1) + 3)
									len <<= 1;
								char[] tmp = new char[len];
								System.arraycopy(_buf, 0, tmp, 0, p);
								_buf =tmp;
							}
							_buf[p++] = _delimiter;
							_buf[p++] = _quote;
							for(int i=0; i<col.length(); i++) {
								char c = col.charAt(i);
								if(c == _delimiter || c == _quote || c == _escape)
									_buf[p++] = _escape;
								_buf[p++] = c;
							}
							_buf[p++] = _quote;
						}
						return new String(_buf, 1, p);
					}
				};
			}
		}
		this._formatter = f;
	}
	/**
	 * @param writer writer
	 * @param delimiter delimiter
	 * @param lineSeparator
	 */
	public CsvWriter(BufferedWriter writer, char delimiter, String lineSeparator) {
		this(writer, delimiter, DEFAULT_QUOTATION_MARK, DEFAULT_ESCAPE_CHAR, lineSeparator);
	}
	/**
	 * @param writer writer
	 * @param delimiter delimiter
	 */
	public CsvWriter(BufferedWriter writer, char delimiter) {
		this(writer, delimiter, DEFAULT_QUOTATION_MARK, DEFAULT_ESCAPE_CHAR, DEFAULT_LINE_SEPARATOR);
	}
	/**
	 * @param writer
	 * @param lineSeparator
	 */
	public CsvWriter(BufferedWriter writer, String lineSeparator) {
		this(writer, DEFAULT_DELIMITER, DEFAULT_QUOTATION_MARK, DEFAULT_ESCAPE_CHAR, lineSeparator);
	}
	/**
	 * @param writer writer
	 */
	public CsvWriter(BufferedWriter writer) {
		this(writer, DEFAULT_DELIMITER, DEFAULT_QUOTATION_MARK, DEFAULT_ESCAPE_CHAR, DEFAULT_LINE_SEPARATOR);
	}

	/*
	 * Methods.
	 */
	/**
	 * write line. without line separator
	 * if you need to write header, use this method.
	 * @return columnms, separated line
	 * @throws IOException
	 */
	public void writeLine(String line) throws IOException {
		_writer.write(line);
		_writer.write(_lineSeparator);
	}
	/**
	 * read line.
	 * @return columnms, separated line
	 * @throws IOException
	 */
	public void writeLine(List<String> cols) throws IOException {
		writeLine(_formatter.toLine(cols));
	}
	/**
	 * close writer
	 * @see BufferedWriter
	 * @throws IOException
	 */
	public void close() throws IOException {
		_writer.close();
	}
	/*
	 * Methods.
	 */
	private static interface Formatter {
		String toLine(List<String> cols);
	}
}