package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BankResResponse {
	
	@JsonProperty("head")
	private BankResHeader head;
	
	@JsonProperty("body")
	private String body;
	
	@JsonIgnore
	public BankResHeader getHead() {
		return head;
	}
	
	@JsonIgnore
	public void setHead(BankResHeader head) {
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
