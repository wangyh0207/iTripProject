package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.HotelTempStore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>酒店实时库存数据持久层接口</b>
 */
@Repository
public interface HotelTempStoreDao {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 */
	List<HotelTempStore> findListByQuery(HotelTempStore query);
}
