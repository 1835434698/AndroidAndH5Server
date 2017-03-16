package com.tang.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sun.media.jfxmedia.logging.Logger;
import com.tang.bean.User;
import com.tang.service.UserService;
import com.tang.util.StreamOperator;

@Controller
@RequestMapping("/mvc")
public class mvcController {

	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception ex) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex);
		// System.out.println("in testExceptionHandler");
		return mv;
	}

	@RequestMapping
	public String error() {

		return "error";
	}

	@RequestMapping("/hello")
	public String hello() {

		return "hello";
	}
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {

		try {
		JSONArray jay = new JSONArray();

		JSONObject json = new JSONObject();

		for(int i=0; i<5; i++ ){
			json.put("content", "中国共产党在领导社会主义事业中"+i+"，必须坚持以______为中心，其他各项工作都服从和服务于这个中心");
	
			json.put("option_a", "A政治建设");
			json.put("option_b", "B登录成功");
			json.put("option_c", "C登录成功");
			json.put("option_d", "D登录成功");
		}
		
		jay.put(json);

		ServletOutputStream os = response.getOutputStream();
		os.write(jay.toString().getBytes("UTF-8"));

		StreamOperator.close(os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return "hello";
	}

	

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		try {

			response.setHeader("Access-Control-Allow-Origin", "*");
	
			// 获取参数方法一。
			// resuest.getParameter("password");2016-05-21%2B09%253A52%253A12.775
			String str = new String(StreamOperator.getInputStreamBytes(request.getInputStream()));
//			System.out.print("str = "+str);
			ApplicationContext act = new ClassPathXmlApplicationContext( "springmvc-servlet.xml");
			String username = request.getParameter("userName");
			String password = request.getParameter("userPassword");
			System.out.print("username = "+username+", password = "+password);
			Map<String, String[]> parameterMap = request.getParameterMap();
			
			Cookie cookie=new Cookie("isLogin", "true");
			response.addCookie(cookie);
			
//		

			UserService userService = (UserService) act.getBean("userService");
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
//			userService.save(user);

			//查询数据库
			boolean result = userService.query(user);
//			boolean result = true;
			JSONObject json = new JSONObject();
			if (result) {

				json.put("retCode", "000000");
				json.put("retMessage", "登录成功");
			}else {
				json.put("retCode", "000001");
				json.put("retMessage", "用户名或者密码不正确");
				
			}

			ServletOutputStream os = response.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));

			StreamOperator.close(os);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	@RequestParam(value = "loginName", required = false) String loginName
//	public Map<String, Object> login(WebRequest request){
//		
//	}

	@RequestMapping("/gridView")
	public void gridView(HttpServletRequest request, HttpServletResponse response){

		try {
			JSONObject json = new JSONObject();

			json.put("retCode", "000000");
			
			JSONArray jsonArray = new JSONArray();
			JSONObject item = new JSONObject();
			
			item.put("text","第一个");
			item.put("name", "one");
			jsonArray.put(item);

			JSONObject item1 = new JSONObject();
			item1.put("text","第二个");
			item1.put("name", "App");
			jsonArray.put(item1);

			JSONObject item2 = new JSONObject();
			item2.put("text","第三个");
			item2.put("name", "three");
			jsonArray.put(item2);

			JSONObject item3 = new JSONObject();
			item3.put("text","第四个");
			item3.put("name", "four");
			jsonArray.put(item3);

			JSONObject item4 = new JSONObject();
			item4.put("text","第五个");
			item4.put("name", "five");
			jsonArray.put(item4);

			JSONObject item5 = new JSONObject();
			item5.put("text","第六个");
			item5.put("name", "six");
			jsonArray.put(item5);
			
			json.put("gridView", jsonArray);

			ServletOutputStream os = response.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));

			StreamOperator.close(os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	@RequestMapping(value="/getFile", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String userName, String userPassword, HttpSession session){
		System.out.print(userName);
		return null;
	}


	@RequestMapping("/getFile")
	public void getFile(HttpServletRequest request, HttpServletResponse response){
		 try {
			//得到要下载的文件名
			String fileName = request.getParameter("filename");  //23239283-92489-阿凡达.avi
//			String fileName = "App";  //23239283-92489-阿凡达.avi
			fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
			//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
			String fileSaveRootPath=request.getServletContext().getRealPath("/WEB-INF/download");
			//通过文件名找出文件的所在目录
//			String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
			//得到要下载的文件
			File file = new File(fileSaveRootPath + "\\" + fileName+".zip");
			//如果文件不存在
			if(!file.exists()){
			    request.setAttribute("message", "您要下载的资源已被删除！！");
			    request.getRequestDispatcher("/message.jsp").forward(request, response);
			    return;
			}
			
			   //处理文件名
			String realname = fileName.substring(fileName.indexOf("_")+1);
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			//读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName+".zip");
			//创建输出流
			OutputStream out = response.getOutputStream();
			//创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			   out.write(buffer, 0, len);
			}
			//关闭文件输入流
			in.close();
			//关闭输出流
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
//		File file1 = new File("");	

//		ServletOutputStream os = response.getOutputStream();
//		os.write(file1.);
//		os.write(arg0);

	}

	@RequestMapping("/getVersion")
	public void getVersion(HttpServletRequest request, HttpServletResponse response){


		System.out.print("start");
		try {
			JSONObject json = new JSONObject();
			json.put("down_url", "/MySpringWeb/mvc/getDex");
			json.put("version_code", "2.0.0");
	
			ServletOutputStream os = response.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));
	
			StreamOperator.close(os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("over");

	}

	@RequestMapping("/getDex")
	public void getDex(HttpServletRequest request, HttpServletResponse response){
		 try {
			//得到要下载的文件名
			String fileName = "App.zip";  
			//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
			String fileSaveRootPath=request.getServletContext().getRealPath("/WEB-INF/upload");
			//通过文件名找出文件的所在目录
//			String path = findFileSavePathByFileName(fileName,fileSaveRootPath);
			//得到要下载的文件
			File file = new File(fileSaveRootPath + "\\" + fileName);
			//如果文件不存在
			if(!file.exists()){
			    request.setAttribute("message", "您要下载的资源已被删除！！");
			    request.getRequestDispatcher("/message.jsp").forward(request, response);
			    return;
			}
			
			//处理文件名
			String realname = fileName.substring(fileName.indexOf("_")+1);
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			//读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);
			//创建输出流
			OutputStream out = response.getOutputStream();
			//创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			//循环将输入流中的内容读取到缓冲区当中
			while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			   out.write(buffer, 0, len);
			}
			//关闭文件输入流
			in.close();
			//关闭输出流
			out.close();
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		File file1 = new File("");
		

//		ServletOutputStream os = response.getOutputStream();
//		os.write(file1.);
//		os.write(arg0);

	}
	
	/**
	* @Method: findFileSavePathByFileName
	* @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
	* @Anthor:孤傲苍狼
	* @param filename 要下载的文件名
	* @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
	* @return 要下载的文件的存储目录
	*/ 
	public String findFileSavePathByFileName(String filename,String saveRootPath){
	    int hashcode = filename.hashCode();
	    int dir1 = hashcode&0xf;  //0--15
	    int dir2 = (hashcode&0xf0)>>4;  //0-15
	    String dir = saveRootPath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
	    File file = new File(dir);
	    if(!file.exists()){
	        //创建目录
	        file.mkdirs();
	    }
	    return dir;
	}
	
	/**
	 * 获取参数方法二。
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}")
	public String getParamere(@PathVariable String id) {

		return id;
	}
//
//	@RequestMapping(value = "/{id}/{str}")
//	public ModelAndView helloWorld(@PathVariable String id,
//			@PathVariable String str) {
//		System.out.println(id);
//		System.out.println(str);
//		return new ModelAndView("/helloWorld");
//	}

}
