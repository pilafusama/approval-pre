package com.tenpay.wxwork.approval.presvr.sender.handler;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.tenpay.bap.common.contants.AccResultType;
import com.tenpay.bap.common.exception.BAPException;
import com.tenpay.bap.relay.context.RelaySessionInfo;
import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import com.tenpay.bap.relay.protocol.BankProxyRelayResponseMsg;
import com.tenpay.bap.relay.protocol.RelayMessage;
import com.tenpay.bap.server.core.RelayMessageHandler;
import com.tenpay.bap.server.log.service.AccLogService;
import com.tenpay.key.api.KeyException;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfos;
import com.tenpay.wxwork.approval.presvr.common.config.ConfigUtils;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.error.ErrorModuleConvert;
import com.tenpay.wxwork.approval.presvr.common.exception.BizException;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.common.exception.RelayException;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizResponse;

/**
 * Created by Sean Lei on 22/11/2016.
 */

/**
 * 分发request指令
 * 去掉之前的if else判断
 * 通过首次启动扫描注解映射指令的处理
 */

@Component
public final class RequestDispatcher implements RelayMessageHandler<BankProxyRelayRequestMsg> {
    private static final Logger LG = LoggerFactory.getLogger(RequestDispatcher.class);

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Integer, Processor> handlerMap = new HashMap<>();

    @Resource
    private AccLogService logService;

    @Value("#{'${supportedBankTypes}'.split(',')}")
    private List<Integer> supportBanks;

    /**
     * 初识化时将所有处理bank请求的handler对象放入map供后续使用
     */
    // FIXME: 23/11/2016 后续可以改为热加载processor,不停应用加载新的processor refresh容器即可 v2.1.0
    @PostConstruct
    public void init() throws IOException {
    	LG.info("LoanRequestDispatcher  init");
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(RequestTypeHandler.class);
        for (String name : beanNames) {
        	RequestTypeHandler annotation = applicationContext.findAnnotationOnBean(name, RequestTypeHandler.class);
            int requestType = annotation.requestType();
            handlerMap.put(requestType, applicationContext.getBean(name, Processor.class));
        }
        ConfigUtils configUtils = ConfigUtils.getInstance();
        configUtils.init();
        BankInfos bankInfos = BankInfos.getInstance();
    	bankInfos.init();
    }

    @Override
    public RelayMessage handleRelayMessage(RelaySessionInfo sessionInfo, BankProxyRelayRequestMsg msg) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        BizResponse response = new BizResponse();
        int requestType = msg.getRequestType();
        int bankType = msg.getBankType();
        Processor processor = handlerMap.get(requestType);
        try {
            if (!supportBanks.contains(bankType)) {
                throw new InnerException(BizError.UNSUPPORT_REQUEST, "doHandle bankType:" + bankType + " not supported");
            }
            sessionInfo.setMsgNo(msg.getMsgNo());
            BizRequest request = new BizRequest(sessionInfo, msg);
            if (processor == null) {
                throw new InnerException(BizError.UNSUPPORT_REQUEST, "doHandle requestType:" + requestType + " not registered");
            }
            //设置msgNo 供后边日志使用
            sessionInfo.getContextInfo().put(BankProxyRelayRequestMsg.MSG_NO, msg.getMsgNo());
            sessionInfo.getContextInfo().put(BankProxyRelayRequestMsg.REQUEST_TYPE, msg.getRequestType() + "");
            sessionInfo.getContextInfo().put(BankProxyRelayRequestMsg.BANK_TYPE, msg.getBankType() + "");

            ErrorModuleConvert errorModuleConvert = ErrorModuleConvert.getInstance();
            errorModuleConvert.init(ConfigUtils.getIntValue("moduleId"));
            LG.debug("moduleId:"+ConfigUtils.getIntValue("moduleId"));
            processor.doProcess(request, response);
        } catch (BizException bizE) {
            LG.warn("finish request with biz exception:" + bizE.getInnerErrCode()+","+bizE.getBankErrMsg(), bizE);
            BankProxyRelayResponseMsg bankProxyRelayResponseMsg = new BankProxyRelayResponseMsg(bizE.getInnerErrCode(), bizE.getInnerErrMsg(),
                    bizE.getBankErrCode(), bizE.getBankErrMsg());
        	response.setResponseMsg(bankProxyRelayResponseMsg);
            return bankProxyRelayResponseMsg;
        } catch (InnerException innerE) {
            LG.error("process request InnerException:" + innerE.getErrorCode(), innerE);
            BankProxyRelayResponseMsg bankProxyRelayResponseMsg = new BankProxyRelayResponseMsg(innerE.getErrorCode(), innerE.getMsg());
            response.setResponseMsg(bankProxyRelayResponseMsg);
            return bankProxyRelayResponseMsg;
        } catch (BAPException re) {
            LG.error("process request BAPError:" + re.getErrorCode(), re);
            BankProxyRelayResponseMsg bankProxyRelayResponseMsg = new BankProxyRelayResponseMsg(re.getErrorCode(), re.getMsg());
            response.setResponseMsg(bankProxyRelayResponseMsg);
            return bankProxyRelayResponseMsg;
        } catch (RelayException re) {
            LG.error("process request RelayError:" + re.getErrorCode(), re);
            response.setResponseMsg(new BankProxyRelayResponseMsg(re.getErrorCode(), re.getMsg()));
            return new BankProxyRelayResponseMsg(re.getErrorCode(), re.getMsg());
        }catch (KeyException ke) {
            LG.error("process request KeyException:" + ke.getErrorCode(), ke);
            BankProxyRelayResponseMsg bankProxyRelayResponseMsg = new BankProxyRelayResponseMsg(ke.getErrorCode(), ke.getErrorMessage());
            response.setResponseMsg(bankProxyRelayResponseMsg);
            return bankProxyRelayResponseMsg;
        }catch (Throwable e) {
            LG.error("process request Other Exception:", e);
            BankProxyRelayResponseMsg bankProxyRelayResponseMsg = new BankProxyRelayResponseMsg(BizError.DEFAULT_ERROR.errorCode(), "UNKNOWN error");
            response.setResponseMsg(bankProxyRelayResponseMsg);
            return bankProxyRelayResponseMsg;
        } finally {
            stopWatch.stop();
            sessionInfo.setProcessTotalTime(stopWatch.getLastTaskTimeMillis());
            int result = response.getResponseMsg().getResult();
            int resultType = 0;
            //如果出错就默认给APP_ERROR 原来的逻辑
            if (result != AccResultType.SUCCESS) {
                resultType = AccResultType.APP_ERROR;
            }
            sessionInfo.put("resultType", resultType + "");
            sessionInfo.put("resCode", result + "");
            logService.logAcc(msg, response.getResponseMsg(), sessionInfo);
        }
        return response.getResponseMsg();
    }
}

