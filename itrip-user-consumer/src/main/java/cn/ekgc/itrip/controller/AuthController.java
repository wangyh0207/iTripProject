package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.enums.ActivatedEnum;
import cn.ekgc.itrip.pojo.enums.UserTypeEnum;
import cn.ekgc.itrip.pojo.vo.UserVO;
import cn.ekgc.itrip.transport.user.UserTransport;
import cn.ekgc.itrip.util.MD5Util;
import cn.ekgc.itrip.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <b>爱旅行-用户认证功能控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("authController")
@RequestMapping("/auth/api")
public class AuthController extends BaseController {
	@Autowired
	private UserTransport userTransport;

	/**
	 * <b>验证用户名是否被占用</b>
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ckusr")
	public ResultVO checkName(String name) throws Exception {
		// 用于验证用户是否存在
		boolean isUsed = userTransport.queryUserCodeIsCanUsed(name);
		if (isUsed) {
			// 如果得到的结果为 true，则说明该用户名未被占用，可以使用
			return ResultVO.success();
		}
		return ResultVO.failure("该邮箱地址已被占用");
	}

	/**
	 * <b>使用邮箱注册</b>
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/doregister")
	public ResultVO registerUserByEmail(@RequestBody UserVO userVO) throws Exception {
		// 检验用户所提交的邮箱是否有效
		if (ValidateUtil.checkEmail(userVO.getUserCode())
				&& ValidateUtil.checkPassword(userVO.getUserPassword())) {
			if (userTransport.queryUserCodeIsCanUsed(userVO.getUserCode())) {
				// 进行用户保存
				// 将 UserVO 切换成 User
				User user = new User();
				user.setUserCode(userVO.getUserCode());
				user.setUserName(userVO.getUserName());
				user.setUserPassword(MD5Util.encrypt(userVO.getUserPassword()));
				// 用户类型
				user.setUserType(UserTypeEnum.USER_TYPE_SELF.getCode());
				user.setCreationDate(new Date());
				user.setModifyDate(new Date());
				user.setActivated(ActivatedEnum.ACTIVATED_FALSE.getCode());
				boolean saveFlag = userTransport.saveUser(user);
				if (saveFlag) {
					return ResultVO.success();
				} else {
					return ResultVO.failure("保存失败");
				}
			} else {
				return ResultVO.failure("该邮箱已被占用");
			}
		} else {
			return ResultVO.failure("请填写正确的邮箱地址和登陆密码");
		}
	}

	/**
	 * <b>使用手机号码注册</b>
	 * @param userVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/registerbyphone")
	public ResultVO registerUserByCellphone(@RequestBody UserVO userVO) throws Exception {
		// 检验用户所提交的邮箱是否有效
		if (ValidateUtil.checkCellphone(userVO.getUserCode())
				&& ValidateUtil.checkPassword(userVO.getUserPassword())) {
			if (userTransport.queryUserCodeIsCanUsed(userVO.getUserCode())) {
				// 进行用户保存
				// 将 UserVO 切换成 User
				User user = new User();
				user.setUserCode(userVO.getUserCode());
				user.setUserName(userVO.getUserName());
				user.setUserPassword(MD5Util.encrypt(userVO.getUserPassword()));
				// 用户类型
				user.setUserType(UserTypeEnum.USER_TYPE_SELF.getCode());
				user.setCreationDate(new Date());
				user.setModifyDate(new Date());
				user.setActivated(ActivatedEnum.ACTIVATED_FALSE.getCode());
				boolean saveFlag = userTransport.saveUser(user);
				if (saveFlag) {
					return ResultVO.success();
				} else {
					return ResultVO.failure("保存失败");
				}
			} else {
				return ResultVO.failure("该手机号码已被占用");
			}
		} else {
			return ResultVO.failure("请填写正确的手机号码和登陆密码");
		}
	}

	/**
	 * <b>邮箱注册用户激活</b>
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/activate")
	public ResultVO activateUserByEmail(String user, String code) throws Exception {
		if (ValidateUtil.checkEmail(user) && code != null && !"".equals(code)) {
			if (!userTransport.queryUserCodeIsCanUsed(user)) {
				// 用户存在
				// 通过使用用户信息和激活码进行激活
				return userTransport.activateUser(user, code);
			}
			return ResultVO.failure("当前用户未注册，请先注册");
		}
		return ResultVO.failure("请填写正确的激活信息");
	}

	/**
	 * <b>手机号码注册用户激活</b>
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/validatephone")
	public ResultVO activateUserByCellphone(String user, String code) throws Exception {
		if (ValidateUtil.checkCellphone(user) && code != null && !"".equals(code)) {
			if (!userTransport.queryUserCodeIsCanUsed(user)) {
				// 用户存在
				// 通过使用用户信息和激活码进行激活
				return userTransport.activateUser(user, code);
			}
			return ResultVO.failure("当前用户未注册，请先注册");
		}
		return ResultVO.failure("请填写正确的激活信息");
	}

	/**
	 * <b>用户登陆</b>
	 * @param name
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/dologin")
	public ResultVO userLogin(String name, String password) throws Exception {
		// 检验数据有效性
		if ((ValidateUtil.checkCellphone(name) || ValidateUtil.checkEmail(name))
				&& ValidateUtil.checkPassword(password)) {
			// 信息有效
			return userTransport.loginUser(name, password);
		}
		// 信息无效
		return ResultVO.failure("请填写有效的登陆信息");
	}

	/**
	 * <b>退出登陆</b>
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public ResultVO logout() throws Exception {
		String token = request.getHeader("token");
		return userTransport.logoutUser(token);
	}
}
