package cn.ekgc.itrip.pojo.enums;

/**
 * <b>订单状态枚举信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public enum OrderStatusEnum {
	ORDER_STATUS_TO_BE_PAID(0, "待支付"),
	ORDER_STATUS_CANCELLED(1, "已取消"),
	ORDER_STATUS_PAYMENT_SUCCESS(2, "支付成功"),
	ORDER_STATUS_CONSUMED(3, "以消费"),
	ORDER_STATUS_COMMENTED(4, "已点评");

	private Integer code;
	private String remark;

	private OrderStatusEnum(Integer code, String remark) {
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
