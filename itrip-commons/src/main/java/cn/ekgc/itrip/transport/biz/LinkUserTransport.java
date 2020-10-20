package cn.ekgc.itrip.transport.biz;

import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.LinkUser;
import cn.ekgc.itrip.pojo.vo.ValidateRoomStoreVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-联系人模块传输层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/linkUser/transport")
public interface LinkUserTransport {
	/**
	 * <b>查询常用联系人</b>
	 * @param linkUserName
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/queryuserlinkuser")
	ResultVO getListByQuery(@RequestParam String linkUserName, @RequestParam String token) throws Exception;

	/**
	 * <b>根据查询条件查询对象</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/query")
	boolean getLinkUserByQuery(@RequestBody LinkUser query) throws Exception;

	/**
	 * <b>添加常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 */
	@PostMapping("/adduserlinkuser")
	ResultVO addUserLinkUser(@RequestBody LinkUser linkUser, @RequestParam String token) throws Exception;

	/**
	 * <b>修改常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/modifyuserlinkuser")
	ResultVO updateLinkUser(@RequestBody LinkUser linkUser, @RequestParam String token) throws Exception;

	/**
	 * <b>删除常用联系人</b>
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/deluserlinkuser")
	ResultVO delUserLinkUser(@RequestParam long[] args) throws Exception;

	/**
	 * <b>根据订单信息查询联系人</b>
	 * @param validateRoomStoreVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/querybyorder")
	ResultVO getListByOrder(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) throws Exception;
}