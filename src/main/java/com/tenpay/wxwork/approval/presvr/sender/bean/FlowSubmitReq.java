package com.tenpay.wxwork.approval.presvr.sender.bean;

import org.apache.commons.lang3.StringUtils;

import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class FlowSubmitReq extends BankProxyRelayRequestMsg{

	private static final long serialVersionUID = 6852778978780565487L;
	
	private static final String BANK_APPID_KEY = "bank_appid";
	private static final String BANK_UIN_KEY = "bank_uin";
	private static final String CORP_ID_KEY = "corp_id";
	private static final String CORP_AUTH_ID_KEY = "corp_auth_id";
	private static final String FLOW_ID_KEY = "flow_id";
	private static final String APPROVAL_TYPE_KEY = "approval_type";
	private static final String NAME_KEY = "name";
	private static final String COUNT_KEY = "count";
	private static final String SUM_AMOUNT_KEY = "sum_amount";
	private static final String APPROVAL_NAME_KEY = "approval_name";
	private static final String STATUS_KEY = "status";
	private static final String DETAILS_KEY = "details";
	private static final String APPROVAL_LIST_KEY = "approval_list";

	//银行标识
	private String bank_appid;
	//银行登录帐号
	private String bank_uin;
	//企业标识
	private String corp_id;
	//企业银行标识
	private String corp_auth_id;
	//审批流
	private String flow_id;
	//审批单类型
	private String approval_type;
	//审批流名称
	private String name;
	//记录条数
	private String count;
	//记录金额
	private String sum_amount;
	//审批人列表
	private String approval_name;
	//审批单状态
	private String status;
	//审批详情
	private String details;
	//审批流列表
	private String approval_list;
	//审批流类型
	private String flow_type;
	
	
    public FlowSubmitReq(BankProxyRelayRequestMsg msg) {
        super(msg.getRawStr());
        //设置初始值
        this.validate();
    }
	public String getBankAppid() {
		this.bank_appid = this.get(BANK_APPID_KEY);
		return bank_appid;
	}
	
	public void setBankAppid(String bank_appid) {
        this.put(BANK_APPID_KEY, bank_appid);
		this.bank_appid = bank_appid;
	}
		
	public String getBankUin() {
		this.bank_uin = this.get(BANK_UIN_KEY);
		return bank_uin;
	}

	public void setBankUin(String bank_uin) {
		this.put(BANK_UIN_KEY,bank_uin);
		this.bank_uin = bank_uin;
	}

	
	public String getCorpId() {
		this.corp_id = this.get(CORP_ID_KEY);
		return corp_id;
	}
	
	public void setCorpId(String corp_id) {
		this.put(CORP_ID_KEY, corp_id);
		this.corp_id = corp_id;
	}

	public String getCorpAuthId() {
		this.corp_auth_id = this.get(CORP_AUTH_ID_KEY);
		return corp_auth_id;
	}
	public void setCorpAuthId(String corp_auth_id) {
		this.put(CORP_AUTH_ID_KEY, corp_auth_id);
		this.corp_auth_id = corp_auth_id;
	}
	public String getFlowId() {
		this.flow_id = this.get(FLOW_ID_KEY);
		return flow_id;
	}
	public void setFlowId(String flow_id) {
		this.put(FLOW_ID_KEY, flow_id);
		this.flow_id = flow_id;
	}
	public String getApprovalType() {
		this.approval_type = this.get(APPROVAL_TYPE_KEY);
		return approval_type;
	}
	public void setApprovalType(String approval_type) {
		this.put(APPROVAL_TYPE_KEY, approval_type);
		this.approval_type = approval_type;
	}
	public String getName() {
		this.name = this.get(NAME_KEY);
		return name;
	}
	public void setName(String name) {
		this.put(NAME_KEY, name);
		this.name = name;
	}
	public String getCount() {
		this.count = this.get(COUNT_KEY);
		return count;
	}
	public void setCount(String count) {
		this.put(COUNT_KEY,count);
		this.count = count;
	}
	public String getSumAmount() {
		this.sum_amount = this.get(SUM_AMOUNT_KEY);
		return sum_amount;
	}
	public void setSumAmount(String sum_amount) {
		this.put(SUM_AMOUNT_KEY,sum_amount);
		this.sum_amount = sum_amount;
	}
	public String getApprovalName() {
		this.approval_name = this.get(APPROVAL_NAME_KEY);
		return approval_name;
	}
	public void setApprovalName(String approval_name) {
		this.put(APPROVAL_NAME_KEY, approval_name);
		this.approval_name = approval_name;
	}
	public String getDetails() {
		this.details = this.get(DETAILS_KEY);
		return details;
	}
	public void setDetails(String details) {
		this.put(DETAILS_KEY, details);
		this.details = details;
	}
	public String getApprovalList() {
		this.approval_list = this.get(APPROVAL_LIST_KEY);
		return approval_list;
	}
	public void setApprovalList(String approval_list) {
		this.put(APPROVAL_LIST_KEY, approval_list);
		this.approval_list = approval_list;
	}
	public String getStatus() {
		this.status = this.get(STATUS_KEY);
		return status;
	}
	
	public void setStatus(String status) {
		this.put(STATUS_KEY,status);
		this.status = status;
	}
	
	public String getFlowType() {
		return flow_type;
	}
	public void setFlowType(String flow_type) {
		this.flow_type = flow_type;
	}
	private void validate()
	{
		if(StringUtils.isBlank(this.getBankAppid())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "bank_appid is empty.");
    	}
    	if(StringUtils.isBlank(this.getBankUin())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "bank_uin is empty.");
    	}
    	if(StringUtils.isBlank(this.getCorpId())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "corp_id is empty.");
    	}
    	if(StringUtils.isBlank(this.getFlowId())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "flow_id is empty.");
    	}
    	if(StringUtils.isBlank(this.getApprovalType())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "approval_type is empty.");
    	}
    	if(StringUtils.isBlank(this.getName())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "name is empty.");
    	}
    	if(this.getCount() != null && Integer.parseInt(this.getCount()) == 0){
        	if(StringUtils.isBlank(this.getApprovalList())){
        		throw new InnerException(BizError.PARAM_NOT_EXIST, "approval_list is empty.");
        	} 
        } 		 	
	}
}
