package org.bear.dao;
import java.util.List;
import org.bear.entity.ThreeBigExchangeEntity;

public interface ThreeBigExchangeDao {
	public void insert(ThreeBigExchangeEntity entity); 
	public List<ThreeBigExchangeEntity> latest(String stockID, int duration);
	public void update(String date, String stockID, int rank, String buyer);
}
