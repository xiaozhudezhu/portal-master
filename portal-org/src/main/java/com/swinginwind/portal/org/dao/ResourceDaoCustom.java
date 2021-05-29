package com.swinginwind.portal.org.dao;

import java.util.List;
import java.util.Map;

import com.swinginwind.portal.org.entity.Resource;


public interface ResourceDaoCustom {

	public List<Resource> findMenuResource(Map<String, Object> params);
	
}
