package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.LinkUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>爱旅行-联系人数据持久层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface LinkUserDao {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<LinkUser> findListByQuery(LinkUser query) throws Exception;

	/**
	 * <b>保存对象</b>
	 * @param linkUser
	 * @return
	 * @throws Exception
	 */
	Integer save(LinkUser linkUser) throws Exception;

	/**
	 * <b>修改对象</b>
	 * @param linkUser
	 * @return
	 */
	Integer update(LinkUser linkUser) throws Exception;

	/**
	 * <b>查询与订单有关的常用联系人</b>
	 * @param linkUserName
	 * @return
	 */
	List<LinkUser> findListInOrder(String linkUserName);

	/**
	 * <b>删除常用联系人信息</b>
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Integer delete(Long id) throws Exception;
}
