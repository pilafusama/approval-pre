package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqBankRequest {
	
	@JsonProperty("head")
	private ReqBankHeader head;
	
	@JsonProperty("body")
	private String body;
	
	@JsonIgnore
	public ReqBankHeader getHead() {
		return head;
	}
	
	@JsonIgnore
	public void setHead(ReqBankHeader head) {
		this.head = head;
	}
	
	@JsonIgnore
	public String getBody() {
		return body;
	}
	
	@JsonIgnore
	public void setBody(String body) {
		this.body = body;
	}
}
