package cn.ekgc.itrip.pojo.enums;

/**
 * <b></b>证件类型枚举信息
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public enum LinkCardTypeEnum {
	LINK_CARD_ID(0, "身份证"),
	LINK_CARD_PASSPORT(1, "护照"),
	LINK_CARD_STUDENT(2, "学生证"),
	LINK_CARD_SOLDIER(3, "军人证"),
	LINK_CARD_DRIVER(4, "驾驶证"),
	LINK_CARD_TRAVEL(5, "旅行证");

	private Integer code;
	private String remark;

	private LinkCardTypeEnum(Integer code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
