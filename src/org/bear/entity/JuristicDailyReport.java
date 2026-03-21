package org.bear.entity;

/**
 * 每日法人成交資訊日報
 * @author edward
 *
 */
public class JuristicDailyReport extends JuristicDailyEntity {
	/**
	 * 外資大盤買賣超&期貨未平倉口數評論
	 */
	String foreignerComment;
	/**
	 * 十大法人未沖銷部位當月差額
	 */
	int topTenMonthDiff;
	/**
	 * 十大法人未沖銷部位下月差額
	 */
	int topTenNextDiff;
	public String getForeignerComment() {
		return foreignerComment;
	}
	public void setForeignerComment(String foreignerComment) {
		this.foreignerComment = foreignerComment;
	}
	public int getTopTenMonthDiff() {
		return topTenMonthDiff;
	}
	public void setTopTenMonthDiff(int topTenMonthDiff) {
		this.topTenMonthDiff = topTenMonthDiff;
	}
	public int getTopTenNextDiff() {
		return topTenNextDiff;
	}
	public void setTopTenNextDiff(int topTenNextDiff) {
		this.topTenNextDiff = topTenNextDiff;
	}
	
	
}
