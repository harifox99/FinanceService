/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class ROEAnalysisWrapper extends BasicEntity 
{
	/**
	 * ROE
	 */
	double roe;
	/**
	 * 純益率
	 */
	double netProfitMargin;
	/**
	 * 總資產週轉率
	 */
	double totalAssetTurnover;
	/**
	 * 權益乘數
	 */
	double equityMultiplier;
	public double getRoe() {
		return roe;
	}
	public void setRoe(double roe) {
		this.roe = roe;
	}
	public double getNetProfitMargin() {
		return netProfitMargin;
	}
	public void setNetProfitMargin(double netProfitMargin) {
		this.netProfitMargin = netProfitMargin;
	}
	public double getTotalAssetTurnover() {
		return totalAssetTurnover;
	}
	public void setTotalAssetTurnover(double totalAssetTurnover) {
		this.totalAssetTurnover = totalAssetTurnover;
	}
	public double getEquityMultiplier() {
		return equityMultiplier;
	}
	public void setEquityMultiplier(double equityMultiplier) {
		this.equityMultiplier = equityMultiplier;
	}
}
