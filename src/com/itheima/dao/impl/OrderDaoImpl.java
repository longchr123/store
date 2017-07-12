package com.itheima.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加一条订单
	 */
	@Override
	public void add(Order order) throws Exception {
		QueryRunner qr = new QueryRunner();//参数为空，要不然开启的事务不会在同一个线程中，无法开启回滚
		
		/*
		 * `oid` varchar(32) NOT NULL,
		  `ordertime` datetime DEFAULT NULL,
		  `total` double DEFAULT NULL,
		  
		  `state` int(11) DEFAULT NULL,
		  `address` varchar(30) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  
		  `telephone` varchar(20) DEFAULT NULL,
		  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		
		qr.update(DataSourceUtils.getConnection(),sql, order.getOid(),null,order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	/**
	 * 添加一条订单项
	 */
	@Override
	public void addItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		 /**
		 * `itemid` varchar(32) NOT NULL,
		  `count` int(11) DEFAULT NULL,
		  `subtotal` double DEFAULT NULL,
		  `pid` varchar(32) DEFAULT NULL,
		  `oid` varchar(32) DEFAULT NULL,
		 */
		String sql="insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),oi.getProduct().getPid(),oi.getOrder().getOid());
	}

	/**查询我的订单分页,order中有list<orderItem>,orderitem中有product*/
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid)
			throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid=? order by ordertime desc limit?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currPage-1)*pageSize,pageSize);
		sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";//关联查询
		for (Order order : list) {
			List<Map<String, Object>> mlist = qr.query(sql,new MapListHandler(),order.getOid());
			for (Map<String, Object> map : mlist) {
				//封装product
				Product product = new Product();
				BeanUtils.populate(product, map);
				//封装orderItem
				OrderItem item = new OrderItem();
				BeanUtils.populate(item, map);//将map中的数据映射到item中
				item.setProduct(product);
				order.getItems().add(item);
			}
			
		}
		return list;
	}

	/**获取我的订单总条数*/
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		return ((Long) qr.query(sql, new ScalarHandler(),uid)).intValue();//不能直接转int
	}

	
	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		Order o = qr.query(sql, new BeanHandler<>(Order.class),oid);
		sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";//关联查询
		List<Map<String,Object>> mList = qr.query(sql, new MapListHandler(),oid);
		for (Map<String,Object> object : mList) {
			Product p = new Product();
			BeanUtils.populate(p, object);
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, object);
			oi.setProduct(p);
			o.getItems().add(oi);
		}
		
		return o;
	}

	/**修改订单*/
	@Override
	public Order update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state=?,address=?,name=?,telephone=? where oid=?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		return null;
	}

	@Override
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql;
		List<Order> list;
		if(state == null){
		sql = "select * from orders";
		list = qr.query(sql, new BeanListHandler<>(Order.class));
		}else{
			sql = "select * from orders where state = ?";
			list = qr.query(sql, new BeanListHandler<>(Order.class),state);
		}
		
		sql="select * from orderitem oi,product p where oi.pid=p.pid and oi.oid=?";//关联查询
		for (Order order : list) {
			List<Map<String, Object>> mlist = qr.query(sql,new MapListHandler(),order.getOid());
			for (Map<String, Object> map : mlist) {
				//封装product
				Product product = new Product();
				BeanUtils.populate(product, map);
				//封装orderItem
				OrderItem item = new OrderItem();
				BeanUtils.populate(item, map);//将map中的数据映射到item中
				item.setProduct(product);
				order.getItems().add(item);
			}
			
		}
		return list;
	}

}
