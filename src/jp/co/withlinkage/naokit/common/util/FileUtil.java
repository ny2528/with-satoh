package jp.co.withlinkage.naokit.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author naoki
 *
 */
public class FileUtil {
	/* ----------------------------------------------------------------------
	 *  constants
	 * ---------------------------------------------------------------------- */
	/** default char set */
	public static final String DEFAULT_CHAR_SET;
	/* read / write byte buffer size */
	private static final int BUF_LEN;

	static {
		ResourceBundle rb = null;
		try { rb = ResourceBundle.getBundle(FileUtil.class.getSimpleName()); } catch (Exception ignore) {}

		String s =null;
		try { s = rb.getString("file.encoding"); } catch (Exception ignore) {}
		if(s == null)
			try { s = System.getProperty("file.encoding"); } catch (Exception ignore) {}
		if(s == null)
			s = "UTF-8";
		DEFAULT_CHAR_SET = s;
		/*
		 * buffer size
		 */
		int i = -1;
		try { i = Integer.parseInt(rb.getString("buffer.size")); } catch (Exception ignore) {}
		if(i <= 0) i = 2048;
		BUF_LEN = i;
	}
	/* ----------------------------------------------------------------------
	 *  methods
	 * ---------------------------------------------------------------------- */
	/* ********************************************************************** *
	 * Read / Write BASICS
	 * ********************************************************************** */
	/**
	 * read file
	 * @param file file to read
	 * @param charSet char set
	 * @return (String) content of file
	 * @throws IOException
	 */
	public static String read(File file, String charSet) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return IOUtil.readString(in, BUF_LEN, charSet);
		} finally {
			try { in.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * read file
	 * @param filename file name to read
	 * @param charSet char set
	 * @return (String) content of file
	 * @throws IOException
	 */
	public static String read(String filename, String charSet) throws IOException {
		return read(new File(filename), charSet);
	}
	/**
	 * read file. use default char set.
	 * @param filename file name to read
	 * @return (String) content of file
	 * @throws IOException
	 */
	public static String read(String filename) throws IOException {
		return read(filename, DEFAULT_CHAR_SET);
	}
	/**
	 * read file. use default char set.
	 * @param file to read
	 * @return (String) content of file
	 * @throws IOException
	 */
	public static String read(File file) throws IOException {
		return read(file, DEFAULT_CHAR_SET);
	}
	/**
	 * read file
	 * @param file file to read
	 * @return content bytes of file
	 * @throws IOException
	 */
	public static byte[] readByte(File file) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return IOUtil.readAll(in, BUF_LEN);
		} finally {
			try { in.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * write file.
	 * @param file file to write
	 * @param source content to write
	 * @param append when true, append content to file.
	 * @return byte size written
	 * @throws IOException
	 */
	public static int write(File file, byte[] source, boolean append) throws IOException {
		OutputStream out = null;
		try {
			final int len = source.length;
			out = new FileOutputStream(file, append);
			out.write(source, 0, len);
			return len;
		} finally {
			try { out.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * write file.
	 * @param file file to write
	 * @param source content to write
	 * @return byte size written
	 * @throws IOException
	 */
	public static int write(File file, byte[] source) throws IOException {
		return write(file, source, false);
	}
	/**
	 * write file.
	 * @param file file to write
	 * @param source content to write
	 * @param charSet char set
	 * @param append when true, append content to file.
	 * @return byte size written
	 * @throws IOException
	 */
	public static int write(File file, String source, String charSet, boolean append) throws IOException {
		return write(file, source.getBytes(charSet), append);
	}
	/**
	 * write file.
	 * @param file file to write
	 * @param source content to write
	 * @param charSet char set
	 * @return byte size written
	 * @throws IOException
	 */
	public static int write(File file, String source, String charSet) throws IOException {
		return write(file, source, charSet, false);
	}
	/**
	 * write file.
	 * @param file file to write
	 * @param source content to write
	 * @return byte size written
	 * @throws IOException
	 */
	public static int write(File file, String source) throws IOException {
		return write(file, source, DEFAULT_CHAR_SET);
	}
	/* ********************************************************************** *
	 * Find files in File System
	 * ********************************************************************** */
	private static final int _FILE_TYPE_ALL = 0;
	private static final int _FILE_TYPE_FILE = 1;
	private static final int _FILE_TYPE_DIR = 2;
	/**
	 * create regex file filter
	 * @param regex regex expession
	 * @param type {@link #_FILE_TYPE_DIR} , {@link #_FILE_TYPE_FILE}
	 * @return regex file filter
	 */
	private static FileFilter regexFileFilter(String regex, int type) {
		final Pattern pattern = Pattern.compile(regex);
		switch (type) {
		case _FILE_TYPE_FILE:
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && pattern.matcher(pathname.getName()).matches();
				}
			};
		case _FILE_TYPE_DIR:
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory() && pattern.matcher(pathname.getName()).matches();
				}
			};
		default:
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pattern.matcher(pathname.getName()).matches();
				}
			};
		}
	}
	private static final String wildcard2regex(String s) {
		//TODO other Regex reseved words...
		return s.replaceAll("\\.", "\\\\.")
				.replaceAll("\\?", "\\.")
				.replaceAll("\\*", "\\.\\*");
	}
	/**
	 * create wildcard file filter
	 * @param target ex.) suffix*.prefix
	 * @param type {@link #_FILE_TYPE_DIR} , {@link #_FILE_TYPE_FILE}
	 * @return wildcard file filter
	 */
	private static FileFilter wildcardFileFilter(String target, int type) {
		return regexFileFilter(wildcard2regex(target), type);
	}
	/**
	 * find files by name. ex) P*.java
	 * @param dir directory for search
	 * @param target target expression
	 * @return matched file list
	 */
	public static File[] find(File dir, String target) {
		return _find(dir, target, _FILE_TYPE_ALL);
	}
	/**
	 * find files by name. ex) P*.java
	 * @param dir directory for search
	 * @param target target expression
	 * @return matched file list
	 */
	public static File[] findFile(File dir, String target) {
		return _find(dir, target, _FILE_TYPE_FILE);
	}
	/**
	 * find files by name. ex) P*.java
	 * @param dir directory for search
	 * @param target target expression
	 * @return matched file list
	 */
	public static File[] findDirectory(File dir, String target) {
		return _find(dir, target, _FILE_TYPE_DIR);
	}
	/** separator for split */
	private static final String _SEPARATOR = File.separator.equals("\\")
											? "\\\\" : File.pathSeparator;
	private static File[] _find(File dir, String target, int type) {
		if(target.contains(File.separator))
			return _findEx(dir, target, type);

		File[] files = dir.listFiles(wildcardFileFilter(target, type));
		if(files != null && files.length > 0)
			Arrays.sort(files);
		return files;
	}
	private static final File[] _findEx(File dir, String target, int type) {
		List<File> matches = new ArrayList<File>();
		String[] targets = target.split(_SEPARATOR);
		FileFilter[] filters = new FileFilter[targets.length];
		int i = 0;
		for(; i<targets.length-1; i++)
			filters[i] = wildcardFileFilter(targets[i], _FILE_TYPE_DIR);
		filters[i] = wildcardFileFilter(targets[i], type);
		_findAppend(dir, filters, 0, matches);
		File[] files = matches.toArray(new File[matches.size()]);
		if(files.length <= 1)
			return files;
		Arrays.sort(files);
		return files;
	}
	private static final void _findAppend(File dir, FileFilter[] children, int pos, List<File> matches) {
		File[] files = dir.listFiles(children[pos++]);
		if(pos == children.length) {
 			for(File f : files)
				matches.add(f);
		} else {
 			for(File f : files)
				_findAppend(f, children, pos, matches);
		}
	}
	/**
	 * find reflectively.
	 * @param dir search target directory
	 * @param target target filename word(A*.xl*)
	 * @return list of files
	 */
	public static final File[] findR(File dir, String target) {
		return _findR(dir, target, _FILE_TYPE_ALL);
	}
	/**
	 * find file reflectively.
	 * @param dir search target directory
	 * @param target target filename word(A*.xl*)
	 * @return list of files
	 */
	public static final File[] findFileR(File dir, String target) {
		return _findR(dir, target, _FILE_TYPE_FILE);
	}
	/**
	 * find directory reflectively.
	 * @param dir search target directory
	 * @param target target filename word(A*.xl*)
	 * @return list of files
	 */
	public static final File[] findDirectoryR(File dir, String target) {
		return _findR(dir, target, _FILE_TYPE_FILE);
	}
	/**
	 * list reflectively.
	 * @param dir search target directory
	 * @return list of files
	 */
	public static final File[] listR(File dir) {
		return _findR(dir, "*", _FILE_TYPE_ALL);
	}
	/**
	 * list file reflectively.
	 * @param dir search target directory
	 * @return list of files
	 */
	public static final File[] listFileR(File dir) {
		return _findR(dir, "*", _FILE_TYPE_FILE);
	}
	/**
	 * list directory reflectively.
	 * @param dir search target directory
	 * @return list of files
	 */
	public static final File[] listDirectoryR(File dir) {
		return _findR(dir, "*", _FILE_TYPE_DIR);
	}

	private static final File[] _findR(File dir, String target, int type) {
		List<File> matches = new ArrayList<File>();
		if(target.contains(File.separator)) {
			String[] targets = target.split(_SEPARATOR);
			FileFilter[] children = new FileFilter[targets.length - 1];
			for(int i=0; i<children.length; i++)
				children[i] = wildcardFileFilter(targets[i], _FILE_TYPE_DIR);
			List<File> dirs = new ArrayList<File>();
			_findAppend(dir, children, 0, dirs);
			if(targets[targets.length - 1].equals("*")) {
				for(File d : dirs)
					_findAppendAll(d, matches, type);
			} else {
				RFileFilter filter = new RFileFilter(targets[targets.length - 1]);
				for(File d : dirs)
					_findAppend(d, filter, matches, type);
			}
		} else if(target.equals("*")) {
			_findAppendAll(dir, matches, type);
		} else {
			_findAppend(dir, new RFileFilter(target), matches, type);
		}
		File[] files = matches.toArray(new File[matches.size()]);
		if(files.length <= 1)
			return files;
		Arrays.sort(files);
		return files;
	}
	private static final class RFileFilter implements FileFilter {
		private final Pattern _pattern;
		public RFileFilter(String target) {
			super();
			_pattern = Pattern.compile(wildcard2regex(target));
		}
		@Override
		public boolean accept(final File pathname) {
			return pathname.isDirectory() || _pattern.matcher(pathname.getName()).find();
		}
		public final boolean matches(File file) {
			return _pattern.matcher(file.getName()).find();
		}
	}
	private static final void _findAppend(File dir, RFileFilter filter, List<File> matches, int type) {
		switch (type) {
		case _FILE_TYPE_DIR:
			for(File f : dir.listFiles(filter)) {
				if(filter.matches(f))
					matches.add(f);
				_findAppend(f, filter, matches, type);
			}
			break;
		case _FILE_TYPE_FILE:
			for(File f : dir.listFiles(filter)) {
				if(f.isFile())
					matches.add(f);
				_findAppend(f, filter, matches, type);
			}
			break;
		default:
			for(File f : dir.listFiles(filter)) {
				if(f.isFile()) {
					matches.add(f);
				} else {
					if(filter.matches(f))
						matches.add(f);
					if(f.isDirectory())
						_findAppend(f, filter, matches, type);
				}
			}
			break;
		}
	}
	private static final void _findAppendAll(File dir, List<File> matches, int type) {
		switch (type) {
		case _FILE_TYPE_DIR:
			for(File f : dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			})) {
				matches.add(f);
				_findAppendAll(f, matches, type);
			}
			break;
		case _FILE_TYPE_FILE:
			for(File f : dir.listFiles()) {
				if(f.isFile()) {
					matches.add(f);
				} else if(f.isDirectory()) {
					_findAppendAll(f, matches, type);
				}
			}
			break;
		default:
			for(File f : dir.listFiles()) {
				matches.add(f);
				if(f.isDirectory())
					_findAppendAll(f, matches, type);
			}
			break;
		}
	}

	/* ********************************************************************** *
	 * File operation
	 * ********************************************************************** */
	/**
	 * delete force
	 * @param dir directory(or file) to delete
	 * @return if delete is complete, return true.
	 */
	public static final boolean deleteF(File dir) {
		boolean b = true;
		for(File child : dir.listFiles())
			if(child.isDirectory())
				b &= deleteF(child);
			else
				b &= child.delete();
		return dir.delete() & b;
	}
	/**
	 * get directory size.
	 * @param dir target directory
	 * @return byte size
	 */
	public static final long size(File dir) {
		long size = dir.length();
		for(File child : dir.listFiles())
			size += size(child);
		return size;
	}
	private static long _copy(File from, File to) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(from);
			out = new FileOutputStream(to);
			return IOUtil.copy(in, out, BUF_LEN);
		} finally {
			try { in.close(); } catch(Exception ignore) {}
			try { out.close(); } catch(Exception ignore) {}
		}
	}
	private static long _copyDirectory(File from, File to) throws IOException {
		long size = 0L;
		to.mkdirs();
		for(File child : from.listFiles()) {
			File toChild = new File(to,child.getName());
			size += child.isDirectory()
					? _copyDirectory(child, toChild)
					: _copy(child, toChild);
		}
		return size;
	}
	/**
	 * copy file
	 * @param from
	 * @param to
	 * @return
	 * @throws IOException
	 */
	public static long copy(File from, File to) throws IOException {
		if(from.isDirectory()) {
			File ancestor = getAncestor(from, to);
			if(ancestor != null && (ancestor.equals(from) || ancestor.equals(to)))
				throw new IllegalArgumentException("cannot copy, directory be nested.. : " + from + " to " + to);
			return _copyDirectory(from, to);
		}
		return _copy(from, to);
	}
	/**
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static final File getAncestor(File file1, File file2) {
		Queue<File> que1 = new LinkedList<File>();
		Queue<File> que2 = new LinkedList<File>();

		try {
			for(File f=file1.getCanonicalFile(); f!=null; f=f.getParentFile())
				que1.add(f);
			for(File f=file2.getCanonicalFile(); f!=null; f=f.getParentFile())
				que2.add(f);
		} catch (IOException e) {
			return null;
		}

		while(que1.size() > que2.size())
			que1.poll();
		while(que2.size() > que1.size())
			que2.poll();
		while(true) {
			File f = que1.poll();
			if(f == null)
				return null;
			if(f.equals(que2.poll()))
				return f;
		}
	}
	/**
	 * count file
	 * @param dir target directory
	 * @return file(not directory) count. if argument is not directory, returns 1.
	 */
	public static final int countFile(File dir) {
		if(!dir.exists())
			return 0;
		if(!dir.isDirectory())
			return 1;
		int count = 0;
		for(File child : dir.listFiles())
			count += _countFile(child);
		return count;
	}
	private static final int _countFile(File file) {
		if(!file.isDirectory())
			return 1;
		int count = 0;
		for(File child : file.listFiles())
			count += _countFile(child);
		return count;
	}
	/**
	 * count directory
	 * @param dir target directory
	 * @return child directory count. include argument.
	 */
	public static final int countDirectory(File dir) {
		if(!dir.exists())
			return 0;
		if(!dir.isDirectory())
			return 0;
		int count = 0;
		for(File child : dir.listFiles())
			count += _countDir(child, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
		return count;
	}
	private static final int _countDir(File dir, FileFilter filter) {
		int count = 1;
		for(File child : dir.listFiles(filter))
			count += _countDir(child, filter);
		return count;
	}
	/* ********************************************************************** *
	 * File content
	 * ********************************************************************** */
	private static final byte _CR = 0x0a;
	private static final byte _LF = 0x0d;

	/**
	 * read file and separate lines.
	 * @param file file
	 * @param charSet char set
	 * @return list of String
	 * @throws IOException
	 */
	public static List<String> readLines(File file, String charSet) throws IOException {
		final byte[] b = readByte(file);
		List<String> lines = new ArrayList<String>();
		int pos = 0;
		for(int i=0; i<b.length; i++) {
			switch (b[i]) {
			case _CR:
				lines.add(new String(b, pos, i - pos, charSet));
				if(++i == b.length || b[i] != _LF) i--;
				pos = i + 1;
				break;
			case _LF:
				lines.add(new String(b, pos, i - pos, charSet));
				pos = i + 1;
			default:
				break;
			}
		}
		if(pos < b.length)
			lines.add(new String(b, pos, b.length - pos, charSet));
		return lines;
	}
	/**
	 * read file and separate lines.
	 * @param file file
	 * @return list of String
	 * @throws IOException
	 */
	public static List<String> readLines(File file) throws IOException {
		return readLines(file, DEFAULT_CHAR_SET);
	}
	/**
	 * trim CR of file content.
	 * @param file target file
	 * @return CR count
	 * @throws IOException
	 */
	public static int trimCR(File file) throws IOException {
		byte[] b = readByte(file);
		if(b.length == 0) return 0;
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int skip = 0;
			int pos = b[0] == _CR ? 1 : 0;
			for(int i=1; i<b.length; i++) {
				if(b[i] == _CR) {
					skip++;
					if(b[i-1]==_CR) {
						pos++;
					} else {
						out.write(b, pos, i-pos);
						pos = i + 1;
					}
				}
			}
			if(b[b.length-1] != _CR)
				out.write(b, pos, b.length-pos);
			return skip;
		} finally {
			try { out.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * compare file content.
	 * @param file1
	 * @param file2
	 * @return if same, return true.
	 * @throws IOException
	 */
	public static final boolean equalsContent(File file1, File file2) throws IOException {
		byte[] buf1 = new byte[BUF_LEN];
		byte[] buf2 = new byte[BUF_LEN];
		int len1 = 0;
		int len2 = 0;
		InputStream in1 = null;
		InputStream in2 = null;
		try {
			in1 = new FileInputStream(file1);
			in2 = new FileInputStream(file2);
			while((len1 = in1.read(buf1, 0, BUF_LEN)) >= 0) {
				len2 = in2.read(buf2, 0, BUF_LEN);
				if(len1 != len2)
					return false;
				int i = len1;
				while(--i >= 0)
					if(buf1[i] != buf2[i])
						return false;
			}
			if(in2.read(buf2) >= 0)
				return false;

			return true;
		} finally {
			try { in1.close(); } catch(Exception ignore) {}
			try { in2.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * contain
	 * @param file search file
	 * @param content search String
	 * @param charSet char set
	 * @return if <i>file</i> contain <i>content</i>, return true.
	 * @throws IOException
	 */
	public static final boolean contain(File file, String content, String charSet) throws IOException {
		final byte[] target = content.getBytes(charSet);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			return IOUtil.contain(in, target, BUF_LEN);
		} finally {
			try { in.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * contain
	 * @param file search file
	 * @param content search String
	 * @return if <i>file</i> contain <i>content</i>, return true.
	 * @throws IOException
	 */
	public static final boolean contain(File file, String content) throws IOException {
		return contain(file, content, DEFAULT_CHAR_SET);
	}
	/**
	 * matches
	 * @param file search file
	 * @param regex regex
	 * @param charSet char set
	 * @return if <i>file</i> contain <i>content</i>, return true.
	 * @throws IOException
	 */
	public static final boolean matches(File file, String regex, String charSet) throws IOException {
		Pattern pattern = Pattern.compile(regex);
		BufferedReader reader = null;
		try {
			reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(file),
						charSet
					),
					BUF_LEN
				);
			String line = null;
			while((line = reader.readLine()) != null) {
				if(pattern.matcher(line).find())
					return true;
			}
			return false;
		} finally {
			try { reader.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * matches
	 * @param file search file
	 * @param regex regex
	 * @return if <i>file</i> contain <i>content</i>, return true.
	 * @throws IOException
	 */
	public static final boolean matches(File file, String regex) throws IOException {
		return matches(file, regex, DEFAULT_CHAR_SET);
	}
	/**
	 * grep
	 * @param file search file
	 * @param regex regex
	 * @param charSet char set
	 * @return match words
	 * @throws IOException
	 */
	public static final List<String> grep(File file, String regex, String charSet) throws IOException {
		Pattern pattern = Pattern.compile(regex);
		BufferedReader reader = null;
		List<String> result = new ArrayList<String>();
		try {
			reader =
				new BufferedReader(
					new InputStreamReader(
						new FileInputStream(file),
						charSet
					),
					BUF_LEN
				);
			String line = null;
			while((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				while(matcher.find())
					result.add(matcher.group());
			}
			return result;
		} finally {
			try { reader.close(); } catch(Exception ignore) {}
		}

	}
	/**
	 * grep
	 * @param file search file
	 * @param regex regex
	 * @return match words
	 * @throws IOException
	 */
	public static final List<String> grep(File file, String regex) throws IOException {
		return grep(file, regex, DEFAULT_CHAR_SET);
	}
	/* ********************************************************************** *
	 * Properties
	 * ********************************************************************** */
	/**
	 * read properties file
	 * @param file file
	 * @param charSet char set
	 * @return {@link Properties}
	 * @throws IOException
	 */
	public static final Properties readProperties(File file, String charSet) throws IOException {
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file), charSet);
			Properties properties = new Properties();
			properties.load(reader);
			return properties;
		} finally {
			try { reader.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * read properties file. char set ISO-8859_1
	 * @param file file
	 * @return {@link Properties}
	 * @throws IOException
	 */
	public static final Properties readProperties(File file) throws IOException {
		return readProperties(file, "8859_1");
	}
	/**
	 * write properties
	 * @param file file
	 * @param properties properties
	 * @param charSet char set
	 * @param comments comments
	 * @throws IOException
	 */
	public static final void writeProperties(File file, Properties properties, String charSet, String comments) throws IOException {
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file), charSet);
			properties.store(writer, comments);
		} finally {
			try { writer.close(); } catch(Exception ignore) {}
		}
	}
	/**
	 * write properties. char set ISO-8859_1
	 * @param file file
	 * @param properties properties
	 * @throws IOException
	 */
	public static final void writeProperties(File file, Properties properties) throws IOException {
		writeProperties(file, properties, "8859_1", null);
	}
	/**
	 * add properties to properties file
	 * @param file file
	 * @param properties properties to add
	 * @param charSet char set
	 * @param comments comments
	 * @throws IOException
	 */
	public static final void addProperties(File file, Properties properties, String charSet, String comments) throws IOException {
		Properties current = null;
		try {
			current = readProperties(file, charSet);
		} catch (FileNotFoundException e) {
			current = new Properties();
		}
		current.putAll(properties);
		writeProperties(file, properties, charSet, comments);
	}
	/**
	 * add properties to properties file. char set ISO-8859_1
	 * @param file file
	 * @param properties properties to add
	 * @param comments comments
	 * @throws IOException
	 */
	public static final void addProperties(File file, Properties properties, String comments) throws IOException {
		addProperties(file, properties, "8859_1", comments);
	}
	/**
	 * add properties to properties file. char set ISO-8859_1
	 * @param file file
	 * @param properties properties to add
	 * @throws IOException
	 */
	public static final void addProperties(File file, Properties properties) throws IOException {
		addProperties(file, properties, null);
	}
}
