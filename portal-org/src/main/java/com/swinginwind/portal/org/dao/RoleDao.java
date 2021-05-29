package com.swinginwind.portal.org.dao;


import org.springframework.data.jpa.repository.Query;

import com.swinginwind.portal.common.dao.CommonDao;
import com.swinginwind.portal.org.entity.Role;

public interface RoleDao extends RoleCustomDao,CommonDao<Role,String>{

	@Query("from Role r where r.roleName = ?1 ")
	public Role findByRoleName(String roleName);
	
}
