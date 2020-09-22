package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.Hotel;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <b>酒店模块业务层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public interface HotelService {

	/**
	 * <b>根据酒店主键查询酒店信息</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	Hotel getHotelById(Long hotelId) throws Exception;
}
