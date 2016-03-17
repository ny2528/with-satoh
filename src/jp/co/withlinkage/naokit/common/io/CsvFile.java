package jp.co.withlinkage.naokit.common.io;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CsvFile {
	/** include heder rows? */
	boolean hasHeader() default true;
	/** delimiter */
	char delimiter() default CsvCommon.DEFAULT_DELIMITER;
	/** quoted? */
	boolean isQuoteString() default true;
	/** quoted null? */
	boolean isQuoteNull() default false;
	/** quoted numeric? */
	boolean isQuoteNumeric() default false;
	/** quoted date and/or time? */
	boolean isQuoteDate() default true;
	/** quoted label? */
	boolean isQuoteLabel() default false;
	/** quotation mark */
	char quotationMark() default CsvCommon.DEFAULT_QUOTATION_MARK;
	/** escape charactor */
	char escapeChar() default CsvCommon.DEFAULT_ESCAPE_CHAR;
}
