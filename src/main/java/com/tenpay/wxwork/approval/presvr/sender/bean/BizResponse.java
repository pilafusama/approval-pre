package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.tenpay.bap.relay.protocol.BankProxyRelayResponseMsg;

/**
 * Created by Sean Lei on 22/11/2016.
 */
public class BizResponse {
    private BankProxyRelayResponseMsg responseMsg;

    public BizResponse() {
        responseMsg = new BankProxyRelayResponseMsg(-1, "");
    }

    public BankProxyRelayResponseMsg getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(BankProxyRelayResponseMsg responseMsg) {
        this.responseMsg = responseMsg;
    }

    private int retCode;

    private Throwable ex;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }
}

