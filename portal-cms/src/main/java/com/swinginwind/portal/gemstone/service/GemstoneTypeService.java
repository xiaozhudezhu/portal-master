package com.swinginwind.portal.gemstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.portal.common.service.CommonService;
import com.swinginwind.portal.gemstone.dao.GemstoneTypeDao;
import com.swinginwind.portal.gemstone.dao.GemstoneTypeSelectDao;
import com.swinginwind.portal.gemstone.entity.GemstoneType;
import com.swinginwind.portal.gemstone.entity.GemstoneTypeSelect;

@Service
public class GemstoneTypeService extends CommonService<GemstoneType, String> {
	
	@Autowired
	private GemstoneTypeSelectDao typeSelectDao;
	
	private List<GemstoneTypeSelect> rootTypeSelects;

	@Autowired
	public void setDao(GemstoneTypeDao dao) {
		super.setCommonDao(dao);
		initTypeSelect();
	}
	
	public void initTypeSelect() {
		rootTypeSelects = this.typeSelectDao.getRootTypeList();
	}
	
	public List<GemstoneTypeSelect> getRootTypeList() {
		return rootTypeSelects;
	}
	
}
