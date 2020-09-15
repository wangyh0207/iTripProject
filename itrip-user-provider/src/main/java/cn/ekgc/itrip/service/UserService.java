package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.User;

/**
 * <b>爱旅行-用户模块业务层接口/b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public interface UserService {

	/**
	 * <b>根据用户名校验该用户名是否被使用</b>
	 * @param userCode
	 * @return true-未被占用
	 * @throws Exception
	 */
	boolean queryUserCodeIsCanUsed(String userCode) throws Exception;

	/**
	 * <b>保存用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean saveUser(User user) throws Exception;
}
