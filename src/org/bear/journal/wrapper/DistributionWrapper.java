package org.bear.journal.wrapper;

public class DistributionWrapper {
	/**
	 * 散戶
	 */
	long retailInvestor;
	/**
	 * 中實戶
	 */
	long mediumInvestor;
	/**
	 * 大戶
	 */
	long majorInvestor;
	/**
	 * 大股東
	 */
	long majorShareholders;
	/**
	 * 董監
	 */
	long supervisors;
	/**
	 * 經理人
	 */
	long manager;
	/**
	 * 三大法人
	 */
	long threeBig;
	/**
	 * 10%大股東
	 */
	long strongStockHolder;
	String yearMonth;
	String stockName;
	public long getRetailInvestor() {
		return retailInvestor;
	}
	public void setRetailInvestor(long retailInvestor) {
		this.retailInvestor = retailInvestor;
	}
	public long getMediumInvestor() {
		return mediumInvestor;
	}
	public void setMediumInvestor(long mediumInvestor) {
		this.mediumInvestor = mediumInvestor;
	}
	public long getMajorInvestor() {
		return majorInvestor;
	}
	public void setMajorInvestor(long majorInvestor) {
		this.majorInvestor = majorInvestor;
	}
	public long getMajorShareholders() {
		return majorShareholders;
	}
	public void setMajorShareholders(long majorShareholders) {
		this.majorShareholders = majorShareholders;
	}
	public long getSupervisors() {
		return supervisors;
	}
	public void setSupervisors(long supervisors) {
		this.supervisors = supervisors;
	}
	public long getManager() {
		return manager;
	}
	public void setManager(long manager) {
		this.manager = manager;
	}
	public long getThreeBig() {
		return threeBig;
	}
	public void setThreeBig(long threeBig) {
		this.threeBig = threeBig;
	}
	public long getStrongStockHolder() {
		return strongStockHolder;
	}
	public void setStrongStockHolder(long strongStockHolder) {
		this.strongStockHolder = strongStockHolder;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
}
