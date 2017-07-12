package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void add(Order order) throws Exception {
		// TODO Auto-generated method stub
		// 1.开启事务
		try {
			DataSourceUtils.startTransaction();
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			// 2.向order表中添加一条数据
			od.add(order);
			// 3.向orderItem中添加n条数据
			for (OrderItem item : order.getItems()) {
				od.addItem(item);
			}
			// 4.处理事务
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();// 事务回滚
			throw e;
		}
	}

	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user)
			throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		//查询当前页数据
		List<Order> list = od.findAllByPage(currPage,pageSize,user.getUid());
		//查询总条数
		
		int totalCount = od.getTotalCount(user.getUid());
		return new PageBean<>(list,currPage,pageSize,totalCount);
	}

	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		Order o = od.getById(oid);
		return o;
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		Order o = od.update(order);
	}

	@Override
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		
		return od.findAllByState(state);
	}

}
