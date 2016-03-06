package jp.co.withlinkage.naokit.common.io;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Type safe CSV reader.
 * @author naoky@with-linkage.co.jp
 * @param <T>
 */
public class CsvReaderEx<T> {
	/** type */
	private final Class<T> _clazz;
	/** simple csv reader */
	private final CsvReader _reader;
	/** setter to invoke */
	private final List<Setter> _setters = new ArrayList<>();

	/**
	 * @param clazz
	 * @param reader
	 */
	public CsvReaderEx(Class<T> clazz, CsvReader reader) {
		super();
		this._clazz = clazz;
		this._reader = reader;

		for(Field f : clazz.getDeclaredFields()) {
			Setter s = createSetter(clazz, f);
			if(s != null)
				_setters.add(s);
		}
	}

	public CsvReaderEx(Class<T> clazz, File file, String charset) throws IOException {
		this(clazz, new CsvReader(file, charset));
	}

	public CsvReaderEx(Class<T> clazz, File file) throws IOException {
		this(clazz, new CsvReader(file));
	}

	/**
	 * read line.
	 * @return line value as <T>
	 * @throws IOException
	 */
	public T readLine() throws IOException {
		List<String> cols = _reader.readLine();
		if(cols == null) return null;

		T t = null;
		try {
			t = (T)_clazz.newInstance();
			for(Setter setter : _setters) {
				setter.set(t, cols);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return t;
	}
	/**
	 * @see CsvReader#close()
	 * @throws IOException
	 */
	public void close() throws IOException {
		_reader.close();
	}

	/**
	 * Setter interface
	 */
	private static interface Setter {
		public void set(Object obj, List<String> list)
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	}
	/**
	 * create setter
	 * @param clazz class
	 * @param f field
	 * @return setter
	 */
	private static Setter createSetter(Class<?> clazz, Field f) {
		CsvCol a = f.getAnnotation(CsvCol.class);
		if(a == null)
			return null;

		PropertyDescriptor pd = null;
		try {
			pd = new PropertyDescriptor(f.getName(), clazz);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		final Class<?> t = f.getType();
		final Method m = pd.getWriteMethod();
		final int index = a.value() - 1;

		if(t.equals(String.class)) {
			return new Setter() {
				@Override
				public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
					m.invoke(obj, list.get(index));
				}
			};
		}

		CsvColFormat a2 = f.getAnnotation(CsvColFormat.class);
		final String format = a2 == null ? null : a2.value();

		if(t.equals(Date.class)) {
			return new Setter() {
				final DateFormat fmt = new SimpleDateFormat(format == null ? "yyyy/MM/dd" : format);
				@Override
				public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
					String s = list.get(index);
					try {
						m.invoke(obj, s == null ? null : fmt.parse(s));
					} catch (ParseException e) {
						throw new IllegalArgumentException(e);
					}
				}
			};
		}

		if(format == null) {
			if(t.equals(int.class) || t.equals(Integer.class)) {
				return new Setter() {
					@Override
					public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
						String s = list.get(index);
						m.invoke(obj, s == null ? null : Integer.parseInt(s));
					}
				};
			}
			if(t.equals(BigDecimal.class)) {
				return new Setter() {
					@Override
					public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
						String s = list.get(index);
						m.invoke(obj, s == null ? null : new BigDecimal(s));
					}
				};
			}
		} else {
			if(t.equals(int.class) || t.equals(Integer.class)) {
				final NumberFormat fmt = new DecimalFormat(format);
				fmt.setParseIntegerOnly(true);
				return new Setter() {
					@Override
					public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
						String s = list.get(index);
						try {
							m.invoke(obj, s == null ? null : fmt.parse(s).intValue());
						} catch (ParseException e) {
							throw new IllegalArgumentException(e);
						}
					}
				};
			}
			if(t.equals(BigDecimal.class)) {
				final DecimalFormat fmt = new DecimalFormat(format);
				fmt.setParseBigDecimal(true);
				return new Setter() {
					@Override
					public void set(Object obj, List<String> list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
						String s = list.get(index);
						try {
							m.invoke(obj, s == null ? null : fmt.parse(s));
						} catch (ParseException e) {
							throw new IllegalArgumentException(e);
						}
					}
				};
			}
		}
		//TODO: more supported types
		throw new IllegalStateException(t.getName() + " is not suppoted.");
	}
}
