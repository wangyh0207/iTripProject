package cn.ekgc.itrip.controller;

import cn.ekgc.itrip.base.controller.BaseController;
import cn.ekgc.itrip.base.pojo.vo.ResultVO;
import cn.ekgc.itrip.pojo.entity.Hotel;
import cn.ekgc.itrip.pojo.entity.Image;
import cn.ekgc.itrip.pojo.entity.LabelDic;
import cn.ekgc.itrip.pojo.vo.*;
import cn.ekgc.itrip.transport.biz.CommentTransport;
import cn.ekgc.itrip.transport.biz.HotelTransport;
import cn.ekgc.itrip.transport.biz.ImageTransport;
import cn.ekgc.itrip.transport.biz.LabelDicTransport;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * <b>爱旅行-评论模块控制器</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("commentController")
@RequestMapping("/biz/api/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentTransport commentTransport;
	@Autowired
	private ImageTransport imageTransport;
	@Autowired
	private HotelTransport hotelTransport;
	@Autowired
	private LabelDicTransport labelDicTransport;


	/**
	 * <b>据酒店id查询酒店平均分</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/gethotelscore/{hotelId}")
	public ResultVO getHotelScore(@PathVariable("hotelId") Long hotelId) throws Exception {
		// 根据酒店主键查询该酒店总分数
		ScoreCommentVO scoreCommentVO = commentTransport.getScoreCommentVOByHotelId(hotelId);
		return ResultVO.success(scoreCommentVO);
	}

	/**
	 * <b>根据酒店id查询各类评论数量</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getcount/{hotelId}")
	public ResultVO queryCommentCount(@PathVariable("hotelId") Long hotelId) throws Exception {
		CommentCountVO commentCountVO = commentTransport.getCommentCountByHotelId(hotelId);
		return ResultVO.success(commentCountVO);
	}

	/**
	 * <b>根据评论类型查询评论列表，并分页显示</b>
	 * @param searchCommentVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getcommentlist")
	public ResultVO getCommentList(@RequestBody SearchCommentVO searchCommentVO) throws Exception {
		Page page = commentTransport.getCommentList(searchCommentVO);
		return ResultVO.success(page);
	}

	/**
	 * <b>根据targetId查询评论照片</b>
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getimg/{targetId}")
	public ResultVO getImg(@PathVariable("targetId") Long targetId) throws Exception {
		Image query = new Image();
		query.setType(2);
		query.setTargetId(targetId);
		List<ImageVO> list = imageTransport.getImageListByQuery(query);
		return ResultVO.success(list);
	}

	/**
	 * <b>获取酒店相关信息（酒店名称、酒店星级）</b>
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/gethoteldesc/{hotelId}")
	public ResultVO getHotelDesc(@PathVariable("hotelId") Long hotelId) throws Exception {
		Hotel hotel = hotelTransport.getHotelById(hotelId);
		ItripHotelDescVO hotelDescVO = new ItripHotelDescVO();
		hotelDescVO.setHotelId(hotelId);
		hotelDescVO.setHotelLevel(hotel.getHotelLevel());
		hotelDescVO.setHotelName(hotel.getHotelName());
		return ResultVO.success(hotelDescVO);
	}

	/**
	 * <b>查询出游类型列表</b>
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/gettraveltype")
	public ResultVO getTravelType() throws Exception {
		LabelDic parent = new LabelDic();
		parent.setId(107L);
		LabelDic query = new LabelDic();
		query.setParent(parent);
		List<LabelDic> list = labelDicTransport.getLabelDicListByQuery(query);
		return ResultVO.success(list);
	}

	/**
	 * <b>上传图片</b>
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/upload")
	public ResultVO upLoad(@RequestParam("file") MultipartFile file) throws Exception {
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png")) {
			// 对文件进行重命名
			fileName = System.currentTimeMillis() + suffix;
			// 保存对象
			File file1 = new File("/Users/wyh/IdeaProjects/KJ00301/GraduationProject/upload" + File.separator + fileName);
			// 创建输出流
			OutputStream out = new FileOutputStream(file1);
			// 通过 MultipartFile 对象获得文件输入流
			InputStream in = file.getInputStream();
			// 将数据写入到输出流
			IOUtils.copy(in, out);
			return ResultVO.success(fileName);
		}
		return ResultVO.failure("文件格式错误", "100008");
	}

	/**
	 * <b>新增评论</b>
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/add")
	public ResultVO add(@RequestBody ItripAddCommentVO addCommentVO) throws Exception {
		String token = request.getHeader("token");
		boolean flag = commentTransport.add(addCommentVO, token);
		if (flag) {
			return ResultVO.success();
		}
		return ResultVO.failure("新增评论失败", "100003");
	}
}
