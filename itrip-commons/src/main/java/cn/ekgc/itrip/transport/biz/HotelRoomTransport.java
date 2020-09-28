package cn.ekgc.itrip.transport.biz;

import cn.ekgc.itrip.pojo.entity.HotelRoom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <b>酒店房间传输层接口</b>
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/hotelroom/transport")
public interface HotelRoomTransport {

	/**
	 * <b>查询酒店房间列表</b>
	 * @param query
	 * @return
	 */
	@PostMapping("/queryhotelroombyhotel")
	List<HotelRoom> getListByQuery(@RequestBody HotelRoom query) throws Exception;
}
