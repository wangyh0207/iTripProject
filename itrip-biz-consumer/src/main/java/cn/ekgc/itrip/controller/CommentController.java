package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.transport.biz.CommentTransport;
import cn.ekgc.itrip.transport.biz.ImageTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b>爱旅行-评论模块控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("commentController")
@RequestMapping("/biz/api/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentTransport commentTransport;
	@Autowired
	private ImageTransport imageTransport;

	/**
	 * <b>据酒店id查询酒店平均分</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/gethotelscore/{hotelId}")
	public ResultVO getHotelScore(@PathVariable("hotelId") Long hotelId) throws Exception {
		// 根据酒店主键查询该酒店总分数
		ScoreCommentVO scoreCommentVO = commentTransport.getScoreCommentVOByHotelId(hotelId);
		return ResultVO.success(scoreCommentVO);
	}

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getcount/{hotelId}")
	public ResultVO queryCommentCount(@PathVariable("hotelId") Long hotelId) throws Exception {
		CommentCountVO commentCountVO = commentTransport.getCommentCountByHotelId(hotelId);
		return ResultVO.success(commentCountVO);
	}

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getcommentlist")
	public ResultVO getCommentList(@RequestBody SearchCommentVO searchCommentVO) throws Exception {
		Page page = commentTransport.getCommentList(searchCommentVO);
		return ResultVO.success(page);
	}

	/**
	 * <b>根据targetId查询评论照片</b>
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getimg/{targetId}")
	public ResultVO getImg(@PathVariable("targetId") Long targetId) throws Exception {
		Image query = new Image();
		query.setType(2);
		query.setTargetId(targetId);
		List<ImageVO> list = imageTransport.getImageListByQuery(query);
		return ResultVO.success(list);
	}
}
