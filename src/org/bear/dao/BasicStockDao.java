package org.bear.dao;

import java.util.List;
import org.bear.entity.BasicStockWrapper;

public interface BasicStockDao 
{
	public void insertBatch(List<BasicStockWrapper> entity);
	//┮Τ布
	public List<BasicStockWrapper> findAllData();
	//┮Τ布眖程秨﹍
	public List<BasicStockWrapper> findAllDataDesc();
	//カ(branchType=1)┪耫(branchType=2)布
	public List<BasicStockWrapper> findStockTypeData(String stockBranch);
	public void updateCapital(String stcokID, String capital);
	public BasicStockWrapper findBasicData(String stockID);
	//Τ程穝犁Μ布
	public List<BasicStockWrapper> findSpecificDate();
	public void updateOutstandingShare(String stockID, int share);
}
