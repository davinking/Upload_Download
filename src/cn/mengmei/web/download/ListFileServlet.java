package cn.mengmei.web.download;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListFileServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String filePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(filePath);
		
		Map map = new HashMap();
		listFile(file, map);
		
		System.out.println(map.size());
		
		request.setAttribute("map", map);
		request.getRequestDispatcher("/download.jsp").forward(request, response);
	}
	
	public void listFile(File file, Map map){
		if(!file.isFile()){
			File[] files = file.listFiles();
			for(File f : files){
				listFile(f, map);
			}
		}else{
			if(!file.isHidden()){
				String fileName = file.getName();
				String name = fileName.substring(fileName.indexOf("_")+1);
				map.put(fileName, name);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
