package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;

public class CategoryServlet extends BaseServlet {
	
	/**查询所有的分类*/
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//1.调用categoryservice 查询所有的分类 返回值为list
//		CategoryService cs = new CategoryServiceImpl();
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> clist = null;
		try{
			clist = cs.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		//2.将返回值转成json格式，返回到页面上。
		String json =JsonUtil.list2json(clist);
		//3.写回去
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().println(json);
		System.out.println("json = " + json);
		return null;
	}

}
