/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class PerShareWrapper extends BasicEntity 
{
	/**
	 * 每股營收
	 */
	double revenuePerShare;
	/**
	 * 每股營業利益
	 */
	double incomePerShare;
	/**
	 * 每股盈餘
	 */
	double eps;
	public double getRevenuePerShare() {
		return revenuePerShare;
	}
	public void setRevenuePerShare(double revenuePerShare) {
		this.revenuePerShare = revenuePerShare;
	}
	public double getIncomePerShare() {
		return incomePerShare;
	}
	public void setIncomePerShare(double incomePerShare) {
		this.incomePerShare = incomePerShare;
	}
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
}
