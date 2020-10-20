package cn.ekgc.itrip.util;

import java.net.URL;
import java.util.Properties;

/**
 * <b>支付宝接口常量</b>
 */
public class AlipayConstants {
	private static Properties props = new Properties();

	static {
		try {
			props.load(ConstantUtils.class.getClassLoader().getResourceAsStream("props/alipay.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String URl = props.getProperty("alipay.url");

	public static final String APPID = props.getProperty("alipay.appid");

	public static final String APP_PRIVATE_KEYRl = props.getProperty("alipay.app_private_key");

	public static final String FORMAT = props.getProperty("alipay.format");

	public static final String CHARSET = props.getProperty("alipay.charset");

	public static final String ALIPAY_PUBLIC_KEY = props.getProperty("alipay.public_key");

	public static final String SIGN_TYPE = props.getProperty("alipay.sign_type");

	public static final String FAST_INSTANT_TRADE_PAY = props.getProperty("alipay.product_code");
}
