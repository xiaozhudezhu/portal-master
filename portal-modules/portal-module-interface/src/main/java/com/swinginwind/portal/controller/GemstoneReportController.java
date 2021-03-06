package com.swinginwind.portal.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.portal.common.dto.AjaxResult;
import com.swinginwind.portal.common.entity.PageModel;
import com.swinginwind.portal.gemstone.dto.GemstoneReportQueryDTO;
import com.swinginwind.portal.gemstone.entity.GemstoneReport;
import com.swinginwind.portal.gemstone.service.GemstoneReportService;

@Controller
@RequestMapping("/gr/api")
public class GemstoneReportController {

	private static final int FILE_BUFFER_SIZE = 10240;
	
	@Autowired
	private GemstoneReportService gemstoneReportService;

	/**
	 * 获取证书列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public AjaxResult list(HttpServletRequest request, GemstoneReportQueryDTO queryDTO, Model model) {
		AjaxResult ajaxResult = new AjaxResult();
		PageModel<GemstoneReport> page = gemstoneReportService.queryGemstoneReportPage(queryDTO, false);
		ajaxResult.setSuccess(true);
		ajaxResult.setData(page);
		return ajaxResult;
	}


	
	@RequestMapping("/getReportFile")
	public String getReportFile(HttpServletRequest request, HttpServletResponse response, String reportId, boolean isPrint) {
		File file = gemstoneReportService.getReportFile(reportId, isPrint, request);
		return this.downLoadFile(file.getAbsolutePath(), file.getName(), "application/pdf", request, response, null);
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
					}
					else {
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
