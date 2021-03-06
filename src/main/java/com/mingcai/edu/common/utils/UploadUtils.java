package com.mingcai.edu.common.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.mingcai.edu.modules.oa.entity.OaProductResources;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 *
 * @author yangdc
 * @date Apr 18, 2012
 *
 * <pre>
 * </pre>
 */
public class UploadUtils {
	/**
	 * 表单字段常量
	 */
	public static final String FORM_FIELDS = "uploadFile";
	/**
	 * 文件域常量
	 */
	public static final String FILE_FIELDS = "uploadFile";

	// 最大文件大小
	private long maxSize = 1000*1024*1024;
	// 定义允许上传的文件扩展名
	public  Map<String, String> extMap = new HashMap<String, String>();
	// 文件保存目录相对路径
	private String basePath = "uploadFile";
	// 文件的目录名
	private String dirName = "all";
	public String img="images";
	// 上传临时路径
	private static final String TEMP_PATH = "/temp";
	private String tempPath = "upload" + TEMP_PATH;
	// 若不指定则文件名默认为 yyyyMMddHHmmss_xyz
	private String fileName;
	private String saveName;
	// 文件保存目录路径
	private String savePath;
	// 文件保存目录url
	private String saveUrl;
	// 文件最终的url包括文件名
	private String fileUrl;
	// 文件大小
	private String fileSize;

	protected String filex="gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf,mmap,xmind";
	//文件格式
	private String fileExt;
	public UploadUtils() {
		// 其中images,flashs,medias,files,对应文件夹名称,对应dirName
		// key文件夹名称
		// value该文件夹内可以上传文件的后缀名
		extMap.put("all",filex);
		extMap.put("images", "gif,jpg,jpeg,png,bmp");
		extMap.put("flashs", "swf,flv");
		extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("files", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2");
	}

	public String[] uploadFile(HttpServletRequest request,MultipartFile file) {
		String[] infos = new String[8];
		infos[0] = this.validateFields(request,file);
		String info=this.saveFile(file);
		if(!"true".equals(info)){
			infos[0]=info;
		}
		infos[1] = savePath;
		infos[2] = saveUrl;
		infos[3] = fileUrl;
		infos[4] = fileName;
		infos[5] = fileExt;
		infos[6] = fileSize;
		infos[7] =saveName;

		return infos;
	}

	/**
	 * 文件上传
	 *
	 * @param request
	 * @return infos info[0] 验证文件域返回错误信息 info[1] 上传文件错误信息 info[2] savePath info[3] saveUrl info[4] fileUrl
	 */
//	@SuppressWarnings("unchecked")
//	public String[] uploadFile(HttpServletRequest request) {
//		String[] infos = new String[5];
//		// 验证
//		infos[0] = this.validateFields(request);
//		// 初始化表单元素
//		Map<String, Object> fieldsMap = new HashMap<String, Object>();
//		if (infos[0].equals("true")) {
//			fieldsMap = this.initFields(request);
//		}
//		// 上传
//		List<FileItem> fiList = (List<FileItem>) fieldsMap.get(UploadUtils.FILE_FIELDS);
//		if (fiList != null) {
//			for (FileItem item : fiList) {
//				infos[1] = this.saveFile(item);
//			}
//			infos[2] = savePath;
//			infos[3] = saveUrl;
//			infos[4] = fileUrl;
//		}
//		return infos;
//	}

	/**
	 * 上传验证,并初始化文件目录
	 *
	 * @param request
	 */
//	private String validateFields(HttpServletRequest request) {
//		String errorInfo = "true";
//		// boolean errorFlag = true;
//		// 获取内容类型
//		String contentType = request.getContentType();
//		int contentLength = request.getContentLength();
//		// 文件保存目录路径
//		savePath = request.getSession().getServletContext().getRealPath("/") + basePath + "/"+request.getParameter("path");
//		// 文件保存目录URL
//		saveUrl = request.getContextPath() + "/" + basePath + "/"+request.getParameter("path");
//		File uploadDir = new File(savePath);
//		if (contentType == null || !contentType.startsWith("multipart")) {
//			// TODO
//			System.out.println("请求不包含multipart/form-data流");
//			errorInfo = "请求不包含multipart/form-data流";
//		} else if (maxSize < contentLength) {
//			// TODO
//			System.out.println("上传文件大小超出文件最大大小");
//			errorInfo = "上传文件大小超出文件最大大小[" + maxSize + "]";
//		} else if (!ServletFileUpload.isMultipartContent(request)) {
//			// TODO
//			errorInfo = "请选择文件";
//		} else if (!uploadDir.isDirectory()) {// 检查目录
//			// TODO
//			errorInfo = "上传目录[" + savePath + "]不存在";
//		} else if (!uploadDir.canWrite()) {
//			// TODO
//			errorInfo = "上传目录[" + savePath + "]没有写权限";
//		} else if (!extMap.containsKey(dirName)) {
//			// TODO
//			errorInfo = "目录名不正确";
//		} else {
//			File dirFile = new File(savePath);
//			if (!dirFile.exists()) {
//				dirFile.mkdirs();
//			}
//
//			// 获取上传临时路径
//			tempPath = request.getSession().getServletContext().getRealPath("/") + tempPath + "/";
//			File file = new File(tempPath);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//		}
//
//		return errorInfo;
//	}
	/**
	 * 上传验证,并初始化文件目录
	 *
	 * @param request
	 */
	private String validateFields(HttpServletRequest request,MultipartFile files) {
		String errorInfo = "true";
		// 获取内容类型
		String contentType = request.getContentType();
		int contentLength = request.getContentLength();
		String path=request.getParameter("respath");
		if(path==null){
			path= (String) request.getAttribute("respath");
		}
		// 文件保存目录路径
		savePath = request.getSession().getServletContext().getRealPath("/") + basePath + "/"+path;
		// 文件保存目录URL
		saveUrl = request.getContextPath() + "/" + basePath + "/"+path;
		File uploadDir = new File(savePath);
		if (contentType == null || !contentType.startsWith("multipart")) {
			// TODO
			System.out.println("请求不包含multipart/form-data流");
			errorInfo = "请求不包含multipart/form-data流";
		} else if (maxSize < contentLength) {
			// TODO
			System.out.println("上传文件大小超出文件最大大小");
			errorInfo = "上传文件大小超出文件最大大小[" + maxSize + "]";
		} else if (!ServletFileUpload.isMultipartContent(request)) {
			// TODO
			errorInfo = "请选择文件";
		}else {
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
//			// 获取上传临时路径
//			tempPath = request.getSession().getServletContext().getRealPath("/") + tempPath + "/";
//			File file = new File(tempPath);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
		}

		return errorInfo;
	}
	/**
	 * 处理上传内容
	 *
	 * @param request
	 * @return
	 */
//	@SuppressWarnings("unchecked")
	private Map<String, Object> initFields(HttpServletRequest request) {

		// 存储表单字段和非表单字段
		Map<String, Object> map = new HashMap<String, Object>();

		// 第一步：判断request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 第二步：解析request
		if (isMultipart) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 阀值,超过这个值才会写到临时目录,否则在内存中
			factory.setSizeThreshold(1024 * 1024 * 10);
			factory.setRepository(new File(tempPath));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("UTF-8");

			// 最大上传限制
			upload.setSizeMax(maxSize);

			/* FileItem */
			List<FileItem> items = null;
			// Parse the request
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 第3步：处理uploaded items
			if (items != null && items.size() > 0) {
				Iterator<FileItem> iter = items.iterator();
				// 文件域对象
				List<FileItem> list = new ArrayList<FileItem>();
				// 表单字段
				Map<String, String> fields = new HashMap<String, String>();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					// 处理所有表单元素和文件域表单元素
					if (item.isFormField()) { // 表单元素
						String name = item.getFieldName();
						String value = item.getString();
						fields.put(name, value);
					} else { // 文件域表单元素
						list.add(item);
					}
				}
				map.put(FORM_FIELDS, fields);
				map.put(FILE_FIELDS, list);
			}
		}
		return map;
	}

	/**
	 * 保存文件
	 *
	 * @return
	 */
//	private String saveFile(FileItem item) {
//		String error = "true";
//		String fileName = item.getName();
//		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//
//		if (item.getSize() > maxSize) { // 检查文件大小
//			// TODO
//			error = "上传文件大小超过限制";
//		} else if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {// 检查扩展名
//			error = "上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。";
//		} else {
//			String newFileName;
//			if ("".equals(fileName.trim())) {
//				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//				newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//			} else {
//				newFileName = fileName + "." + fileExt;
//			}
//			// .../basePath/dirName/yyyyMMdd/yyyyMMddHHmmss_xxx.xxx
//			fileUrl = saveUrl + newFileName;
//			try {
//				File uploadedFile = new File(savePath, newFileName);
//
//				item.write(uploadedFile);
//
//				/*
//				 * FileOutputStream fos = new FileOutputStream(uploadFile); // 文件全在内存中 if (item.isInMemory()) { fos.write(item.get()); } else { InputStream is = item.getInputStream(); byte[] buffer =
//				 * new byte[1024]; int len; while ((len = is.read(buffer)) > 0) { fos.write(buffer, 0, len); } is.close(); } fos.close(); item.delete();
//				 */
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println("上传失败了！！！");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return error;
//	}
	/**
	 * 保存文件
	 *
	 * @return
	 */
	private String saveFile(MultipartFile item) {
		String error = "true";
		String fileName = item.getOriginalFilename();
		this.fileSize=item.getSize()/1024+"KB";
		this.fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		this.fileName=fileName.substring(0,fileName.lastIndexOf(".")).toLowerCase();
		if (item.getSize() > maxSize) { // 检查文件大小
			// TODO
			error = "上传文件大小超过限制";
		} else if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {// 检查扩展名
			error = "上传文件扩展名是不允许的扩展名";

		} else {
			String newFileName;
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
			this.saveName=newFileName;
			// .../basePath/dirName/yyyyMMdd/yyyyMMddHHmmss_xxx.xxx
			savePath = savePath +"/"+newFileName;
			try {
				File uploadedFile = new File(savePath);
				FileUtils.copyInputStreamToFile(item.getInputStream(), uploadedFile);
				/*
				 * FileOutputStream fos = new FileOutputStream(uploadFile); // 文件全在内存中 if (item.isInMemory()) { fos.write(item.get()); } else { InputStream is = item.getInputStream(); byte[] buffer =
				 * new byte[1024]; int len; while ((len = is.read(buffer)) > 0) { fos.write(buffer, 0, len); } is.close(); } fos.close(); item.delete();
				 */
				return "true";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("上传失败了！！！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return error;
	}
	/** **********************get/set方法********************************* */

	public String getSavePath() {
		return savePath;
	}

	public String getSaveUrl() {
		return saveUrl;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public Map<String, String> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
		tempPath = basePath + TEMP_PATH;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
}
