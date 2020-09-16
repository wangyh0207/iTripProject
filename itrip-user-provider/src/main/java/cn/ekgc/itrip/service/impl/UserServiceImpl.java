package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.dao.UserDao;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.enums.ActivatedEnum;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>爱旅行-用户模块业务层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private EmailUtil emailUtil;
	@Autowired
	private SmsUtil smsUtil;

	/**
	 * <b>根据用户名校验该用户名是否被使用</b>
	 * @param userCode
	 * @return true-未被占用
	 * @throws Exception
	 */
	@Override
	public boolean queryUserCodeIsCanUsed(String userCode) throws Exception {
		// 封装查询对象
		User query = new User();
		query.setUserCode(userCode);
		// 进行查询
		List<User> list = userDao.findListByQuery(query);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * <b>保存用户信息</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean saveUser(User user) throws Exception {
		// 保存用户信息
		Integer count = userDao.save(user);
		if (count > 0) {
			// 产生随机激活码
			String cdk = CDKUtil.generate();
			// 判断用户此时使用的是邮箱还是手机号码
			if (user.getUserCode().indexOf("@") > -1) {
				// 此时使用的是邮箱地址
				// 发送邮件
				String context = "<div style='width: 600px; margin: 0 auto;'>" +
						"<h3>尊敬的:<strong>"+ user.getUserCode() +"</h3>" +
						"<p>您已经成功注册成为<strong>爱旅行</strong>会员！</p>" +
						"<p>只剩下最后一步，激活您的账号，您的激活码是:<strong style='color: red;'>" + cdk + "</strong></p>" +
						"<p>请在<strong>" + ConstantUtils.MAIL_EXPIRE + "</strong>分钟之内进行账号激活！</p></div>";
				emailUtil.sendMail(user.getUserCode(), "爱旅行用户激活", context);
			} else {
				// 使用的是手机号码
				smsUtil.sendActivationCodeCloopen(user.getUserCode(), cdk);
			}
			// 将激活码存储于 Redis 中，使用 userCode 作为 key
			redisUtil.saveToRedis(user.getUserCode(), cdk, ConstantUtils.MAIL_EXPIRE);
			System.out.println(cdk);
			return true;
		}
		return false;
	}


	/**
	 * <b>使用激活码激活用户</b>
	 * @param userCode
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO activateUser(String userCode, String code) throws Exception {
		// 通过使用 userCode 作为 key 从 redis 中查询用户信息
		String cdk = (String) redisUtil.getFromRedis(userCode, String.class);
		if (cdk != null) {
			// 找到对应的激活码，和用户提交的信息进行比较
			if (cdk.equals(code)) {
				// 查询此时的用户对象
				User query = new User();
				query.setUserCode(userCode);
				User user = userDao.findListByQuery(query).get(0);
				// 设定为激活状态
				user.setActivated(ActivatedEnum.ACTIVATED_TRUE.getCode());
				user.setModifyDate(new Date());
				userDao.update(user);
				return ResultVO.success();
			}
			return ResultVO.failure("验证码错误");
		} else {
			// 激活码过期
			// 产生随机激活码
			String reCDK = CDKUtil.generate();
			// 判断用户此时使用的是邮箱还是手机号码
			if (userCode.indexOf("@") > -1) {
				// 此时使用的是邮箱地址
				// 发送邮件
				String context = "<div style='width: 600px; margin: 0 auto;'>" +
						"<h3>尊敬的:<strong>"+ userCode +"</h3>" +
						"<p>您已经成功注册成为<strong>爱旅行</strong>会员！</p>" +
						"<p>只剩下最后一步，激活您的账号，您的激活码是:<strong style='color: red;'>" + reCDK + "</strong></p>" +
						"<p>请在<strong>" + ConstantUtils.MAIL_EXPIRE + "</strong>分钟之内进行账号激活！</p></div>";
				emailUtil.sendMail(userCode, "爱旅行用户激活", context);
			} else {
				// 使用的是手机号码
				smsUtil.sendActivationCodeCloopen(userCode, reCDK);
			}
			// 将激活码存储于 Redis 中，使用 userCode 作为 key
			redisUtil.saveToRedis(userCode, reCDK, ConstantUtils.MAIL_EXPIRE);
			System.out.println(reCDK);
			return ResultVO.failure("验证码已过期，请注意查收新的验证码");
		}
	}

	/**
	 * <b>使用 token 查找当前登陆用户</b>
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@Override
	public User getUserByToken(String token) throws Exception {
		// 通过 redis 进行查询
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			return user;
		}
		return null;
	}

	/**
	 * <b>使用 userCode 和 password 进行登陆</b>
	 * @param userCode
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO loginUser(String userCode, String password) throws Exception {
		User query = new User();
		query.setUserCode(userCode);
		query.setUserPassword(MD5Util.encrypt(password));
		List<User> list = userDao.findListByQuery(query);
		if (list != null && !list.isEmpty()) {
			User user = list.get(0);
			// 登陆成功，检查用户是否处于激活状态
			if (user.getActivated() == ActivatedEnum.ACTIVATED_TRUE.getCode()) {
				// 设定用户登陆有效期
				Map<String, Object> resultMap = new HashMap<String, Object>();
				// 设置 token，使用 MD5 对用户的 userCode 进行加密，并且全部变为大写
				String token = MD5Util.encrypt(user.getUserCode()).toUpperCase();
				// 将 token 作为 key 存储于 redis
				redisUtil.saveToRedis(token, user, ConstantUtils.LOGIN_EXPIRE);
				resultMap.put("token", token);
				// 设置过期时间
				resultMap.put("expTime", ConstantUtils.LOGIN_EXPIRE);
				return ResultVO.success(resultMap);
			} else {
				// 未激活
				return ResultVO.failure("请激活后在登陆");
			}
		} else {
			// 登陆失败
			return ResultVO.failure("请填写正确的登陆账号及密码");
		}
	}

	/**
	 * <b>退出登陆</b>
	 * @return
	 * @throws Exception
	 */
	@Override
	public ResultVO logoutUser(String token) throws Exception {
		redisUtil.deleteFromRedis(token);
		return ResultVO.success();
	}
}
