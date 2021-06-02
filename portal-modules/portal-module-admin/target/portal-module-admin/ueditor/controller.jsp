<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter, java.io.File, org.springframework.web.context.support.WebApplicationContextUtils,
	com.swinginwind.portal.common.config.AppConfig"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	AppConfig appConfig = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(AppConfig.class);
	String rootPath = appConfig.getFileDir() + "/ue";
	
	out.write( new ActionEnter( request, rootPath ).exec() );
	
%>