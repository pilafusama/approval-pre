package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenpay.wxwork.approval.presvr.common.utils.JsonUtils;

public class FlowSubmit1003ReqBody extends FlowSubmitReqBodyBase{
	//审批详情
	@JsonProperty("details")
	private Flow1003Detail flow1003Detail;

	public FlowSubmit1003ReqBody(FlowSubmitReq flowSubmitReq){
		super(flowSubmitReq);
		this.doDecrypt(flowSubmitReq);
	}
	@JsonIgnore
    public Flow1003Detail getFlow1003Detail() {
		return flow1003Detail;
	}
	@JsonIgnore
	public void setFlow1003Detail(Flow1003Detail flow1003Detail) {
		this.flow1003Detail = flow1003Detail;
	}

	protected void doDecrypt(FlowSubmitReq flowSubmitReq) {
		String details = flowSubmitReq.getDetails();
		Flow1003DetailReq flow1003DetailReq = JsonUtils.fromJson(details, Flow1003DetailReq.class);
		if (flow1003DetailReq != null){
			flow1003DetailReq.validate();
			this.flow1003Detail = new Flow1003Detail(flow1003DetailReq);
		}
    }
}
