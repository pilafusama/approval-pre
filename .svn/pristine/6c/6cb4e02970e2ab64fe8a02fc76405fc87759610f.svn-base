package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tenpay.wxwork.approval.presvr.common.utils.JsonUtils;

public class FlowSubmit1002ReqBody extends FlowSubmitReqBodyBase{
	//审批详情
	@JsonProperty("details")
	private Flow1002Detail flow1002Detail;

	public FlowSubmit1002ReqBody(FlowSubmitReq flowSubmitReq){
		super(flowSubmitReq);
		this.parseJsonObject(flowSubmitReq);
	}
	@JsonIgnore
    public Flow1002Detail getFlow1002Detail() {
		return flow1002Detail;
	}
	@JsonIgnore
	public void setFlow1002Detail(Flow1002Detail flow1002Detail) {
		this.flow1002Detail = flow1002Detail;
	}

	protected void parseJsonObject(FlowSubmitReq flowSubmitReq) {
		String details = flowSubmitReq.getDetails();
		Flow1002DetailReq flow1002DetailReq = JsonUtils.fromJson(details, Flow1002DetailReq.class);
		if (flow1002DetailReq != null){
			flow1002DetailReq.validate();
			this.flow1002Detail = new Flow1002Detail(flow1002DetailReq);
		}
    }
}
