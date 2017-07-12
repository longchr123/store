package com.itheima.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 查询所有
	 * @throws Exception 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//调用categoryservice查询所有的分类信息，返回list
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = cs.findAll();
		//将list放入request，请求转发
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	
	/**
	 * 转到添加分类页面上
	 * @throws Exception 
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/admin/category/add.jsp";
	}
	
	/**
	 * 添加分类
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收cname
		String cname=request.getParameter("cname");
		//封装category
		Category c = new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		
		//调用service完成添加操作
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.add(c);
		
		//重定向查询所有分类
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
	
	/**
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收cname
		String cid=request.getParameter("cid");	
		
		//调用service完成添加操作
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		Category c = cs.getById(cid);
		request.setAttribute("bean", c);
		
		return "/admin/category/edit.jsp";
	}
	
	/**
	 * 更新分类信息
	 * @throws Exception 
	 */
	public String update(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收cname
		String cid=request.getParameter("cid");	
		String cname=request.getParameter("cname");	
		
		//调用service完成添加操作
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		Category c = new Category();
		c.setCid(cid);
		c.setCname(cname);
		cs.update(c);
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
	/**
	 * 删除分类信息
	 * @throws Exception 
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收cid
		String cid=request.getParameter("cid");	
		
		//调用service完成添加操作
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		cs.delete(cid);
		//重定向
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}

}
