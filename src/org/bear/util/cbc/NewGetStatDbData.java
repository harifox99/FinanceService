package org.bear.util.cbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.bear.dao.MacroEconomicDao;

public class NewGetStatDbData {
	protected String url;
	protected String valdavarden2;
	protected String valdavarden3;
	protected int endDateValue;
	protected int startDateValue;
	/**
	 * 表格種類
	 */
	protected String matrix;
	protected String[] values2;
	protected String[] values3;
	protected String hasAggregno; 
	protected String root;
	protected String classdir;
	protected List<NameValuePair> paramList = new ArrayList<NameValuePair>();
	protected MacroEconomicDao dao;	
	public void setData(String key, String value)
	{
		paramList.add(new BasicNameValuePair(key, value));
	}	
	public void setDao(MacroEconomicDao dao) {
		this.dao = dao;
	}	
	public void setUrl(String url) {
		this.url = url;
	}
	public NewGetStatDbData()
	{
		this.hasAggregno = "0";
		setData("strList", "L");
		setData("var1", "期間");
		setData("var2", "指標");
		setData("var3", "種類");			
		setData("pxkonv", "asp1");
		setData("noofvar", "3");
		setData("elim", "NNN");
		setData("lang", "9");
	}
	public void setParameter()
	{					
		//初始值
		setData("Valdavarden1", String.valueOf(endDateValue-startDateValue+1));
		setData("Valdavarden2", valdavarden2);
		setData("Valdavarden3", valdavarden3);
		//日期
		for (int i = startDateValue; i <= endDateValue; i++)
		{
			setData("values1", String.valueOf(i));
		}
		//指標
		for (int i = 0; i < values2.length; i++)
		{
			setData("values2", values2[i]);
		}
		//平均&期底
		for (int i = 0; i < values3.length; i++)
		{
			setData("values3", values3[i]);
		}
		/************************************/
		setData("matrix", matrix);
		//paramList.add(new BasicNameValuePair("varparm", "ma=ES0101A1M&amp;ti=%B4%BA%AE%F0%BB%E2%A5%FD%AB%FC%BC%D0%2D%A4%EB&amp;path=%2E%2E%2FPXfile%2FEconomicStatistics%2F&amp;xu=&amp;yp=&amp;lang=9"));
		setData("hasAggregno", hasAggregno);	
		//paramList.add(new BasicNameValuePair("ti", "景氣領先指標-月"));		
		setData("root", root);
		setData("classdir", classdir);		
		//paramList.add(new BasicNameValuePair("numberstub", "1"));		
		//paramList.add(new BasicNameValuePair("stubceller", "0"));
		//paramList.add(new BasicNameValuePair("headceller", "0"));			
	}
	public String getValdavarden2() {
		return valdavarden2;
	}
	public void setValdavarden2(String valdavarden2) {
		this.valdavarden2 = valdavarden2;
	}
	public String getValdavarden3() {
		return valdavarden3;
	}
	public void setValdavarden3(String valdavarden3) {
		this.valdavarden3 = valdavarden3;
	}
	public String getMatrix() {
		return matrix;
	}
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	public String[] getValues2() {
		return values2;
	}
	public void setValues2(String[] values2) {
		this.values2 = values2;
	}
	public String[] getValues3() {
		return values3;
	}
	public void setValues3(String[] values3) {
		this.values3 = values3;
	}
	public String getHasAggregno() {
		return hasAggregno;
	}
	public void setHasAggregno(String hasAggregno) {
		this.hasAggregno = hasAggregno;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getClassdir() {
		return classdir;
	}
	public void setClassdir(String classdir) {
		this.classdir = classdir;
	}
	
}
