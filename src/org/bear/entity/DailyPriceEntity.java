package org.bear.entity;

import java.util.Date;

public class DailyPriceEntity 
{
	Date exchangeDate;
	double openPrice;
	double closePrice;
	double lowPrice;
	double highPrice;
	String stockId;
	/**
	 * 周轉率
	 */
	double turnoverRate;
	/**
	 * 流通在外股數
	 */
	int sharesOutstanding;
	/**
	 * 成交筆數
	 */
	int exchangeNum;
	/**
	 * 成交量(成交股數)
	 */
	int volume;	
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getExchangeNum() {
		return exchangeNum;
	}
	public void setExchangeNum(int exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getTurnoverRate() {
		return turnoverRate;
	}
	public void setTurnoverRate(double turnoverRate) {
		this.turnoverRate = turnoverRate;
	}
	public int getSharesOutstanding() {
		return sharesOutstanding;
	}
	public void setSharesOutstanding(int sharesOutstanding) {
		this.sharesOutstanding = sharesOutstanding;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
}
