package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.LinkUser;
import cn.ekgc.itrip.pojo.vo.AddLinkUserVO;
import cn.ekgc.itrip.pojo.vo.ModifyLinkUserVO;
import cn.ekgc.itrip.pojo.vo.SearchUserVO;
import cn.ekgc.itrip.pojo.vo.ValidateRoomStoreVO;
import cn.ekgc.itrip.transport.biz.LinkUserTransport;
import cn.ekgc.itrip.util.ConstantUtils;
import cn.ekgc.itrip.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

/**
 * <b>爱旅行-个人信息功能模块控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("linkUserController")
@RequestMapping("/biz/api/userinfo")
public class LinkUserController extends BaseController {
	@Autowired
	private LinkUserTransport linkUserTransport;

	/**
	 * <b>查询常用联系人</b>
	 * @param searchUserVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/queryuserlinkuser")
	public ResultVO queryUserLinkUser(@RequestBody SearchUserVO searchUserVO) throws Exception {
		String token = request.getHeader("token");
		if (searchUserVO.getLinkUserName() == null) {
			searchUserVO.setLinkUserName("");
		}
		return linkUserTransport.getListByQuery(searchUserVO.getLinkUserName(), token);
	}

	/**
	 * <b>添加常用联系人信息</b>
	 * @param addLinkUserVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/adduserlinkuser")
	public ResultVO addUserLinkUser(@RequestBody AddLinkUserVO addLinkUserVO) throws Exception {
		String token = request.getHeader("token");
		String linkUserName = addLinkUserVO.getLinkUserName();
		String linkIdCard = addLinkUserVO.getLinkIdCard();
		String linkPhone = addLinkUserVO.getLinkPhone();
		Integer linkIdCardType = addLinkUserVO.getLinkIdCardType();
		if (linkUserName != null && !"".equals(linkUserName) && ValidateUtil.checkCellphone(linkPhone)
				&& ValidateUtil.checkIdCard(linkIdCard)) {
			// 提交信息都不为空且格式正确
			LinkUser queryByName = new LinkUser();
			queryByName.setLinkUserName(linkUserName);
			LinkUser queryByIdCard = new LinkUser();
			queryByIdCard.setLinkIdCard(linkIdCard);
			if (linkUserTransport.getLinkUserByQuery(queryByName) || linkUserTransport.getLinkUserByQuery(queryByIdCard)) {
				// 用户已存在
				return ResultVO.failure("手机号码或身份证号码重复", ConstantUtils.SYSTEM_ADD_LINK_USER_FALSE);
			} else {
				// 用户不存在
				LinkUser linkUser = new LinkUser();
				linkUser.setLinkUserName(linkUserName);
				linkUser.setLinkIdCard(linkIdCard);
				linkUser.setLinkPhone(linkPhone);
				linkUser.setLinkIdCardType(linkIdCardType);
				return linkUserTransport.addUserLinkUser(linkUser, token);
			}
		}
		return ResultVO.failure("请填写正确的手机号码及身份证号码", ConstantUtils.SYSTEM_ADD_NULL);
	}

	/**
	 * <b>修改常用联系人信息</b>
	 * @param modifyLinkUserVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/modifyuserlinkuser")
	public ResultVO modifyUserLinkUser(@RequestBody ModifyLinkUserVO modifyLinkUserVO) throws Exception {
		String linkUserName = modifyLinkUserVO.getLinkUserName();
		String linkIdCard = modifyLinkUserVO.getLinkIdCard();
		String linkPhone = modifyLinkUserVO.getLinkPhone();
		Long id = modifyLinkUserVO.getId();
		if (linkUserName != null && !"".equals(linkUserName) && ValidateUtil.checkIdCard(linkIdCard)
				&& ValidateUtil.checkCellphone(linkPhone) && id != null) {
			// 用户提交信息有效
			String token = request.getHeader("token");
			LinkUser linkUser = new LinkUser();
			linkUser.setId(id);
			linkUser.setLinkUserName(linkUserName);
			linkUser.setLinkIdCard(linkIdCard);
			linkUser.setLinkIdCardType(modifyLinkUserVO.getLinkIdCardType());
			linkUser.setLinkPhone(linkPhone);
			return linkUserTransport.updateLinkUser(linkUser, token);
		}
		return ResultVO.failure("请填写正确的信息", ConstantUtils.SYSTEM_UPDATE_NULL);
	}

	/**
	 * <b>删除常用联系人</b>
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/deluserlinkuser")
	public ResultVO delUserLinkUser(long[] ids) throws Exception {
		if (ids != null) {
			return linkUserTransport.delUserLinkUser(ids);
		}
		return ResultVO.failure("未选择删除人", ConstantUtils.SYSTEM_DELETE_NULL);
	}
}
