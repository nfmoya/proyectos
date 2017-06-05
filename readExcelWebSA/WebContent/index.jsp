<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>

<%
	String token = request.getParameter("token");
	request.getSession().setAttribute("token", token);
	if (token == null) {
%>
<logic:redirect forward="logout" />
<%
	} else {
%>
<logic:redirect forward="inicio" />
<%
	}
%>

