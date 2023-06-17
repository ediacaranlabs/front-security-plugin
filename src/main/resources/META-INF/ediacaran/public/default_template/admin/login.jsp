<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="template" value="${plugins.ediacaran.security.template}" />

<%
String template = (String)pageContext.getAttribute("template");
javax.servlet.ServletContext context = application.getContext("/plugins/ediacaran/front");
javax.servlet.RequestDispatcher rd = 
	context.getRequestDispatcher("/templates/" + template + "/admin/login/login.jsp");
	rd.include(request, response);
%>