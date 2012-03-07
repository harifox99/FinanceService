/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 * 損益科目Entity
 */
public class IncomeStatementEntity extends BasicEntity 
{
	/**
	 * 營業收入淨額
	 */
	int operatingRevenue;
	/**
	 * 營業成本
	 */
	int operatingCost;
	/**
	 * 營業毛利
	 */
	int grossProfit;
	/**
	 * 營業費用
	 */
	int operatingExpense;
	/**
	 * 營業利益
	 */
	int operatingIncome;
	/**
	 * 投資收入
	 */
	int investmentIncome;
	/**
	 * 營業外收入
	 */
	int nonOperatingRevenue;
	/**
	 * 營業外支出
	 */
	int nonOperatingExpense;
	/**
	 * 稅前淨利
	 */
	int preTaxIncome;
	/**
	 * 稅後淨利
	 */
	int netIncome;
	/**
	 * 加權平均股本
	 */
	int wghtAvgStocks;
	double eps;
	public int getOperatingRevenue() {
		return operatingRevenue;
	}
	public void setOperatingRevenue(int operatingRevenue) {
		this.operatingRevenue = operatingRevenue;
	}
	public int getOperatingCost() {
		return operatingCost;
	}
	public void setOperatingCost(int operatingCost) {
		this.operatingCost = operatingCost;
	}
	public int getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(int grossProfit) {
		this.grossProfit = grossProfit;
	}
	public int getOperatingExpense() {
		return operatingExpense;
	}
	public void setOperatingExpense(int operatingExpense) {
		this.operatingExpense = operatingExpense;
	}
	public int getOperatingIncome() {
		return operatingIncome;
	}
	public void setOperatingIncome(int operatingIncome) {
		this.operatingIncome = operatingIncome;
	}
	public int getInvestmentIncome() {
		return investmentIncome;
	}
	public void setInvestmentIncome(int investmentIncome) {
		this.investmentIncome = investmentIncome;
	}
	public int getNonOperatingRevenue() {
		return nonOperatingRevenue;
	}
	public void setNonOperatingRevenue(int nonOperatingRevenue) {
		this.nonOperatingRevenue = nonOperatingRevenue;
	}
	public int getNonOperatingExpense() {
		return nonOperatingExpense;
	}
	public void setNonOperatingExpense(int nonOperatingExpense) {
		this.nonOperatingExpense = nonOperatingExpense;
	}
	public int getPreTaxIncome() {
		return preTaxIncome;
	}
	public void setPreTaxIncome(int preTaxIncome) {
		this.preTaxIncome = preTaxIncome;
	}
	public int getNetIncome() {
		return netIncome;
	}
	public void setNetIncome(int netIncome) {
		this.netIncome = netIncome;
	}
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
	public int getWghtAvgStocks() {
		return wghtAvgStocks;
	}
	public void setWghtAvgStocks(int wghtAvgStocks) {
		this.wghtAvgStocks = wghtAvgStocks;
	}
}
