<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>download.jsp</title>
  </head>
  
  <body>
	  <c:forEach items="${map}" var="me">
	  	<c:url value="/DownloadServlet" var="DownloadURL">
	  		<c:param name="fileName" value="${me.key}"></c:param>
	  	</c:url>
	  	${me.value} <a href="${DownloadURL}">下载</a> <br>
	  </c:forEach>
  </body>
</html>
