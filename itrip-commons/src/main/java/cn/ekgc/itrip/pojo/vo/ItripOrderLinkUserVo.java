package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

/**
 * <b>根据订单查询联系人返回VO</b>
 */
public class ItripOrderLinkUserVo implements Serializable {
	private static final long serialVersionUID = 5777304118804734875L;
	private Long linkUserId;
	private String linkUserName;

	public Long getLinkUserId() {
		return linkUserId;
	}

	public void setLinkUserId(Long linkUserId) {
		this.linkUserId = linkUserId;
	}

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}
}
