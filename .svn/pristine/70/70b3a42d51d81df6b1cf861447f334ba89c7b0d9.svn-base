package com.tenpay.wxwork.approval.presvr.sender.bean;

import org.apache.commons.lang3.StringUtils;

import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class FlowPaymentQueryReq extends BankProxyRelayRequestMsg{

	private static final long serialVersionUID = 6196176837783687096L;
	
	private static final String BANK_APPID_KEY = "bank_appid";
	private static final String BANK_UIN_KEY = "bank_uin";
	private static final String CORP_ID_KEY = "corp_id";
	private static final String CORP_AUTH_ID_KEY = "corp_auth_id";
	private static final String FLOW_ID_KEY = "flow_id";
	private static final String APPROVAL_TYPE_KEY = "approval_type";
	private static final String ID_KEY = "id";

	
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
	//审批单标识
	private String id;
	
    public FlowPaymentQueryReq(BankProxyRelayRequestMsg msg) {
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
		this.corp_id = corp_id;
		this.put(CORP_ID_KEY, corp_id);
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
	public String getId() {
		this.id = this.get(ID_KEY);
		return id;
	}
	public void setId(String id) {
		this.put(ID_KEY, id);
		this.id = id;
	}
	private void validate()
	{
		if(StringUtils.isBlank(this.getBankAppid())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "bank_appid is empty.");
    	}
    	if(StringUtils.isBlank(this.getBankUin())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "bank_uin is empty.");
    	}
    	if(StringUtils.isBlank(this.getCorpAuthId())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "corp_auth_id is empty.");
    	}
    	if(StringUtils.isBlank(this.getFlowId())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "flow_id is empty.");
    	}
	}
}
