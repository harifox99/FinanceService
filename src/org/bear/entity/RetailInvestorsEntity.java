package org.bear.entity;

import java.util.Date;
/**
 * 散戶指標Entity
 * @author edward
 *
 */
public class RetailInvestorsEntity {
	/**
	 * 交易日期
	 */
	Date exchangeDate;
	/**
	 * 三大法人小台指未平倉餘額
	 */
	int institutionalMtx;
	/**
	 * 小台指未平倉餘額
	 */
	int totalMtx;
	/**
	 * 散戶指標比率
	 */
	double retailRate;
	/**
	 * 收盤價
	 */
	double twseIndex;
	/**
	 * 座標用的日期
	 */
	String axisDate;
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public int getInstitutionalMtx() {
		return institutionalMtx;
	}
	public void setInstitutionalMtx(int institutionalMtx) {
		this.institutionalMtx = institutionalMtx;
	}
	public int getTotalMtx() {
		return totalMtx;
	}
	public void setTotalMtx(int totalMtx) {
		this.totalMtx = totalMtx;
	}
	public double getRetailRate() {
		return retailRate;
	}
	public void setRetailRate(double retailRate) {
		this.retailRate = retailRate;
	}
	public double getTwseIndex() {
		return twseIndex;
	}
	public void setTwseIndex(double twseIndex) {
		this.twseIndex = twseIndex;
	}
	public String getAxisDate() {
		return axisDate;
	}
	public void setAxisDate(String axisDate) {
		this.axisDate = axisDate;
	}
	
}
