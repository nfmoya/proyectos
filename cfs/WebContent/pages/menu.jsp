<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

${menuCompleto}
<!--<input type="hidden" name="hidMenuCompleto" id="hidMenuCompleto" value="<c:out value="${loginForm.menuCompleto}"/>"/>-->
<input type="hidden" name="hidUserPerfil" id="hidUserPerfil" value="<c:out value="${loginForm.userIdPerfil}"/>"/>
<input type="hidden" name="hidUserFullname" id="hidUserFullname" value="<c:out value="${loginForm.userFullname}"/>"/>	
<input type="hidden" name="hidUserName" id="hidUserName" value="<c:out value="${loginForm.username}"/>"/>
