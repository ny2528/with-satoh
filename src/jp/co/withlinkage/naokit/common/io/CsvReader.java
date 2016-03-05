package jp.co.withlinkage.naokit.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

	/** default delimiter */
	public static final char DEFAULT_DELIMITER = ',';
	/** default quote */
	public static final char DEFAULT_QUOTER = '"';
	/** default escape char */
	public static final char DEFAULT_ESCAPE = '\\';

	private final BufferedReader _reader;

	private final char _delimiter;
	private final char _quoter;
	private final char _escape;
	/** columns buffer */
	private char[] _buf = new char[1024];
	boolean _open = true;

	/**
	 * @param reader reader
	 * @param delimiter delimiter
	 * @param quoter quote
	 * @param escape escape char
	 */
	public CsvReader(BufferedReader reader, char delimiter, char quoter, char escape) {
		super();
		this._reader = reader;
		this._delimiter = delimiter;
		this._quoter = quoter;
		this._escape = escape;
	}
	/**
	 * @param reader reader
	 * @param delimiter delimiter
	 */
	public CsvReader(BufferedReader reader, char delimiter) {
		this(reader, delimiter, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param reader reader
	 * @param delimiter delimiter
	 */
	public CsvReader(BufferedReader reader) {
		this(reader, DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param file file to read
	 * @throws IOException
	 */
	public CsvReader(File file) throws IOException {
		this(new BufferedReader(new FileReader(file)), DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param file file to read
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public CsvReader(File file, String charset) throws IOException {
		this(new BufferedReader(
				new InputStreamReader(new FileInputStream(file), charset)),
				DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}


	/**
	 * @return columnms, separated line
	 * @throws IOException
	 */
	public List<String> readLine() throws IOException {
		if(!_open)
			return null;

		List<String> cols = new ArrayList<>();
		String line = null;
		boolean quoted = false;
		int p = 0;
		while((_open = (line = _reader.readLine()) != null)) {
			char c = 0x00;
			try {
				for(int i=0; i<line.length(); i++) {
					c = line.charAt(i);
					if(c == _delimiter) {
						if(!quoted) {
							cols.add(new String(_buf, 0, p));
							p = 0;
						}
					} else if(c == _quoter) {
						quoted = !quoted;
					} else if(c == _escape) {
						_buf[p++] = line.charAt(++i);
					} else {
						_buf[p++] = c;
					}
				}
			} catch(IndexOutOfBoundsException e) {
				int len = _buf.length;
				char[] tmp = new char[len << 1];
				System.arraycopy(_buf, 0, tmp, 0, len);
				tmp[len] = c;
				_buf = tmp;
			}

			if(quoted) continue;

			cols.add(new String(_buf, 0, p));
			return cols;
		}

		return cols;
	}

	/**
	 * close reader
	 * @see BufferedReader
	 * @throws IOException
	 */
	public void close() throws IOException {
		_reader.close();
	}
}
