package com.swinginwind.portal.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.swinginwind.portal.common.config.AppConfig;
import com.swinginwind.portal.common.dto.AjaxResult;

/**
 * 文件异步上传Controller
 * 
 * @author JeffXu
 * @since 2016-03-14
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	public final static String ATTACH_SAVE_PATH = "attach";

	private static final int FILE_BUFFER_SIZE = 10240;

	@Autowired
	private AppConfig appConfig;

	@RequestMapping("/uploadAttach")
	public void uploadAttach(HttpServletRequest request, PrintWriter out) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		MultipartFile multipartFile = null;
		String fileName = null;
		for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
			multipartFile = set.getValue();// 文件名
		}
		fileName = this.storeIOc(multipartRequest, multipartFile);

		out.print(fileName);
	}

	@RequestMapping("/ajax/upload_file")
	@ResponseBody
	public AjaxResult ajaxUploadFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		try {

			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			MultipartFile multipartFile = null;
			String fileName = null;
			for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
				multipartFile = set.getValue();// 文件名
			}
			fileName = this.storeIOc(multipartRequest, multipartFile);

			ajaxResult.setData(fileName);
			ajaxResult.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ajaxResult;
	}

	// 接受图片，返回文件地址
	private String storeIOc(HttpServletRequest request, MultipartFile file) {
		String result = "";
		String realPath = !StringUtils.isEmpty(appConfig.getFileDir()) ? appConfig.getFileDir()
				: request.getSession().getServletContext().getRealPath("");

		if (file == null) {
			return null;
		}
		String fileName = "";
		String logImageName = "";
		if (file.isEmpty()) {
			result = "文件未上传";
		} else {
			String _fileName = file.getOriginalFilename();
			String suffix = _fileName.substring(_fileName.lastIndexOf("."));
			if (StringUtils.isNotBlank(suffix)) {
				if (suffix.equalsIgnoreCase(".xls") || suffix.equalsIgnoreCase(".xlsx")
						|| suffix.equalsIgnoreCase(".txt") || suffix.equalsIgnoreCase(".png")
						|| suffix.equalsIgnoreCase(".doc") || suffix.equalsIgnoreCase(".docx")
						|| suffix.equalsIgnoreCase(".pdf") || suffix.equalsIgnoreCase(".ppt")
						|| suffix.equalsIgnoreCase(".pptx") || suffix.equalsIgnoreCase(".gif")
						|| suffix.equalsIgnoreCase(".jpg") || suffix.equalsIgnoreCase(".jpeg")
						|| suffix.equalsIgnoreCase(".bmp")) {
					// /**使用UUID生成文件名称**/
					logImageName = UUID.randomUUID().toString() + suffix;

					fileName = realPath + "/uploads/" + ATTACH_SAVE_PATH + File.separator + logImageName;
					File restore = new File(fileName);
					try {
						file.transferTo(restore);
						result = "/uploads/attach/" + logImageName;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				} else {
					result = "文件格式不对，只能上传ppt、ptx、doc、docx、xls、xlsx、pdf、png、jpg、jpeg、gif、bmp格式";
				}
			}
		}
		return result;
	}

	@RequestMapping("/getImage")
	public String getImage(String imagePath, HttpServletRequest request, HttpServletResponse response) {
		return this.downLoadFile(appConfig.getFileDir() + "/" + imagePath, "", null, request, response, "img");
	}

	/**
	 * 文件下载通用方法, 可以被其他action调用
	 * 
	 * @param filePath
	 *            文件完整路径
	 * @param fileName
	 *            下载显示的文件名
	 * @param contentType
	 *            内容类型
	 * @return page
	 */
	public String downLoadFile(String filePath, String fileName, String contentType, HttpServletRequest request,
			HttpServletResponse response, String type) {
		File destFile = new File(filePath);
		if (destFile.exists()) {
			try {
				InputStream bais;
				bais = new FileInputStream(destFile);
				byte[] buffer = new byte[FILE_BUFFER_SIZE];

				if (fileName == null || fileName.equals(""))
					fileName = destFile.getName();
				// fileName = new String(fileName.getBytes("gbk"),
				// "iso-8859-1");
				response.reset();
				if (contentType == null)
					contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

				if (type != null && "img".equals(type)) {
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (!"".equals(prefix)) {
						prefix = prefix.toLowerCase();
						if ("jpg".equals(prefix) || "jpeg".equals(prefix)) {
							contentType = MediaType.IMAGE_JPEG_VALUE;
						} else if ("png".equals(prefix)) {
							contentType = MediaType.IMAGE_PNG_VALUE;
						} else if ("gif".equals(prefix)) {
							contentType = MediaType.IMAGE_GIF_VALUE;
						} else {
							response.setHeader("Content-disposition", "filename=\"" + fileName + "\"");
						}
					} else {
						response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
					}
				} else {
					response.setHeader("Content-disposition", "filename=\"" + fileName + "\"");
				}
				response.setContentType(contentType);

				// 设置文件大小, 客户端可以读取文件大小显示进度
				response.setContentLength((int) destFile.length());
				OutputStream out = response.getOutputStream();
				int length = 0;
				while ((length = bais.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				out.flush();
				bais.close();
				out.close();
				return null;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}

}