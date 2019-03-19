package com.tenpay.wxwork.approval.presvr.common.exception;

import com.tenpay.bap.common.error.BAPError;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.error.ErrorModuleConvert;

public class InnerException extends RuntimeException {
	
	private static final long serialVersionUID = 5058450716534758128L;
	// 错误码
    private int errorCode = BAPError.DEFAULT.errorCode();

    private String msg;

    /**
     * @param error 错误类型
     */
    public InnerException(BizError error) {
        super(error.errorMsg());
        this.errorCode = ErrorModuleConvert.toModuleError(error.errorCode());
        this.msg = error.errorMsg();
    }

    /**
     * @param error 错误类型
     * @param cause 原因
     */
    public InnerException(BizError error, Throwable cause) {
        super(error.errorMsg(), cause);
        this.errorCode = ErrorModuleConvert.toModuleError(error.errorCode());
        this.msg = error.errorMsg();
    }

    /**
     * @param error    错误类型
     * @param extraMsg 额外补充错误信息
     */
    public InnerException(BizError error, String extraMsg) {
        super(error.errorMsg() + ":" + extraMsg);
        this.errorCode = ErrorModuleConvert.toModuleError(error.errorCode());
        this.msg = error.errorMsg() + ":" + extraMsg;
    }

    /**
     * @param error    错误类型
     * @param cause    原因
     * @param extraMsg 额外补充错误信息
     */
    public InnerException(BizError error, Throwable cause, String extraMsg) {
        super(error.errorMsg() + ":" + extraMsg, cause);
        this.errorCode = ErrorModuleConvert.toModuleError(error.errorCode());
        this.msg = error.errorMsg() + ":" + extraMsg;
    }

    /**
     * 获取错误码
     *
     * @return
     */
    public int getErrorCode() {
        // 防止出现异常情况下仍然返回客户端0响应码。
        if (this.errorCode == 0) {
            this.errorCode = BAPError.DEFAULT.errorCode();
        }

        return this.errorCode;
    }

    public String getMsg() {
        return msg;
    }
}

