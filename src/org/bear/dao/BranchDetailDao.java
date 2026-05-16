package org.bear.dao;
import org.bear.entity.BranchDetailEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import java.util.Date;
import java.util.List;
/**
 * Gemini Auto Gen Java
 */
public class BranchDetailDao extends SimpleJdbcDaoSupport {

    /**
     * 新增一筆資料 (Insert)
     */
    public boolean insert(BranchDetailEntity detail) {
        String sql = "INSERT INTO BranchDetail (StockId, BuyColumn, SellColumn, Diff, ExchangeDate) VALUES (?, ?, ?, ?, ?)";
        
        int rowsAffected = getJdbcTemplate().update(
            sql, 
            detail.getStockId(), 
            detail.getBuyColumn(), 
            detail.getSellColumn(), 
            detail.getDiff(), 
            detail.getExchangeDate()
        );
        
        return rowsAffected > 0;
    }

    /**
     * 查詢全部資料 (Query All)
     */
    public List<BranchDetailEntity> queryAll() {
        String sql = "SELECT StockId, BuyColumn, SellColumn, Diff, ExchangeDate FROM BranchDetail";
        
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(BranchDetailEntity.class));
    }

    /**
     * 依據複合主鍵 (StockId + ExchangeDate) 查詢單筆資料
     */
    public BranchDetailEntity queryById(String stockId, Date exchangeDate) {
        String sql = "SELECT StockId, BuyColumn, SellColumn, Diff, ExchangeDate FROM BranchDetail WHERE StockId = ? AND ExchangeDate = ?";
        
        try 
        {
            return getJdbcTemplate().queryForObject(
                sql, 
                new BeanPropertyRowMapper<>(BranchDetailEntity.class), 
                stockId, 
                exchangeDate
            );
        } 
        catch (EmptyResultDataAccessException e) {
            // 若查無資料，攔截例外並回傳 null
            return null;
        }
    }
}
