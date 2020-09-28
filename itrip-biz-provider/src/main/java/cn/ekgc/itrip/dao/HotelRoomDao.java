package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.HotelRoom;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>酒店房间数据持久层接口</b>
 */
@Repository
public interface HotelRoomDao {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<HotelRoom> findListByQuery(HotelRoom query) throws Exception;
}
