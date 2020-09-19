package cn.ekgc.itrip.base.pojo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>基本信息实体类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 6185314795812407378L;
	private Date creationDate;
	private Long createdBy;
	private Date modifyDate;
	private Long modifiedBy;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
