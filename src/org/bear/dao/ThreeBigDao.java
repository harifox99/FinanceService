package org.bear.dao;

import java.util.List;
import org.bear.entity.ThreeBigEntity;

public interface ThreeBigDao {
	public void insert(ThreeBigEntity entity); 
	public List<ThreeBigEntity> latest(int duration);
}
