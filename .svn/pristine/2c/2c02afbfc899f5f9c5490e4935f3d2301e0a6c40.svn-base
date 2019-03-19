package com.tenpay.wxwork.approval.presvr.sender.core;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpay.bap.relay.context.RelaySessionInfo;
import com.tenpay.wxwork.approval.presvr.common.biz.bean.BankInfo;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.sender.component.log.MessageLogService;

@Component
public class BankClientCommunicator {

    private static final Logger LG = LoggerFactory.getLogger(BankClientCommunicator.class);
    //ConfigUtils.getIntValue("Bank.Timeout")
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(5000).build();  
    
    /**
     * 日志service
     */
    @Resource
    protected MessageLogService logService;

    /**
     * 这里必须初始化xmlConvertService的context，因为这个对象是个多例
     */
    @PostConstruct
    public void init() throws JAXBException {
    	//
    }

    /**
     * @param sessionInfo
     * @param bankUrl
     * @Title: sendRequestToBank
     * @Description: 与银行通讯
     * @return: MessageSuit
     * @throws InnerException,Exception 
     */
    public String sendRequestToBank(BankInfo bankInfo, RelaySessionInfo sessionInfo, Object object, String bankUrl,String charset,StringBuilder responseCharset) throws InnerException{
		
    	if(null == bankInfo)
		{
			LG.error("Unsuported bank:");
			throw new InnerException(BizError.BANK_INFO_NOT_EXIST,"Bank info not exist.");
		}
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
    	CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(REQUEST_CONFIG).build();  
    	CloseableHttpResponse httpResponse;    	
    	String strResponse = null;
    	
    	if(null == bankUrl)
    	{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new InnerException(BizError.ILLEGAL_LOANURL_ERROR,"loan bank url illegal.");
		}
    	try
    	{
	    	String reqJsonString = mapper.writeValueAsString(object);
	    	//组http包
	    	HttpPost httpPost = buildRequestObject(sessionInfo, reqJsonString, bankUrl,charset);
	    	//发送请求
			httpResponse = httpClient.execute(httpPost);
			
	    	if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	    	{
		        HttpEntity entity = httpResponse.getEntity();   
		        if(entity != null)
		        {  
		        	String resCharset = getEntityContentEncoding(entity);
		        	responseCharset.append(resCharset);
		        	LG.debug("bank charset:" + resCharset);
		        	strResponse = EntityUtils.toString(entity, resCharset);      
		            LG.info("response from bank:" + strResponse);
					logService.logMessage(sessionInfo, String.format("response from bank:[%s]", strResponse));
			     }
	        }
	    	else
	    	{
			    // 如果请求失败
	    		LG.error("Failed to get response from bank."+httpResponse.getStatusLine());
	    		throw new InnerException(BizError.GET_BANK_RESPONSE_FAILED,"Get Bank response failed.");
	    	}
    	}
    	catch(InnerException e)
    	{
    		throw e;
    	}
    	catch (JsonProcessingException e) {
    		LG.error("JsonProcessingException:"+e);
    		throw new InnerException(BizError.OBJECT2STR_CONVERT_ERROR,"Object converted to jsonString error.");
        }
    	catch (ClientProtocolException e)
    	{
            LG.error("ClientProtocolException:" + e);
            throw new InnerException(BizError.CLIENT_PROTOCOL_ERROR,"Http client protocol error.");
        }
        catch (IOException e)
    	{
            LG.error("ClientProtocolException" + e);
            throw new InnerException(BizError.HTTP_IO_ERROR,"http io error.");
        }
    	catch(Exception e)
    	{
    		LG.error("sendRequestToBank error:" + e);
            throw new InnerException(BizError.REQUEST_LOANEE_ERROR,"http io error.");
    	}
    	finally
    	{
    		try
    		{
				httpClient.close();
			}
    		catch (IOException e)
    		{
    			LG.error("httpClient close error:" + e);
                throw new InnerException(BizError.HTTP_IO_ERROR,"httpClient close error.");
			}
    	}
    	return strResponse;
    }
    
    public HttpPost buildRequestObject(RelaySessionInfo sessionInfo, String entityString,String bankUrl,String charset) throws InnerException{
		LG.info("BankUrl=" + bankUrl);
		if(bankUrl.isEmpty())
		{
			LG.error("BankUrl is empty.");
			throw new InnerException(BizError.ILLEGAL_LOANURL_ERROR,"bank url is empty.");	
		}
    	//组装http请求报文
    	try{
			HttpPost httpPost = new HttpPost(bankUrl);
			String contentType = "application/json; charset="+charset;
			httpPost.setHeader("Content-Type", contentType);
			StringEntity stringEntity = new StringEntity(entityString, Charset.forName(charset));
			stringEntity.setContentType("text/json");
			httpPost.setEntity(stringEntity);
			
			logService.logMessage(sessionInfo, String.format("message sended to bank：[%s]", entityString));

			return httpPost;
		}
    	catch(IllegalArgumentException e)
    	{
    		LG.error("catch IllegalArgumentException in buildRequestObject.");
    		throw new InnerException(BizError.ILLEGAL_HTTARG_ERROR,"illegal http(s) argument");	
    	}
    }
    
    private static String getEntityContentEncoding(HttpEntity entity)  
    {  
    	/*
    	String defaultChaset = "UTF-8";
        Header header = entity.getContentType();
        HeaderElement[] hes = header.getElements();  
        for(HeaderElement he : hes)  
        {  
            for(NameValuePair p : he.getParameters())  
            {  
                if("input_charset".equals(p.getName()))  
                {  
                    return p.getValue();  
                }  
            }  
        }
        return defaultChaset;  */
    	return "UTF-8";
    }   
}
