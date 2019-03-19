package com.tenpay.wxwork.approval.presvr.sender.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Approval {
	//审批单标识
	@JsonProperty("id")
	private String id;
	//申请人姓名
	@JsonProperty("apply_name")
	private String apply_name;
	//提交时间
	@JsonProperty("apply_time")
	private String apply_time;
	//收款户名
	@JsonProperty("receive_name")
	private String receive_name;
	//收款帐户
	@JsonProperty("receive_account")
	private String receive_account;
	//付款金额
	@JsonProperty("total_fee")
	private long total_fee;
	//开户行
	@JsonProperty("account_bank")
	private String account_bank;
	//开户行所在地
	@JsonProperty("account_area")
	private String account_area;
	//开户行支行联行号
	@JsonProperty("account_subbank")
	private String account_subbank;
	//开户行支行所在城市
	@JsonProperty("account_city")
	private String account_city;
	//开户行支行联行号
	@JsonProperty("account_bank_code")
	private String account_bank_code;
	//开户行支行所在城市
	@JsonProperty("account_subbank_code")
	private String account_subbank_code;
	//是否跨行标记
	@JsonProperty("cross_flag")
	private int cross_flag;
	//账户类型
	@JsonProperty("account_type")
	private int account_type;
	
	public Approval(ApprovalReq approvalReq){
		this.setId(approvalReq.getId());
		this.setApplyName(approvalReq.getApplyName());
		this.setApplyTime(approvalReq.getApplyTime());
		this.setReceiveName(approvalReq.getReceiveName());
		this.setReceiveAccount(approvalReq.getReceiveAccount());
		this.setTotalFee(approvalReq.getTotalFee());
		this.setAccountBank(approvalReq.getAccountBank());
		this.setAccountArea(approvalReq.getAccountArea());
		this.setAccountSubBank(approvalReq.getAccountSubBank());
		this.setAccountCity(approvalReq.getAccountCity());
		this.setAccountBankCode(approvalReq.getAccountBankCode());
		this.setAccountSubbankCode(approvalReq.getAccountSubbankCode());
		this.setCrossFlag(approvalReq.getCrossFlag());
		this.setAccountType(approvalReq.getAccountType());
	}
	
	@JsonIgnore
	public int getAccountType() {
		return account_type;
	}
	@JsonIgnore
	public void setAccountType(int account_type) {
		this.account_type = account_type;
	}
	@JsonIgnore
	public String getId() {
		return id;
	}
	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}
	@JsonIgnore
	public String getApplyName() {
		return apply_name;
	}
	@JsonIgnore
	public void setApplyName(String apply_name) {
		this.apply_name = apply_name;
	}
	@JsonIgnore
	public String getApplyTime() {
		return apply_time;
	}
	@JsonIgnore
	public void setApplyTime(String apply_time) {
		this.apply_time = apply_time;
	}
	@JsonIgnore
	public String getReceiveName() {
		return receive_name;
	}
	@JsonIgnore
	public void setReceiveName(String receive_name) {
		this.receive_name = receive_name;
	}
	@JsonIgnore
	public String getReceiveAccount() {
		return receive_account;
	}
	@JsonIgnore
	public void setReceiveAccount(String receive_account) {
		this.receive_account = receive_account;
	}
	@JsonIgnore
	public long getTotalFee() {
		return total_fee;
	}
	@JsonIgnore
	public void setTotalFee(long total_fee) {
		this.total_fee = total_fee;
	}
	@JsonIgnore
	public String getAccountBank() {
		return account_bank;
	}
	@JsonIgnore
	public void setAccountBank(String account_bank) {
		this.account_bank = account_bank;
	}
	@JsonIgnore
	public String getAccountArea() {
		return account_area;
	}
	@JsonIgnore
	public void setAccountArea(String account_area) {
		this.account_area = account_area;
	}
	@JsonIgnore
	public String getAccountSubBank() {
		return account_subbank;
	}
	@JsonIgnore
	public void setAccountSubBank(String account_subbank) {
		this.account_subbank = account_subbank;
	}
	@JsonIgnore
	public String getAccountCity() {
		return account_city;
	}
	@JsonIgnore
	public void setAccountCity(String account_city) {
		this.account_city = account_city;
	}
	@JsonIgnore
	public String getAccountBankCode() {
		return account_bank_code;
	}
	@JsonIgnore
	public void setAccountBankCode(String account_bank_code) {
		this.account_bank_code = account_bank_code;
	}
	@JsonIgnore
	public String getAccountSubbankCode() {
		return account_subbank_code;
	}
	@JsonIgnore
	public void setAccountSubbankCode(String account_subbank_code) {
		this.account_subbank_code = account_subbank_code;
	}
	@JsonIgnore
	public int getCrossFlag() {
		return cross_flag;
	}
	@JsonIgnore
	public void setCrossFlag(int cross_flag) {
		this.cross_flag = cross_flag;
	}
}
