package cn.ekgc.itrip.pojo.entity;

import cn.ekgc.itrip.base.pojo.entity.BaseEntity;

/**
 * <b>订单联系人信息实体类</b>
 */
public class OrderLinkUser extends BaseEntity {
	private static final long serialVersionUID = 9149569924982232877L;
	private Long id;
	private Long orderId;
	private Long linkUserId;
	private String linkUserName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

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
