package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.HotelRoom;
import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.ImageVO;
import cn.ekgc.itrip.pojo.vo.SearchHotelRoomVO;
import cn.ekgc.itrip.transport.biz.HotelRoomTransport;
import cn.ekgc.itrip.transport.biz.ImageTransport;
import cn.ekgc.itrip.transport.biz.LabelDicTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>酒店房间模块控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("hotelRoomController")
@RequestMapping("/biz/api/hotelroom")
public class HotelRoomController extends BaseController {
	@Autowired
	private HotelRoomTransport hotelRoomTransport;
	@Autowired
	private LabelDicTransport labelDicTransport;
	@Autowired
	private ImageTransport imageTransport;

	/**
	 * <b>查询酒店房间列表</b>
	 * @param searchHotelRoomVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/queryhotelroombyhotel")
	public ResultVO queryRoomByHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception {
		// 判断结束时间是否早于开始时间
		if (searchHotelRoomVO.getEndDate().getTime() - searchHotelRoomVO.getStartDate().getTime() > 0) {
			// 查询酒店列表
			HotelRoom query = new HotelRoom();
			query.setHotelId(searchHotelRoomVO.getHotelId());
			query.setRoomBedTypeId(searchHotelRoomVO.getRoomBedTypeId());
			query.setPayType(searchHotelRoomVO.getPayType());
			List<List<HotelRoom>> list = new ArrayList<List<HotelRoom>>();
			List<HotelRoom> hotelRoomList = hotelRoomTransport.getListByQuery(query);
			// 将每个房间都独立存储成一个集合
			for (HotelRoom hotelRoom: hotelRoomList) {
				List<HotelRoom> roomList = Arrays.asList(new HotelRoom[] {hotelRoom});
				list.add(roomList);
			}
			return ResultVO.success(list);
		}
		return ResultVO.failure("入住时间不可大于退房时间");
	}

	/**
	 * <b>查询酒店床型列表</b>
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/queryhotelroombed")
	public ResultVO queryHotelRoomBed() throws Exception {
		LabelDic parent = new LabelDic();
		parent.setId(1L);
		LabelDic query = new LabelDic();
		query.setParent(parent);
		List<LabelDic> list = labelDicTransport.getLabelDicListByQuery(query);
		return ResultVO.success(list);
	}

	/**
	 * <b>根据targetId查询酒店房型图片(type=1)</b>
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getimg/{targetId}")
	public ResultVO getImg(@PathVariable("targetId") Long targetId) throws Exception {
		Image query = new Image();
		query.setType(1);
		query.setTargetId(targetId);
		List<ImageVO> list = imageTransport.getImageListByQuery(query);
		return ResultVO.success(list);
	}
}
