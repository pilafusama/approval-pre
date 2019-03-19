package com.tenpay.wxwork.approval.presvr.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.cglib.beans.BeanMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapConvert{
    private static final Logger LG = LoggerFactory.getLogger(MapConvert.class);

	/**
	 * 将javabean转为map
	 * @param bean
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean) {  

	    Map<String, Object> map = new HashMap<String, Object>();  
	    if (bean != null) {  
	        BeanMap beanMap = BeanMap.create(bean);
			Set<Map.Entry<String,Object>> entrySet = beanMap.entrySet();
			for (Map.Entry<String,Object> entry : entrySet){
				String key = entry.getKey();
				Object value = entry.getValue();
				map.put(key,value.toString());
				LG.info("key:"+key);
				LG.info("value:"+value);
			}
	    }
	    return map;  
	}    

	/** 
	 * 将map装换为javabean对象 
	 * @param map 
	 * @param bean 
	 * @return 
	 */  
	public static <T> T mapToBean(TreeMap<String, Object> map,T bean) {  
		BeanMap beanMap = BeanMap.create(bean);  
	    beanMap.putAll(map);  
	    return bean;  
	}
}
