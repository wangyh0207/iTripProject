package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.CommentDao;
import cn.ekgc.itrip.dao.HotelOrderDao;
import cn.ekgc.itrip.pojo.entity.Comment;
import cn.ekgc.itrip.pojo.entity.HotelOrder;
import cn.ekgc.itrip.pojo.entity.User;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.service.CommentService;
import cn.ekgc.itrip.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * <b>爱旅行-评论模块业务层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private HotelOrderDao hotelOrderDao;

	/**
	 * <b>根据酒店主键查询总体评价分数</b>
	 * @param hotelId
	 * @return
	 */
	@Override
	public ScoreCommentVO getScoreCommentVOByHotelId(Long hotelId) throws Exception {
		// 封装查询对象
		Comment query = new Comment();
		query.setHotelId(hotelId);
		List<Comment> list = commentDao.findListByQuery(query);
		// 循环集合，整合分数
		ScoreCommentVO scoreCommentVO = new ScoreCommentVO();

		Double positionScore = 0.0;//点评查询页面酒店的位置得分
		Double facilitiesScore = 0.0;//点评查询页面酒店的设施得分
		Double serviceScore = 0.0;//点评查询页面酒店的服务得分
		Double hygieneScore = 0.0;//点评查询页面酒店的卫生得分
		Double score = 0.0;//点评查询页面酒店的总体得分

		DecimalFormat df = new DecimalFormat("0.0#");

		if (list != null && list.size() > 0) {
			for (Comment comment : list) {
				// 位置评分
				positionScore = positionScore + comment.getPositionScore();
				// 设施评分
				facilitiesScore = facilitiesScore + comment.getFacilitiesScore();
				// 服务评分
				serviceScore = serviceScore + comment.getServiceScore();
				// 卫生评分
				hygieneScore = hygieneScore + comment.getHygieneScore();
				// 总体评分
				score = score + comment.getScore();
			}
			// 计算平均分
			scoreCommentVO.setAvgPositionScore(df.format(new BigDecimal(positionScore / list.size()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			scoreCommentVO.setAvgFacilitiesScore(df.format(new BigDecimal(facilitiesScore / list.size()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			scoreCommentVO.setAvgServiceScore(df.format(new BigDecimal(serviceScore / list.size()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			scoreCommentVO.setAvgHygieneScore(df.format(new BigDecimal(hygieneScore / list.size()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			scoreCommentVO.setAvgScore(df.format(new BigDecimal(score / list.size()).setScale(1, BigDecimal.ROUND_HALF_UP)));
			return scoreCommentVO;
		} else {
			scoreCommentVO.setAvgPositionScore(df.format(new BigDecimal(0)));
			scoreCommentVO.setAvgFacilitiesScore(df.format(new BigDecimal(0)));
			scoreCommentVO.setAvgServiceScore(df.format(new BigDecimal(0)));
			scoreCommentVO.setAvgHygieneScore(df.format(new BigDecimal(0)));
			scoreCommentVO.setAvgScore(df.format(new BigDecimal(0)));
			return scoreCommentVO;
		}
	}

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@Override
	public CommentCountVO getCommentCountByHotelId(Long hotelId) throws Exception {
		// 创建 CommentCountVO 对象
		CommentCountVO commentCountVO = new CommentCountVO();
		// 查询总评论
		Comment query = new Comment();
		query.setHotelId(hotelId);
		List<Comment> list = commentDao.findListByQuery(query);
		if (list != null && !list.isEmpty()) {
			commentCountVO.setAllcomment(list.size());
			// 查询 值得推荐
			query.setIsOk(1);
			list = commentDao.findListByQuery(query);
			if (list != null && !list.isEmpty()) {
				commentCountVO.setIsok(list.size());
			} else {
				commentCountVO.setIsok(0);
			}
			// 查询值得改善
			query.setIsOk(0);
			list = commentDao.findListByQuery(query);
			if (list != null && !list.isEmpty()) {
				commentCountVO.setImprove(list.size());
			} else {
				commentCountVO.setImprove(0);
			}
			// 查询是否有图片
			query.setIsOk(null);
			query.setIsHavingImg(1);
			list = commentDao.findListByQuery(query);
			if (list != null && !list.isEmpty()) {
				commentCountVO.setHavingimg(list.size());
			} else {
				commentCountVO.setHavingimg(0);
			}
		} else {
			commentCountVO.setAllcomment(0);
			commentCountVO.setHavingimg(0);
			commentCountVO.setImprove(0);
			commentCountVO.setIsok(0);
		}
		return commentCountVO;
	}

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page getCommentList(SearchCommentVO searchCommentVO) throws Exception {
		Page page = new Page(searchCommentVO.getPageNo(), searchCommentVO.getPageSize());
		Comment query = new Comment();
		query.setHotelId(searchCommentVO.getHotelId());
		if (searchCommentVO.getIsHavingImg()==1 && searchCommentVO.getIsOk()==-1) {
			query.setHotelId(searchCommentVO.getHotelId());
			query.setIsHavingImg(1);
		} else if (searchCommentVO.getIsHavingImg()==-1 && searchCommentVO.getIsOk()==1) {
			query.setHotelId(searchCommentVO.getHotelId());
			query.setIsOk(1);
		} else if (searchCommentVO.getIsHavingImg()==-1 && searchCommentVO.getIsOk()==0) {
			query.setHotelId(searchCommentVO.getHotelId());
			query.setIsOk(0);
		}
		PageHelper.startPage(page.getCurPage(), page.getPageSize());
		List<Comment> list = commentDao.findListByQuery(query);
		// 获得分页信息
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);
		Long total = pageInfo.getTotal();
		page = new Page(searchCommentVO.getPageNo(), searchCommentVO.getPageSize(), total);
		page.setRows(pageInfo.getList());
		return page;
	}

	/**
	 * <b>新增评论</b>
	 * @param addCommentVO
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean add(ItripAddCommentVO addCommentVO, String token) throws Exception {
		// 获得当前登陆用户
		User user = (User) redisUtil.getFromRedis(token, User.class);
		if (user != null) {
			Comment comment = new Comment();
			comment.setHotelId(addCommentVO.getHotelId());
			comment.setProductId(addCommentVO.getProductId());
			comment.setOrderId(addCommentVO.getOrderId());
			comment.setProductType(addCommentVO.getProductType());
			comment.setContent(addCommentVO.getContent());
			comment.setUserId(user.getId());
			comment.setIsHavingImg(addCommentVO.getIsHavingImg());
			comment.setPositionScore(addCommentVO.getPositionScore());
			comment.setFacilitiesScore(addCommentVO.getFacilitiesScore());
			comment.setServiceScore(addCommentVO.getServiceScore());
			comment.setHygieneScore(addCommentVO.getHygieneScore());
			Integer score = (addCommentVO.getPositionScore() + addCommentVO.getFacilitiesScore()
					+ addCommentVO.getServiceScore() + addCommentVO.getHygieneScore()) / 4;
			comment.setScore(score);
			comment.setTripMode(Integer.parseInt(addCommentVO.getTripMode()));
			comment.setIsOk(addCommentVO.getIsOk());
			comment.setCreationDate(new Date());
			comment.setCreatedBy(user.getId());
			int count = commentDao.save(comment);
			if (count > 0) {
				HotelOrder hotelOrder = new HotelOrder();
				hotelOrder.setId(addCommentVO.getOrderId());
				hotelOrder.setOrderStatus(4);
				hotelOrderDao.update(hotelOrder);
				return true;
			}
		}
		return false;
	}
}
