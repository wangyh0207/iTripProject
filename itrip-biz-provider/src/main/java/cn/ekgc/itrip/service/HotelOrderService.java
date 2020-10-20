package cn.ekgc.itrip.service;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.vo.*;

/**
 * <b>酒店订单业务层接口</b>
 */
public interface HotelOrderService {

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	RoomStoreVO getRoomStoreVO(ValidateRoomStoreVO validateRoomStoreVO) throws Exception;

	/**
	 * <b>生成订单信息</b>
	 * @param addHotelOrderVO
	 * @return
	 * @throws Exception
	 */
	HotelOrder createHotelOrder(ItripAddHotelOrderVO addHotelOrderVO, String token) throws Exception;

	/**
	 * <b>根据订单ID查看个人订单详情</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	HotelOrder getHotelOrderById(Long id) throws Exception;

	/**
	 * <b>根据订单编号查询信息</b>
	 * @param tradeNo
	 * @return
	 * @throws Exception
	 */
	HotelOrder getHotelOrderByTradeNo(String tradeNo) throws Exception;

	/**
	 * <b>修改订单状态</b>
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	boolean updateOrderStatus(HotelOrder entity) throws Exception;

	/**
	 * <b>根据个人订单列表，并分页显示</b>
	 * @param searchOrderVO
	 * @return
	 * @throws Exception
	 */
	ResultVO getHotelOrderListByPage(ItripSearchOrderVO searchOrderVO, String token) throws Exception;
}
