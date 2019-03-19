package com.tenpay.wxwork.approval.presvr.sender.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.reflect.TypeToken;
import com.tenpay.wxwork.approval.presvr.common.utils.JsonUtils;

public class FlowSubmitReqBodyBase {
    private static final Logger LG = LoggerFactory.getLogger(FlowSubmitReqBodyBase.class);

	//银行标识
	@JsonProperty("bank_appid")
	private String bank_appid;
	
	//银行登录帐号
	@JsonProperty("bank_uin")
	private String bank_uin;
	
	//企业标识
	@JsonProperty("corp_id")
	private String corp_id;
	//企业银行标识
	@JsonProperty("corp_auth_id")
	private String corp_auth_id;
	//审批流
	@JsonProperty("flow_id")
	private String flow_id;
	//审批单类型
	@JsonProperty("approval_type")
	private String approval_type;
	//审批流名称
	@JsonProperty("name")
	private String name;
	//记录条数
	@JsonProperty("count")
	private String count;
	//记录金额
	@JsonProperty("sum_amount")
	private String sum_amount;
	//审批人列表
	@JsonProperty("approval_name")
	private List<String> approval_name;
	//审批单状态
	@JsonProperty("status")
	private String status;
	//审批流列表
	@JsonProperty("approval_list")
	private List<Approval> approval_list;

	public FlowSubmitReqBodyBase(FlowSubmitReq flowSubmitReq){
		this.setBankAppid(flowSubmitReq.getBankAppid());
		this.setBankUin(flowSubmitReq.getBankUin());
		this.setCorpId(flowSubmitReq.getCorpId());
		this.setCorpAuthId(flowSubmitReq.getCorpAuthId());
		this.setFlowId(flowSubmitReq.getFlowId());
		this.setApprovalType(flowSubmitReq.getApprovalType());
		this.setName(flowSubmitReq.getName());
		this.setCount(flowSubmitReq.getCount());
		this.setSumAmount(flowSubmitReq.getSumAmount());
		this.setApprovalName(JsonUtils.fromJson(flowSubmitReq.getApprovalName(), (new TypeToken<List<String>>(){}).getType()));
		this.setStatus(flowSubmitReq.getStatus());
		this.parseJsonObject(flowSubmitReq);
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
	public String getCorpAuthId() {
		return corp_auth_id;
	}
	@JsonIgnore
	public void setCorpAuthId(String corp_auth_id) {
		this.corp_auth_id = corp_auth_id;
	}
	@JsonIgnore
	public String getFlowId() {
		return flow_id;
	}
	@JsonIgnore
	public void setFlowId(String flow_id) {
		this.flow_id = flow_id;
	}
	@JsonIgnore
	public String getApprovalType() {
		return approval_type;
	}
	@JsonIgnore
	public void setApprovalType(String approval_type) {
		this.approval_type = approval_type;
	}
	@JsonIgnore
	public String getName() {
		return name;
	}
	@JsonIgnore
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public String getCount() {
		return count;
	}
	@JsonIgnore
	public void setCount(String count) {
		this.count = count;
	}
	@JsonIgnore
	public String getSumAmount() {
		return sum_amount;
	}
	@JsonIgnore
	public void setSumAmount(String sum_amount) {
		this.sum_amount = sum_amount;
	}
	@JsonIgnore
	public List<String> getApprovalName() {
		return approval_name;
	}
	@JsonIgnore
	public void setApprovalName(List<String> approval_name) {
		this.approval_name = approval_name;
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
	public List<Approval> getApprovalList() {
		return approval_list;
	}
	@JsonIgnore
	public void setApprovalList(List<Approval> approval_list) {
		this.approval_list = approval_list;
	}
	
	private void parseJsonObject(FlowSubmitReq flowSubmitReq)
	{
    	String approvalList = flowSubmitReq.getApprovalList();
    	List<ApprovalReq> list = JsonUtils.fromJson(approvalList, (new TypeToken<List<ApprovalReq>>(){}).getType());
		approval_list = new ArrayList<Approval>();
		if (list != null && list.size() > 0){
			for(ApprovalReq element:list)
			{
				element.validate();
				Approval approval = new Approval(element);
				this.approval_list.add(approval);
			}
		}
	}
}
