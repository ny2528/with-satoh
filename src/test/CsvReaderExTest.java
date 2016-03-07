package test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import jp.co.withlinkage.naokit.common.io.CsvCol;
import jp.co.withlinkage.naokit.common.io.CsvColFormat;
import jp.co.withlinkage.naokit.common.io.CsvFile;
import jp.co.withlinkage.naokit.common.io.CsvReaderEx;

public class CsvReaderExTest {

	@Test
	public void test01() {
		try {
			File file = new File("./data/test.csv");
			CsvReaderEx<Bean> reader = new CsvReaderEx<>(Bean.class, file, "MS932");
			Bean t = null;
			while((t = reader.readLine()) != null) {
				System.out.print(t.getNo());
				System.out.print("\t");
				System.out.print(t.getName());
				System.out.print("\t");
				System.out.print(t.getValue());
				System.out.print("\t");
				System.out.print(t.getDate());
				System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@CsvFile
	public static class Bean {
		@CsvCol(1)
		private int no;
		@CsvCol(2)
		private String name;
		@CsvCol(3)
		@CsvColFormat("yyyy-MM-dd")
		private Date date;
		@CsvCol(4)
		private BigDecimal value;
		/**
		 * @return no
		 */
		public final int getNo() {
			return no;
		}
		/**
		 * @param no セットする no
		 */
		public final void setNo(int no) {
			this.no = no;
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
		 * @return date
		 */
		public final Date getDate() {
			return date;
		}
		/**
		 * @param date セットする date
		 */
		public final void setDate(Date date) {
			this.date = date;
		}
		/**
		 * @return value
		 */
		public final BigDecimal getValue() {
			return value;
		}
		/**
		 * @param value セットする value
		 */
		public final void setValue(BigDecimal value) {
			this.value = value;
		}
	}

	public static class TestBean {
		@CsvCol(1)
		private String date;
		@CsvCol(3)
		private String beginTime;
		/**
		 * @return date
		 */
		public final String getDate() {
			return date;
		}
		/**
		 * @param date セットする date
		 */
		public final void setDate(String date) {
			this.date = date;
//			System.out.println("set date value=" + date);
		}
		/**
		 * @return beginTime
		 */
		public final String getBeginTime() {
			return beginTime;
		}
		/**
		 * @param beginTime セットする beginTime
		 */
		public final void setBeginTime(String beginTime) {
//			System.out.println("set time value=" + beginTime);
			this.beginTime = beginTime;
		}
	}
}
