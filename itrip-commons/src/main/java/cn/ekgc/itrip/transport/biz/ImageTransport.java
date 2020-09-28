package cn.ekgc.itrip.transport.biz;

import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.vo.ImageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "itrip-biz-provider")
@RequestMapping("/image/transport")
public interface ImageTransport {
	/**
	 * <b>根据查询对象查询列表</b>
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getimg")
	List<ImageVO> getImageListByQuery(@RequestBody Image query) throws Exception;
}
