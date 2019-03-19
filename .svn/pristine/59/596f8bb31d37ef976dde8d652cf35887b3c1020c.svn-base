package com.tenpay.wxwork.approval.presvr.common.exception;

/**
 * Created by Sean Lei on 30/11/2016.
 */

/**
 * 业务异常类
 */
public class BizException extends RuntimeException {
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -7535002921247256633L;
    
    private int innerErrCode;

    private String bankErrCode;

    private String innerErrMsg;

    private String bankErrMsg;

    public BizException(int innerErrCode, String bankErrCode, String innerErrMsg, String bankErrMsg) {
        super(innerErrMsg);
        this.innerErrCode = innerErrCode;
        this.bankErrCode = bankErrCode;
        this.innerErrMsg = innerErrMsg;
        this.bankErrMsg = bankErrMsg;
    }

    public int getInnerErrCode() {
        return innerErrCode;
    }

    public String getBankErrCode() {
        return bankErrCode;
    }

    public String getInnerErrMsg() {
        return innerErrMsg;
    }

    public String getBankErrMsg() {
        return bankErrMsg;
    }
}
