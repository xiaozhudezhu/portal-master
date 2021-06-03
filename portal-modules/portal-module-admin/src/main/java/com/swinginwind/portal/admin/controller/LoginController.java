package com.swinginwind.portal.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swinginwind.portal.admin.util.WebHelper;
import com.swinginwind.portal.common.util.Md5Util;
import com.swinginwind.portal.org.entity.Resource;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value = "/do_login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request, Model model){
		
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		
		boolean rememberMe = false;
		
		String md5Pwd = Md5Util.generatePassword(pwd);
		
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, md5Pwd, rememberMe);
			
			Subject subject = SecurityUtils.getSubject();
			
			subject.login(token);
			SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
			if(savedRequest != null) {
				// 取得url之后对SavedRequest进行清空
				// 如果未使用接口方式，可以直接跳转url并清空，使用WebUtils中的redirectToSavedRequest方法
				return "redirect:" + savedRequest.getRequestUrl().replaceFirst(request.getContextPath(), "");
			}
			else {
				return "redirect:/";
			}
			
		} catch (LockedAccountException lae) {
//			lae.printStackTrace();
			model.addAttribute("msg", "账号已被禁用");
		} catch (AuthenticationException ae) {
//			ae.printStackTrace();
			model.addAttribute("msg", "账号或密码错误");
		} catch (Exception e) {
//			e.printStackTrace();
			model.addAttribute("msg", "登录异常");
		}
		
		return "login";
	}
	
	@RequestMapping("/login_out")
	public String loginOut(HttpServletRequest request){
		
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return "redirect:/login";
	}

}
