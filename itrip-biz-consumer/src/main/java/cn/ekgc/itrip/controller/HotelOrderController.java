package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.Hotel;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.entity.HotelRoom;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.transport.biz.HotelOrderTransport;
import cn.ekgc.itrip.transport.biz.HotelRoomTransport;
import cn.ekgc.itrip.transport.biz.HotelTransport;
import cn.ekgc.itrip.transport.biz.LabelDicTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController("hotelOrderController")
@RequestMapping("/biz/api/hotelorder")
public class HotelOrderController extends BaseController {
	@Autowired
	private HotelOrderTransport hotelOrderTransport;
	@Autowired
	private HotelTransport hotelTransport;
	@Autowired
	private HotelRoomTransport hotelRoomTransport;
	@Autowired
	private LabelDicTransport labelDicTransport;

	/**
	 * <b>生成订单前,获取预订信息</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpreorderinfo")
	public ResultVO getPreorderInfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception {
		RoomStoreVO roomStoreVO = hotelOrderTransport.getRoomStoreVO(validateRoomStoreVO);
		return ResultVO.success(roomStoreVO);
	}

	/**
	 * <b>生成订单信息</b>
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addhotelorder")
	public ResultVO addHotelOrder(@RequestBody ItripAddHotelOrderVO addHotelOrderVO) throws Exception {
		String token = request.getHeader("token");
		HotelOrder hotelOrder = hotelOrderTransport.createHotelOrder(addHotelOrderVO, token);
		if (hotelOrder != null) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", hotelOrder.getId());
			resultMap.put("orderNo", hotelOrder.getOrderNo());
			// 返回该 Map 集合
			return ResultVO.success(resultMap);
		}
		return null;
	}

	/**
	 * <b>根据订单id查询订单</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getpersonalorderinfo/{id}")
	public ResultVO getPersonalOrderInfo(@PathVariable("id") Long id) throws Exception {
		HotelOrder hotelOrder = hotelOrderTransport.getPersonalOrderInfoById(id);
		ItripPersonalHotelOrderVO personalHotelOrderVO = new ItripPersonalHotelOrderVO();
		personalHotelOrderVO.setId(hotelOrder.getId());
		personalHotelOrderVO.setOrderNo(hotelOrder.getOrderNo());
		personalHotelOrderVO.setOrderStatus(hotelOrder.getOrderStatus());
		personalHotelOrderVO.setPayType(hotelOrder.getPayType());
		personalHotelOrderVO.setSpecialRequirement(hotelOrder.getSpecialRequirement());
		personalHotelOrderVO.setNoticePhone(hotelOrder.getNoticePhone());
		Double payAmount = hotelOrder.getPayAmount();
		DecimalFormat df = new DecimalFormat("0.00#");
		personalHotelOrderVO.setPayAmount(new BigDecimal(df.format(payAmount)));
		personalHotelOrderVO.setRoomPayType(3);
		personalHotelOrderVO.setBookType(hotelOrder.getBookType());
		return ResultVO.success(personalHotelOrderVO);
	}

	/**
	 * <b>根据订单id 查询房间信息</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getpersonalorderroominfo/{id}")
	public ResultVO getPersonalOrderRoomInfo(@PathVariable("id") Long id) throws Exception {
		HotelOrder hotelOrder = hotelOrderTransport.getPersonalOrderInfoById(id);
		ItripPersonalOrderRoomVO personalOrderRoomVO = new ItripPersonalOrderRoomVO();
		personalOrderRoomVO.setId(hotelOrder.getId());
		personalOrderRoomVO.setHotelId(hotelOrder.getHotelId());
		Hotel hotel = hotelTransport.getHotelById(hotelOrder.getHotelId());
		personalOrderRoomVO.setHotelName(hotel.getHotelName());
		personalOrderRoomVO.setHotelLevel(hotel.getHotelLevel());
		personalOrderRoomVO.setAddress(hotel.getAddress());
		personalOrderRoomVO.setRoomId(hotelOrder.getRoomId());
		HotelRoom query = new HotelRoom();
		query.setId(hotelOrder.getRoomId());
		HotelRoom hotelRoom = hotelRoomTransport.getListByQuery(query).get(0);
		personalOrderRoomVO.setRoomTitle(hotelRoom.getRoomTitle());
		personalOrderRoomVO.setRoomBedTypeId(hotelRoom.getRoomBedTypeId());
		personalOrderRoomVO.setCheckInDate(hotelOrder.getCheckInDate());
		personalOrderRoomVO.setCheckOutDate(hotelOrder.getCheckOutDate());
		personalOrderRoomVO.setCount(hotelOrder.getCount());
		personalOrderRoomVO.setBookingDays(hotelOrder.getBookingDays());
		personalOrderRoomVO.setLinkUserName(hotelOrder.getLinkUserName());
		personalOrderRoomVO.setSpecialRequirement(hotelOrder.getSpecialRequirement());
		Double payAmount = hotelOrder.getPayAmount();
		DecimalFormat df = new DecimalFormat("0.00#");
		personalOrderRoomVO.setPayAmount(new BigDecimal(df.format(payAmount)));
		personalOrderRoomVO.setIsHavingBreakfast(hotelRoom.getIsHavingBreakfast());
		LabelDic queryLabelDic = new LabelDic();
		queryLabelDic.setId(hotelRoom.getRoomBedTypeId());
		LabelDic labelDic = labelDicTransport.getLabelDicListByQuery(queryLabelDic).get(0);
		personalOrderRoomVO.setRoomBedTypeName(labelDic.getName());
		return ResultVO.success(personalOrderRoomVO);
	}

	@GetMapping("/queryOrderById/{id}")
	public ResultVO queryOrderById(@PathVariable("id") Long id) throws Exception {
		HotelOrder hotelOrder = hotelOrderTransport.getPersonalOrderInfoById(id);
		ItripPersonalOrderRoomVO personalOrderRoomVO = new ItripPersonalOrderRoomVO();
		personalOrderRoomVO.setId(hotelOrder.getId());
		personalOrderRoomVO.setHotelId(hotelOrder.getHotelId());
		Hotel hotel = hotelTransport.getHotelById(hotelOrder.getHotelId());
		personalOrderRoomVO.setHotelName(hotel.getHotelName());
		personalOrderRoomVO.setHotelLevel(hotel.getHotelLevel());
		personalOrderRoomVO.setAddress(hotel.getAddress());
		personalOrderRoomVO.setRoomId(hotelOrder.getRoomId());
		HotelRoom query = new HotelRoom();
		query.setId(hotelOrder.getRoomId());
		HotelRoom hotelRoom = hotelRoomTransport.getListByQuery(query).get(0);
		personalOrderRoomVO.setRoomTitle(hotelRoom.getRoomTitle());
		personalOrderRoomVO.setRoomBedTypeId(hotelRoom.getRoomBedTypeId());
		personalOrderRoomVO.setCheckInDate(hotelOrder.getCheckInDate());
		personalOrderRoomVO.setCheckOutDate(hotelOrder.getCheckOutDate());
		personalOrderRoomVO.setCount(hotelOrder.getCount());
		personalOrderRoomVO.setBookingDays(hotelOrder.getBookingDays());
		personalOrderRoomVO.setLinkUserName(hotelOrder.getLinkUserName());
		personalOrderRoomVO.setSpecialRequirement(hotelOrder.getSpecialRequirement());
		Double payAmount = hotelOrder.getPayAmount();
		DecimalFormat df = new DecimalFormat("0.00#");
		personalOrderRoomVO.setPayAmount(new BigDecimal(df.format(payAmount)));
		personalOrderRoomVO.setIsHavingBreakfast(hotelRoom.getIsHavingBreakfast());
		LabelDic queryLabelDic = new LabelDic();
		queryLabelDic.setId(hotelRoom.getRoomBedTypeId());
		LabelDic labelDic = labelDicTransport.getLabelDicListByQuery(queryLabelDic).get(0);
		personalOrderRoomVO.setRoomBedTypeName(labelDic.getName());
		return ResultVO.success(personalOrderRoomVO);
	}

	/**
	 * <b>根据个人订单列表，并分页显示</b>
	 * @param searchOrderVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getpersonalorderlist")
	public ResultVO getPersonalOrderList(@RequestBody ItripSearchOrderVO searchOrderVO) throws Exception {
		String token = request.getHeader("token");
		ResultVO resultVO = hotelOrderTransport.getHotelOrderListByPage(searchOrderVO, token);
		return resultVO;
	}
}
