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
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmit1001ReqBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmit1002ReqBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmit1003ReqBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmitReq;
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmitRes;
import com.tenpay.wxwork.approval.presvr.sender.bean.FlowSubmitResBody;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankHeader;
import com.tenpay.wxwork.approval.presvr.sender.bean.ReqBankRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.RequestBank;
import com.tenpay.wxwork.approval.presvr.sender.common.ApporvalType;
import com.tenpay.wxwork.approval.presvr.sender.common.CftErrorBean;
import com.tenpay.wxwork.approval.presvr.sender.common.RequestType;
import com.tenpay.wxwork.approval.presvr.sender.component.log.MessageLogService;
import com.tenpay.wxwork.approval.presvr.sender.core.BankClientCommunicator;
import com.tenpay.wxwork.approval.presvr.sender.handler.Processor;
import com.tenpay.wxwork.approval.presvr.sender.handler.RequestTypeHandler;
import com.tenpay.wxwork.approval.presvr.sender.service.ErrCodeConvertServiceImpl;

@RequestTypeHandler(requestType = RequestType.FlowSubmit)
public class FlowSubmitHandler implements Processor {
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
	
    private static final Logger LG = LoggerFactory.getLogger(FlowSubmitHandler.class);

    @Override
    public void doProcess(BizRequest request, BizResponse response) throws InnerException,KeyException,BizException{
        BankProxyRelayRequestMsg requestMsg = request.getRequestMsg();
        FlowSubmitReq flowSubmitReq = new FlowSubmitReq(requestMsg);

        RelaySessionInfo sessionInfo = request.getSessionInfo();
        
        //银行响应报文
        String strLoaneeRes;
        String responseBodyJson;
        FlowSubmitResBody FlowSubmitResBody;
        
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
        StopWatch stopWatch = new StopWatch();
        try {
	            stopWatch.start();
	            //读取配置
	            int bankType = request.getRequestMsg().getBankType();
	            LG.debug("bankType："+Integer.toString(bankType));
	            BankInfo bankInfo = BankInfos.getInstance().getBankInfobyType(bankType);
	            String bankUrl = ConfigUtils.getStringValue("reqUrl",bankType,RequestType.FlowSubmit,"");
	            LG.debug("bankUrl："+bankUrl);
	            String orgId = ConfigUtils.getStringValue("orgId",bankType,RequestType.FlowSubmit,"");
	            String charset = ConfigUtils.getStringValue("charset");

	            String stringFlowSubmitReqBody;
	            ReqBankHeader reqBankHeader = new ReqBankHeader(flowSubmitReq.getBankAppid(), orgId, "FLOW_SUBMIT");

				try
				{
		            //组包
		            switch(Integer.parseInt(flowSubmitReq.getApprovalType()))
		            {
			            case ApporvalType.Reimbursement:
							stringFlowSubmitReqBody = mapper.writeValueAsString(new FlowSubmit1001ReqBody(flowSubmitReq));
							break;
			            case ApporvalType.Cost:
							stringFlowSubmitReqBody = mapper.writeValueAsString(new FlowSubmit1002ReqBody(flowSubmitReq));
							break;
			            case ApporvalType.Payment:
							stringFlowSubmitReqBody = mapper.writeValueAsString(new FlowSubmit1003ReqBody(flowSubmitReq));
							break;
			            case ApporvalType.Salary:
			            	stringFlowSubmitReqBody = mapper.writeValueAsString(new FlowSubmit1003ReqBody(flowSubmitReq));
			            	break;
						default:
							throw new InnerException(BizError.UNSUPPORTED_APPROVAL_TYPE_ERROR);
		            }
		            LG.debug("stringCorpBindConfirmReqBody:"+stringFlowSubmitReqBody);
				}
				catch (JsonProcessingException e1) {
	            	LG.error("reqBody converted to object String error.");
	            	throw new InnerException(BizError.JSON_CONVERT_ERROR,"Json converted to object error.");
				}
	            RequestBank requestBank = packageProcessor.packRequest(bankInfo, stringFlowSubmitReqBody, reqBankHeader, charset);
	            //发送银行
	            StringBuilder responseCharset = new StringBuilder();
	            strLoaneeRes = bankCommunicator.sendRequestToBank(bankInfo,sessionInfo, requestBank, bankUrl,charset,responseCharset);
	            responseBodyJson = packageProcessor.handleBankResponse(bankInfo, reqBankHeader, strLoaneeRes, responseCharset.toString());
				
	            try
	            {
	            	// 返回成功结果
	            	FlowSubmitResBody = mapper.readValue(responseBodyJson, FlowSubmitResBody.class);
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
	            if(!FlowSubmitResBody.getRetcode().equals("0"))
	            {
        			CftErrorBean cftErrorBean = errCodeConvertService.getConverterErrorBean(FlowSubmitResBody.getRetcode(), FlowSubmitResBody.getErrmsg(),Integer.toString(bankInfo.getBankType()));
        			LG.error("cftErrorBean："+Integer.toString(cftErrorBean.getCftcode())+cftErrorBean.getCftdesc());
        		
        			throw new BizException(ErrorModuleConvert.toModuleError(cftErrorBean.getCftcode()), FlowSubmitResBody.getRetcode(), cftErrorBean.getCftdesc(), FlowSubmitResBody.getErrmsg());
	            }
	            FlowSubmitRes FlowSubmitRes = new FlowSubmitRes(0,"OK",FlowSubmitResBody.getRetcode(),FlowSubmitResBody.getErrmsg());
	            FlowSubmitRes.setResponse(FlowSubmitResBody.getBankListid(),FlowSubmitRes.getRetcode(),FlowSubmitRes.getErrmsg());	
	            response.setResponseMsg(FlowSubmitRes);
        }
        finally
        {
            stopWatch.stop();
            AtomicLong costTime = sessionInfo.getCallBankTotalCostTime();
            costTime.getAndAdd(stopWatch.getLastTaskTimeMillis());
        }
    }
}

