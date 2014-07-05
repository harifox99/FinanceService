package org.bear.entity;

public class EarningPowerWrapper extends BasicEntity 
{
	/**
	 * 毛利率
	 */
	double grossProfitRatio;
	/**
	 * 營業利益率
	 */
	double operatingProfitRatio;
	/**
	 * 稅前淨利率
	 */
	double incomeBeforeTaxRatio;
	/**
	 * 存貨營收比
	 */
	double inventoryRevenueRatio;
	public double getGrossProfitRatio() {
		return grossProfitRatio;
	}
	public void setGrossProfitRatio(double grossProfitRatio) {
		this.grossProfitRatio = grossProfitRatio;
	}
	public double getOperatingProfitRatio() {
		return operatingProfitRatio;
	}
	public void setOperatingProfitRatio(double operatingProfitRatio) {
		this.operatingProfitRatio = operatingProfitRatio;
	}
	public double getIncomeBeforeTaxRatio() {
		return incomeBeforeTaxRatio;
	}
	public void setIncomeBeforeTaxRatio(double incomeBeforeTaxRatio) {
		this.incomeBeforeTaxRatio = incomeBeforeTaxRatio;
	}
	public double getInventoryRevenueRatio() {
		return inventoryRevenueRatio;
	}
	public void setInventoryRevenueRatio(double inventoryRevenueRatio) {
		this.inventoryRevenueRatio = inventoryRevenueRatio;
	}
	
}
