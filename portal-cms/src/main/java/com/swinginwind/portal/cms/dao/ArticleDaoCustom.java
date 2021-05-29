package com.swinginwind.portal.cms.dao;

import com.swinginwind.portal.cms.dto.ArticleQueryDTO;
import com.swinginwind.portal.cms.dto.CurrentArticleInfoDTO;
import com.swinginwind.portal.cms.entity.Article;
import com.swinginwind.portal.common.entity.PageModel;

import java.util.List;
import java.util.Map;
/**
 * @author xujianfang
 * @desc ArticleDaoCustom接口 
 * @date 2017-03-16
 */
public interface ArticleDaoCustom {

      PageModel<Article> queryArticlePage(ArticleQueryDTO articleQueryDTO);

      List<Article> queryArticleList(ArticleQueryDTO articleQueryDTO);

      List<Map<String, Object>> queryStatisMapList(ArticleQueryDTO articleQueryDTO);
      
      List<Article> queryNextArticleList(CurrentArticleInfoDTO currentArticleInfoDTO);
      
      List<Article> queryPreArticleList(CurrentArticleInfoDTO currentArticleInfoDTO);

}