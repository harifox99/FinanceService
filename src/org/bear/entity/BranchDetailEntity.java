package org.bear.entity;

import java.util.Date;

/**
 * 分點明細資料
 */
public class BranchDetailEntity
{
	/**
	 * 券商分點代碼 (新增)
	 */
	String code;
	/**
	 * 股票代碼
	 */
	String stockId;
	/**
	 * 買進張數
	 */
	int buyColumn;
	/**
	 * 賣出張數
	 */
	int sellColumn;
	/**
	 * 差額
	 */
	int diff;
	/**
	 * 交易日期
	 */
	public Date exchangeDate;

	// --- 新增的 Getter / Setter ---
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	// --- 原本的 Getter / Setter ---
	public Date getExchangeDate()
	{
		return exchangeDate;
	}

	public void setExchangeDate(Date exchangeDate)
	{
		this.exchangeDate = exchangeDate;
	}

	public String getStockId()
	{
		return stockId;
	}

	public void setStockId(String stockID)
	{
		this.stockId = stockID;
	}

	public int getBuyColumn()
	{
		return buyColumn;
	}

	public void setBuyColumn(int buyColumn)
	{
		this.buyColumn = buyColumn;
	}

	public int getSellColumn()
	{
		return sellColumn;
	}

	public void setSellColumn(int sellColumn)
	{
		this.sellColumn = sellColumn;
	}

	public int getDiff()
	{
		return diff;
	}

	public void setDiff(int diff)
	{
		this.diff = diff;
	}
}