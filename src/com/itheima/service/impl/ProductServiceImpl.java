package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pd= new ProductDaoImpl();
		
		return pd.findNew();
	}

	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pd= new ProductDaoImpl();
		return pd.findHot();
	}

	/**查询单个商品详情*/
	@Override
	public Product getByPid(String pid) throws Exception {
		ProductDao pd= new ProductDaoImpl();
		return pd.getByPid(pid);
	}

	/**根据分页查询数据*/
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid)
			throws Exception {
		ProductDao pd= new ProductDaoImpl();
		//当前页面数据
		List<Product> list = pd.findByPage(currPage,pageSize,cid);
		//总条数
		Integer totalCount = pd.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}

	@Override
	public List findAll() throws Exception {
		ProductDao pd= new ProductDaoImpl();
		List<Product> list = pd.findAll();
		return list;
	}

	/**添加商品*/
	@Override
	public void add(Product p) throws Exception {
		ProductDao pd= new ProductDaoImpl();
		pd.add(p);
	}

}
