package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.OrderLinkUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>订单联系人数据持久层</b>
 */
@Repository
public interface OrderLinkUserDao {
	List<OrderLinkUser> findListByQuery(OrderLinkUser query) throws Exception;
}
