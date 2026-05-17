package org.bear.dao;

import org.bear.entity.BranchDetailEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.util.Date;
import java.util.List;

public class BranchDetailDao extends SimpleJdbcDaoSupport
{

	/**
	 * 新增一筆資料 (Insert)
	 */
	public boolean insert(BranchDetailEntity detail)
	{
		// 新增 Code 欄位
		String sql = "INSERT INTO BranchDetail (StockId, BuyColumn, SellColumn, Diff, ExchangeDate, Code) VALUES (?, ?, ?, ?, ?, ?)";

		int rowsAffected = getJdbcTemplate().update(sql, detail.getStockId(), detail.getBuyColumn(),
				detail.getSellColumn(), detail.getDiff(), detail.getExchangeDate(), detail.getCode() // 新增參數
		);

		return rowsAffected > 0;
	}

	/**
	 * 查詢全部資料 (Query All)
	 */
	public List<BranchDetailEntity> queryAll()
	{
		// 查詢結果加入 Code
		String sql = "SELECT StockId, BuyColumn, SellColumn, Diff, ExchangeDate, Code FROM BranchDetail";

		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(BranchDetailEntity.class));
	}

	/**
	 * 依據複合主鍵 (StockId + ExchangeDate + Code) 查詢單筆資料
	 */
	public BranchDetailEntity queryById(String stockId, Date exchangeDate, String code)
	{
		// WHERE 條件加入 Code
		String sql = "SELECT StockId, BuyColumn, SellColumn, Diff, ExchangeDate, Code FROM BranchDetail WHERE StockId = ? AND ExchangeDate = ? AND Code = ?";

		try
		{
			return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(BranchDetailEntity.class), stockId,
					exchangeDate, code // 新增查詢條件參數
			);
		}
		catch (EmptyResultDataAccessException e)
		{
			return null;
		}
	}
}