<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>upload.jsp</title>
  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/UploadServlet1" method="post" enctype="multipart/form-data">
    	上传用户：<input type="text" name="username"><br>
    	上传文件1：<input type="file" name="file1"><br>
    	上传文件2：<input type="file" name="file2"><br>
    	<input type="submit" value="提交">
    </form>
  </body>
</html>
