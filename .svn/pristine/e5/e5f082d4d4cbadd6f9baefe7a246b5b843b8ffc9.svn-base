package com.tenpay.wxwork.approval.presvr.common.biz.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenpay.wxwork.approval.presvr.common.config.ConfigUtils;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class BankInfos {
	private static final Logger LG = LoggerFactory.getLogger(BankInfos.class);

	static String BANKMAPSUFFIX = ".bankType";
	static String CFTVERSUFFIX = ".cftVer";
	static String BANKVERCRTSUFFIX = ".bankCrtVer";

	private Map<String,BankInfo> bankInfoMap = new HashMap<String,BankInfo>();
	private String supportBankNames;
	private String supportBankTypes;
	private Map<String,Integer> bankNameMap = new HashMap<String,Integer>();
	private Map<Integer,String> bankTypeMap = new HashMap<Integer,String>();

	public static BankInfos bankInfosInstance = new BankInfos();
	private BankInfos(){}
	
	public static BankInfos getInstance(){
		return bankInfosInstance;
	}
	
    public void init(){
    	this.setSupportBankNames(ConfigUtils.getStringValue("supportedBankNames"));
		this.setSupportBankTypes(ConfigUtils.getStringValue("supportedBankTypes"));

		
		String bankMapKey;
		int bankType;
		
		String bankCrtVerKey;
		String bankCrtId;
		String cftVerKey;
		String cftKeyId;
		String cftCrtId;
	
		if(supportBankNames.isEmpty()||supportBankTypes.isEmpty())
		{
			LG.error("config error.supportBankNames or supportedBankTypes is empty.");
			throw new InnerException(BizError.INVALID_CONFIG, " config error.supportBankNames or supportedBankTypes is empty.");
		}
	    for (String bankName: supportBankNames.split(",")){
	    	bankMapKey = bankName+BANKMAPSUFFIX;
	    	bankType = ConfigUtils.getIntValue(bankMapKey);
	    	bankNameMap.put(bankName, bankType);
	    	bankTypeMap.put(bankType, bankName);

	    	LG.debug("bankName:"+bankName);
	    	LG.debug("bankType:"+bankType);
	    	
	    	BankInfo bankInfo = new BankInfo();
	    	bankInfo.setBankName(bankName);
	    	bankInfo.setBankType(bankType);
	    	  	
	    	bankCrtVerKey = new String(Integer.toString(bankType) + BANKVERCRTSUFFIX);
	    	bankInfo.setBankCrtVer(ConfigUtils.getIntValue(bankCrtVerKey,1));
	    	bankCrtId =new String(ConfigUtils.getStringValue("bankCrtPrefix") + Integer.toString(bankType));
	    	bankInfo.setBankCrtId(bankCrtId);
	    	
	    	LG.debug("bankCrtVerKey:"+bankCrtVerKey);
	    	LG.debug("BankCrtVer:"+Integer.toString(ConfigUtils.getIntValue(bankCrtVerKey,1)));
	    	LG.debug("bankCrtId:"+bankCrtId);
   	
	    	cftVerKey = new String(Integer.toString(bankType) + CFTVERSUFFIX);
	    	bankInfo.setCftVer(ConfigUtils.getIntValue(cftVerKey,1));
	    	cftKeyId = new String(ConfigUtils.getStringValue("keyPrefix") + Integer.toString(bankType));
	    	bankInfo.setCftKeyId(cftKeyId);
	  
	    	LG.debug("cftVerKey:"+cftVerKey);
	    	LG.debug("cftVer:"+Integer.toString(ConfigUtils.getIntValue(cftVerKey,1)));
	    	LG.debug("cftKeyId:"+cftKeyId);
	    	
	    	cftCrtId = new String(ConfigUtils.getStringValue("crtPrefix") + Integer.toString(bankType));
	    	bankInfo.setCftCrtId(cftCrtId);
	    	LG.debug("cftCrtId:"+cftCrtId);
	    	
	    	bankInfoMap.put(bankName, bankInfo);
	    }
	    for (String supportedBankType: supportBankTypes.split(","))
	    {
	    	if(!bankTypeMap.containsKey(Integer.parseInt(supportedBankType)))
	    	{
				LG.error("Config error.supportedBankType error.");
				throw new InnerException(BizError.INVALID_CONFIG, "Config error.supportedBankType error.");	
	    	}
	    }
    }
	
	public String getSupportBankNames() {
		return supportBankNames;
	}

	public void setSupportBankNames(String supportBankNames) {
		this.supportBankNames = supportBankNames;
	}

	public String getSupportBankTypes() {
		return supportBankTypes;
	}

	public void setSupportBankTypes(String supportBankTypes) {
		this.supportBankTypes = supportBankTypes;
	}

	public boolean isBankNameSupported(String bankName) {
		return supportBankNames.contains(bankName);
	}
	
	public boolean isBankTypeSupported(String bankType) {
		return supportBankTypes.contains(bankType);
	}
	
	public int getBankTypeByName(String bankName) {
		String bankType = bankName+".bankType";
        if (NumberUtils.isDigits(bankType)) {
            return Integer.parseInt(bankType);
        }
        throw new InnerException(BizError.INVALID_CONFIG, bankType + " not find in config file");
	}
	
	public boolean getCftCrtInfo(int bankType) {
		return supportBankTypes.contains(Integer.toString(bankType));
	}
	public boolean getCftKeyInfo(String bankType) {
		return supportBankTypes.contains(bankType);
	}
	
	public BankInfo getBankInfobyName(String bankName) {
		return bankInfoMap.get(bankName);
	}
	
	public BankInfo getBankInfobyType(int type) {
		String bankName = bankTypeMap.get(type);
		return bankInfoMap.get(bankName);
	}
}
