package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.myconventer.MyConventer;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;

public class UserServlet extends BaseServlet {

	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("userservlet 的 add 方法执行了");
		return null;
	}

	/** 跳转至注册页面,servlet跳转更安全 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	/** 注册页面 
	 * @throws Exception */
	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 1.封装数据
		User user = new User();
		System.out.println("============================");
		//注册自定义转化器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, req.getParameterMap());
		//1.1 设置用户id
		user.setUid(UUIDUtils.getId());
		//1.2设置激活码
		user.setCode(UUIDUtils.getCode());
		user.setPassword(MD5Utils.md5(user.getPassword()));
		// 2.调用servlet完成注册
		UserService service = new UserServiceImpl();
		System.out.println("service ========== "+service.toString());
		service.regist(user);
		// 3.注册成功后请求转发,在msg.jsp中调用：<h1>${msg}</h1>
		req.setAttribute("msg", "注册成功，请进入邮箱激活");
		return "/jsp/msg.jsp";
	}
	
	public String active(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		//1.获取激活码
		String code = req.getParameter("code");
		//2.调用service完成激活
		UserService service = new UserServiceImpl();
		User user = service.active(code);
		if(user == null){
			req.setAttribute("msg", "请重新激活");
		}else{
			req.setAttribute("msg", "激活成功");
		}
		//3.请求转发至msg.jsp
		return "/jsp/msg.jsp";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		//获取用户名和密码
		String username = req.getParameter("username");
		String password = MD5Utils.md5(req.getParameter("password"));
		
		//调用service完成登录操作返回user
		UserService s = new UserServiceImpl();
		User user = s.login(username,password);
		
		//判断用户
		if(user == null){
			//用户名密码不匹配
			req.setAttribute("msg", "用户名密码不匹配");
//			req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);//重新登录
			return "/jsp/login.jsp";
		}else{
			//继续判断用户是否激活
			if(Constant.USER_IS_ACTIVE != user.getState()){
				req.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		//将user放入session中，重定向
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(req.getContextPath()+"/"); // /store
		return null;
	}
	
	public String loginUI(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	public String logout(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//清空session
		req.getSession().invalidate();
		//重定向
		resp.sendRedirect(req.getContextPath()); // /store
		//TODO 处理自动登录
		return null;
	}
}
