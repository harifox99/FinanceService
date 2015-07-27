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
	int totalLot;
	/**
	 * 十大法人未沖銷部位，當月，買方
	 */
	int topTenLotBuyMonth;
	/**
	 * 十大法人未沖銷部位，所有，買方
	 */
	int topTenLotBuyTotal;
	/**
	 * 十大法人未沖銷部位，當月，賣方
	 */
	int topTenLotSellMonth;
	/**
	 * 十大法人未沖銷部位，所有，賣方
	 */
	int topTenLotSellTotal;
	/**
	 * 小台指外資未平倉口數
	 */	
	int totalSmallLot;
	/**
	 * 外資新增台指期口數
	 */
	int newLot;
	/**
	 * 外資新增小台指口數
	 */
	int newSmallLot;
	/**
	 * 自營商賣權餘額
	 */
	int dealerPut;
	/**
	 * 自營商買權餘額
	 */
	int dealerCall;
	/**
	 * 台股收盤指數
	 */
	double twseIndex;
	/**
	 * 漲跌幅
	 */
	double change;
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
	public int getTopTenLotBuyMonth() {
		return topTenLotBuyMonth;
	}
	public void setTopTenLotBuyMonth(int topTenLotBuyMonth) {
		this.topTenLotBuyMonth = topTenLotBuyMonth;
	}
	public int getTopTenLotBuyTotal() {
		return topTenLotBuyTotal;
	}
	public void setTopTenLotBuyTotal(int topTenLotBuyTotal) {
		this.topTenLotBuyTotal = topTenLotBuyTotal;
	}
	public int getTopTenLotSellMonth() {
		return topTenLotSellMonth;
	}
	public void setTopTenLotSellMonth(int topTenLotSellMonth) {
		this.topTenLotSellMonth = topTenLotSellMonth;
	}
	public int getTopTenLotSellTotal() {
		return topTenLotSellTotal;
	}
	public void setTopTenLotSellTotal(int topTenLotSellTotal) {
		this.topTenLotSellTotal = topTenLotSellTotal;
	}
	public int getTotalLot() {
		return totalLot;
	}
	public void setTotalLot(int totalLot) {
		this.totalLot = totalLot;
	}
	public int getTotalSmallLot() {
		return totalSmallLot;
	}
	public void setTotalSmallLot(int totalSmallLot) {
		this.totalSmallLot = totalSmallLot;
	}
	public int getNewLot() {
		return newLot;
	}
	public void setNewLot(int newLot) {
		this.newLot = newLot;
	}
	public int getNewSmallLot() {
		return newSmallLot;
	}
	public void setNewSmallLot(int newSmallLot) {
		this.newSmallLot = newSmallLot;
	}
	public int getDealerPut() {
		return dealerPut;
	}
	public void setDealerPut(int dealerPut) {
		this.dealerPut = dealerPut;
	}
	public int getDealerCall() {
		return dealerCall;
	}
	public void setDealerCall(int dealerCall) {
		this.dealerCall = dealerCall;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getTwseIndex() {
		return twseIndex;
	}
	public void setTwseIndex(double twseIndex) {
		this.twseIndex = twseIndex;
	}	
	
}
