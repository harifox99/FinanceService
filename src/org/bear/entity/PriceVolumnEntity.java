package org.bear.entity;

import java.util.Date;

/**
 * 價量分析
 * @author edward
 *
 */
public class PriceVolumnEntity 
{
	/**
	 * 交易日期
	 */
	Date exchangeDate;
	/**
	 * 量
	 */
	int volumn;
	/**
	 * 收盤價
	 */
	double price;
	/**
	 * 平均價
	 */
	int averageVolumn;
	/**
	 * 平均量
	 */
	double averagePrice;
	/**
	 * 價評論
	 */
	String priceComment;
	/**
	 * 量評論
	 */
	String volumnComment;
	/**
	 * 盤勢
	 */
	String trend;
	/**
	 * 操作方向
	 */
	String direction;
	/**
	 * 使用策略
	 */
	String policy;
	/**
	 * 漲跌幅
	 */
	double change;
	public int getVolumn() {
		return volumn;
	}
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAverageVolumn() {
		return averageVolumn;
	}
	public void setAverageVolumn(int averageVolumn) {
		this.averageVolumn = averageVolumn;
	}
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public String getPriceComment() {
		return priceComment;
	}
	public void setPriceComment(String priceComment) {
		this.priceComment = priceComment;
	}
	public String getVolumnComment() {
		return volumnComment;
	}
	public void setVolumnComment(String volumnComment) {
		this.volumnComment = volumnComment;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public Date getExchangeDate() {
		return exchangeDate;
	}
	public void setExchangeDate(Date exchangeDate) {
		this.exchangeDate = exchangeDate;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}	
	
}
