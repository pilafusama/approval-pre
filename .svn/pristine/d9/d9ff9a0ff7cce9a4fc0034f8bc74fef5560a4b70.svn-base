package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.tenpay.bap.relay.protocol.BankProxyRelayResponseMsg;

public class CorpBindConfirmRes extends BankProxyRelayResponseMsg {

	private static final long serialVersionUID = 1215933782793607099L;
	private static final String CORP_AUTH_ID_KEY = "corp_auth_id";
	private static final String RETCODE_KEY = "retcode";
	private static final String ERRMSG_KEY = "errmsg";
	
	//企业绑定id
    private String corp_auth_id; 
    //业务结果状态码
	private String retcode; 
	//结果说明
	private String errmsg;

    public CorpBindConfirmRes(int result, String resInfo,String bankResult, String bankResInfo) {
        super(result, resInfo,bankResult,bankResInfo);
    } 
    public void setResponse(String corpAuthId,String retcode,String errMsg) {
    	setCorpAuthId(corpAuthId);
    	setRetcode(retcode);
    	setErrmsg(errMsg);
    }
	public String getCorpAuthId() {
		this.corp_auth_id = this.get(CORP_AUTH_ID_KEY);
		return corp_auth_id;
	}
	public void setCorpAuthId(String corp_auth_id) {
		this.put(CORP_AUTH_ID_KEY, corp_auth_id);
		this.corp_auth_id = corp_auth_id;
	}
	public String getRetcode() {
		this.retcode = this.get(RETCODE_KEY);
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.put(RETCODE_KEY,retcode);
		this.retcode = retcode;
	}
	public String getErrmsg() {
		this.errmsg = this.get(ERRMSG_KEY);
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.put(ERRMSG_KEY, errmsg);
		this.errmsg = errmsg;
	} 
}
