package com.swinginwind.portal.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swinginwind.portal.admin.util.WebHelper;
import com.swinginwind.portal.org.entity.Resource;

/**
 * 后台工程欢迎页
 * @author Jeff Xu
 * @since 2017-12-02
 */
@Controller
public class IndexController {
	
	/**
	 * 入口
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		//跳转第一个菜单
		List<Resource> hasResource = (List<Resource>) request.getSession().getAttribute(WebHelper.SESSION_MENU_RESOURCE);
		if(hasResource != null && !hasResource.isEmpty()){
			for(Resource resource : hasResource){
				
				List<Resource> chResources = resource.getChildren();
				if(StringUtils.isNotBlank(resource.getUrl()) && (chResources == null || chResources.isEmpty())){
					return "redirect:" + resource.getUrl();
				}
				if(chResources != null && !chResources.isEmpty()){
					for(Resource chRes : chResources){
						if(StringUtils.isNotBlank(chRes.getUrl())){
							return "redirect:" + chRes.getUrl();
						}
					}
				}
			}
		}
		
		return "redirect:/user/list";
	}
	

}
