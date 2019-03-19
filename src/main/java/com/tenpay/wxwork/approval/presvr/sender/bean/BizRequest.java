package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.tenpay.bap.relay.context.RelaySessionInfo;
import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;

/**
 * Created by Sean Lei on 22/11/2016.
 */
public class BizRequest {
    private RelaySessionInfo sessionInfo;

    private BankProxyRelayRequestMsg requestMsg;

    public BizRequest(RelaySessionInfo sessionInfo, BankProxyRelayRequestMsg requestMsg) {
        this.sessionInfo = sessionInfo;
        this.requestMsg = requestMsg;
    }

    public RelaySessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(RelaySessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public BankProxyRelayRequestMsg getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(BankProxyRelayRequestMsg requestMsg) {
        this.requestMsg = requestMsg;
    }

}

