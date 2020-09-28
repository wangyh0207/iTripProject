package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;

/**
 * <b>图片视图对象</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class ImageVO implements Serializable {
	private static final long serialVersionUID = 7222414383852959716L;
	private Integer position;//页面图片展现顺序
	private String imgUrl;//图片的URL访问路径

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
