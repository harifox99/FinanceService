/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class LongTermInvestmentWrapper extends BasicEntity 
{
	/**
	 * 長期投資佔總資產比率
	 */
	double longTermTotalAssetRatio;
	/**
	 * 長期資金佔固定資產比率
	 */
	double longTermCapitalRatio;
	public double getLongTermTotalAssetRatio() {
		return longTermTotalAssetRatio;
	}

	public void setLongTermTotalAssetRatio(double longTermTotalAssetRatio) {
		this.longTermTotalAssetRatio = longTermTotalAssetRatio;
	}

	public double getLongTermCapitalRatio() {
		return longTermCapitalRatio;
	}

	public void setLongTermCapitalRatio(double longTermCapitalRatio) {
		this.longTermCapitalRatio = longTermCapitalRatio;
	}
}
