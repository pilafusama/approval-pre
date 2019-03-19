package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenpay.wxwork.approval.presvr.common.utils.JsonUtils;

public class FlowSubmit1001ReqBody extends FlowSubmitReqBodyBase{
	//审批详情
	@JsonProperty("details")
	private Flow1001Detail flow1001Detail;

	public FlowSubmit1001ReqBody(FlowSubmitReq flowSubmitReq){
		super(flowSubmitReq);
		this.parseJsonObject(flowSubmitReq);
	}
	@JsonIgnore
    public Flow1001Detail getFlow1001Detail() {
		return flow1001Detail;
	}
	@JsonIgnore
	public void setFlow1001Detail(Flow1001Detail flow1001Detail) {
		this.flow1001Detail = flow1001Detail;
	}


	protected void parseJsonObject(FlowSubmitReq flowSubmitReq) {
		String details = flowSubmitReq.getDetails();
		Flow1001DetailReq flow1001DetailReq = JsonUtils.fromJson(details, Flow1001DetailReq.class);	
		flow1001DetailReq.validate();
    	this.flow1001Detail = new Flow1001Detail(flow1001DetailReq);
    }
}
