package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.ImageVO;
import cn.ekgc.itrip.service.ImageService;
import cn.ekgc.itrip.transport.biz.ImageTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("imageTransportController")
@RequestMapping("/image/transport")
public class ImageTransportImpl implements ImageTransport {
	@Autowired
	private ImageService imageService;

	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Override
	@RequestMapping("/getimg")
	public List<ImageVO> getImageListByQuery(@RequestBody Image query) throws Exception {
		return imageService.getImageListByQuery(query);
	}
}
