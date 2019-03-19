package com.tenpay.wxwork.approval.presvr.sender.common;

import java.io.Serializable;

/**
 * 财付通错误
 *
 * @author jayolu
 *         <p>
 *         Modified by Sean Lei on 30/11/2016.
 */
public class CftErrorBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1574365444444L;

	private Integer cftcode;

    private String bankcode;

    private String cftdesc;

    private String bankdesc;


    /**
     * 财付通错误实例
     *
     * @param cftcode  财付通错误码
     * @param bankcode 银行错误码
     * @param cftdesc  财付通错误信息
     * @param bankdesc 银行错误信息
     */
    public CftErrorBean(Integer cftcode, String bankcode, String cftdesc, String bankdesc) {
        super();
        this.cftcode = cftcode;
        this.bankcode = bankcode;
        this.cftdesc = cftdesc;
        this.bankdesc = bankdesc;
    }

    /**
     * 获取银行错误码
     *
     * @return
     */
    public String getBankcode() {
        return bankcode;
    }

    public String getBankdesc() {
        return bankdesc;
    }

    /**
     * 获取财付通错误码
     *
     * @return
     */
    public Integer getCftcode() {
        return cftcode;
    }

    public String getCftdesc() {
        return cftdesc;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public void setBankdesc(String bankdesc) {
        this.bankdesc = bankdesc;
    }

    public void setCftcode(Integer cftcode) {
        this.cftcode = cftcode;
    }

    public void setCftdesc(String cftdesc) {
        this.cftdesc = cftdesc;
    }
}

