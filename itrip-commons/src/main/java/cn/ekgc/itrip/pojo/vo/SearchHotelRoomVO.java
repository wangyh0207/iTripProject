package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>查询酒店房间搜索条件VO</b>
 */
public class SearchHotelRoomVO implements Serializable {
	private static final long serialVersionUID = 2693837172086470829L;
	private Long hotelId;
	private Integer isBook;
	private Integer isHavingBreakfast;
	private Integer isTimelyResponse;
	private Long roomBedTypeId;
	private Date startDate;
	private Date endDate;
	private Integer isCancel;
	private Integer payType;

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}

	public Integer getIsHavingBreakfast() {
		return isHavingBreakfast;
	}

	public void setIsHavingBreakfast(Integer isHavingBreakfast) {
		this.isHavingBreakfast = isHavingBreakfast;
	}

	public Integer getIsTimelyResponse() {
		return isTimelyResponse;
	}

	public void setIsTimelyResponse(Integer isTimelyResponse) {
		this.isTimelyResponse = isTimelyResponse;
	}

	public Long getRoomBedTypeId() {
		return roomBedTypeId;
	}

	public void setRoomBedTypeId(Long roomBedTypeId) {
		this.roomBedTypeId = roomBedTypeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
