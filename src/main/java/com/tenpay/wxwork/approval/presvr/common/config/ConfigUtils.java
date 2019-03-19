package com.tenpay.wxwork.approval.presvr.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.tenpay.bap.common.utils.CharsetUitls;
import com.tenpay.wxwork.approval.presvr.common.biz.model.AbstractConstant;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

@Component
public class ConfigUtils {

	private static final Logger LG = LoggerFactory.getLogger(ConfigUtils.class);

	private static Properties properties = new Properties();

	private static List<String> propertyKeys = new ArrayList<>();

	private static final Charset DEFAULT_CHARSET = CharsetUitls.GBK;

	private static ConfigUtils configUtilInstance = new ConfigUtils();
	private String serviceIp;
	private int[] servicePort;
	private String plsCftKeyId;

	private ConfigUtils() {
	}

	public static ConfigUtils getInstance() {
		return configUtilInstance;
	}

	public void init() throws IOException {
		String fileName = "common.properties";
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		InputStream ins;
		if (classPathResource.exists()) {
			LG.info("start to load " + fileName);
			ins = classPathResource.getInputStream();
			try {
				// 使用UTF-8格式读取配置文件
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(ins, DEFAULT_CHARSET));
				Properties proper = new Properties();
				proper.load(reader);
				properties.putAll(proper);
				propertyKeys.addAll(properties.stringPropertyNames());
			} finally {
				IOUtils.closeQuietly(ins);
			}
			LG.info("end load " + fileName);
		} else {
			LG.warn(fileName + " file not exists");
		}

		serviceIp = properties
				.getProperty(AbstractConstant.AbstractCommonPropertiesKey.SERVICE_IP);
		String[] servicePortArray = StringUtils
				.split(properties
						.getProperty(AbstractConstant.AbstractCommonPropertiesKey.SERVICE_PORT),
						",");

		if (null != servicePortArray) {
			servicePort = new int[servicePortArray.length];
			for (int i = 0; i < servicePortArray.length; i++) {
				servicePort[i] = Integer.parseInt(servicePortArray[i]);
			}
		}
		plsCftKeyId = properties.getProperty(AbstractConstant.PLS_CFT_KEYID);
	}

	public static int getIntValue(String key) {
		String valueStr = properties.getProperty(key);
		if (NumberUtils.isDigits(valueStr)) {
			return Integer.parseInt(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");
	}

	public static long getLongValue(String key) {
		String valueStr = properties.getProperty(key);
		if (NumberUtils.isDigits(valueStr)) {
			return Long.parseLong(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");
	}

	public static String getStringValue(String key) {
		return properties.getProperty(key);
	}

	public static double getDoubleValue(String key) {
		String valueStr = properties.getProperty(key);
		if (NumberUtils.isDigits(valueStr)) {
			return Double.parseDouble(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");
	}

	public static boolean getBooleanValue(String key, boolean defaultValue) {
		String valueStr = properties.getProperty(key);
		if ("true".equalsIgnoreCase(valueStr)) {
			return true;
		}
		if ("false".equalsIgnoreCase(valueStr)) {
			return false;
		}
		return defaultValue;
	}

	public static List<String> getKeysStartWith(String key) {
		List<String> result = propertyKeys.stream()
				.filter(propertyKey -> propertyKey.startsWith(key))
				.collect(Collectors.toList());
		return result;
	}

	public static String getStringValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getIntValue(String key, int defValue) {
		String valueStr = properties.getProperty(key, String.valueOf(defValue));
		if (NumberUtils.isDigits(valueStr)) {
			return Integer.parseInt(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");

	}

	public static double getDoubleValue(String key, double defValue) {
		String valueStr = properties.getProperty(key, String.valueOf(defValue));
		if (NumberUtils.isDigits(valueStr)) {
			return Double.parseDouble(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");
	}

	/**
	 * 按交易类型、bank_type来获取配置信息
	 *
	 * @param key
	 * @param bankType
	 * @param requestType
	 * @param defaultValue
	 * @Title: getStringValue
	 * @return: String
	 */
	public static String getStringValue(String key, int bankType,
			int requestType, String defaultValue) {
		String valueStr = properties.getProperty(String.format("%s.%d.%d", key,
				bankType, requestType));
		if (valueStr == null) {
			valueStr = properties.getProperty(String.format("%s.%d", key,
					bankType));
		}

		if (valueStr == null) {
			valueStr = properties.getProperty(key, defaultValue);
		}
		return valueStr;
	}

	/**
	 * key.bankType.requestType取值，逐级取值，最后用默认值
	 *
	 * @param key
	 *            配置项前缀
	 * @param bankType
	 * @param requestType
	 * @param defValue
	 * @return
	 */
	public static int getIntValue(String key, int bankType, int requestType,
			int defValue) {
		String valueStr = properties.getProperty(String.format("%s.%d.%d", key,
				bankType, requestType));
		if (valueStr == null) {
			valueStr = properties.getProperty(String.format("%s.%d", key,
					bankType));
		}

		if (valueStr == null) {
			valueStr = properties.getProperty(key, String.valueOf(defValue));
		}
		if (NumberUtils.isDigits(valueStr)) {
			return Integer.parseInt(valueStr);
		}
		throw new InnerException(BizError.INVALID_CONFIG, key
				+ " not find in config file");

	}

	/**
	 * key.bankType.requestType取值，逐级取值，最后用默认值
	 *
	 * @param key
	 *            配置项前缀
	 * @param bankType
	 * @param requestType
	 * @param defaultValue
	 * @Title: getStringValue
	 * @return: String
	 */
	public static boolean getBooleanValue(String key, int bankType,
			int requestType, boolean defaultValue) {
		String valueStr = properties.getProperty(String.format("%s.%d.%d", key,
				bankType, requestType));
		if (valueStr == null) {
			valueStr = properties.getProperty(String.format("%s.%d", key,
					bankType));
		}

		if (valueStr == null) {
			valueStr = properties.getProperty(key);
		}

		if ("true".equalsIgnoreCase(valueStr)) {
			return true;
		}
		if ("false".equalsIgnoreCase(valueStr)) {
			return false;
		}
		return defaultValue;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	public int[] getServicePort() {
		return servicePort;
	}

	public String getPlsCftKeyId() {
		return plsCftKeyId;
	}

	public void setPlsCftKeyId(String plsCftKeyId) {
		this.plsCftKeyId = plsCftKeyId;
	}
}
