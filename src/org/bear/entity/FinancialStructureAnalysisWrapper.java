/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class FinancialStructureAnalysisWrapper extends BasicEntity 
{
	/**
	 * 權益比率/自有資本比率
	 */
	double holdersEquityRatio;
	/**
	 * 負債比率
	 */
	double debtRatio;
	/**
	 * 權益乘數
	 */
	double equityMultiplier;
	/**
	 * 金融負債
	 */
	double bankLiability;
	public double getHoldersEquityRatio() {
		return holdersEquityRatio;
	}
	public void setHoldersEquityRatio(double holdersEquityRatio) {
		this.holdersEquityRatio = holdersEquityRatio;
	}
	public double getDebtRatio() {
		return debtRatio;
	}
	public void setDebtRatio(double debtRatio) {
		this.debtRatio = debtRatio;
	}
	public double getEquityMultiplier() {
		return equityMultiplier;
	}
	public void setEquityMultiplier(double equityMultiplier) {
		this.equityMultiplier = equityMultiplier;
	}
	public double getBankLiability() {
		return bankLiability;
	}
	public void setBankLiability(double bankLiability) {
		this.bankLiability = bankLiability;
	} 
	
}
