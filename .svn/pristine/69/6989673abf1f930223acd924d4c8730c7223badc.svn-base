package com.tenpay.wxwork.approval.presvr.sender.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//付款
public class Flow1003Detail {
	//审批流类型
	@JsonProperty("type")
	private String type;
	
	//事由
	@JsonProperty("reason")
	private String reason;
	
	//备注
	@JsonProperty("common")
	private String common;
	
	//发生时间
	@JsonProperty("pay_time")
	private String pay_time;

	public Flow1003Detail(Flow1003DetailReq flow1003DetailReq){
		this.setType(flow1003DetailReq.getType());
		this.setReason(flow1003DetailReq.getReason());
		this.setCommon(flow1003DetailReq.getCommon());
		
        Long timestamp = Long.parseLong(flow1003DetailReq.getPayTime());
        String date = new SimpleDateFormat("yyyyMMddHHmmSS", Locale.CHINA).format(new Date(timestamp));
		this.setPayTime(date.substring(0,14));
	}
	
	@JsonIgnore
	public String getType() {
		return type;
	}
	@JsonIgnore
	public void setType(String type) {
		this.type = type;
	}
	@JsonIgnore
	public String getReason() {
		return reason;
	}
	@JsonIgnore
	public void setReason(String reason) {
		this.reason = reason;
	}
	@JsonIgnore
	public String getCommon() {
		return common;
	}
	@JsonIgnore
	public void setCommon(String common) {
		this.common = common;
	}
	@JsonIgnore
	public String getPayTime() {
		return pay_time;
	}
	@JsonIgnore
	public void setPayTime(String pay_time) {
		this.pay_time = pay_time;
	}

}
