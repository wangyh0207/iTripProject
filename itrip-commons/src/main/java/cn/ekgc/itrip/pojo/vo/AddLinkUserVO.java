package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

/**
 * <b>添加常用联系人视图信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class AddLinkUserVO implements Serializable {
	private static final long serialVersionUID = 4883235736458008439L;
	private String linkUserName;
	private Integer linkIdCardType;
	private String linkIdCard;
	private String linkPhone;
	private Long userId;

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}

	public Integer getLinkIdCardType() {
		return linkIdCardType;
	}

	public void setLinkIdCardType(Integer linkIdCardType) {
		this.linkIdCardType = linkIdCardType;
	}

	public String getLinkIdCard() {
		return linkIdCard;
	}

	public void setLinkIdCard(String linkIdCard) {
		this.linkIdCard = linkIdCard;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
