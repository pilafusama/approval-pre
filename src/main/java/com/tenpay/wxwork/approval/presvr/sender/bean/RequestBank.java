package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBank {

		@JsonProperty("request")
		private ReqBankRequest request;
		
		@JsonProperty("signature")
		private String signature;

		@JsonIgnore
		public ReqBankRequest getRequest() {
			return request;
		}
		
		@JsonIgnore
		public void setRequest(ReqBankRequest request) {
			this.request = request;
		}

		@JsonIgnore
		public String getSignature() {
			return signature;
		}

		@JsonIgnore
		public void setSignature(String signature) {
			this.signature = signature;
		}	
}
