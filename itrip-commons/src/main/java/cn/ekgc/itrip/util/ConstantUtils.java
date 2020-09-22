package cn.ekgc.itrip.util;

import java.util.Properties;

/**
 * <b>系统常量工具类</b>
 * @author wyh
 * @version 1.0.0
 */
public class ConstantUtils {
	private static Properties props = new Properties();

	static {
		try {
			props.load(ConstantUtils.class.getClassLoader().getResourceAsStream("props/itrip.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final Long LOGIN_EXPIRE = Long.parseLong(props.getProperty("login.expire"));

	/**
	 * <b>邮件信息：发送人</b>
	 */
	public static final String MAIL_FROM = props.getProperty("mail.from");

	/**
	 * <b>邮件信息：过期时间</b>
	 */
	public static final Long MAIL_EXPIRE = Long.parseLong(props.getProperty("mail.expire"));

	/**
	 * <b>常用联系人获取失败响应码</b>
	 */
	public static final String SYSTEM_NO_LINK_USER = props.getProperty("system.no.link.user");

	/**
	 * <b>token 失效响应码</b>
	 */
	public static final String SYSTEM_NO_TOKEN = props.getProperty("system.no.token");

	/**
	 * <b>新增常用联系人失败错误码</b>
	 */
	public static final String SYSTEM_ADD_LINK_USER_FALSE = props.getProperty("system.add.link.user.false");

	/**
	 * <b>常用联系人信息提交为空错误码</b>
	 */
	public static final String SYSTEM_ADD_NULL = props.getProperty("system.add.null");

	/**
	 * <b>修改常用联系人信息失败错误码</b>
	 */
	public static final String SYSTEM_UPDATE_LINK_USER_FALSE = props.getProperty("system.update.link.user.false");

	/**
	 * <b>修改信息提交为空错误码</b>
	 */
	public static final String SYSTEM_UPDATE_NULL = props.getProperty("system.update.null");

	/**
	 * <b>删除常用联系人失败错误码</b>
	 */
	public static final String SYSTEM_DELETE_LINK_USER_FALSE = props.getProperty("system.delete.link.user.false");

	/**
	 * <b>未选择删除人错误码</b>
	 */
	public static final String SYSTEM_DELETE_NULL = props.getProperty("system.delete.null");

	/**
	 * <b>删除联系人时存在订单关联错误码</b>
	 */
	public static final String SYSTEM_DELETE_FALSE_LINK = props.getProperty("system.delete.false.link");

	/**
	 * <b>获取酒店视频文字描述失败错误码</b>
	 */
	public static final String SYSTEM_HOTEL_VIDEO_FALSE = props.getProperty("system.hotel.video.false");

	/**
	 * <b>酒店 ID 为空错误码</b>
	 */
	public static final String SYSTEM_HOTEL_ID_NULL = props.getProperty("system.hotel.id.null");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_SERVER_IP = props.getProperty("cloopen.server.ip");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_SERVER_PORT = props.getProperty("cloopen.server.port");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_ACCOUNT_SID = props.getProperty("cloopen.account.sid");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_ACCOUNT_TOKEN = props.getProperty("cloopen.account.token");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_APP_ID = props.getProperty("cloopen.app.id");

	/**
	 * <b>容联云配置信息</b>
	 */
	public static final String CLOOPEN_TEMPLATE_ID = props.getProperty("cloopen.template.id");
}
