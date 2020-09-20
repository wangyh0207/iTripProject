package cn.ekgc.itrip.pojo.entity;

import cn.ekgc.itrip.base.pojo.entity.BaseEntity;

/**
 * <b>爱旅行-酒店模块酒店实体信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class Hotel extends BaseEntity {
	private static final long serialVersionUID = -3610351561226977627L;
	private Long id;
	private String hotelName;
	private Long countryId;
	private Long provinceId;
	private Long cityId;
	private String address;
	private String details;
	private String facilities;
	private String hotelPolicy;
	private Integer hotelType;
	private Integer hotelLevel;
	private Integer isGroupPurchase;
	private String redundantCityName;
	private String redundantProvinceName;
	private String redundantCountryName;
	private Integer redundantHotelStore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getHotelPolicy() {
		return hotelPolicy;
	}

	public void setHotelPolicy(String hotelPolicy) {
		this.hotelPolicy = hotelPolicy;
	}

	public Integer getHotelType() {
		return hotelType;
	}

	public void setHotelType(Integer hotelType) {
		this.hotelType = hotelType;
	}

	public Integer getHotelLevel() {
		return hotelLevel;
	}

	public void setHotelLevel(Integer hotelLevel) {
		this.hotelLevel = hotelLevel;
	}

	public Integer getIsGroupPurchase() {
		return isGroupPurchase;
	}

	public void setIsGroupPurchase(Integer isGroupPurchase) {
		this.isGroupPurchase = isGroupPurchase;
	}

	public String getRedundantCityName() {
		return redundantCityName;
	}

	public void setRedundantCityName(String redundantCityName) {
		this.redundantCityName = redundantCityName;
	}

	public String getRedundantProvinceName() {
		return redundantProvinceName;
	}

	public void setRedundantProvinceName(String redundantProvinceName) {
		this.redundantProvinceName = redundantProvinceName;
	}

	public String getRedundantCountryName() {
		return redundantCountryName;
	}

	public void setRedundantCountryName(String redundantCountryName) {
		this.redundantCountryName = redundantCountryName;
	}

	public Integer getRedundantHotelStore() {
		return redundantHotelStore;
	}

	public void setRedundantHotelStore(Integer redundantHotelStore) {
		this.redundantHotelStore = redundantHotelStore;
	}
}
