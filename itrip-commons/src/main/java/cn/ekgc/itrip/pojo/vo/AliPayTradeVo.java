package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

/**
 * <b>支付宝支付交易对象</b>
 */
public class AliPayTradeVo implements Serializable {
	private static final long serialVersionUID = -8570996222340861136L;
	private String out_trade_no;
	private String product_code;
	private String total_amount;
	private String subject;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
