package cn.ekgc.itrip.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>酒店各类评分VO</b>
 * @author wyh
 * @version 1.0.0
 * @since 1.0.0
 */
public class ScoreCommentVO implements Serializable {
	private static final long serialVersionUID = -2419584127722711607L;
	private String avgPositionScore;//点评查询页面酒店的位置得分
	private String  avgFacilitiesScore;//点评查询页面酒店的设施得分
	private String  avgServiceScore;//点评查询页面酒店的服务得分
	private String  avgHygieneScore;//点评查询页面酒店的卫生得分
	private String  avgScore;//点评查询页面酒店的总体得分

	public String getAvgPositionScore() {
		return avgPositionScore;
	}

	public void setAvgPositionScore(String avgPositionScore) {
		this.avgPositionScore = avgPositionScore;
	}

	public String getAvgFacilitiesScore() {
		return avgFacilitiesScore;
	}

	public void setAvgFacilitiesScore(String avgFacilitiesScore) {
		this.avgFacilitiesScore = avgFacilitiesScore;
	}

	public String getAvgServiceScore() {
		return avgServiceScore;
	}

	public void setAvgServiceScore(String avgServiceScore) {
		this.avgServiceScore = avgServiceScore;
	}

	public String getAvgHygieneScore() {
		return avgHygieneScore;
	}

	public void setAvgHygieneScore(String avgHygieneScore) {
		this.avgHygieneScore = avgHygieneScore;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}
}
