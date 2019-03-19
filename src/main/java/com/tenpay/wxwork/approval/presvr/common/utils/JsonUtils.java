package com.tenpay.wxwork.approval.presvr.common.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtils {
	private static Gson gson = new Gson();

	private JsonUtils() {
	}

	/**
	 * 将json字符串反序列化为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param clazz
	 *            反序列化的类型
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Class<T> clazz) {
		return gson.fromJson(jsonStr, clazz);
	}

	/**
	 * 将对象序列化为json字符串
	 * 
	 * @param obj
	 *            需要序列化的对象
	 * @return
	 */
	public static <T> String toJson(T obj) {
		return gson.toJson(obj);
	}

	/**
	 * 将json字符反序列化为对象，当对象的类型是一个泛型时，需要使用这个方法 <br>
	 * 如：List&ltString&gt fromJson = JsonUtils.fromJson(jsonStr, new
	 * TypeTokenWrapper&ltList&ltString&gt&gt());
	 * 
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String jsonStr, Type type) {
		return gson.fromJson(jsonStr, type);
	}

}
