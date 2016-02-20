package jp.co.withlinkage.sato;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Class4 implements If1 {

	public Class4() {
	}

	@Override
	public String getName() {
		return "now = " + new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

}
