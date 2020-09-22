package cn.ekgc.itrip.transport.biz;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>爱旅行-评论模块传输层接口</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient("itrip-biz-provider")
@RequestMapping("/comment/transport")
public interface CommentTransport {
}
