package cn.ekgc.itrip.pojo.entity;

import cn.ekgc.itrip.base.pojo.entity.BaseEntity;

/**
 * <b>爱旅行-酒店模块城市实体信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class Area extends BaseEntity {
	private static final long serialVersionUID = -4253703392708398680L;
	private Long id;
	private String name;
	private String areaNo;
	private Area parent;
	private Integer isActivated;
	private Integer isTradingArea;
	private Integer isHot;
	private Integer level;
	private Integer isChina;
	private String pinyin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}

	public Integer getIsTradingArea() {
		return isTradingArea;
	}

	public void setIsTradingArea(Integer isTradingArea) {
		this.isTradingArea = isTradingArea;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIsChina() {
		return isChina;
	}

	public void setIsChina(Integer isChina) {
		this.isChina = isChina;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
