package org.bear.entity;

public class RedRabbitWrapper {
	String stockID;
	String stockName;
	//本月營收創六年以來新高
	int sixYearHigh;
	//創近一年新高
	int oneYearHigh;
	//創六年同期新高(2)&次高(1)
	int sixYearHighYoy;	
	//創六年同期新低(2)&次低(1)
	int sixYearLowYoy;
	//上個月創六年內同期新高(2)&次高(1)
	int sixYearHighYoyLastMonth;	
	//上個月創六年內同期新低(2)&次低(1)
	//int sixYearLowYoyLastMonth;
	//過去一年累計營收創下六年新高
	int sixYearAccumulationHigh;
	//過去一年累計營收YoY創下六年新高
	int sixYearAccumulationYoyHigh;
	//營收總月份，原則上是72個月，但是怕有新股少於72個月，故予以註記
	int monthSize;
	//本月MoM>去年同期MoM
	int momGrow;
	//連三月絕對營收成長
	int consecutive3MRevenueGrow;
	//連三月單月YoY成長
	int consecutive3MYoyGrow;
	//連三月累計YoY成長
	int consecutive3MAccumuYoyGrow;
	
	public int getMonthSize() {
		return monthSize;
	}
	public void setMonthSize(int monthSize) {
		this.monthSize = monthSize;
	}
	public int getSixYearHigh() {
		return sixYearHigh;
	}
	public void setSixYearHigh(int sixYearHigh) {
		this.sixYearHigh = sixYearHigh;
	}
	public int getOneYearHigh() {
		return oneYearHigh;
	}
	public void setOneYearHigh(int oneYearHigh) {
		this.oneYearHigh = oneYearHigh;
	}
	public int getSixYearHighYoy() {
		return sixYearHighYoy;
	}
	public void setSixYearHighYoy(int sixYearHighYoy) {
		this.sixYearHighYoy = sixYearHighYoy;
	}
	public int getSixYearHighYoyLastMonth() {
		return sixYearHighYoyLastMonth;
	}
	public void setSixYearHighYoyLastMonth(int sixYearHighYoyLastMonth) {
		this.sixYearHighYoyLastMonth = sixYearHighYoyLastMonth;
	}
	public int getSixYearAccumulationHigh() {
		return sixYearAccumulationHigh;
	}
	public void setSixYearAccumulationHigh(int sixYearAccumulationHigh) {
		this.sixYearAccumulationHigh = sixYearAccumulationHigh;
	}
	public int getSixYearAccumulationYoyHigh() {
		return sixYearAccumulationYoyHigh;
	}
	public void setSixYearAccumulationYoyHigh(int sixYearAccumulationYoyHigh) {
		this.sixYearAccumulationYoyHigh = sixYearAccumulationYoyHigh;
	}
	public int getSixYearLowYoy() {
		return sixYearLowYoy;
	}
	public void setSixYearLowYoy(int sixYearLowYoy) {
		this.sixYearLowYoy = sixYearLowYoy;
	}
	public int getConsecutive3MRevenueGrow() {
		return consecutive3MRevenueGrow;
	}
	public void setConsecutive3MRevenueGrow(int consecutive3mRevenueGrow) {
		consecutive3MRevenueGrow = consecutive3mRevenueGrow;
	}
	public int getConsecutive3MYoyGrow() {
		return consecutive3MYoyGrow;
	}
	public void setConsecutive3MYoyGrow(int consecutive3mYoyGrow) {
		consecutive3MYoyGrow = consecutive3mYoyGrow;
	}
	public int getConsecutive3MAccumuYoyGrow() {
		return consecutive3MAccumuYoyGrow;
	}
	public void setConsecutive3MAccumuYoyGrow(int consecutive3mAccumuYoyGrow) {
		consecutive3MAccumuYoyGrow = consecutive3mAccumuYoyGrow;
	}
	public int getMomGrow() {
		return momGrow;
	}
	public void setMomGrow(int momGrow) {
		this.momGrow = momGrow;
	}
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
}
