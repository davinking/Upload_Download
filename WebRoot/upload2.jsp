<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>upload2.jsp</title>
    
    <script type="text/javascript">
    	function addfile(){
    		var div = document.getElementById("file");
    		
    		var input = document.createElement("input");
    		input.type = "file";
    		input.name = "fileName";
    		
    		var del = document.createElement("input");
    		del.type = "button";
    		del.value = "删除";
    		del.onclick = function d(){
    			this.parentNode.parentNode.removeChild(this.parentNode);
    		}
    		
    		var innerdiv = document.createElement("div");
    		innerdiv.appendChild(input);
    		innerdiv.appendChild(del);
    		
    		div.appendChild(innerdiv);
    	}
    </script>
  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/UploadServlet1" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>上传用户</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>上传文件</td>
				<td><input type="button" value="添加上传文件" onclick="addfile()"></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<div id="file">
					
					</div>
				</td>
			</tr>
			<tr id="submit">
				<td colspan="2"><input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
  </body>
</html>
