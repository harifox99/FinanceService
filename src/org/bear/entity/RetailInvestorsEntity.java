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
}
