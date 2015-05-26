package cn.mengmei.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadServlet1 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20); //设置缓冲区的大小为20KB，如果上传文件超过20KB，则不使用缓冲区来保存，使用临时文件来保存。默认为10KB。
		factory.setRepository(new File(this.getServletContext().getRealPath("/WEB-INF/temp"))); //设置临时文件保存位置。
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		//监听文件上传进度,一定要放在解析request之前。
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long pBytesRead, long pContentLength, int pItems){
				//pBytesRead已经处理的字节数，pContentLength文件总字节数，pItems当前正在上传第几个文件(如果是多个文件上传)
				System.out.println("total: "+pContentLength+" bytes, have finished: "+pBytesRead+" bytes, at present uploading the "+pItems+" item");
			}
		});
		upload.setHeaderEncoding("UTF-8"); //解决上传文件名和文件数据的中文乱码问题
		
		if(!upload.isMultipartContent(request)){
			//如果不是上传表单，则采用传统方式获取表单数据。
			return;
		}
		
		try {
			upload.setFileSizeMax(1048576); //设置单个文件上传不能超过1048576个字节(byte)＝1MB。
			upload.setSizeMax(20971520); //设置上传文件总量的最大值为20MB。
			
			List<FileItem> list = upload.parseRequest(request);
			
			String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
			System.out.println(savePath);
			
			for(FileItem item : list){
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8"); //解决表单项的中文乱码问题
					System.out.println(name+"="+value);
				}else{
					String fileName = item.getName();
					if(fileName==null || fileName.trim().equals("")){
						continue; //continue是结束单次循环，break是结束整个循环体。
					}
					System.out.println(fileName);
					fileName = fileName.substring(fileName.indexOf("/")+1);
					
					//根据检查文件名后缀来控制上传文件的类型
					if((!fileName.endsWith(".jpg")) && (!fileName.endsWith(".txt")) && (!fileName.endsWith(".avi"))){
						request.setAttribute("message", "只能上传 .jpg 、 .avi 和 .txt 文件");
						request.getRequestDispatcher("/message.jsp").forward(request, response);
						return;
					}
					
					InputStream in = item.getInputStream();
					
					/*为了避免上传文件重名，使用UUID算法生成文件名前缀。
					为了避免一个目录保存太多上传文件，用文件名的哈西值计算一级目录和二级目录。
					(文件巨多时还可依次计算多级目录)(每级目录有0-15共16个文件夹)*/
					String realFileName = makeFileName(fileName);
					String realSavePath = makePath(realFileName, savePath);
					
					OutputStream out = new FileOutputStream(realSavePath+"/"+realFileName);
					byte[] buffer = new byte[1024];
					int len = 0;
					
					while((len=in.read(buffer))>0){
						out.write(buffer, 0, len);
					}
					
					in.close();
					out.close();
					item.delete(); //确保在关闭流后,删除临时文件。
				}
			}
			request.setAttribute("message", "上传成功!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}catch(FileUploadBase.SizeLimitExceededException e){
			e.printStackTrace();
			request.setAttribute("message", "上传文件总大小超过了配置的最大值（ 20MB ）");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}catch(FileUploadBase.FileSizeLimitExceededException e){
			e.printStackTrace();
			request.setAttribute("message", "上传文件超过了1MB的最大允许大小。");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传失败，请稍后再试!");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}
	
	public String makeFileName(String fileName){
		return UUID.randomUUID().toString() + "_" + fileName;
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
