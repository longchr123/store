package com.itheima.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;

public class AdminOrderServlet extends BaseServlet {//如果直接继承HttpServlet就会出现问题，无法请求到方法？

	//自己理解原因：BaseServlet是通过重写service方法接收参数，如果直接继承HttpServlet，那只能从doGet与doPost中获取参数
	/**
	 * 根据状态查找相应的数据
	 * @throws Exception 
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String state = request.getParameter("state");
		System.out.println("state = " + state);
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> olist = os.findAllByState(state);
		request.setAttribute("list", olist);
		return "/admin/order/list.jsp";
	}
	
	/**
	 * 查询订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public  String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");//响应格式
		
		//1.接受oid
		String oid = request.getParameter("oid");
		
		
		//2.调用serivce查询订单详情 返回值 list<OrderItem>
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		
		
		//3.将list转成json 写回
		//排除不用写回去的数据
		JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items,config);
		//System.out.println(json);
		response.getWriter().println(json);
		return null;
	}	
	
	/*
	 * 修改订单状态
	 */
	public  String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受 oid state
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");
		
		//2.调用service 
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);		
		order.setState(2);
		
		os.update(order);
		
		//3.页面重定向
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		return null;
	}	

}
