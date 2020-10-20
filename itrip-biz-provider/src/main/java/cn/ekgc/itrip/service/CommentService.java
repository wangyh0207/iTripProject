package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.vo.*;

/**
 * <b>爱旅行-评论模块业务层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CommentService {

	/**
	 * <b>根据酒店主键查询总体评价分数</b>
	 * @param hotelId
	 * @return
	 */
	ScoreCommentVO getScoreCommentVOByHotelId(Long hotelId) throws Exception;

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	CommentCountVO getCommentCountByHotelId(Long hotelId) throws Exception;

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	Page getCommentList(SearchCommentVO searchCommentVO) throws Exception;

	/**
	 * <b>新增评论</b>
	 * @param addCommentVO
	 * @return
	 * @throws Exception
	 */
	boolean add(ItripAddCommentVO addCommentVO, String token) throws Exception;
}
