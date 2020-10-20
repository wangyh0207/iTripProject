package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.HotelOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>酒店订单信息数据持久层</b>
 */
@Repository
public interface HotelOrderDao {

	/**
	 * <b>根据查询对象获得集合</b>
	 * @param query
	 * @return
	 */
	List<HotelOrder> findListByQuery(HotelOrder query);

	/**
	 * <b>保存订单信息</b>
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	Integer save(HotelOrder entity) throws Exception;

	/**
	 * <b>修改订单信息</b>
	 * @param entity
	 * @return
	 */
	Boolean update(HotelOrder entity);
}
