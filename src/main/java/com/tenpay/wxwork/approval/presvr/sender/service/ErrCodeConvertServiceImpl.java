package com.tenpay.wxwork.approval.presvr.sender.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tenpay.bap.common.utils.CharsetUitls;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.sender.common.CftErrorBean;

/**
 * Created by Sean Lei on 30/11/2016.
 */
@Service
public class ErrCodeConvertServiceImpl implements ErrCodeConvertService {
    private static final Logger LG = LoggerFactory.getLogger(ErrCodeConvertService.class);

    private final static String LOAD_ERROR_FILE = "ErrorCode.xml";

    /**
     * 用于存储银行对应错误码映射
     */
    private Map<String, Map<String, CftErrorBean>> bankErrors = new HashMap<String, Map<String, CftErrorBean>>();

    @PostConstruct
    public void load() {
        this.loadBankErrors();
    }

    @SuppressWarnings("unchecked")
	private void loadBankErrors() {
        ClassPathResource fileSource = new ClassPathResource(LOAD_ERROR_FILE);
        if (!fileSource.exists()) {
            LG.warn(LOAD_ERROR_FILE + " file not exists");
            return;
        }
        //加载错误码文件XML
        Element root;
        try {
            //使用指定编码读取文件，避免传入InputStream到SAX解析使用不固定编码解码造成乱码
            String content = IOUtils.toString(fileSource.getInputStream(), CharsetUitls.UTF_8);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(content)); //构造文档对象

            root = doc.getRootElement(); //获取根元素
            if (root != null) {
                //遍历Bank节点
                for (Element itembank : (List<Element>) root.getChildren("bankType")) {
                	String bankType = itembank.getAttributeValue("name");
                	LG.debug(bankType);
               	 	Map<String, CftErrorBean> errors= new HashMap<String, CftErrorBean>();

                    List itembankErrors = itembank.getChildren();

                    for (Element item : (List<Element>) itembankErrors) {
	                    String bankErrorCode = item.getChildTextTrim("bankCode");
	                    String cftErrorCode = item.getChildTextTrim("cftCode");
	                    String comment = item.getChildTextTrim("comment");
	                    String bankcomment = item.getChildTextTrim("bankcomment");
	                    LG.debug("bankErrorCode:"+bankErrorCode);
	                    LG.debug("cftErrorCode:"+cftErrorCode);
	                    LG.debug("comment:"+comment);
	                    LG.debug("bankcomment:"+bankcomment);

	                    if (cftErrorCode != null && cftErrorCode.matches("[0-9]*")) {
	                        Integer cftCode = Integer.parseInt(cftErrorCode.trim());
	                        CftErrorBean cfterrorbean = new CftErrorBean(cftCode, bankErrorCode, comment, bankcomment);
	                        errors.put(bankErrorCode, cfterrorbean);
	                    } else {
	                        LG.warn("解析错误码 出错:");
	                    }
                    }
                    bankErrors.put(bankType, errors);
                }
            }
        } catch (IOException e) {
            LG.warn("初始化错误码 出错:" + LOAD_ERROR_FILE);
        } catch (JDOMException e) {
            LG.warn("初始化错误码 出错:" + LOAD_ERROR_FILE);
        }
    }

    /**
     * 根据传入的RA错误码、RA错误信息找到匹配的财付通错误码
     * @param errorCode   RA错误码
     * @param errorMsg    RA错误信息
     * @return
     */
    @Override
    public int getConverterErrorCode(String errorCode, String errorMsg,String bankType) {

        CftErrorBean bean = getConverterErrorBean(errorCode, errorMsg,bankType);
        return bean.getCftcode();
    }

    /**
     * 根据传入的银行类型、银行错误码、银行错误信息找到匹配的财付通错误码
     * @param errorCode   RA错误码
     * @param errorMsg    RA错误信息
     * @return
     */
    @Override
    public CftErrorBean getConverterErrorBean(String errorCode, String errorMsg,String bankType) {
        //先根据错误码找规则
        CftErrorBean bean = getCftErrorCodeBaseByMap(errorCode,bankType);

        if (bean.getCftcode() == BizError.NO_FIND_ERROR.errorCode()) {
            errorCode = errorCode == null ? "" : errorCode;
            errorMsg = errorMsg == null ? "" : errorMsg;
        }

        if (bean != null) {
            bean.setBankcode(errorCode);
            bean.setBankdesc(errorMsg);
        }

        return bean;
    }

    private CftErrorBean getCftErrorCodeBaseByMap(String bakErrorCode,String bankType) {
    	LG.debug("bakErrorCode:"+bakErrorCode);
        CftErrorBean deft = new CftErrorBean(BizError.NO_FIND_ERROR.errorCode(), null, "未匹配到错误码", null);
        if (bakErrorCode == null) {
            return deft;
        }

        if (bankErrors != null && bankErrors.size() != 0) {
        	//1先查找通用错误码
        	LG.debug("bankErrors,size:"+bankErrors.size());

            if(bankErrors.containsKey("common") && bankErrors.get("common")!= null && bankErrors.get("common").size() != 0)
            {
            	LG.debug("common");
                CftErrorBean bean = bankErrors.get("common").get(bakErrorCode);
                if (bean != null) {
                    return bean;
                }
            }
            //2再查找银行对应错误码
            if(bankErrors.containsKey(bankType) && bankErrors.get(bankType)!= null && bankErrors.get(bankType).size() != 0)
            {
            	LG.debug("bankType:"+bankType);
	            CftErrorBean bean = bankErrors.get(bankType).get(bakErrorCode);
	            if (bean != null) {
	                return bean;
	            }
            }
        }
        else
        {
        	load();
        }
        return deft;
    }

}
