package com.itheima.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

/**后台商品管理*/
public class AdminProductServlet extends BaseServlet {

	/**
	 * @throws Exception 
		查询所有商品
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//1.调用service查询所有，返回一个list
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List list = ps.findAll();
		//2.将list放入request域中 请求转发
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";

	}
	
	/**
	 * @throws Exception 
		转到添加商品页面
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//查询所有分类
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		request.setAttribute("clist", list);
		return "/admin/product/add.jsp";

	}

}
