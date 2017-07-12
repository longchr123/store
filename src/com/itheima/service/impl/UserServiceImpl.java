package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void regist(User user) throws Exception {
		UserDao dao = new UserDaoImpl();
		System.out.println("-------------" + dao.toString() + "----------"
				+ user.toString());
		dao.add(user);
		// 发送邮件
	}

	@Override
	public User active(String code) throws Exception {
		UserDao dao = new UserDaoImpl();
		// 1.通过code获取一个用户
		User user = dao.getByCode(code);
		// 2.判断用户是否为空
		if (user != null) {
			user.setState(1);
			// 3.修改用户状态
			dao.update(user);
		}
		return user;

	}

	@Override
	public User login(String username, String password) throws Exception { //用户登录
		UserDao dao = new UserDaoImpl();
		return dao.getByUserNameAndPwd(username,password);
	}

}
