<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<ul id="validation-msg">
	<html:messages id="errors">
		<li><c:out value="${errors}" /></li>
	</html:messages>
</ul>