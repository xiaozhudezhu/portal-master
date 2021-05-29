package com.swinginwind.portal.cms.dao;

import com.swinginwind.portal.cms.dto.ColumnInfoQueryDTO;
import com.swinginwind.portal.cms.entity.ColumnInfo;
import com.swinginwind.portal.common.entity.PageModel;

import java.util.List;
/**
 * @author xujianfang
 * @desc ColumnInfoDaoCustom接口 
 * @date 2017-03-16
 */
public interface ColumnInfoDaoCustom {

      PageModel<ColumnInfo> queryColumnInfoPage(ColumnInfoQueryDTO columnInfoQueryDTO);

      List<ColumnInfo> queryColumnInfoList(ColumnInfoQueryDTO columnInfoQueryDTO);



}