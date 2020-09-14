package cn.ekgc.itrip.util;

/**
 * <b>用户信息校验工具类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class ValidateUtil {
	private static String emailRegx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	private static String cellphoneRegx = "^1[0-9]{10}$";

	/**
	 * <b>校验 Eamil 是否正确</b>
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (email != null && email.matches(emailRegx)) {
			return true;
		}
		return false;
	}

	/**
	 * <b>校验手机号码是否正确</b>
	 * @param cellphone
	 * @return
	 */
	public static boolean checkCellphone(String cellphone) {
		if (cellphone != null && cellphone.matches(cellphoneRegx)) {
			return true;
		}
		return false;
	}

	/**
	 * <b>校验登陆密码是否正确</b>
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String password) {
		if (password != null && !"".equals(password)) {
			return true;
		}
		return false;
	}
}
