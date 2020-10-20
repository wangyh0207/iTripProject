package cn.ekgc.itrip.util;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.pojo.vo.AliPayTradeVo;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Component;

@Component("alipayUtil")
public class AlipayUtil extends BaseController {

	public void aliPay(AliPayTradeVo tradeVo) throws Exception {
		AlipayClient alipayClient =  new DefaultAlipayClient( AlipayConstants.URl , AlipayConstants.APPID,
				AlipayConstants.APP_PRIVATE_KEYRl, AlipayConstants.FORMAT,
				AlipayConstants.CHARSET, AlipayConstants.ALIPAY_PUBLIC_KEY, AlipayConstants.SIGN_TYPE);  //获得初始化的AlipayClient
		AlipayTradePagePayRequest alipayRequest =  new  AlipayTradePagePayRequest(); //创建API对应的request
		alipayRequest.setReturnUrl( "http://localhost/itrip" );
		alipayRequest.setNotifyUrl( "http://localhost/itrip" ); //在公共参数中设置回跳和通知地址
		JsonMapper jsonMapper = new JsonMapper();
		alipayRequest.setBizContent(jsonMapper.writeValueAsString(tradeVo)); //填充业务参数
		String form= "" ;
		try  {
			form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
		}  catch  (AlipayApiException e) {
			e.printStackTrace();
		}
		response.setContentType( "text/html;charset="  + AlipayConstants.CHARSET);
		response.getWriter().write(form); //直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}
}
