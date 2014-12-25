<%@ page language="java"%>
<%@ page contentType="application/msexcel;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-disposition", "attachment; filename=download.xls");
	String str = request.getParameter("htmltable");
	out.print(str);
%>