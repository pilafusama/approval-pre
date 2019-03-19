package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowPaymentQueryResBody {
	//业务结果状态码
	@JsonProperty("retcode")
	private String retcode;
	
	//结果说明
	@JsonProperty("errmsg")
	private String errmsg;
	
	//企业绑定id
	@JsonProperty("corp_auth_id")
	private String corp_auth_id;
	
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
	public String getCorpAuthId() {
		return corp_auth_id;
	}

	@JsonIgnore
	public void setCorpAuthId(String corp_auth_id) {
		this.corp_auth_id = corp_auth_id;
	}
}
