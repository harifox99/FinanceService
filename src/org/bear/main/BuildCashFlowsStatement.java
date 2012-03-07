package org.bear.main;
import java.util.List;
import org.bear.datainput.*;
import org.bear.entity.CashFlowsEntity;
public class BuildCashFlowsStatement {

	/**
	 * @param args
	 */
	List <CashFlowsEntity> list;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildCashFlowsStatement cashFlowsStatement = new BuildCashFlowsStatement();
		cashFlowsStatement.insertBatch();
	}
	public void insertBatch()
	{
		//ImportCashFlowsYear importCashFlows = new ImportCashFlowsYear();
		//importCashFlows.insertBatchList();
		//ImportCashFlowsCathay importCashFlows = new ImportCashFlowsCathay();
		//importCashFlows.insertBatchList();
		ImportCashFlowsYam importCashFlows = new ImportCashFlowsYam();
		importCashFlows.insertBatchList();
	}
	public List<CashFlowsEntity> getList() {
		return list;
	}
	public void setList(List<CashFlowsEntity> list) {
		this.list = list;
	}
}
