package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.service.HotelOrderService;
import cn.ekgc.itrip.transport.biz.HotelOrderTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>酒店订单传输层接口实现类</b>
 */
@RestController("hotelOrderTransport")
@RequestMapping("/hotelOrder/transport")
public class HotelOrderTransportImpl implements HotelOrderTransport {
	@Autowired
	private HotelOrderService hotelOrderService;

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpreorderinfo")
	@Override
	public RoomStoreVO getRoomStoreVO(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception {
		return hotelOrderService.getRoomStoreVO(validateRoomStoreVO);
	}

	/**
	 * <b>生成订单信息</b>
	 * @param addHotelOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addhotelorder")
	@Override
	public HotelOrder createHotelOrder(@RequestBody ItripAddHotelOrderVO addHotelOrderVO, @RequestParam String token) throws Exception {
		return hotelOrderService.createHotelOrder(addHotelOrderVO, token);
	}

	/**
	 * <b>根据订单ID查看个人订单详情</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpersonalorderinfo")
	@Override
	public HotelOrder getPersonalOrderInfoById(@RequestParam Long id) throws Exception {
		return hotelOrderService.getHotelOrderById(id);
	}

	/**
	 * <b>根据订单编号查询信息</b>
	 * @param tradeNo
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/prepay")
	@Override
	public HotelOrder getHotelOrderByTradeNo(@RequestParam String tradeNo) throws Exception {
		return hotelOrderService.getHotelOrderByTradeNo(tradeNo);
	}

	/**
	 * <b>修改订单状态</b>
	 * @param entity
	 * @return
	 */
	@PostMapping("/update")
	@Override
	public boolean updateOrderStatus(@RequestBody HotelOrder entity)throws Exception {
		return hotelOrderService.updateOrderStatus(entity);
	}

	/**
	 * <b>根据个人订单列表，并分页显示</b>
	 * @param searchOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpersonalorderlist")
	@Override
	public ResultVO getHotelOrderListByPage(@RequestBody ItripSearchOrderVO searchOrderVO, @RequestParam String token) throws Exception {
		return hotelOrderService.getHotelOrderListByPage(searchOrderVO, token);
	}
}
