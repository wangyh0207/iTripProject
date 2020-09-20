package cn.ekgc.itrip.service;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.LinkUser;

/**
 * <b>爱旅行-联系人业务层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LinkUserService {

	/**
	 * <b>查询常用联系人</b>
	 * @param linkUserName
	 * @param token
	 * @return
	 * @throws Exception
	 */
	ResultVO getListByQuery(String linkUserName, String token) throws Exception;

	/**
	 * <b>根据查询条件查询对象</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	boolean getLinkUserByQuery(LinkUser query) throws Exception;

	/**
	 * <b>添加常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 */
	ResultVO addUserLinkUser(LinkUser linkUser, String token) throws Exception;

	/**
	 * <b>修改常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 * @throws Exception
	 */
	ResultVO updateLinkUser(LinkUser linkUser, String token) throws Exception;

	/**
	 * <b>删除常用联系人</b>
	 * @param args
	 * @return
	 * @throws Exception
	 */
	ResultVO delUserLinkUser(long[] args) throws Exception;
}
