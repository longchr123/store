package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	public static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			// 1.获取子类
			Class clazz = this.getClass();// this代表谁调用就是谁，如http://localhost:8080/store/user?method=add，this代表userServlet
			System.out.println(this);
			// 2.获取请求的方法
			String m = req.getParameter("method");
			if (m == null) {
				m = "index";// 执行下面的index方法
			}
			// 3.获取方法对象
			Method method = clazz.getMethod(m, HttpServletRequest.class,
					HttpServletResponse.class);
			System.out.println(m);
			// 4.让方法执行,返回值为请求转发的路径
			String s = (String) method.invoke(this, req, resp);
			// 5.判断s是否为空，不为空表示转发，如add方法返回值
			if (s != null) {
				req.getRequestDispatcher(s).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public String index(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//例如输入http://localhost:8080/store ，s == null 会默认调用indexjsp
		return null;
	}

}
