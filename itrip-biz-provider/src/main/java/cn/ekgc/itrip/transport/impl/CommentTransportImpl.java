package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.service.CommentService;
import cn.ekgc.itrip.transport.biz.CommentTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-评论模块传输层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("commentTransport")
@RequestMapping("/comment/transport")
public class CommentTransportImpl implements CommentTransport {
	@Autowired
	private CommentService commentService;

	/**
	 * <b>根据酒店主键查询总体评价分数</b>
	 * @param hotelId
	 * @return
	 */
	@PostMapping("/getTotalScore")
	public ScoreCommentVO getScoreCommentVOByHotelId(@RequestParam Long hotelId) throws Exception {
		return commentService.getScoreCommentVOByHotelId(hotelId);
	}

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getCount")
	public CommentCountVO getCommentCountByHotelId(@RequestParam Long hotelId) throws Exception {
		return commentService.getCommentCountByHotelId(hotelId);
	}

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getcommentlist")
	@Override
	public Page getCommentList(@RequestBody SearchCommentVO searchCommentVO) throws Exception {
		return commentService.getCommentList(searchCommentVO);
	}

	/**
	 * <b>新增评论</b>
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add")
	@Override
	public boolean add(@RequestBody ItripAddCommentVO addCommentVO, @RequestParam String token) throws Exception {
		return commentService.add(addCommentVO, token);
	}
}
