package com.tenpay.wxwork.approval.presvr.sender.handler;

import com.tenpay.key.api.KeyException;
import com.tenpay.wxwork.approval.presvr.common.exception.BizException;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizRequest;
import com.tenpay.wxwork.approval.presvr.sender.bean.BizResponse;

/**
 * Created by Sean Lei on 23/11/2016.
 */
public interface Processor {

    /**
     * 每个bank指令的处理过程
     *
     * @param request
     * @param response
     */
    void doProcess(BizRequest request, BizResponse response)  throws InnerException,KeyException,BizException;
}
