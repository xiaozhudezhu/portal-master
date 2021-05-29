package com.swinginwind.portal.org.dao;


import java.util.List;
import java.util.Map;

import com.swinginwind.portal.common.entity.PageModel;
import com.swinginwind.portal.org.dto.UserQueryDTO;
import com.swinginwind.portal.org.entity.User;

public interface SystemUserDao {
	
	public List<User> findUsers(Map<String, Object> params);
	
	/**
	 * 根据用户信息查询分页信息
	 * @param userQueryDTO
	 * @return
	 */
	PageModel<User> queryUserPage(UserQueryDTO userQueryDTO);
	
}
