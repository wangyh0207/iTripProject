package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.dao.HotelRoomDao;
import cn.ekgc.itrip.pojo.entity.HotelRoom;
import cn.ekgc.itrip.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>酒店房间业务层接口实现类</b>
 */
@Service("hotelRoomService")
@Transactional
public class HotelRoomServiceImpl implements HotelRoomService {
	@Autowired
	private HotelRoomDao hotelRoomDao;

	/**
	 * <b></b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HotelRoom> getListByQuery(HotelRoom query) throws Exception {
		return hotelRoomDao.findListByQuery(query);
	}
}
