package cn.ekgc.itrip.transport.impl;


import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.LinkUser;
import cn.ekgc.itrip.service.LinkUserService;
import cn.ekgc.itrip.transport.biz.LinkUserTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-联系人模块传输层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("linkUserTransport")
@RequestMapping("/linkUser/transport")
public class LinkUserTransportImpl implements LinkUserTransport {
	@Autowired
	private LinkUserService linkUserService;

	/**
	 * <b>查询常用联系人</b>
	 * @param linkUserName
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/queryuserlinkuser")
	@Override
	public ResultVO getListByQuery(@RequestParam String linkUserName, @RequestParam String token) throws Exception {
		return linkUserService.getListByQuery(linkUserName, token);
	}

	/**
	 * <b>根据查询条件查询对象</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/query")
	public boolean getLinkUserByQuery(@RequestBody LinkUser query) throws Exception {
		return linkUserService.getLinkUserByQuery(query);
	}

	/**
	 * <b>添加常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 */
	@PostMapping("/adduserlinkuser")
	public ResultVO addUserLinkUser(@RequestBody LinkUser linkUser, @RequestParam String token) throws Exception {
		return linkUserService.addUserLinkUser(linkUser, token);
	}

	/**
	 * <b>修改常用联系人信息</b>
	 * @param linkUser
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/modifyuserlinkuser")
	public ResultVO updateLinkUser(@RequestBody LinkUser linkUser, @RequestParam String token) throws Exception {
		return linkUserService.updateLinkUser(linkUser, token);
	}

	/**
	 * <b>删除常用联系人</b>
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/deluserlinkuser")
	public ResultVO delUserLinkUser(@RequestParam long[] args) throws Exception {
		return linkUserService.delUserLinkUser(args);
	}
}
