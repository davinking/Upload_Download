package cn.mengmei.web.upload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UploadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//如果表单的提交类型为multipart/form-data的话，在servlet方就不能采用传统方式获取表单数据。
		//String username = request.getParameter("username");
		//System.out.println(username);  //null

		//从request得到输入流
		ServletInputStream in = request.getInputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len=in.read(buffer))>0){
			System.out.println(new String(buffer,0,len));
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}


/*打印结果：
 
------WebKitFormBoundarySKpht2KuvC3eEbJK
Content-Disposition: form-data; name="username"

mm
------WebKitFormBoundarySKpht2KuvC3eEbJK
Content-Disposition: form-data; name="file1"; filename="1.txt"
Content-Type: text/plain

11111111111111111111111
------WebKitFormBoundarySKpht2KuvC3eEbJK
Content-Disposition: form-data; name="file2"; filename="桌面路径.txt"
Content-Type: text/plain

桌面路径：
/Users/mengmei/Desktop
------WebKitFormBoundarySKpht2KuvC3eEbJK--

*/
