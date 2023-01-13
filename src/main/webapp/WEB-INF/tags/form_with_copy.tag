<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="action" required="true"%>
<%@ attribute name="id" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag import="codingmentor.javabackend.k3.Utils.UrlUtils" %>


<c:set var ="action" value="${pageContext.request.contextPath}${action}"/>
<form action="${action}" method="post" accept-charset="UTF-8" data-remote="true" method="post">
	<jsp:doBody/>
</form>


