package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BankResponse {

		@JsonProperty("response")
		private BankResResponse response;
		
		@JsonProperty("signature")
		private String signature;

		@JsonIgnore
		public BankResResponse getResponse() {
			return response;
		}
		
		@JsonIgnore
		public void setResponse(BankResResponse response) {
			this.response = response;
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
