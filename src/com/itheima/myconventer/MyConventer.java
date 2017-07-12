package com.itheima.myconventer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class MyConventer implements Converter {

	/**数据转换，String转Date*/
	@Override
	public Object convert(Class arg0, Object arg1) {
		//class 要装成的类型
		//object 页面上传入的值 
		//将object转成date
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse((String)arg1);
			System.out.println("-----------------------------------" + date.toString());
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
