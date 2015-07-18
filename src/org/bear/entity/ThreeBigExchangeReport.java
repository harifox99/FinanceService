package org.bear.entity;

public class ThreeBigExchangeReport extends ThreeBigExchangeEntity 
{
	/**
	 * 股票名稱
	 */
	String stockName;
	/**
	 * 股本
	 */
	double capital;
	/**
	 * 公司大小分類
	 */
	String companySize;
	
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public String getCompanySize() {
		return companySize;
	}
	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}	
}
