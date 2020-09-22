package cn.ekgc.itrip.transport.impl;

import cn.ekgc.itrip.transport.biz.CommentTransport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>爱旅行-评论模块传输层接口实现类</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController("commentTransport")
@RequestMapping("/comment/transport")
public class CommentTransportImpl implements CommentTransport {
}
