package com.itheima.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;

/**
 * 商品servlet
 * @author Administrator
 *
 */
public class ProductServlet extends BaseServlet {

	/**
	 * 通过id查询单个商品详情
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//1.获取商品id
		String pid = request.getParameter("pid");
		//调用service
		ProductService ps = new ProductServiceImpl();
		Product p = ps.getByPid(pid);
		//3.将结果放入request域中 请求转发
		request.setAttribute("bean", p);
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 分类查询数据
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws Exception 
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//1.获取类别 当前页 设置一个pagesize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;
		//调用service 返回值pagebean
//		ProductService ps = new ProductServiceImpl();
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		PageBean<Product> pageBean = ps.findByPage(currPage,pageSize,cid);
		//3.将结果放入request域中 请求转发
		request.setAttribute("pb", pageBean);
		return "/jsp/product_list.jsp";
	}

}
