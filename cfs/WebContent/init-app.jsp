<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>

<% 
String ivUser = request.getHeader("iv-user");
	request.getSession().setAttribute("ivUser", ivUser.toUpperCase());
	if (ivUser == null) {
%>
<logic:redirect action="index.action" />
<%
	} else {
%>
<logic:redirect action="AuthenticateUser.action" />
<%
	}
%>
