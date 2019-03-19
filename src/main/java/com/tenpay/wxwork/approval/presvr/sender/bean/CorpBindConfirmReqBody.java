package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CorpBindConfirmReqBody {
	//银行标识
	@JsonProperty("bank_appid")
	private String bank_appid;
	
	//银行登录帐号
	@JsonProperty("bank_uin")
	private String bank_uin;
	
	//企业标识
	@JsonProperty("corp_id")
	private String corp_id;
	
	//企业名称
	@JsonProperty("corp_name")
	private String corp_name;
	
	//证件号
	@JsonProperty("cert_id")
	private String cert_id;
	
	//操作员标识
	@JsonProperty("op_user_id")
	private String op_user_id;
	
	//操作员手机号
	@JsonProperty("op_user_phone")
	private String op_user_phone;
	
	//业务状态
	@JsonProperty("status")
	private String status;
	
	//业务状态
	@JsonProperty("verify_code")
	private String verify_code;
	
	public CorpBindConfirmReqBody(CorpBindConfirmReq corpBindConfirmReq){
		this.setBankAppid(corpBindConfirmReq.getBankAppid());
		this.setBankUin(corpBindConfirmReq.getBankUin());
		this.setCertId(corpBindConfirmReq.getCertId());
		this.setCorpId(corpBindConfirmReq.getCorpId());
		this.setCorpName(corpBindConfirmReq.getCorpName());
		this.setOpUserId(corpBindConfirmReq.getOpUserId());
		this.setOpUserPhone(corpBindConfirmReq.getOpUserPhone());
		this.setStatus(corpBindConfirmReq.getStatus());
		this.setVerifyCode(corpBindConfirmReq.getVerifyCode());
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

	@JsonIgnore
	public String getCorpName() {
		return corp_name;
	}

	@JsonIgnore
	public void setCorpName(String corp_name) {
		this.corp_name = corp_name;
	}

	@JsonIgnore
	public String getCertId() {
		return cert_id;
	}

	@JsonIgnore
	public void setCertId(String cert_id) {
		this.cert_id = cert_id;
	}

	@JsonIgnore
	public String getOpUserId() {
		return op_user_id;
	}

	@JsonIgnore
	public void setOpUserId(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	@JsonIgnore
	public String getOpUserPhone() {
		return op_user_phone;
	}

	@JsonIgnore
	public void setOpUserPhone(String op_user_phone) {
		this.op_user_phone = op_user_phone;
	}

	@JsonIgnore
	public String getStatus() {
		return status;
	}

	@JsonIgnore
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public String getVerifyCode() {
		return verify_code;
	}

	@JsonIgnore
	public void setVerifyCode(String verify_code) {
		this.verify_code = verify_code;
	}
}
