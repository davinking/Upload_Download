package cn.mengmei.web.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String fileName = request.getParameter("fileName");
		fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
		System.out.println(fileName);
		
		String realPath = makePath(fileName, this.getServletContext().getRealPath("/WEB-INF/upload"));
		
		File file = new File(realPath+"/"+fileName);
		System.out.println(file.getAbsolutePath());
		
		if(!file.exists()){
			request.setAttribute("message", "对不起，您要下载的资源已经被系统删除!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		FileInputStream in = new FileInputStream(file);
		
		fileName = fileName.substring(fileName.indexOf("_")+1);
		
		String guessCharset = "UTF-8"; /*根据request的locale 得出可能的编码，中文操作系统通常是gb2312*/ 
	    fileName = new String(fileName.getBytes(guessCharset), "ISO8859-1"); 
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		
		OutputStream out = response.getOutputStream();
		
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
	
	public String makePath(String fileName, String savePath){
		int hashCode = fileName.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode & 0xf0) >> 4;
		
		String dir = savePath + "/" + dir1 + "/" + dir2;
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs(); //创建多级目录，用此方法。
		}
		return dir;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
