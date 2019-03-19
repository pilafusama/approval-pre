package com.tenpay.wxwork.approval.presvr.sender.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqBankHeader {
	@JsonProperty("bank_appid")
	private String bank_appid;

	@JsonProperty("org_id")
	private String org_id;

	@JsonProperty("trans_code")
	private String trans_code;

	@JsonProperty("msg_no")
	private String msg_no;
	
	@JsonProperty("msg_time")
	private String msg_time;
	
	@JsonProperty("sign_type")
	private String sign_type;

	@JsonProperty("ver")
	private String ver;
	
	public ReqBankHeader(String bank_appid,String org_id,String trans_code){
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String msgTime = df.format(new Date());

		long currentTime = System.currentTimeMillis(); 
		
		this.setBankAppid(bank_appid);
		this.setOrgId(org_id);
		this.setTransCode(trans_code);
		this.setMsgNo(Long.toString(currentTime));
		this.setMsgTime(msgTime.substring(0,14));
		this.setSignType("SHA256WithRSA");
		this.setVer("1.0");
	}
	
	@JsonIgnore
	public String getBankAppid() {
		return bank_appid;
	}

	@JsonIgnore
	public void setBankAppid(String bank_appid) {
		this.bank_appid = bank_appid;
	}


	public String getOrgId() {
		return org_id;
	}

	@JsonIgnore
	public void setOrgId(String org_id) {
		this.org_id = org_id;
	}

	@JsonIgnore
	public String getTransCode() {
		return trans_code;
	}

	@JsonIgnore
	public void setTransCode(String trans_code) {
		this.trans_code = trans_code;
	}

	@JsonIgnore
	public String getMsgNo() {
		return msg_no;
	}

	@JsonIgnore
	public void setMsgNo(String msg_no) {
		this.msg_no = msg_no;
	}

	@JsonIgnore
	public String getMsgTime() {
		return msg_time;
	}

	@JsonIgnore
	public void setMsgTime(String msg_time) {
		this.msg_time = msg_time;
	}

	@JsonIgnore
	public String getSignType() {
		return sign_type;
	}

	@JsonIgnore
	public void setSignType(String sign_type) {
		this.sign_type = sign_type;
	}

	@JsonIgnore
	public String getVer() {
		return ver;
	}

	@JsonIgnore
	public void setVer(String ver) {
		this.ver = ver;
	}
}
