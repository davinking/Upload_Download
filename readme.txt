1、上传文件名的中文乱码和上传数据的中文乱码
	upload.setHeaderEncoding("UTF-8");  //解决上传文件名的中文乱码
	//表单为文件上传，设置request编码无效,只能手工转换
	1.1 value = new String(value.getBytes("iso8859-1"),"UTF-8");
	1.2 String value = item.getString("UTF-8");
	
2.为保证服务器安全，上传文件应该放在外界无法直接访问的目录

3、为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名

4、为防止一个目录下面出现太多文件，要使用hash算法打散存储

5.要限制上传文件的最大值，可以通过：
	ServletFileUpload.setFileSizeMax(1024)
	方法实现，并通过捕获：
	FileUploadBase.FileSizeLimitExceededException异常以给用户友好提示
	
6.想确保临时文件被删除，一定要在处理完上传文件后，调用item.delete方法

7.要限止上传文件的类型：在收到上传文件名时，判断后缀名是否合法

8、监听文件上传进度：
	ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setProgressListener(new ProgressListener(){
				public void update(long pBytesRead, long pContentLength, int arg2) {
					System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});
			
9. 在web页面中动态添加文件上传输入项
	function addinput(){
    		var div = document.getElementById("file");
    		
    		var input = document.createElement("input");
    		input.type="file";
    		input.name="filename";
    		
    		var del = document.createElement("input");
    		del.type="button";
    		del.value="删除";
    		del.onclick = function d(){
    			this.parentNode.parentNode.removeChild(this.parentNode);
    		}
    		
    		
    		var innerdiv = document.createElement("div");
    		
    		
    		innerdiv.appendChild(input);
    		innerdiv.appendChild(del);
    		
    		div.appendChild(innerdiv);
    	}



注意：不要忘记在WEB-INF目录下建upload和temp文件夹。
