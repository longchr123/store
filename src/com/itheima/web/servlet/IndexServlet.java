package com.itheima.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;

/**
 * 与首页相关的servlet
 * 
 * @author Administrator
 * 
 */
public class IndexServlet extends BaseServlet {

//	@Override
//	public String index(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		//调用 categoryservice查询所有的分类，返回值list
//		CategoryService cs = new CategoryServiceImpl();
//		List<Category> clist = null;
//		try {
//			clist = cs.findAll();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//将返回值放入request中
//		request.setAttribute("clist", clist);
//		
//		//从数据库中查询最新与热门商品放入request域中，请求转发
//		return "/jsp/index.jsp";//为了安全，在servlet中进行跳转
//	}
	
	public String index(HttpServletRequest request, HttpServletResponse response){
		//去数据库中查询最新商品与热门商品，将他们放入request域中，请求转发
		ProductService ps = new ProductServiceImpl();
		//最新商品
		List<Product> newList = null;
		List<Product> hotList = null;
		try {
			newList = ps.findNew();
			//热门商品
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("nList", newList);
		request.setAttribute("hList", hotList);
		return "/jsp/index.jsp";//为了安全，在servlet中进行跳转
	}
	
	

}
