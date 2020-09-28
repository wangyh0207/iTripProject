package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.HotelRoom;

import java.util.List;

/**
 * <b>酒店房间业务层接口</b>
 */
public interface HotelRoomService {
	/**
	 * <b>查询酒店房间列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<HotelRoom> getListByQuery(HotelRoom query) throws Exception;
}
