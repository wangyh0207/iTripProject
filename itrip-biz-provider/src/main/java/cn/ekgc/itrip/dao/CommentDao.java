package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>爱旅行-评论模块数据持久层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface CommentDao {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<Comment> findListByQuery(Comment query) throws Exception;
}
