package cn.ekgc.itrip.pojo.entity;

import cn.ekgc.itrip.base.pojo.entity.BaseEntity;

/**
 * <b>爱旅行-用户模块用户联系人实体信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class LinkUser extends BaseEntity {
	private static final long serialVersionUID = 7797545824656775851L;
	private Long id;                            // 主键
	private String linkUserName;                // 常用联系人姓名
	private String linkIdCard;                  // 证件号码
	private String linkPhone;                   // 常用联系人电话
	private User user;                          // 用户
	private Integer linkIdCardType;             // 证件类型：(0-身份证，1-护照，2-学生证，3-军人证，4-驾驶证，5-旅行证)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getLinkIdCardType() {
		return linkIdCardType;
	}

	public void setLinkIdCardType(Integer linkIdCardType) {
		this.linkIdCardType = linkIdCardType;
	}
}
