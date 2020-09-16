package cn.ekgc.itrip.transport.user;

import cn.ekgc.itrip.pojo.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>爱旅行-用户模块传输层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(name = "itrip-user-provider")
@RequestMapping("/user/transport")
public interface UserTransport {
	/**
	 * <b>根据用户名校验该用户名是否被使用</b>
	 * @param userCode
	 * @return true-未被占用
	 * @throws Exception
	 */
	@PostMapping("/ckusr")
	boolean queryUserCodeIsCanUsed(@RequestParam String userCode) throws Exception;

	/**
	 * <b>注册用户</b>
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/doregister")
	boolean saveUser(@RequestBody User user) throws Exception;

	/**
	 * <b>使用激活码激活用户</b>
	 * @param userCode
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/activate")
	boolean activateUser(@RequestParam String userCode, @RequestParam String code) throws Exception;

	/**
	 * <b>用户登陆</b>
	 * @param userCode
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/dologin")
	User queryUserForLogin(@RequestParam String userCode, @RequestParam String password) throws Exception;
}
