package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.entity.HotelRoom;
import cn.ekgc.itrip.service.HotelRoomService;
import cn.ekgc.itrip.transport.biz.HotelRoomTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>酒店房间传输层接口实现类</b>
 */
@RestController("hotelRoomTransport")
@RequestMapping("/hotelroom/transport")
public class HotelRoomTransportImpl implements HotelRoomTransport {
	@Autowired
	private HotelRoomService hotelRoomService;

	/**
	 * <b>查询酒店房间列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/queryhotelroombyhotel")
	@Override
	public List<HotelRoom> getListByQuery(@RequestBody HotelRoom query) throws Exception {
		return hotelRoomService.getListByQuery(query);
	}
}
