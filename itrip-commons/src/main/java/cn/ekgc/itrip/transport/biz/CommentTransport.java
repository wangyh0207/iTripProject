package cn.ekgc.itrip.transport.biz;

import cn.ekgc.itrip.pojo.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <b>爱旅行-评论模块传输层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient("itrip-biz-provider")
@RequestMapping("/comment/transport")
public interface CommentTransport {

	/**
	 * <b>根据酒店主键查询总体评价分数</b>
	 * @param hotelId
	 * @return
	 */
	@PostMapping("/getTotalScore")
	ScoreCommentVO getScoreCommentVOByHotelId(@RequestParam Long hotelId) throws Exception;

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getCount")
	CommentCountVO getCommentCountByHotelId(@RequestParam Long hotelId) throws Exception;

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getcommentlist")
	Page getCommentList(@RequestBody SearchCommentVO searchCommentVO) throws Exception;
}
