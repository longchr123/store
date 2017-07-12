package com.itheima.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

public class CartServlet extends BaseServlet {

	public Cart getCart(HttpServletRequest request){
		Cart cart = (Cart) request.getSession().getAttribute("cart");//从会话中获取
		if(cart == null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	/**
	 * 添加 到购物车
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取pid与数量
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		
		//通过pid调用productService获取一个商品
		ProductService service = (ProductService) BeanFactory.getBean("ProductService");
		Product p = service.getByPid(pid);
		//组装CartItem
		CartItem item = new CartItem(p, count);

		//添加到购物车中
		Cart cart = getCart(request);
		cart.add2Cart(item);
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;

	}
	
	public String remove(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("pid");
		//调用购物车的remove方法
		getCart(request).removeFromCart(pid);
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//调用购物车的clearCart方法
		getCart(request).clearCart();
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}

}
