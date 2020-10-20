package cn.ekgc.itrip.cotroller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.entity.HotelRoom;
import cn.ekgc.itrip.pojo.vo.AliPayTradeVo;
import cn.ekgc.itrip.transport.biz.HotelOrderTransport;
import cn.ekgc.itrip.transport.biz.HotelRoomTransport;
import cn.ekgc.itrip.util.AlipayConstants;
import cn.ekgc.itrip.util.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <b>爱旅行-支付功能功能控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("tradeController")
@RequestMapping("/trade/api")
public class TradeController extends BaseController {

	@Autowired
	private HotelOrderTransport hotelOrderTransport;
	@Autowired
	private HotelRoomTransport hotelRoomTransport;
	@Autowired
	private AlipayUtil alipayUtil;

	/**
	 * <b>根据订单编号进行订单支付</b>
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/prepay/{orderNo}")
	public void getTrade(@PathVariable("orderNo") String orderNo) throws Exception {
		HotelOrder query = new HotelOrder();
		query.setOrderNo(orderNo);
		HotelOrder hotelOrder = hotelOrderTransport.getHotelOrderByTradeNo(orderNo);
		HotelRoom queryRoom = new HotelRoom();
		queryRoom.setId(hotelOrder.getRoomId());
		HotelRoom hotelRoom = hotelRoomTransport.getListByQuery(queryRoom).get(0);
		AliPayTradeVo tradeVo = new AliPayTradeVo();
		tradeVo.setSubject(hotelOrder.getHotelName() + hotelRoom.getRoomTitle());
		tradeVo.setOut_trade_no(orderNo);
		tradeVo.setProduct_code(AlipayConstants.FAST_INSTANT_TRADE_PAY);
		Double amount = hotelOrder.getPayAmount();
		DecimalFormat df = new DecimalFormat("0.00#");
		tradeVo.setTotal_amount(df.format(new BigDecimal(amount)));
		alipayUtil.aliPay(tradeVo);
		query.setOrderStatus(2);
		hotelOrderTransport.updateOrderStatus(query);
	}
}