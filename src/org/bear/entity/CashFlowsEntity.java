package org.bear.entity;
/**
 * 現金流量科目Entity
 * @author edward
 *
 */
public class CashFlowsEntity extends BasicEntity
{
	public String stockID;
	public String year;
	public String seasons;
	int incomeSummary;
	int operatingActivity;
	int investingActivity;
	int financingActivity;
	int netCashFlows;
	int beginningCash;
	int endingCash;
	int freeCashFlow;
	public int getIncomeSummary() {
		return incomeSummary;
	}
	public void setIncomeSummary(int incomeSummary) {
		this.incomeSummary = incomeSummary;
	}
	public int getOperatingActivity() {
		return operatingActivity;
	}
	public void setOperatingActivity(int operatingActivity) {
		this.operatingActivity = operatingActivity;
	}
	public int getInvestingActivity() {
		return investingActivity;
	}
	public void setInvestingActivity(int investingActivity) {
		this.investingActivity = investingActivity;
	}
	public int getFinancingActivity() {
		return financingActivity;
	}
	public void setFinancingActivity(int financingActivity) {
		this.financingActivity = financingActivity;
	}
	public int getNetCashFlows() {
		return netCashFlows;
	}
	public void setNetCashFlows(int netCashFlows) {
		this.netCashFlows = netCashFlows;
	}
	public int getBeginningCash() {
		return beginningCash;
	}
	public void setBeginningCash(int beginningCash) {
		this.beginningCash = beginningCash;
	}
	public int getEndingCash() {
		return endingCash;
	}
	public void setEndingCash(int endingCash) {
		this.endingCash = endingCash;
	}
	public CashFlowsEntity add(CashFlowsEntity entity)
	{
		this.beginningCash += entity.beginningCash;
		this.endingCash += entity.endingCash;
		this.financingActivity += entity.financingActivity;
		this.incomeSummary += entity.incomeSummary;
		this.investingActivity += entity.investingActivity;
		this.netCashFlows += entity.netCashFlows;
		this.operatingActivity += entity.operatingActivity;
		return this;
	}
	public int getFreeCashFlow() {
		return freeCashFlow;
	}
	public void setFreeCashFlow(int freeCashFlow) {
		this.freeCashFlow = freeCashFlow;
	}
}
