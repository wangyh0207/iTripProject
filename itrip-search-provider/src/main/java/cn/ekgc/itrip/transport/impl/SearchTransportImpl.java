package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.pojo.vo.HotelVO;
import cn.ekgc.itrip.pojo.vo.SearchHotCityVO;
import cn.ekgc.itrip.service.SearchService;
import cn.ekgc.itrip.transport.search.SearchTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b>搜索功能传输层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("searchTransport")
@RequestMapping("/search/transport")
public class SearchTransportImpl implements SearchTransport {
	@Autowired
	private SearchService searchService;

	/**
	 * <b>根据热门城市查询酒店列表</b>
	 * @param searchHotCityVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/hotellist/searchItripHotelListByHotCity")
	@Override
	public List<HotelVO> searchHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO) throws Exception {
		return searchService.searchHotelListByHotCity(searchHotCityVO);
	}
}
