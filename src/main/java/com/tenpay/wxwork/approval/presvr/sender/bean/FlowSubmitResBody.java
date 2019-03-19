package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowSubmitResBody {
	//业务结果状态码
	@JsonProperty("retcode")
	private String retcode;
	
	//结果说明
	@JsonProperty("errmsg")
	private String errmsg;

	//银行受理标识
	@JsonProperty("bank_listid")
    private String bank_listid; 
	
	@JsonIgnore
	public String getRetcode() {
		return retcode;
	}
	
	@JsonIgnore
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	
	@JsonIgnore
	public String getErrmsg() {
		return errmsg;
	}

	@JsonIgnore
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	@JsonIgnore
	public String getBankListid() {
		return bank_listid;
	}
	
	@JsonIgnore
	public void setBanklistid(String bank_listid) {
		this.bank_listid = bank_listid;
	}

}
