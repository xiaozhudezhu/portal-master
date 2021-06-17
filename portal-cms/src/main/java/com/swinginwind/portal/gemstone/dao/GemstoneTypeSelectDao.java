package com.swinginwind.portal.gemstone.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.swinginwind.portal.common.dao.CommonDao;
import com.swinginwind.portal.gemstone.entity.GemstoneTypeSelect;

public interface GemstoneTypeSelectDao extends CommonDao<GemstoneTypeSelect, String> {
	
	@Query("select r from GemstoneTypeSelect r where r.parentId = '0' order by r.orderNo, code")
	List<GemstoneTypeSelect> getRootTypeList();

}
