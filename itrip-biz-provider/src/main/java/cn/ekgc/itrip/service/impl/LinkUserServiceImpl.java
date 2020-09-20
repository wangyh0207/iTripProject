package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.dao.LinkUserDao;
import cn.ekgc.itrip.pojo.entity.LinkUser;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.service.LinkUserService;
import cn.ekgc.itrip.util.ConstantUtils;
import cn.ekgc.itrip.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <b>爱旅行-联系人业务层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("linkUserService")
@Transactional
public class LinkUserServiceImpl implements LinkUserService {
	@Autowired
	private LinkUserDao linkUserDao;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * <b>查询常用联系人</b>
	 * @param linkUserName
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO getListByQuery(String linkUserName, String token) throws Exception {
		// 获得当前登陆用户
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			// 已经登陆，token 未过期
			LinkUser query = new LinkUser();
			query.setLinkUserName(linkUserName);
			query.setUser(user);
			List<LinkUser> list = linkUserDao.findListByQuery(query);
			if (list != null && !list.isEmpty()) {
				return ResultVO.success(list);
			}
			return ResultVO.failure("联系人不存在", ConstantUtils.SYSTEM_NO_LINK_USER);
		}
		return ResultVO.failure("登陆已过期", ConstantUtils.SYSTEM_NO_TOKEN);
	}

	/**
	 * <b>根据查询条件查询对象</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean getLinkUserByQuery(LinkUser query) throws Exception {
		List<LinkUser> list = linkUserDao.findListByQuery(query);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * <b>添加常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 */
	public ResultVO addUserLinkUser(LinkUser linkUser, String token) throws Exception {
		// 获得当前登陆用户
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			// 已经登陆，token 未过期
			linkUser.setUser(user);
			linkUser.setCreationDate(new Date());
			linkUser.setModifyDate(new Date());
			int count = linkUserDao.save(linkUser);
			if (count > 0) {
				return ResultVO.success();
			}
			return ResultVO.failure("添加失败", ConstantUtils.SYSTEM_ADD_LINK_USER_FALSE);
		}
		return ResultVO.failure("登陆已过期", ConstantUtils.SYSTEM_NO_TOKEN);
	}

	/**
	 * <b>修改常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO updateLinkUser(LinkUser linkUser, String token) throws Exception {
		// 获得当前登陆用户
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			// 已经登陆，token 未过期
			LinkUser query = new LinkUser();
			query.setId(linkUser.getId());
			List<LinkUser> list = linkUserDao.findListByQuery(query);
			if (list != null && !list.isEmpty()) {
				// id 信息正确
				LinkUser queryUser = list.get(0);
				if (queryUser.getLinkIdCard().equals(linkUser.getLinkIdCard())) {
					// 未修改身份证号码
					if (queryUser.getLinkPhone().equals(linkUser.getLinkPhone())) {
						// 未修改联系电话
						linkUser.setUser(user);
						linkUser.setModifyDate(new Date());
						int count = linkUserDao.update(linkUser);
						if (count > 0) {
							return ResultVO.success("修改成功");
						}
						return ResultVO.failure("修改失败", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
					} else {
						// 已修改联系电话
						LinkUser queryByPhone = new LinkUser();
						queryByPhone.setLinkPhone(linkUser.getLinkPhone());
						List<LinkUser> listByPhone = linkUserDao.findListByQuery(queryByPhone);
						if (listByPhone != null && !listByPhone.isEmpty()) {
							// 电话号码已存在
							return ResultVO.failure("手机号码已存在", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
						} else {
							// 电话号码不存在
							linkUser.setUser(user);
							linkUser.setModifyDate(new Date());
							int count = linkUserDao.update(linkUser);
							if (count > 0) {
								return ResultVO.success("修改成功");
							}
							return ResultVO.failure("修改失败", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
						}
					}
				} else {
					// 已修改身份证号码
					LinkUser queryByIdCard = new LinkUser();
					queryByIdCard.setLinkIdCard(linkUser.getLinkIdCard());
					List<LinkUser> listByIdCard = linkUserDao.findListByQuery(queryByIdCard);
					if (listByIdCard != null && !listByIdCard.isEmpty()) {
						// 身份证号码存在
						return ResultVO.failure("身份证号码已存在", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
					} else {
						// 身份证号码不存在
						if (queryUser.getLinkPhone().equals(linkUser.getLinkPhone())) {
							// 未修改联系电话
							linkUser.setUser(user);
							linkUser.setModifyDate(new Date());
							int count = linkUserDao.update(linkUser);
							if (count > 0) {
								return ResultVO.success("修改成功");
							}
							return ResultVO.failure("修改失败", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
						} else {
							// 已修改联系电话
							LinkUser queryByPhone = new LinkUser();
							queryByPhone.setLinkPhone(linkUser.getLinkPhone());
							List<LinkUser> listByPhone = linkUserDao.findListByQuery(queryByPhone);
							if (listByPhone != null && !listByPhone.isEmpty()) {
								// 电话号码已存在
								return ResultVO.failure("手机号码已存在", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
							} else {
								// 电话号码不存在
								linkUser.setUser(user);
								linkUser.setModifyDate(new Date());
								int count = linkUserDao.update(linkUser);
								if (count > 0) {
									return ResultVO.success("修改成功");
								}
								return ResultVO.failure("修改失败", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
							}
						}
					}
				}
			}
			return ResultVO.failure("查无此人，修改失败", ConstantUtils.SYSTEM_UPDATE_LINK_USER_FALSE);
		}
		return ResultVO.failure("登陆已过期", ConstantUtils.SYSTEM_NO_TOKEN);
	}

	/**
	 * <b>删除常用联系人</b>
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO delUserLinkUser(long[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			LinkUser query = new LinkUser();
			query.setId(args[i]);
			LinkUser user = linkUserDao.findListByQuery(query).get(0);
			List<LinkUser> list = linkUserDao.findListInOrder(user.getLinkUserName());
			if (list != null && !list.isEmpty()) {
				// 联系人与未支付的订单有关联
				return ResultVO.failure("存在未支付的订单", ConstantUtils.SYSTEM_DELETE_FALSE_LINK);
			} else {
				int count = linkUserDao.delete(args[i]);
				if (count <= 0) {
					return ResultVO.failure("删除失败", ConstantUtils.SYSTEM_DELETE_LINK_USER_FALSE);
				}
			}
		}
		return ResultVO.success();
	}
}
