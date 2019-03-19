package com.tenpay.wxwork.approval.presvr.sender.common;

public class RequestType {

    /**
     * 3.1企业付款绑定申请接口
     */
    public static final int CorpBindAuthen = 7721;

    /**
     * 企业付款绑定确认接口
     */
    public static final int CorpBindConfirm = 7722;
    
    /**
     * 提交待付款的审批单接口（微信推送审批单模式)
     */
    public static final int FlowSubmit = 7723;
 
    /**
     * 查询审批单付款结果接口
     */
    public static final int FlowPaymentQuery = 7724;
}

