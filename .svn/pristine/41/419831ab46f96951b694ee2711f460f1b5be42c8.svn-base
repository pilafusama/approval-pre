package com.tenpay.wxwork.approval.presvr.sender.bean;

import org.apache.commons.lang3.StringUtils;

import com.tenpay.bap.relay.protocol.BankProxyRelayRequestMsg;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class CorpBindAuthenReq extends BankProxyRelayRequestMsg{

	private static final long serialVersionUID = 6196176837783687096L;
	
	private static final String BANK_APPID_KEY = "bank_appid";
	private static final String BANK_UIN_KEY = "bank_uin";
	private static final String CORP_ID_KEY = "corp_id";
	private static final String CORP_NAME_KEY = "corp_name";
	private static final String CERT_ID_KEY = "cert_id";
	private static final String OP_USER_ID_KEY = "op_user_id";
	private static final String OP_USER_PHONE_KEY = "op_user_phone";
	private static final String STATUS_KEY = "status";
	
	//银行标识
	private String bank_appid;
	//银行登录帐号
	private String bank_uin;
	//企业标识
	private String corp_id;
	//企业名称
	private String corp_name;
	//证件号
	private String cert_id;
	//操作员标识
	private String op_user_id;
	//操作员手机号
	private String op_user_phone;
	//业务状态
	private String status;
	
    public CorpBindAuthenReq(BankProxyRelayRequestMsg msg) {
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

	public String getCorpName() {
		this.corp_name = this.get(CORP_NAME_KEY);
		return corp_name;
	}

	public void setCorpName(String corp_name) {
		this.corp_name = corp_name;
		this.put(CORP_NAME_KEY,corp_name);
	}
	
	public String getCertId() {
		this.cert_id = this.get(CERT_ID_KEY);
		return cert_id;
	}

	public void setCertId(String cert_id) {
		this.put(CERT_ID_KEY,cert_id);
		this.cert_id = cert_id;
	}

	public String getOpUserId() {
		this.op_user_id = this.get(OP_USER_ID_KEY);
		return op_user_id;
	}
	
	public void setOpUserId(String op_user_id) {
		this.put(OP_USER_ID_KEY, op_user_id);
		this.op_user_id = op_user_id;
	}
	public String getOpUserPhone() {
		this.op_user_phone = this.get(OP_USER_PHONE_KEY);
		return op_user_phone;
	}
	public void setOpUserPhone(String op_user_phone) {
		this.put(OP_USER_PHONE_KEY, op_user_phone);
		this.op_user_phone = op_user_phone;
	}
	public String getStatus() {
		this.status = this.get(STATUS_KEY);
		return status;
	}
	public void setStatus(String status) {
		this.put(STATUS_KEY,status);
		this.status = status;
	}
	
	private void validate()
	{
		if(StringUtils.isBlank(this.getBankAppid())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "bank_appid is empty.");
    	}
    	if(StringUtils.isBlank(this.getBankUin())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "ban_uin is empty.");
    	}
    	if(StringUtils.isBlank(this.getCorpId())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "corp_id is empty.");
    	}
    	if(StringUtils.isBlank(this.getCorpName())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "corp_name is empty.");
    	}
    	if(StringUtils.isBlank(this.getStatus())){
    		throw new InnerException(BizError.PARAM_NOT_EXIST, "status is empty.");
    	}
	}
}
