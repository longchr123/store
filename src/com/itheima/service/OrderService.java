package com.itheima.service;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;

public interface OrderService {

	/**添加订单到数据库中*/
	void add(Order order)throws Exception;

	/**分页查询*/
	PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	/**state:null查询所有订单，0未支付，1已支付，2已发货，3已完成*/
	List<Order> findAllByState(String state)throws Exception;

}
