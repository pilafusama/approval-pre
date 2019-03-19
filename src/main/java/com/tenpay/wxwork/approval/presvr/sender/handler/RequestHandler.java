package com.tenpay.wxwork.approval.presvr.sender.handler;

import com.tenpay.wxwork.approval.presvr.sender.bean.BizRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizResponse;


/**
 * Created by Sean Lei on 22/11/2016.
 */
public interface RequestHandler {
    /**
     * handler具体处理过程
     * @param request
     * @param response
     * @param handlerChain
     */
    void doHandle(BizRequest request, BizResponse response);
}

