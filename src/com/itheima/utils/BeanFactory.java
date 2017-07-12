package com.itheima.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 实体工厂
 * @author Administrator
 *
 */
public class BeanFactory {
	
	public static Object getBean(String id){
		//通过id获取一个指定的实现类
		
		try {
			//1.获取document对象
			Document doc=new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//2.获取指定的bean对象 xpath
			Element ele=(Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			
			//3.获取bean对象的class属性
			String value = ele.attributeValue("class");
			
			//4.反射 以前的逻辑直接返回的是实例	
			//return Class.forName(value).newInstance();
			
			//5.现在对service中add方法进行加强 返回值的是代理对象
			final Object obj=Class.forName(value).newInstance();
			//是service的实现类
			if(id.endsWith("Service")){
				Object proxyObj = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//继续判断是否调用的add或者regist
						if("add".equals(method.getName()) || "regist".equals(method.getName())){
							System.out.println("添加操作");
							return method.invoke(obj, args);
						}
						
						return method.invoke(obj, args);
					}
				});
				
				//若是service方法返回的是代理对象
				return proxyObj;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 静态代理
		1.要求被装饰者和装饰者实现同一个接口或者继承同一个类
		2.装饰者中要有被装饰者的引用
		3.对需要加强的方法进行增强
		4.对不需要加强的方法调用原来的方法
	动态代理
		在程序运行的时候,动态的创建一个对象,用这个对象去操作方法方法 
		jdk的中Proxy ,前提:必须实现一个接口
		
		Object Proxy.newProxyInstance(ClassLoader 被代理对象的类加载器,Class[] 被代理对象实现的所有接口,InvocationHandler 处理方法);	
			InvocationHandler:接口 只需要重写一个方法
				Object invoke(Object 代理对象,Method 当前执行的方法,Object[] 当前方法执行的时候需要的参数) 
					返回值就是 当前方法执行之后的返回值*/
}
