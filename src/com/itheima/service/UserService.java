package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

	/**注册用户*/
	void regist(User user) throws Exception ;
	
	//用户激活
	User active(String code) throws Exception;

	User login(String username, String password)throws Exception;

}
