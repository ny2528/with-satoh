package jp.co.withlinkage.naokit.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

public class IOUtil {
	private static final int BUF_LEN;
	static {
		ResourceBundle rb = null;
		try { rb = ResourceBundle.getBundle(IOUtil.class.getSimpleName()); } catch (Exception ignore) {}
		int i = -1;
		try { i = Integer.parseInt(rb.getString("buffer.size")); } catch (Exception ignore) {}
		if(i <= 0) i = 2048;
		BUF_LEN = i;
	}

	public static long copy(InputStream in, OutputStream out, int buffSize) throws IOException {
		byte[] buf = new byte[buffSize];
		long result = 0;
		int len = -1;
		while((len = in.read(buf, 0, buffSize)) >= 0) {
			out.write(buf, 0, len);
			result += len;
		}
		return result;
	}
	
	public static long copy(InputStream in, OutputStream out) throws IOException {
		return copy(in, out, BUF_LEN);
	}

	public static byte[] readAll(InputStream in, int buffSize) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out, buffSize);
		return out.toByteArray();
	}

	public static byte[] readAll(InputStream in) throws IOException {
		return readAll(in, BUF_LEN);
	}

	public static String readString(InputStream in, int buffSize, String charSet) throws IOException {
		byte[] b = readAll(in, buffSize);
		return charSet == null ? new String(b) : new String(b, charSet);
	}
	
	public static String readString(InputStream in, int buffSize) throws IOException {
		return readString(in, buffSize, null);
	}

	public static String readString(InputStream in, String charSet) throws IOException {
		return readString(in, BUF_LEN, charSet);
	}

	public static String readString(InputStream in) throws IOException {
		return readString(in, BUF_LEN, null);
	}
	
	public static boolean contain(InputStream in, byte[] target, int buffSize) throws IOException {
		byte[] buf = new byte[buffSize];
		int len = 0;
		int pos = 0;
		while((len = in.read(buf, 0, buffSize)) >= 0) {
			for(int i=0; i<len; i++) {
				if(buf[i] == target[pos]) {
					if(++pos == target.length)
						return true;
				} else if(pos != 0) {
					pos = buf[i] == buf[0] ? 1 : 0;
				}
			}
		}
		return false;
	}
	public static boolean contain(InputStream in, byte[] target) throws IOException {
		return contain(in, target, BUF_LEN);
	}
	
	public static boolean compare(InputStream in1, InputStream in2, int buffSize) throws IOException {
		byte[] buf1 = new byte[buffSize];
		byte[] buf2 = new byte[buffSize];
		int len1 = 0;
		int len2 = 0;
		while((len1 = in1.read(buf1, 0, buffSize)) >= 0) {
			len2 =  in2.read(buf2, 0, buffSize);
			if(len1 != len2) 
				return false;
			for(int i=0; i<len1; i++)
				if(buf1[i] != buf2[i])
					return false;
		}
		return ((len2 = in2.read(buf2, 0, buffSize)) < 0);
	}
	
	public static boolean compare(InputStream in1, InputStream in2) throws IOException {
		return compare(in1, in2, BUF_LEN);
	}

}
