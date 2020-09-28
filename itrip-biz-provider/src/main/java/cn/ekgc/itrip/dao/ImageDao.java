package cn.ekgc.itrip.dao;

import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.ImageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<ImageVO> findListByQuery(Image query) throws Exception;
}
