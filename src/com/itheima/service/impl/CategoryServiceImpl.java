package com.itheima.service.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.DataSourceUtils;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() throws Exception { // 查询所有分类
		// 1.创建缓存管理器,在src目录下获取文件
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class
				.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		// 3.通过缓存获取数据,将cache看成一个map即可
		Element element = cache.get("clist");
		List<Category> list = null;
		// 4.判断数据
		if (element == null) {
			// 从数据库中获取
			CategoryDao dao = new CategoryDaoImpl();
			list = dao.findAll();
			// 将list放入缓存
			cache.put(new Element("clist", list));
			System.out.println("无数据，从数据库中获取");
		} else {
			// 直接返回
			list = (List<Category>) element.getObjectValue();
			System.out.println("有数据，从本地中获取");
		}

		return list;
	}

	@Override
	public void add(Category c) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		dao.add(c);
		// 更新缓存
		// 1.创建缓存管理器,在src目录下获取文件
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class
				.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		// 3.清空缓存
		cache.removeAll();
	}

	@Override
	public Category getById(String cid) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		return dao.getById(cid);
	}

	@Override
	public void update(Category c) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		dao.update(c);
		// 清除缓存
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class
				.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache = cm.getCache("categoryCache");
		cache.remove("clist");
	}

	@Override
	public void delete(String cid) throws Exception {
		try {
			// 开启事务
			DataSourceUtils.startTransaction();

			// 更新商品信息
			ProductDao pdao = new ProductDaoImpl();
			pdao.updateCid(cid);

			// 删除分类
			CategoryDao dao = new CategoryDaoImpl();
			dao.delete(cid);
			// 提交事务
			DataSourceUtils.commitAndClose();

			// 清除缓存
			CacheManager cm = CacheManager.create(CategoryServiceImpl.class
					.getClassLoader().getResourceAsStream("ehcache.xml"));
			Cache cache = cm.getCache("categoryCache");
			cache.remove("clist");
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;

		}
	}

}
