package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

public interface OrderDao {

	/**添加一条订单*/
	void add(Order order)throws Exception;

	/**添加一条订单项*/
	void addItem(OrderItem item)throws Exception;

	List<Order> findAllByPage(int currPage, int pageSize, String uid)throws Exception;

	int getTotalCount(String uid)throws Exception;

	Order getById(String oid)throws Exception;

	Order update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;



}
