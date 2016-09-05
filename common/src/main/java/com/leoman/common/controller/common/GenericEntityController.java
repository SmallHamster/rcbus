package com.leoman.common.controller.common;

import com.leoman.common.controller.editor.*;
import com.leoman.utils.ServiceLocator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenericEntityController<V, T, M>  {

	/**
	 * 获取Service实例
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public M getEntityManager() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (M) ServiceLocator.lookup((Class) params[2]);
	}

	public static Map<String,Object> emptyData =null;

	static {
		emptyData = new HashMap<String, Object>();
		emptyData.put("data",new ArrayList());
		emptyData.put("iTotalRecords",0);
		emptyData.put("iTotalDisplayRecords",0);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new CustomStringEditor());
		binder.registerCustomEditor(MultipartFile.class, new CustomFileEditor());
		binder.registerCustomEditor(Double.class, new CustomDoubleEditor());
		binder.registerCustomEditor(Float.class, new CustomFloatEditor());
		binder.registerCustomEditor(Integer.class, new CustomIntegerEditor());
		binder.registerCustomEditor(Long.class, new CustomLongEditor());
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
	}


	public Integer getPageNum(Integer start,Integer length){
		if(start==null){
			start = 0;
		}
		if(length == null){
			length = 10;
		}

		int pageNum = (start/length)+1;
		return pageNum;
	}
}
