package com.tenpay.wxwork.approval.presvr.sender.processor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpay.bap.relay.context.RelaySessionInfo;
import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import com.tenpay.key.api.KeyException;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfo;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfos;
import com.tenpay.wxwork.approval.presvr.common.config.ConfigUtils;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.error.ErrorModuleConvert;
import com.tenpay.wxwork.approval.presvr.common.exception.BizException;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizResponse;
import com.tenpay.wxwork.approval.presvr.sender.bean.CorpBindConfirmReq;
import com.tenpay.wxwork.approval.presvr.sender.bean.CorpBindConfirmReqBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.CorpBindConfirmRes;
import com.tenpay.wxwork.approval.presvr.sender.bean.CorpBindConfirmResBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankHeader;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.RequestBank;
import com.tenpay.wxwork.approval.presvr.sender.common.CftErrorBean;
import com.tenpay.wxwork.approval.presvr.sender.common.RequestType;
import com.tenpay.wxwork.approval.presvr.sender.component.log.MessageLogService;
import com.tenpay.wxwork.approval.presvr.sender.core.BankClientCommunicator;
import com.tenpay.wxwork.approval.presvr.sender.handler.Processor;
import com.tenpay.wxwork.approval.presvr.sender.handler.RequestTypeHandler;
import com.tenpay.wxwork.approval.presvr.sender.service.ErrCodeConvertServiceImpl;

@RequestTypeHandler(requestType = RequestType.CorpBindConfirm)
public class CorpBindConfirmHandler implements Processor {
    /**
     * 日志service
     */
    @Resource
    protected MessageLogService logService;
    @Resource
    private BankClientCommunicator bankCommunicator;
    @Resource
    private PackageProcessor packageProcessor;
	@Resource
    private ErrCodeConvertServiceImpl errCodeConvertService;
	
    private static final Logger LG = LoggerFactory.getLogger(CorpBindConfirmHandler.class);

    @Override
    public void doProcess(BizRequest request, BizResponse response) throws InnerException,KeyException,BizException{
        BankProxyRelayRequestMsg requestMsg = request.getRequestMsg();
        CorpBindConfirmReq corpBindConfirmReq = new CorpBindConfirmReq(requestMsg);

        RelaySessionInfo sessionInfo = request.getSessionInfo();
        
        //银行响应报文
        String strLoaneeRes;
        String responseBodyJson;
        CorpBindConfirmResBody corpBindConfirmResBody;
        
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
        StopWatch stopWatch = new StopWatch();
        try {
	            stopWatch.start();
	            //读取配置
	            int bankType = request.getRequestMsg().getBankType();
	            LG.debug("bankType："+Integer.toString(bankType));
	            BankInfo bankInfo = BankInfos.getInstance().getBankInfobyType(bankType);
	            String bankUrl = ConfigUtils.getStringValue("reqUrl",bankType,RequestType.CorpBindConfirm,"");
	            LG.debug("bankUrl："+bankUrl);
	            String orgId = ConfigUtils.getStringValue("orgId",bankType,RequestType.CorpBindConfirm,"");
	            String charset = ConfigUtils.getStringValue("charset");

	            //组包
	            CorpBindConfirmReqBody corpBindConfirmReqBody = new CorpBindConfirmReqBody(corpBindConfirmReq);
	            ReqBankHeader reqBankHeader = new ReqBankHeader(corpBindConfirmReq.getBankAppid(),orgId,"CORP_BIND_CONFIRM");
	            String stringCorpBindConfirmReqBody;
				try
				{
					stringCorpBindConfirmReqBody = mapper.writeValueAsString(corpBindConfirmReqBody);
					LG.debug("stringCorpBindConfirmReqBody:"+stringCorpBindConfirmReqBody);
				}
				catch (JsonProcessingException e1) {
	            	LG.error("reqBody converted to object String error.");
	            	throw new InnerException(BizError.JSON_CONVERT_ERROR,"Json converted to object error.");
				}
	            RequestBank requestBank = packageProcessor.packRequest(bankInfo, stringCorpBindConfirmReqBody, reqBankHeader, charset);
	            //发送银行
	            StringBuilder responseCharset = new StringBuilder();
	            strLoaneeRes = bankCommunicator.sendRequestToBank(bankInfo,sessionInfo, requestBank, bankUrl,charset,responseCharset);
	            responseBodyJson = packageProcessor.handleBankResponse(bankInfo, reqBankHeader, strLoaneeRes, responseCharset.toString());
				
	            try
	            {
	            	// 返回成功结果
	            	corpBindConfirmResBody = mapper.readValue(responseBodyJson, CorpBindConfirmResBody.class);
	            }
	            catch(JsonMappingException e)
	            {
	            	LG.error("strLoaneeRes converted to object String error.");
	            	throw new InnerException(BizError.JSON_CONVERT_ERROR,"Json converted to object error.");
	            }
	            catch(JsonParseException e)
	            {
	            	LG.error("strLoaneeRes converted to object String error.");
	            	throw new InnerException(BizError.JSON_CONVERT_ERROR,"Json converted to object error.");
	            }
	            catch(IOException e)
	            {
	            	LG.error("strLoaneeRes converted to object String error.");
	            	throw new InnerException(BizError.JSON_CONVERT_ERROR,"Json converted to object error.");
	            }
	            if(!corpBindConfirmResBody.getRetcode().equals("0"))
	            {
        			CftErrorBean cftErrorBean = errCodeConvertService.getConverterErrorBean(corpBindConfirmResBody.getRetcode(), corpBindConfirmResBody.getErrmsg(),Integer.toString(bankInfo.getBankType()));
        			LG.error("cftErrorBean："+Integer.toString(cftErrorBean.getCftcode())+cftErrorBean.getCftdesc());
        		
        			throw new BizException(ErrorModuleConvert.toModuleError(cftErrorBean.getCftcode()), corpBindConfirmResBody.getRetcode(), cftErrorBean.getCftdesc(), corpBindConfirmResBody.getErrmsg());
	            }
	            CorpBindConfirmRes corpBindConfirmRes = new CorpBindConfirmRes(0,"OK",corpBindConfirmResBody.getRetcode(),corpBindConfirmResBody.getErrmsg());
	            corpBindConfirmRes.setResponse(corpBindConfirmResBody.getCorpAuthId(),corpBindConfirmRes.getRetcode(),corpBindConfirmRes.getErrmsg());	
	            response.setResponseMsg(corpBindConfirmRes);
        }
        finally
        {
            stopWatch.stop();
            AtomicLong costTime = sessionInfo.getCallBankTotalCostTime();
            costTime.getAndAdd(stopWatch.getLastTaskTimeMillis());
        }
    }
}

