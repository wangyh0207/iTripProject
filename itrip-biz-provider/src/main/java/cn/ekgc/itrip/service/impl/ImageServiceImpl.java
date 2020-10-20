package cn.ekgc.itrip.service.impl;

import cn.ekgc.itrip.dao.ImageDao;
import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.ImageVO;
import cn.ekgc.itrip.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageDao imageDao;

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ImageVO> getImageListByQuery(Image query) throws Exception {
		List<Image> imageList = imageDao.findListByQuery(query);
		List<ImageVO> list = new ArrayList<ImageVO>();
		for (Image image: imageList) {
			ImageVO imageVO = new ImageVO();
			imageVO.setImgUrl(image.getImgUrl());
			imageVO.setPosition(image.getPosition());
			list.add(imageVO);
		}
		return list;
	}
}
