package cn.ekgc.itrip.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DaysUtil {

	public static String getDateDays(String beginDate, String endDate) {
		String num="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdf.parse(beginDate);
			Date d2= sdf.parse(endDate);
			long daysBetween=(d2.getTime()-d1.getTime()+1000000)/(3600*24*1000);
			num=daysBetween+"";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
}
