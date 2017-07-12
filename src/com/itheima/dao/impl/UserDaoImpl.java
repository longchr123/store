package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void add(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		System.out.println("qr ===================== " + qr.toString());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(),
				user.getCode());
	}

	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return query.query(sql, new BeanHandler<>(User.class), code);

	}

	@Override
	public void update(User user) throws Exception {
		QueryRunner query = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set username=?, password=?,name=?,email=?,birthday=?state=?,code=? where uid=?";
		query.update(sql, user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getBirthday(),
				user.getState(), null,user.getUid());
	}

	@Override
	public User getByUserNameAndPwd(String username, String password)
			throws Exception { // 用户登录
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		return qr.query(sql,new BeanHandler<>(User.class),username,password);
	}

}
