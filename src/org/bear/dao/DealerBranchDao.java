package org.bear.dao;
import java.util.List;
/**
 * Semi-Auto Gen by Gemini，券商分點資料
 */

import org.bear.entity.DealerBranchEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
public class DealerBranchDao extends SimpleJdbcDaoSupport 
{
	    /**
	     * 新增一筆資料 (Insert)
	     */
	    public boolean insert(DealerBranchEntity branch) 
	    {
	        String sql = "INSERT INTO DealerBranch (GroupCode, Code, Name) VALUES (?, ?, ?)";
	        
	        // 透過 getJdbcTemplate() 直接執行 update
	        int rowsAffected = getJdbcTemplate().update(
	            sql, 
	            branch.getGroupCode(), 
	            branch.getCode(), 
	            branch.getName()
	        );
	        
	        return rowsAffected > 0;
	    }

	    /**
	     * 查詢全部資料 (Query All)
	     */
	    public List<DealerBranchEntity> queryAll() 
	    {
	        String sql = "SELECT GroupCode, Code, Name FROM DealerBranch";
	        
	        // 使用 BeanPropertyRowMapper 自動將 ResultSet 欄位對應到 DealerBranch 物件的屬性
	        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(DealerBranchEntity.class));
	    }

	    /**
	     * 依據主鍵 (Code) 查詢單筆資料 (Query by ID)
	     */
	    public DealerBranchEntity queryByCode(String code) {
	        String sql = "SELECT GroupCode, Code, Name FROM DealerBranch WHERE Code = ?";
	        
	        try 
	        {
	            // queryForObject 預期只會回傳一筆結果
	            return getJdbcTemplate().queryForObject(
	                sql, 
	                new BeanPropertyRowMapper<>(DealerBranchEntity.class), 
	                code
	            );
	        } 
	        catch (EmptyResultDataAccessException e) 
	        {
	            // Spring 的 queryForObject 如果找不到資料會拋出 EmptyResultDataAccessException
	            // 這裡將其攔截並回傳 null，以符合一般的 DAO 習慣
	            return null;
	        }
	    }
	
}
