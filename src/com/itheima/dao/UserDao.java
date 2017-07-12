package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {

	/**增加用户*/
	void add(User user) throws Exception ;

	/**通过code获取用户数据*/
	User getByCode(String code)throws Exception ;

	/**更新用户数据*/
	void update(User user)throws Exception ;

	User getByUserNameAndPwd(String username, String password)throws Exception ;
	

}
