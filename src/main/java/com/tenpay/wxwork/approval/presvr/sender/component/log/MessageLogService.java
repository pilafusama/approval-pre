package com.tenpay.wxwork.approval.presvr.sender.component.log;


import com.tenpay.bap.relay.context.RelaySessionInfo;

/**
 * Created by Sean Lei on 16/12/2016.
 */
public interface MessageLogService {
    void logMessage(RelaySessionInfo sessionInfo, String msg);
}
