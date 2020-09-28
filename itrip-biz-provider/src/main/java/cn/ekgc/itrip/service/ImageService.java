package cn.ekgc.itrip.service;

import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.ImageVO;

import java.util.List;

public interface ImageService {

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<ImageVO> getImageListByQuery(Image query) throws Exception;
}
