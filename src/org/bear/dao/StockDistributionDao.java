package org.bear.dao;
import java.util.List;
import org.bear.entity.StockDistributionEntity;

public interface StockDistributionDao {
	public void insert(StockDistributionEntity entity);
	public List <StockDistributionEntity> latest(int duration);
}
