package com.jeff.tianti.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeff.tianti.common.dto.AjaxResult;
import com.jeff.tianti.gemstone.service.GemstoneReportService;

@Controller
@RequestMapping("/gr")
public class GemstoneReportController {
	
	@Autowired
	private GemstoneReportService gemstoneReportService;

	@RequestMapping("/generateFile")
	@ResponseBody
	public AjaxResult generateFile(String reportId) {
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setSuccess(false);
		gemstoneReportService.generateFile(reportId);
		return ajaxResult;
	}
}
