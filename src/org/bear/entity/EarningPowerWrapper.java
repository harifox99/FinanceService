package org.bear.entity;

public class EarningPowerWrapper extends BasicEntity 
{
	double grossProfitRatio;
	double operatingProfitRatio;
	double incomeBeforeTaxRatio;
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
}
