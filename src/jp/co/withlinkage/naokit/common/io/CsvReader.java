package jp.co.withlinkage.naokit.common.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV reader
 *
 * @author naoky@with-linkage.co.jp
 */
public class CsvReader {

	/*
	 * constants.
	 */
	/** default delimiter */
	public static final char DEFAULT_DELIMITER = ',';
	/** default quote */
	public static final char DEFAULT_QUOTER = '"';
	/** default escape char */
	public static final char DEFAULT_ESCAPE = '\\';

	/*
	 * private fields.
	 */
	/** reader */
	private final BufferedReader _reader;
	/** delimiter char */
	private final char _delimiter;
	/** quote char */
	private final char _quoter;
	/** escape char */
	private final char _escape;
	/** columns buffer */
	private char[] _buf = new char[1024];
	/** stream is open? */
	boolean _open = true;

	/*
	 * Constructors.
	 */
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
	 * @param in input stream reader
	 * @param delimiter delimiter
	 * @param quoter quote
	 * @param escape escape char
	 * @throws IOException
	 */
	public CsvReader(InputStreamReader in, char delimiter, char quoter, char escape) throws IOException {
		this(new BufferedReader(in), delimiter, quoter, escape);
	}
	/**
	 * @param in input stream reader
	 * @throws IOException
	 */
	public CsvReader(InputStreamReader in) throws IOException {
		this(in, DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param file file to read
	 * @param charset char set of file
	 * @param delimiter delimiter
	 * @param quoter quote
	 * @param escape escape char
	 * @throws IOException
	 */
	public CsvReader(File file, String charset, char delimiter, char quoter, char escape) throws IOException {
		this(new InputStreamReader(new FileInputStream(file), charset),
				delimiter, quoter, escape);
	}
	/**
	 * @param file file to read
	 * @param charset char set of file
	 * @param delimiter delimiter
	 * @throws IOException
	 */
	public CsvReader(File file, String charset, char delimiter) throws IOException {
		this(file, charset, delimiter, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param file file to read
	 * @param charset char set of file
	 * @throws IOException
	 */
	public CsvReader(File file, String charset) throws IOException {
		this(file, charset, DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}
	/**
	 * @param file file to read
	 * @throws IOException
	 */
	public CsvReader(File file) throws IOException {
		this(new BufferedReader(new FileReader(file)), DEFAULT_DELIMITER, DEFAULT_QUOTER, DEFAULT_ESCAPE);
	}

	/*
	 * Methods.
	 */
	/**
	 * read line.
	 * @return columnms, separated line
	 * @throws IOException
	 */
	public List<String> readLine() throws IOException {
		if(!_open) return null;

		String line = _reader.readLine();
		_open = line != null;
		if(!_open) return null;


		List<String> cols = new ArrayList<>();
		boolean inQuote = false;
		boolean quoted = false;
		int p = 0;
		char c = 0x00;
		do {
			for(int i=0; i<line.length(); i++) {
				c = line.charAt(i);
				try {
					if(c == _delimiter) {
						if(!inQuote) {
							cols.add(p>0 ? new String(_buf, 0, p) : quoted ? "" : null);
							p = 0;
							quoted = false;
						} else {
							_buf[p++] = c;
						}
					} else if(c == _quoter) {
						inQuote = !inQuote;
						quoted = true;
					} else if(c == _escape) {
						if(++i < line.length())
							_buf[p++] = line.charAt(i);
					} else {
						_buf[p++] = c;
					}
				} catch(IndexOutOfBoundsException e) {
					p = resize();
					_buf[p++] = c;
				}
			}
			// last
			if(!inQuote) {
				cols.add(p>0 ? new String(_buf, 0, p) : quoted ? "" : null);
				return cols;
			}

			// if in quote to add new line char and continue.
			if(p >= _buf.length)
				p = resize();
			_buf[p++] = '\n';
			_open = (line = _reader.readLine()) != null;
		} while(_open);

		// in quote but end of file..
		cols.add(p>0 ? new String(_buf, 0, p) : quoted ? "" : null);
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
	/*
	 * private methods.
	 */
	/**
	 * resize _buf
	 * @return previous _buf length
	 */
	private final int resize() {
		int len = _buf.length;
		char[] tmp = new char[len << 1];
		System.arraycopy(_buf, 0, tmp, 0, len);
		_buf = tmp;
		return len;
	}
}
