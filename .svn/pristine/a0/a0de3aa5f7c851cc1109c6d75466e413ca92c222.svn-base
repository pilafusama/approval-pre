package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowPaymentQueryReqBody {
	//银行标识
	@JsonProperty("bank_appid")
	private String bank_appid;
	
	//银行登录帐号
	@JsonProperty("bank_uin")
	private String bank_uin;
	
	//企业标识
	@JsonProperty("corp_id")
	private String corp_id;
	
	//企业银行标识
	@JsonProperty("corp_auth_id")
	private String corp_auth_id;
	
	//审批单类型
	@JsonProperty("approval_type")
	private String approval_type;
	
	//审批流
	@JsonProperty("flow_id")
	private String flow_id;
	
	//审批单标识
	@JsonProperty("id")
	private String id;

	
	public FlowPaymentQueryReqBody(FlowPaymentQueryReq flowPaymentQueryReq){
		this.setBankAppid(flowPaymentQueryReq.getBankAppid());
		this.setBankUin(flowPaymentQueryReq.getBankUin());
		this.setCorpId(flowPaymentQueryReq.getCorpId());
		this.setCorpAuthId(flowPaymentQueryReq.getCorpAuthId());
		this.setApprovalType(flowPaymentQueryReq.getApprovalType());
		this.setFlowId(flowPaymentQueryReq.getFlowId());
		this.setId(flowPaymentQueryReq.getId());
	}
	
	@JsonIgnore
	public String getBankAppid() {
		return bank_appid;
	}
	
	@JsonIgnore
	public void setBankAppid(String bank_appid) {
		this.bank_appid = bank_appid;
	}
	
	@JsonIgnore
	public String getBankUin() {
		return bank_uin;
	}

	@JsonIgnore
	public void setBankUin(String bank_uin) {
		this.bank_uin = bank_uin;
	}

	@JsonIgnore
	public String getCorpId() {
		return corp_id;
	}

	@JsonIgnore
	public void setCorpId(String corp_id) {
		this.corp_id = corp_id;
	}

	public String getCorpAuthId() {
		return corp_auth_id;
	}
	@JsonIgnore
	public void setCorpAuthId(String corp_auth_id) {
		this.corp_auth_id = corp_auth_id;
	}
	@JsonIgnore
	public String getApprovalType() {
		return approval_type;
	}
	@JsonIgnore
	public void setApprovalType(String approval_type) {
		this.approval_type = approval_type;
	}
	@JsonIgnore
	public String getFlowId() {
		return flow_id;
	}
	@JsonIgnore
	public void setFlowId(String flow_id) {
		this.flow_id = flow_id;
	}
	@JsonIgnore
	public String getId() {
		return id;
	}
	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}
}
