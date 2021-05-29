package com.swinginwind.portal.org.dao;

import java.util.List;
import java.util.Map;

import com.swinginwind.portal.common.entity.PageModel;
import com.swinginwind.portal.org.dto.RoleQueryDTO;
import com.swinginwind.portal.org.entity.Role;

public interface RoleCustomDao {
	
	public List<Role> findRoles(Map<String, Object> params);
	
	/**
	 * 根据查询条件查询角色分页信息
	 * @param userQueryDTO
	 * @return
	 */
	PageModel<Role> queryRolePage(RoleQueryDTO roleQueryDTO);
	
}
