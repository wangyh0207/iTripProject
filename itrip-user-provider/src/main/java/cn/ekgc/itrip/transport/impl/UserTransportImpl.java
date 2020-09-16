package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.service.UserService;
import cn.ekgc.itrip.transport.user.UserTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-用户模块传输层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("userTransport")
@RequestMapping("/user/transport")
public class UserTransportImpl implements UserTransport {
	@Autowired
	private UserService userService;

	/**
	 * <b>根据用户名校验该用户名是否被使用</b>
	 * @param userCode
	 * @return true-未被占用
	 * @throws Exception
	 */
	@PostMapping("/ckusr")
	@Override
	public boolean queryUserCodeIsCanUsed(@RequestParam String userCode) throws Exception {
		return userService.queryUserCodeIsCanUsed(userCode);
	}

	/**
	 * <b>注册用户</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/doregister")
	@Override
	public boolean saveUser(@RequestBody User user) throws Exception {
		return userService.saveUser(user);
	}


	/**
	 * <b>使用激活码激活用户</b>
	 * @param userCode
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/activate")
	@Override
	public ResultVO activateUser(@RequestParam String userCode, @RequestParam String code) throws Exception {
		return userService.activateUser(userCode, code);
	}

	/**
	 * <b>使用 token 查找当前登陆用户</b>
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/token")
	@Override
	public User getUserByToken(@RequestParam String token) throws Exception {
		return userService.getUserByToken(token);
	}

	/**
	 * <b>使用 userCode 和 password 进行登陆</b>
	 * @param userCode
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	@Override
	public ResultVO loginUser(@RequestParam String userCode, @RequestParam String password) throws Exception {
		return userService.loginUser(userCode, password);
	}

	/**
	 * <b>退出登陆</b>
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/logout")
	@Override
	public ResultVO logoutUser(@RequestParam String token) throws Exception {
		return userService.logoutUser(token);
	}
}
