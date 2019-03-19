package com.tenpay.wxwork.approval.presvr.common.biz.bean;

public class BankInfo {
	
	private int bankType;
	
	private String BankName;
	
	private String bankCrtId;
	
	private int bankCrtVer;
	
	private int cftVer;

	private String cftCrtId;
		
	private String cftKeyId;
	
	public int getBankType() {
		return bankType;
	}
	public void setBankType(int bankType) {
		this.bankType = bankType;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getBankCrtId() {
		return bankCrtId;
	}
	public void setBankCrtId(String bankCrtId) {
		this.bankCrtId = bankCrtId;
	}
	public String getCftCrtId() {
		return cftCrtId;
	}
	public void setCftCrtId(String cftCrtId) {
		this.cftCrtId = cftCrtId;
	}
	public String getCftKeyId() {
		return cftKeyId;
	}
	public void setCftKeyId(String cftKeyId) {
		this.cftKeyId = cftKeyId;
	}
	public int getBankCrtVer() {
		return bankCrtVer;
	}
	public void setBankCrtVer(int bankCrtVer) {
		this.bankCrtVer = bankCrtVer;
	}
	public int getCftVer() {
		return cftVer;
	}
	public void setCftVer(int cftVer) {
		this.cftVer = cftVer;
	}
}
