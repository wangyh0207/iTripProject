package cn.ekgc.itrip.transport.biz;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import sun.tools.jstat.Token;

/**
 * <b>酒店订单传输层接口</b>
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/hotelOrder/transport")
public interface HotelOrderTransport {

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpreorderinfo")
	RoomStoreVO getRoomStoreVO(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception;

	/**
	 * <b>生成订单信息</b>
	 * @param addHotelOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addhotelorder")
	HotelOrder createHotelOrder(@RequestBody ItripAddHotelOrderVO addHotelOrderVO, @RequestParam String token) throws Exception;

	/**
	 * <b>根据订单ID查看个人订单详情</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpersonalorderinfo")
	HotelOrder getPersonalOrderInfoById(@RequestParam Long id) throws Exception;

	/**
	 * <b>根据订单编号查询信息</b>
	 * @param tradeNo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/prepay")
	HotelOrder getHotelOrderByTradeNo(@RequestParam String tradeNo) throws Exception;

	/**
	 * <b>修改订单状态</b>
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	boolean updateOrderStatus(@RequestBody HotelOrder entity) throws Exception;

	/**
	 * <b>根据个人订单列表，并分页显示</b>
	 * @param searchOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpersonalorderlist")
	ResultVO getHotelOrderListByPage(@RequestBody ItripSearchOrderVO searchOrderVO, @RequestParam String token) throws Exception;
}
