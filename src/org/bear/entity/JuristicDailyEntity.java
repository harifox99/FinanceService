package org.bear.entity;

import java.util.Date;

/**
 * 每日法人成交資訊
 * @author edward
 *
 */
public class JuristicDailyEntity {
	/**
	 * 交易日期
	 */
	Date exchangeDate;
	/**
	 * 證交所外資買賣超金額
	 */
	long amount;
	/**
	 * 期交所外資未平倉口數
	 */
	int lot;
	/**
	 * 十大法人未沖銷部位，當月，買方
	 */
	int bigTenLotBuyMonth;
	/**
	 * 十大法人未沖銷部位，所有，買方
	 */
	int bigTenLotBuyTotal;
	/**
	 * 十大法人未沖銷部位，當月，賣方
	 */
	int bigTenLotSellMonth;
	/**
	 * 十大法人未沖銷部位，所有，賣方
	 */
	int bigTenLotSellTotal;
	/**
	 * 小台指外資未平倉口數
	 */
	int smallLot;
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getLot() {
		return lot;
	}
	public void setLot(int lot) {
		this.lot = lot;
	}
	public int getBigTenLotBuyMonth() {
		return bigTenLotBuyMonth;
	}
	public void setBigTenLotBuyMonth(int bigTenLotBuyMonth) {
		this.bigTenLotBuyMonth = bigTenLotBuyMonth;
	}
	public int getBigTenLotBuyTotal() {
		return bigTenLotBuyTotal;
	}
	public void setBigTenLotBuyTotal(int bigTenLotBuyTotal) {
		this.bigTenLotBuyTotal = bigTenLotBuyTotal;
	}
	public int getBigTenLotSellMonth() {
		return bigTenLotSellMonth;
	}
	public void setBigTenLotSellMonth(int bigTenLotSellMonth) {
		this.bigTenLotSellMonth = bigTenLotSellMonth;
	}
	public int getBigTenLotSellTotal() {
		return bigTenLotSellTotal;
	}
	public void setBigTenLotSellTotal(int bigTenLotSellTotal) {
		this.bigTenLotSellTotal = bigTenLotSellTotal;
	}
	public int getSmallLot() {
		return smallLot;
	}
	public void setSmallLot(int smallLot) {
		this.smallLot = smallLot;
	}
	
}
