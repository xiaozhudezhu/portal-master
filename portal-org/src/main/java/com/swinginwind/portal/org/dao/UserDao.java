package com.swinginwind.portal.org.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.swinginwind.portal.common.dao.CommonDao;
import com.swinginwind.portal.org.entity.Resource;
import com.swinginwind.portal.org.entity.Role;
import com.swinginwind.portal.org.entity.User;

public interface UserDao extends SystemUserDao,CommonDao<User,String>{

	@Query("select u from User u where u.deleteFlag = 0 and u.username=?1 ")
	List<User> findUserByName(String userName);
	
	@Query("select DISTINCT re from User u join u.roles r join r.resources re where r.deleteFlag = 0 and re.deleteFlag = 0 and u.id = ?1 ")
	public List<Resource> findResourcesByUserId(String userId);
	
	@Query("select DISTINCT r from User u join u.roles r where r.deleteFlag = 0 and u.id = ?1 ")
	public List<Role> findRolesByUserId(String userId);
	
}
