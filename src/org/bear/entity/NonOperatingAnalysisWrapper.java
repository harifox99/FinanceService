/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class NonOperatingAnalysisWrapper extends BasicEntity 
{
	/**
	 * 營業外收入占稅前盈餘比率
	 */
	double nonOperatingRevenue;
	/**
	 * 營業外支出占稅前盈餘比率
	 */
	double nonOperatingExpense;
	/**
	 * 營業外收支占稅前盈餘比率
	 */
	double nonOperatingTotal;
	public double getNonOperatingRevenue() {
		return nonOperatingRevenue;
	}
	public void setNonOperatingRevenue(double nonOperatingRevenue) {
		this.nonOperatingRevenue = nonOperatingRevenue;
	}
	public double getNonOperatingExpense() {
		return nonOperatingExpense;
	}
	public void setNonOperatingExpense(double nonOperatingExpense) {
		this.nonOperatingExpense = nonOperatingExpense;
	}
	public double getNonOperatingTotal() {
		return nonOperatingTotal;
	}
	public void setNonOperatingTotal(double nonOperatingTotal) {
		this.nonOperatingTotal = nonOperatingTotal;
	}
}
