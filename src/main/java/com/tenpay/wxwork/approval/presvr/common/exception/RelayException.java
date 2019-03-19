package com.tenpay.wxwork.approval.presvr.common.exception;

/**
 * Created by Sean Lei on 02/12/2016.
 */

/**
 * 调用relay的异常类
 */
public class RelayException extends RuntimeException {
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = -7535242921247256633L;
    private int errorCode;
    private String msg;

    public RelayException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }
}

