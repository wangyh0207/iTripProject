package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.dao.HotelDao;
import cn.ekgc.itrip.dao.HotelOrderDao;
import cn.ekgc.itrip.dao.HotelRoomDao;
import cn.ekgc.itrip.dao.HotelTempStoreDao;
import cn.ekgc.itrip.pojo.entity.*;
import cn.ekgc.itrip.pojo.enums.OrderStatusEnum;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.service.HotelOrderService;
import cn.ekgc.itrip.util.ConstantUtils;
import cn.ekgc.itrip.util.DaysUtil;
import cn.ekgc.itrip.util.OrderNoCreate;
import cn.ekgc.itrip.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.solr.client.solrj.response.FacetField;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <b>酒店订单业务层接口实现类</b>
 */
@Service("hotelOrderService")
@Transactional
public class HotelOrderServiceImpl implements HotelOrderService {
	@Autowired
	private HotelOrderDao hotelOrderDao;
	@Autowired
	private HotelTempStoreDao hotelTempStoreDao;
	@Autowired
	private HotelRoomDao hotelRoomDao;
	@Autowired
	private HotelDao hotelDao;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public RoomStoreVO getRoomStoreVO(ValidateRoomStoreVO validateRoomStoreVO) throws Exception {
		// 根据酒店id和房间id查询库存
		HotelTempStore hotelTempStore = new HotelTempStore();
		hotelTempStore.setHotelId(validateRoomStoreVO.getHotelId());
		hotelTempStore.setRoomId(validateRoomStoreVO.getRoomId());
		List<HotelTempStore> hotelTempStoreList = hotelTempStoreDao.findListByQuery(hotelTempStore);
		if (hotelTempStoreList != null && hotelTempStoreList.size() > 0) {
			// 实时库存
			int tempStore = hotelTempStoreList.get(hotelTempStoreList.size() - 1).getStore();
			if (tempStore - validateRoomStoreVO.getCount() > 0) {
				// 查询订单状态为待支付
				HotelOrder hotelOrder = new HotelOrder();
				hotelOrder.setHotelId(validateRoomStoreVO.getHotelId());
				hotelOrder.setRoomId(validateRoomStoreVO.getRoomId());
				hotelOrder.setCheckInDate(validateRoomStoreVO.getCheckInDate());
				hotelOrder.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
				hotelOrder.setOrderStatus(OrderStatusEnum.ORDER_STATUS_TO_BE_PAID.getCode());
				List<HotelOrder> hotelOrderList = hotelOrderDao.findListByQuery(hotelOrder);
				int orderCount = 0;
				for (HotelOrder order: hotelOrderList) {
					orderCount = order.getCount() + orderCount;
				}
				if (tempStore - orderCount - validateRoomStoreVO.getCount() >= 0) {
					HotelRoom query = new HotelRoom();
					query.setId(validateRoomStoreVO.getRoomId());
					query.setHotelId(validateRoomStoreVO.getHotelId());
					List<HotelRoom> list = hotelRoomDao.findListByQuery(query);
					RoomStoreVO roomStoreVO = new RoomStoreVO();
					roomStoreVO.setHotelId(validateRoomStoreVO.getHotelId());
					roomStoreVO.setRoomId(validateRoomStoreVO.getRoomId());
					roomStoreVO.setCheckInDate(validateRoomStoreVO.getCheckInDate());
					roomStoreVO.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
					Hotel hotel = new Hotel();
					hotel.setId(validateRoomStoreVO.getHotelId());
					List<Hotel> hotelList = hotelDao.findListByQuery(hotel);
					roomStoreVO.setHotelName(hotelList.get(0).getHotelName());
					roomStoreVO.setPrice(list.get(0).getRoomPrice());
					roomStoreVO.setStore(tempStore - orderCount);
					roomStoreVO.setCount(tempStore);
					return roomStoreVO;
				}
			}
		}
		HotelRoom query = new HotelRoom();
		query.setId(validateRoomStoreVO.getRoomId());
		query.setHotelId(validateRoomStoreVO.getHotelId());
		List<HotelRoom> list = hotelRoomDao.findListByQuery(query);
		RoomStoreVO roomStoreVO = new RoomStoreVO();
		roomStoreVO.setHotelId(validateRoomStoreVO.getHotelId());
		roomStoreVO.setRoomId(validateRoomStoreVO.getRoomId());
		roomStoreVO.setCheckInDate(validateRoomStoreVO.getCheckInDate());
		roomStoreVO.setCheckOutDate(validateRoomStoreVO.getCheckOutDate());
		Hotel hotel = new Hotel();
		hotel.setId(validateRoomStoreVO.getHotelId());
		List<Hotel> hotelList = hotelDao.findListByQuery(hotel);
		roomStoreVO.setHotelName(hotelList.get(0).getHotelName());
		roomStoreVO.setPrice(list.get(0).getRoomPrice());
		roomStoreVO.setStore(0);
		roomStoreVO.setCount(0);
		return roomStoreVO;
	}

	/**
	 * <b>生成订单信息</b>
	 * @param addHotelOrderVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public HotelOrder createHotelOrder(ItripAddHotelOrderVO addHotelOrderVO, String token) throws Exception {
		HotelTempStore hotelTempStore = new HotelTempStore();
		hotelTempStore.setHotelId(addHotelOrderVO.getHotelId());
		hotelTempStore.setRoomId(addHotelOrderVO.getRoomId());
		List<HotelTempStore> hotelTempStoreList = hotelTempStoreDao.findListByQuery(hotelTempStore);
		if (hotelTempStoreList != null && hotelTempStoreList.size() > 0) {
			// 实时库存
			int tempStore = hotelTempStoreList.get(hotelTempStoreList.size() - 1).getStore();
			if (tempStore - addHotelOrderVO.getCount() > 0) {
				// 查询订单状态为待支付
				HotelOrder hotelOrder = new HotelOrder();
				hotelOrder.setHotelId(addHotelOrderVO.getHotelId());
				hotelOrder.setRoomId(addHotelOrderVO.getRoomId());
				hotelOrder.setCheckInDate(addHotelOrderVO.getCheckInDate());
				hotelOrder.setCheckOutDate(addHotelOrderVO.getCheckOutDate());
				hotelOrder.setOrderStatus(OrderStatusEnum.ORDER_STATUS_TO_BE_PAID.getCode());
				List<HotelOrder> hotelOrderList = hotelOrderDao.findListByQuery(hotelOrder);
				int orderCount = hotelOrderList.size();
				if (tempStore - orderCount - addHotelOrderVO.getCount() >= 0) {
					// 此时有空房
					HotelOrder createOrder = new HotelOrder();
					User user = (User) redisUtil.getFromRedis(token, User.class);
					createOrder.setUserId(user.getId());
					createOrder.setOrderType(addHotelOrderVO.getOrderType());
					String orderNo = OrderNoCreate.createOrderNo();
					createOrder.setOrderNo(orderNo);
					createOrder.setTradeNo(OrderNoCreate.createOrderNo());
					createOrder.setHotelId(addHotelOrderVO.getHotelId());
					createOrder.setHotelName(addHotelOrderVO.getHotelName());
					createOrder.setRoomId(addHotelOrderVO.getRoomId());
					createOrder.setCount(addHotelOrderVO.getCount());
					createOrder.setCheckInDate(addHotelOrderVO.getCheckInDate());
					createOrder.setCheckOutDate(addHotelOrderVO.getCheckOutDate());
					createOrder.setOrderStatus(OrderStatusEnum.ORDER_STATUS_TO_BE_PAID.getCode());
					createOrder.setNoticePhone(addHotelOrderVO.getNoticePhone());
					createOrder.setNoticeEmail(addHotelOrderVO.getNoticeEmail());
					createOrder.setSpecialRequirement(addHotelOrderVO.getSpecialRequirement());
					createOrder.setIsNeedInvoice(addHotelOrderVO.getIsNeedInvoice());
					createOrder.setInvoiceType(addHotelOrderVO.getInvoiceType());
					createOrder.setInvoiceHead(addHotelOrderVO.getInvoiceHead());
					// 获得天数
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					String beginDate = formatter.format(addHotelOrderVO.getCheckInDate());
					String endDate = formatter.format(addHotelOrderVO.getCheckOutDate());
					Integer days = Integer.parseInt(DaysUtil.getDateDays(beginDate, endDate));
					createOrder.setBookingDays(days);
					// 总价格
					HotelRoom query = new HotelRoom();
					query.setId(addHotelOrderVO.getRoomId());
					List<HotelRoom> hotelRoomList = hotelRoomDao.findListByQuery(query);
					createOrder.setPayAmount(addHotelOrderVO.getCount() * hotelRoomList.get(0).getRoomPrice());
					// 添加联系人信息
					List<LinkUser> userLinkUserList = addHotelOrderVO.getLinkUser();
					StringBuffer sb = new StringBuffer();
					for (LinkUser userLinkUser : userLinkUserList) {
						sb.append(userLinkUser.getLinkUserName() + ",");
					}
					createOrder.setLinkUserName(sb.toString().substring(0, sb.toString().length() - 1));
					createOrder.setBookType(0);
					createOrder.setCreationDate(new Date());
					createOrder.setCreatedBy(user.getId());
					createOrder.setModifyDate(new Date());
					createOrder.setModifiedBy(user.getId());
					int count = hotelOrderDao.save(createOrder);
					if (count > 0) {
						HotelOrder hotelOrder1 = new HotelOrder();
						hotelOrder1.setOrderNo(orderNo);
						List<HotelOrder> hotelOrders = hotelOrderDao.findListByQuery(hotelOrder1);
						return hotelOrders.get(0);
					}
				}
			}
		}
		return null;
	}

	/**
	 * <b>根据订单ID查看个人订单详情</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public HotelOrder getHotelOrderById(Long id) throws Exception {
		HotelOrder query = new HotelOrder();
		query.setId(id);
		List<HotelOrder> list = hotelOrderDao.findListByQuery(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <b>根据订单编号查询信息</b>
	 * @param tradeNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public HotelOrder getHotelOrderByTradeNo(String tradeNo) throws Exception {
		HotelOrder query = new HotelOrder();
		query.setOrderNo(tradeNo);
		List<HotelOrder> list = hotelOrderDao.findListByQuery(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <b>修改订单状态</b>
	 * @param entity
	 * @return
	 */
	@Override
	public boolean updateOrderStatus(HotelOrder entity) throws Exception {
		boolean flag = hotelOrderDao.update(entity);
		if (flag) {
			return true;
		}
		return false;
	}

	/**
	 * <b>根据个人订单列表，并分页显示</b>
	 * @param searchOrderVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO getHotelOrderListByPage(ItripSearchOrderVO searchOrderVO, String token) throws Exception {
		// 获得当前登陆用户
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			Page page = new Page(searchOrderVO.getPageNo(), searchOrderVO.getPageSize());
			HotelOrder query = new HotelOrder();
			query.setUserId(user.getId());
			if (searchOrderVO.getOrderStatus() != -1) {
				query.setOrderStatus(searchOrderVO.getOrderStatus());
			}
			if (searchOrderVO.getOrderType() != -1) {
				query.setOrderType(searchOrderVO.getOrderType());
			}
			PageHelper.startPage(page.getCurPage(), page.getPageSize());
			List<HotelOrder> list = hotelOrderDao.findListByQuery(query);
			if (list.size() > 0 && !list.isEmpty()) {
				PageInfo<HotelOrder> pageInfo = new PageInfo<HotelOrder>(list);
				Long total = pageInfo.getTotal();
				page = new Page(searchOrderVO.getPageNo(), searchOrderVO.getPageSize(), total);
				page.setRows(pageInfo.getList());
				return ResultVO.success(page);
			}
			return ResultVO.failure("暂无订单信息", "100503");
		}
		return ResultVO.failure("登陆已过期", ConstantUtils.SYSTEM_NO_TOKEN);
	}
}
