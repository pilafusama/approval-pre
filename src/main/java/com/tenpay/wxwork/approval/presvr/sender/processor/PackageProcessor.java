package com.tenpay.wxwork.approval.presvr.sender.processor;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpay.key.api.KeyException;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfo;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.error.ErrorModuleConvert;
import com.tenpay.wxwork.approval.presvr.common.exception.BizException;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.common.service.KeyStoreService;
import com.tenpay.wxwork.approval.presvr.sender.bean.BankResHeader;
import com.tenpay.wxwork.approval.presvr.sender.bean.BankResResponse;
import com.tenpay.wxwork.approval.presvr.sender.bean.BankResponse;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankHeader;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.RequestBank;
import com.tenpay.wxwork.approval.presvr.sender.common.CftErrorBean;
import com.tenpay.wxwork.approval.presvr.sender.service.ErrCodeConvertServiceImpl;

@Component
public class PackageProcessor {
	
    private static final Logger LG = LoggerFactory.getLogger(PackageProcessor.class);
    
	@Resource
    private ErrCodeConvertServiceImpl errCodeConvertService;
	
	public RequestBank packRequest(BankInfo bankInfo,String jsonStringReqBody,ReqBankHeader reqBankHeader,String charset) throws InnerException, KeyException
	{
		ReqBankRequest reqBankRequest = new ReqBankRequest();
		reqBankRequest.setHead(reqBankHeader);//包体加密
		String encryptBody = KeyStoreService.encrypt(bankInfo.getBankCrtId(),bankInfo.getBankCrtVer(),jsonStringReqBody,charset);
		reqBankRequest.setBody(encryptBody);
  
		RequestBank requestBank = new RequestBank();
		requestBank.setRequest(reqBankRequest);
		  
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try
		{
			String jsonRequestBank = mapper.writeValueAsString(requestBank.getRequest());
			String signature = KeyStoreService.sign(bankInfo.getCftKeyId(),bankInfo.getCftVer(),jsonRequestBank,charset);
			requestBank.setSignature(signature);
		}catch (JsonProcessingException e) {
    		LG.error("JsonProcessingException:"+e);
    		throw new InnerException(BizError.JSON_CONVERT_ERROR,"catch JsonProcessingException.");
        }
		return requestBank;
	}
	
	public String handleBankResponse(BankInfo bankInfo, ReqBankHeader reqBankHeader,String responseJsonString,String charset) throws InnerException, KeyException
	{
		if(null == bankInfo)
		{
			LG.error("Unsuported bank:");
			throw new InnerException(BizError.BANK_INFO_NOT_EXIST,"Bank info not exist.");
		}
		if (StringUtils.isBlank(responseJsonString)) {
		    throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message is empty.");
		}
		
		BankResponse bankResponse;
		
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    	try 
    	{
			bankResponse = mapper.readValue(responseJsonString, BankResponse.class);
	        if(StringUtils.isBlank(bankResponse.getSignature()))
	        {
	        	throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message lack of signature or sign.");
	        }
	        if(null == bankResponse.getResponse())
	        {
	        	throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message lack of response.");
	        }
	        else
	        {
	        	if(null == bankResponse.getResponse().getHead())
	        	{
	        		throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message lack of head.");
	        	}
	        }
		}
		catch (IOException e) {
			LG.error("IOException:"+e);
    		throw new InnerException(BizError.JSON_CONVERT_ERROR,"catch IOException.");
   
		}

    	String bankResponseJson;
    	boolean verifyResult = false;

		int beginIndex = StringUtils.ordinalIndexOf(responseJsonString,"{",2);
		String responseSubString = responseJsonString.substring(beginIndex,(StringUtils.lastIndexOf(responseJsonString, "}")));
		int lastIndex = StringUtils.lastIndexOf(responseSubString, "}");
		bankResponseJson = responseSubString.substring(0, lastIndex+1);
		LG.debug("signsrc(bankResponseJson):"+bankResponseJson);

		verifyResult = KeyStoreService.verifySign(bankInfo.getBankCrtId(),bankInfo.getBankCrtVer(),bankResponseJson,bankResponse.getSignature(),charset);

    	if(!verifyResult)
    	{
    		LG.error("verify bank sign error.");
    		throw new InnerException(BizError.VERIFY_SIGN_ERROR,"verify bank sign error.");
    	}
    	else
    	{
        	this.checkConsistency(reqBankHeader, bankResponse.getResponse());
    		BankResHeader bankResHeader = bankResponse.getResponse().getHead();
    		if (StringUtils.isBlank(bankResHeader.getRetcode())) 
    		{
    		    throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message lack of retcode.");
    		}
    		else if(! bankResHeader.getRetcode().equals("0"))
    		{	
    			if (StringUtils.isEmpty(bankResHeader.getErrmsg())) 
    			{
    		        throw new InnerException(BizError.MISSING_REQUIRED_FIELD, "Bank response message lack of errmsg.");
    			}
    			else
    			{
        			CftErrorBean cftErrorBean = errCodeConvertService.getConverterErrorBean(bankResHeader.getRetcode(), bankResHeader.getErrmsg(),Integer.toString(bankInfo.getBankType()));
        			LG.error("cftErrorBean："+Integer.toString(cftErrorBean.getCftcode())+cftErrorBean.getCftdesc());
        		
        			throw new BizException(ErrorModuleConvert.toModuleError(cftErrorBean.getCftcode()), bankResHeader.getRetcode(), cftErrorBean.getCftdesc(), bankResHeader.getErrmsg());
    			}
    		}	
    	}
    	
		String decryptBody = KeyStoreService.decrypt(bankInfo.getCftKeyId(),bankInfo.getCftVer(),bankResponse.getResponse().getBody(),charset);

		LG.debug("decryptBody:"+decryptBody);
		return decryptBody;
	}
	
	public void checkConsistency(ReqBankHeader reqBankHeader,BankResResponse bankResResponse)
    {
    	if(! reqBankHeader.getBankAppid().equals(bankResResponse.getHead().getBankAppid()))
    	{
    		LG.error("The key word bank_appid is inconsistent.");
    		throw new InnerException(BizError.KEYMSG_CONSISTENCY_ERROR,"The key word bank_appid is inconsistent.");
    	}
    	if(! reqBankHeader.getOrgId().equals(bankResResponse.getHead().getOrgId()))
    	{
    		LG.error("The key word org_id is inconsistent.");
    		throw new InnerException(BizError.KEYMSG_CONSISTENCY_ERROR,"The key word org_id is inconsistent.");
    	}
    	if(! reqBankHeader.getMsgNo().equals(bankResResponse.getHead().getMsgNo()))
    	{
    		LG.error("The key word msg_no is inconsistent.");
    		throw new InnerException(BizError.KEYMSG_CONSISTENCY_ERROR,"The key word msg_no is inconsistent.");
    	}
    	if(! reqBankHeader.getTransCode().equals(bankResResponse.getHead().getTransCode()))
    	{
    		LG.error("The key word trans_code is inconsistent.");
    		throw new InnerException(BizError.KEYMSG_CONSISTENCY_ERROR,"The key word trans_code is inconsistent.");
    	}
    }
}
