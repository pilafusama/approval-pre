package com.tenpay.wxwork.approval.presvr.sender.component.log;

import com.tenpay.bap.relay.context.RelaySessionInfo;
import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Sean Lei on 16/12/2016.
 */
@Service
public class MessageLogServiceImpl implements MessageLogService {

    private static final Logger LG_MSG = LoggerFactory.getLogger("message");

    @Override
    public void logMessage(RelaySessionInfo sessionInfo, String msg) {
        LG_MSG.info(String.format("[%s][%s][%s][%s]", sessionInfo.get(BankProxyRelayRequestMsg.BANK_TYPE),
                sessionInfo.get(BankProxyRelayRequestMsg.REQUEST_TYPE),
                sessionInfo.get(BankProxyRelayRequestMsg.MSG_NO), msg));
    }
}
