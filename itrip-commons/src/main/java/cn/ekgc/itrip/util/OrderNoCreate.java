package cn.ekgc.itrip.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>订单编号生成器</b>
 */
public class OrderNoCreate {

	/**
	 * <b>生成订单编号</b>
	 * <p>
	 *     生成规则：使用当前时间，格式为yyyyMMddHHmmssSSSSSS
	 * </p>
	 * @return
	 * @throws Exception
	 */
	public static String createOrderNo() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		return dateFormat.format(new Date());
	}
}
