package com.tenpay.wxwork.approval.presvr.common.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfos;

public class ErrorModuleConvert {

public static int moduleCode = 0;
public static final int minCode = 0;
public static final int maxCode = 9999;
private static ErrorModuleConvert ErrorModuleConvertInstance = new ErrorModuleConvert();
private static final Logger LG = LoggerFactory.getLogger(ErrorModuleConvert.class);

private ErrorModuleConvert(){}

public static ErrorModuleConvert getInstance(){
	return ErrorModuleConvertInstance;
}
public void init(int moduleCode)
{
	ErrorModuleConvert.moduleCode = moduleCode;
}

public static int toModuleError(int error)
{
    if ((error >= minCode && error <= maxCode))
    {
    	LG.debug("ERROR:"+error+"moduleCode:"+Integer.toString(ErrorModuleConvert.moduleCode * 10000 + error));
        return ErrorModuleConvert.moduleCode * 10000 + error;
    }

    return error;    
}
}
