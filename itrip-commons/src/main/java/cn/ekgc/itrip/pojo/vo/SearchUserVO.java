package cn.ekgc.itrip.pojo.vo;

import org.springframework.scheduling.support.SimpleTriggerContext;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>查询常用联系人视图信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class SearchUserVO implements Serializable {
	private static final long serialVersionUID = -7321866493116175519L;
	private String linkUserName;
	private String checkInDate;
	private String checkOutDate;
	private String count;
	private String hotelId;
	private String roomId;

	public String getLinkUserName() {
		return linkUserName;
	}

	public void setLinkUserName(String linkUserName) {
		this.linkUserName = linkUserName;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
