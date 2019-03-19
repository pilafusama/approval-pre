package com.tenpay.wxwork.approval.presvr.common.utils;

import java.security.PublicKey;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenpay.key.api.KeyException;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfo;
import com.tenpay.wxwork.approval.presvr.common.config.ConfigUtils;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.common.service.KeyStoreService;

/**
 * 敏感信息处理
 **/
public class SensitiveInfoUtils {
	
    private static final Logger LG = LoggerFactory.getLogger(SensitiveInfoUtils.class);

	public static void encryptSensitiveInfo(Map<String,Object> map,BankInfo bankInfo,String charset) throws InnerException, KeyException
	{
		String sensitiveInfo = ConfigUtils.getStringValue("sensitiveInfo");
		LG.info("sensitiveInfo:"+sensitiveInfo);	
		if(sensitiveInfo.isEmpty())
		{
			return;
		}
		Set<Map.Entry<String, Object>> entrySet = map.entrySet();

		for (Map.Entry<String,Object> entry : entrySet) {
			String mapKey = entry.getKey();
            LG.debug("mapKey:"+mapKey);
        	if (! mapKey.isEmpty()) {
				Object val = entry.getValue();
                if ((val != null) && !(val.toString().isEmpty())&& sensitiveInfo.contains(mapKey))
                {
                    LG.debug("val:"+val.toString());
                	PublicKey publicKey = KeyStoreService.getBankPublicKey(bankInfo.getBankCrtId(),bankInfo.getBankCrtVer());
                	val = RsaHandler.encrypt(publicKey,val.toString(),charset);
                	map.put(mapKey, val);
                }  
            }  
        } 
	}
	
	public static void decryptSensitiveInfo(Map<String,Object> map,BankInfo bankInfo,String charset) throws InnerException, KeyException
	{
		String sensitiveInfo = ConfigUtils.getStringValue("sensitiveInfo");
		LG.info("sensitiveInfo:"+sensitiveInfo);
		if(sensitiveInfo.isEmpty())
		{
			return;
		}
		Set<Map.Entry<String, Object>> entrySet = map.entrySet();
		for (Map.Entry<String,Object> entry : entrySet) {
			String mapKey = entry.getKey();
            LG.debug("mapKey:"+mapKey);
        	if (! mapKey.isEmpty()) {  
        		Object val = entry.getValue();
                if ((val != null) && !(val.toString().isEmpty()) && sensitiveInfo.contains(mapKey))
                {
                    LG.debug("val:"+val.toString());
                	val = RsaHandler.decrypt(KeyStoreService.getCftPrivateKey(bankInfo.getCftKeyId(),bankInfo.getCftVer()),val.toString(),charset);
                	map.put(mapKey, val);
                }  
            }  
        } 
	}
}
