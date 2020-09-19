package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

/**
 * <b>查询常用联系人视图信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class SearchUserVO implements Serializable {
	private static final long serialVersionUID = -7321866493116175519L;
	private String linkUserName;

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}
}
