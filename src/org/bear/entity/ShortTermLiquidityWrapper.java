/**
 * 
 */
package org.bear.entity;

/**
 * @author edward
 *
 */
public class ShortTermLiquidityWrapper extends BasicEntity 
{
	double currentRatio;
	double quickRatio;
	/**
	 * 應收帳款及存貨佔總資產比率
	 */
	double specialRatio;
	public double getCurrentRatio() {
		return currentRatio;
	}
	public void setCurrentRatio(double currentRatio) {
		this.currentRatio = currentRatio;
	}
	public double getQuickRatio() {
		return quickRatio;
	}
	public void setQuickRatio(double quickRatio) {
		this.quickRatio = quickRatio;
	}
	public double getSpecialRatio() {
		return specialRatio;
	}
	public void setSpecialRatio(double specialRatio) {
		this.specialRatio = specialRatio;
	}
}
