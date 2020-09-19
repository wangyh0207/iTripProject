package cn.ekgc.itrip.pojo.entity;

import cn.ekgc.itrip.base.pojo.entity.BaseEntity;

/**
 * <b>爱旅行-酒店模块订单实体信息</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class HotelOrder extends BaseEntity {
	private static final long serialVersionUID = -6906403346186711278L;
	private Long id;
	private User user;
	private Integer orderType;
	private String orderNo;
	private String tradeNo;
	private Hotel hotel;

}
