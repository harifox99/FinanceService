package org.bear.entity;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
/**
 * 總經指標
 * @author edward
 *
 */
public class MacroEconomicEntity 
{
	String year;
	String month;
	Date yearMonth;
	String twseOpen;
	String twseHigh;
	String twseLow;
	String twseClose;
	String m1bAverage;
	String m1bEnd;
	String m2Average;
	String m2End;
	String inventoryIndex;
	String overworkTime;
	String semiIndex;
	String sixMonthLeadIndex;
	String lightSignal;
	String generalIndex;
	double stockMoneyIndex;
	double stockMoneyMoM;
	double stockMoneyYoY;
	int stockMoneyNumber;
	String demandDeposits;
	String generalIndexYoy;
	String confidence;
	String exportOrder;
	public String getTwseOpen() {
		return twseOpen;
	}
	public void setTwseOpen(String twseOpen) {
		this.twseOpen = twseOpen;
	}
	public String getTwseHigh() {
		return twseHigh;
	}
	public void setTwseHigh(String twseHigh) {
		this.twseHigh = twseHigh;
	}
	public String getTwseLow() {
		return twseLow;
	}
	public void setTwseLow(String twseLow) {
		this.twseLow = twseLow;
	}
	public String getTwseClose() {
		return twseClose;
	}
	public void setTwseClose(String twseClose) {
		this.twseClose = twseClose;
	}
	public String getM1bAverage() {
		return m1bAverage;
	}
	public void setM1bAverage(String m1bAverage) {
		this.m1bAverage = m1bAverage;
	}
	public String getM1bEnd() {
		return m1bEnd;
	}
	public void setM1bEnd(String m1bEnd) {
		this.m1bEnd = m1bEnd;
	}
	public String getM2Average() {
		return m2Average;
	}
	public void setM2Average(String m2Average) {
		this.m2Average = m2Average;
	}
	public String getM2End() {
		return m2End;
	}
	public void setM2End(String m2End) {
		this.m2End = m2End;
	}
	public String getLightSignal() {
		return lightSignal;
	}
	public void setLightSignal(String lightSignal) {
		this.lightSignal = lightSignal;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getSixMonthLeadIndex() {
		return sixMonthLeadIndex;
	}
	public void setSixMonthLeadIndex(String sixMonthLeadIndex) {
		this.sixMonthLeadIndex = sixMonthLeadIndex;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getInventoryIndex() {
		return inventoryIndex;
	}
	public void setInventoryIndex(String inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}
	public String getOverworkTime() {
		return overworkTime;
	}
	public void setOverworkTime(String overworkTime) {
		this.overworkTime = overworkTime;
	}
	public String getSemiIndex() {
		NumberFormat formatter = new DecimalFormat("#.##");
		double temp = Double.parseDouble(semiIndex);
		return formatter.format(temp);
	}
	public void setSemiIndex(String semiIndex) {
		this.semiIndex = semiIndex;
	}
	public String getGeneralIndex() {
		return generalIndex;
	}
	public void setGeneralIndex(String generalIndex) {
		this.generalIndex = generalIndex;
	}
	public double getStockMoneyIndex() {
		return stockMoneyIndex;
	}
	public void setStockMoneyIndex(double stockMoneyIndex) {
		this.stockMoneyIndex = stockMoneyIndex;
	}
	public double getStockMoneyMoM() {
		return stockMoneyMoM;
	}
	public void setStockMoneyMoM(double stockMoneyMoM) {
		this.stockMoneyMoM = stockMoneyMoM;
	}
	public double getStockMoneyYoY() {
		return stockMoneyYoY;
	}
	public void setStockMoneyYoY(double stockMoneyYoY) {
		this.stockMoneyYoY = stockMoneyYoY;
	}
	public int getStockMoneyNumber() {
		return stockMoneyNumber;
	}
	public void setStockMoneyNumber(int stockMoneyNumber) {
		this.stockMoneyNumber = stockMoneyNumber;
	}
	public String getDemandDeposits() {
		return demandDeposits;
	}
	public void setDemandDeposits(String demandDeposits) {
		this.demandDeposits = demandDeposits;
	}
	public String getGeneralIndexYoy() {
		return generalIndexYoy;
	}
	public void setGeneralIndexYoy(String generalIndexYoy) {
		this.generalIndexYoy = generalIndexYoy;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
	public String getExportOrder() {
		return exportOrder;
	}
	public void setExportOrder(String exportOrder) {
		this.exportOrder = exportOrder;
	}	
	
}
