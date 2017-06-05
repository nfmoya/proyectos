<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="template"%>

<template:insert template="/WEB-INF/pages/template.jsp">
<%--     <template:put name="title" content="Carga de Archivos" direct="false"/> --%>
    <template:put name="content" content="/WEB-INF/pages/content/cargaDeArchivoContent.jsp"/>
<%--     <template:put name="iconoURL" content="src='./images/file-manager_64x64.png'" direct="false"/> --%>
<%--     <template:put name="tituloCuerpo" content="Carga de Archivos" direct="false"/> --%>
</template:insert>