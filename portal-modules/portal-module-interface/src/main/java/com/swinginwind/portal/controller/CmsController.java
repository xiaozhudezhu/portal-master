package com.swinginwind.portal.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swinginwind.portal.cms.dto.ArticleQueryDTO;
import com.swinginwind.portal.cms.dto.ColumnInfoQueryDTO;
import com.swinginwind.portal.cms.dto.CurrentArticleInfoDTO;
import com.swinginwind.portal.cms.entity.Article;
import com.swinginwind.portal.cms.entity.ColumnInfo;
import com.swinginwind.portal.cms.service.ArticleService;
import com.swinginwind.portal.cms.service.ColumnInfoService;
import com.swinginwind.portal.common.dto.AjaxResult;
import com.swinginwind.portal.common.entity.PageModel;
/**
 * CMS的Controller
 * @author Jeff Xu
 */
@Controller
@RequestMapping("/cms/api")
public class CmsController {
	
	@Autowired
	private ColumnInfoService columnInfoService;
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 获取栏目列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/column/list")
	@ResponseBody
	public AjaxResult getColumnList(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		AjaxResult ajaxResult = new AjaxResult();
		String code = request.getParameter("code");
		String name = request.getParameter("name");
		String levelStr = request.getParameter("level");
		String columnId = request.getParameter("columnId");
		String rootColumnId = request.getParameter("rootColumnId");
		//1为true,0为false
		String isRootColumnLikeStr= request.getParameter("isRootColumnLike");
		Integer level = null;
		Boolean isRootColumnLike = false;
		if(StringUtils.isNotBlank(levelStr)){
			level = Integer.parseInt(levelStr);
		}
		if(StringUtils.isNotBlank(isRootColumnLikeStr) && isRootColumnLikeStr.equals("1")){
			isRootColumnLike = true;
		}
		ColumnInfoQueryDTO columnInfoQueryDTO = new ColumnInfoQueryDTO();
		columnInfoQueryDTO.setLevel(level);
		columnInfoQueryDTO.setCode(code);
		columnInfoQueryDTO.setName(name);
		columnInfoQueryDTO.setRootColumnId(rootColumnId);
		columnInfoQueryDTO.setIsRootColumnLike(isRootColumnLike);
		columnInfoQueryDTO.setColumnId(columnId);
		List<ColumnInfo> list = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
		if(level != null && level == 0) {
			for(ColumnInfo info : list) {
				columnInfoQueryDTO = new ColumnInfoQueryDTO();
				columnInfoQueryDTO.setRootColumnId(info.getId());
				columnInfoQueryDTO.setIsRootColumnLike(false);
				info.setChildren(this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO));
				for(ColumnInfo info1 : info.getChildren())
					info1.setParent(null);
			}
		}
		if("4028821e5b7a0971015b7a0a1cbf0000".equals(rootColumnId)) {
			List<ColumnInfo> list1 = list;
			list = new ArrayList<ColumnInfo>();
			for(ColumnInfo info : list1) {
				columnInfoQueryDTO = new ColumnInfoQueryDTO();
				columnInfoQueryDTO.setCode(info.getCode().replace("home_", ""));
				columnInfoQueryDTO.setIsCodeLike(false);
				List<ColumnInfo> tempList = this.columnInfoService.queryColumnInfoList(columnInfoQueryDTO);
				if(tempList.size() > 0) {
					tempList.get(0).setLayout(info.getLayout());
					tempList.get(0).setName(info.getName());
					list.add(tempList.get(0));
				}
			}
		}
		ajaxResult.setSuccess(true);
		ajaxResult.setData(list);
		return ajaxResult;
	}	
	
	/**
	 * 获取文章列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/list")
	@ResponseBody
	public AjaxResult getArticleList(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		AjaxResult ajaxResult = new AjaxResult();
		String columnId = request.getParameter("columnId");
		//是否分页:1为是，0为否，默认为分页
		String isPage = request.getParameter("isPage");
		
		//获取前N条的数据
		String topStr = request.getParameter("top");
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");
		
		int currentPage = 1;
		int pageSize = 10;
		Integer top = null;
		if(StringUtils.isNotBlank(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		if(StringUtils.isNotBlank(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		if(StringUtils.isNotBlank(topStr)){
			top = Integer.parseInt(topStr);
		}
		
		PageModel<Article> page = null;
		List<Article> list = null;
		
		ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
		articleQueryDTO.setColumnId(columnId);
		articleQueryDTO.setCurrentPage(currentPage);
		articleQueryDTO.setPageSize(pageSize);
		articleQueryDTO.setTop(top);
		articleQueryDTO.setDeleteFlag(Article.DELETE_FLAG_NORMAL);
		articleQueryDTO.setIsFront(true);
		if(StringUtils.isNotBlank(isPage)){
			if(isPage.equals("1")){
				page = this.articleService.queryArticlePage(articleQueryDTO);
				ajaxResult.setData(page);
			}else{
				list = this.articleService.queryArticleList(articleQueryDTO);
				ajaxResult.setData(list);
			}
		}else{
			page = this.articleService.queryArticlePage(articleQueryDTO);
			ajaxResult.setData(page);
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
	
	/**
	 * 获取文章详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/detail")
	@ResponseBody
	public AjaxResult getArticle(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		AjaxResult ajaxResult = new AjaxResult();
		String articleId = request.getParameter("articleId");
		Article article = null;
		if(StringUtils.isNotBlank(articleId)){
			article = this.articleService.find(articleId);
			if(article != null){
				article.setViewCount(article.getViewCount() == null ? 1 : article.getViewCount()+1);
			    this.articleService.update(article);
			}
		}
		ajaxResult.setSuccess(true);
		ajaxResult.setData(article);
		return ajaxResult;
	}
	
	/**
	 * 获取下一篇文章详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/next")
	@ResponseBody
	public AjaxResult getNextArticle(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		AjaxResult ajaxResult = new AjaxResult();
		//当前文章ID
		String currentArticleId = request.getParameter("currentArticleId");
		String columnId = request.getParameter("columnId");
		String articleDateStr = request.getParameter("articleDate");
		String orderNoStr = request.getParameter("orderNo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Article nextArticle = null;
		Date articleDate = null;
		Integer orderNo = null;
		if(StringUtils.isNotBlank(articleDateStr)){
			try {
				articleDate = sdf.parse(articleDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(orderNoStr)){
			orderNo = Integer.parseInt(orderNoStr);
		}
		CurrentArticleInfoDTO currentArticleInfoDTO = new CurrentArticleInfoDTO();
		currentArticleInfoDTO.setColumnId(columnId);
		currentArticleInfoDTO.setArticleId(currentArticleId);
		currentArticleInfoDTO.setArticleDate(articleDate);
		currentArticleInfoDTO.setOrderNo(orderNo);
		if(StringUtils.isNotBlank(columnId)){
			nextArticle = this.articleService.queryNextArticle(currentArticleInfoDTO);
		}
		ajaxResult.setSuccess(true);
		ajaxResult.setData(nextArticle);
		return ajaxResult;
	}
	
	/**
	 * 获取上一篇文章详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/article/pre")
	@ResponseBody
	public AjaxResult getPreArticle(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		AjaxResult ajaxResult = new AjaxResult();
		//当前文章ID
		String currentArticleId = request.getParameter("currentArticleId");
		String columnId = request.getParameter("columnId");
		String articleDateStr = request.getParameter("articleDate");
		String orderNoStr = request.getParameter("orderNo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Article preArticle = null;
		Date articleDate = null;
		Integer orderNo = null;
		if(StringUtils.isNotBlank(articleDateStr)){
			try {
				articleDate = sdf.parse(articleDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(orderNoStr)){
			orderNo = Integer.parseInt(orderNoStr);
		}
		CurrentArticleInfoDTO currentArticleInfoDTO = new CurrentArticleInfoDTO();
		currentArticleInfoDTO.setColumnId(columnId);
		currentArticleInfoDTO.setArticleId(currentArticleId);
		currentArticleInfoDTO.setArticleDate(articleDate);
		currentArticleInfoDTO.setOrderNo(orderNo);
		
		if(StringUtils.isNotBlank(columnId)){
			preArticle = this.articleService.queryPreArticle(currentArticleInfoDTO);
		}
		ajaxResult.setSuccess(true);
		ajaxResult.setData(preArticle);
		return ajaxResult;
	}

}
