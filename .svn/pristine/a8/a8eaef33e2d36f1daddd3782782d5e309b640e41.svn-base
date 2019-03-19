package com.tenpay.wxwork.approval.presvr.common.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenpay.key.api.KeyApi;
import com.tenpay.key.api.KeyException;
import com.tenpay.key.api.KeyInfo;
import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;
import com.tenpay.wxwork.approval.presvr.common.utils.RsaHandler;

public class KeyStoreService{
    /**
     * 银行证书存储路径
     */

    
	static String PriKeyHeader = "-----BEGIN PRIVATE KEY-----";
	static String PriKeyFooter = "-----END PRIVATE KEY-----";
    
    /**
     * 银行公钥
     */
    private static PublicKey bankPublicKey;

    /**
     * 财付通私钥
     */
    private static PrivateKey privateKey;
    /**
     * 财付通私钥
     */
    /**
     * 密钥管理系统api
     */
    private static KeyApi keyApi = new KeyApi();
    
	private static final Logger LG = LoggerFactory.getLogger(KeyStoreService.class);

    
    private static void loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {    
        try
        {  
        	privateKeyStr = privateKeyStr.replace(PriKeyHeader,"");
        	privateKeyStr = privateKeyStr.replace(PriKeyFooter,"");
    		Base64 base64 = new Base64();
    		byte[] decoded = base64.decode(privateKeyStr.getBytes("UTF-8"));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");    
    		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
    		privateKey = keyFactory.generatePrivate(keySpec);
        }
        catch (NoSuchAlgorithmException e)
        {    
            throw new Exception("无此算法");    
        }
        catch (InvalidKeySpecException e)
        {    
            throw new Exception("私钥非法");    
        }
        catch (NullPointerException e)
        {    
            throw new Exception("私钥数据为空");    
        }    
    }  

    private static void loadCertPublicKey(String certString)
    {
        if (StringUtils.isBlank(certString)) {
            throw new RuntimeException("certificate info is empty.");
        }
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream stream = new ByteArrayInputStream(certString.getBytes(StandardCharsets.UTF_8.name()));
            Certificate c2 = cf.generateCertificate(stream);
            bankPublicKey = c2.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(String.format("get bank public key error[%s] ", certString), e);
        }
    }

    public static PublicKey getBankPublicKey(String bankCrtId,int bankCrtVer) throws KeyException,InnerException {
    	KeyInfo key;
		LG.debug("certKeyId:" + bankCrtId);

    	try
    	{
    		key = keyApi.getNewestKey(bankCrtId, bankCrtVer);
    		//todo:delete debug
    		LG.debug("key:"+key.getValue());
    		loadCertPublicKey(key.getValue());
    	}
    	catch (KeyException e) {
    		LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
    		throw e;
    	}
    	catch(Exception e)
    	{
    		LG.error("Failed to get public key."+e);
    		throw new InnerException(BizError.GET_PUBLIC_ERROR,"Failed to get public key.");
		}
		return bankPublicKey;
    }

    public static PrivateKey encrptedData(String privateKeyId,int privateKeyVer) throws InnerException{
    	KeyInfo key;
		LG.debug("privateKeyId:"+privateKeyId);

    	try
    	{
    		key = keyApi.getNewestKey(privateKeyId, privateKeyVer);
    		loadPrivateKeyByStr(key.getValue());
    	}
    	catch (KeyException e) {
    		LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
    		throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
    	}
    	catch(Exception e)
    	{
    		LG.error("Failed to get private key."+e);
    		throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
		}
        return privateKey;
    }
    
    public static KeyInfo getNewestKey(String privateKeyId,int keyVer){
		try
		{
			KeyInfo key = keyApi.getNewestKey(privateKeyId, keyVer);
			return key;
		}
		catch (KeyException e) {
			LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
			throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
		}
    } 
    
    public static KeyInfo getNewestKey(String privateKeyId){
		try
		{
			KeyInfo key = keyApi.getNewestKey(privateKeyId, 1);
			return key;
		}
		catch (KeyException e) {
			LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
			throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
		}
    }
    public static PrivateKey getCftPrivateKey(KeyInfo key) throws InnerException{
		LG.debug("privateKeyId:"+key.getId());
    	try
    	{
    		loadPrivateKeyByStr(key.getValue());
    	}
    	catch (KeyException e) {
    		LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
    		throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
    	}
    	catch(Exception e)
    	{
    		LG.error("Failed to get private key."+e);
    		throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
		}
        return privateKey;
    }    
    public static PrivateKey getCftPrivateKey(String cftKeyId,int cftVer) throws InnerException,KeyException{
		
    	LG.debug("privateKeyId:"+cftKeyId);
	   	KeyInfo key;

    	try
    	{
    		key = keyApi.getNewestKey(cftKeyId, cftVer);
    		//todo:delete debug
    		//LG.debug("key:"+key.getValue());
    		loadPrivateKeyByStr(key.getValue());
    	}
    	catch (KeyException e) {
    		LG.error("getNewestkey error."+e.getErrorCode()+e.getErrorMessage());
    		throw e;
    	}
    	catch(Exception e)
    	{
    		LG.error("Failed to get private key."+e);
    		throw new InnerException(BizError.GET_PRIKEY_ERROR,"Failed to get private key.");
		}
        return privateKey;
    }
    
    public static String sign(String cftKeyId,int cftVer, String signSrc,String charset) throws InnerException, KeyException{
    	PrivateKey cftPrivateKey = getCftPrivateKey(cftKeyId,cftVer);
    	return RsaHandler.genSign(signSrc, cftPrivateKey, charset);
    }
   
    public static boolean verifySign(String bankCrtId, int bankCrtVer, String signSrc, String signature, String charset) throws KeyException,InnerException{
    	PublicKey bankPublicKey = getBankPublicKey(bankCrtId,bankCrtVer);
    	boolean verifyResult = false;
    	try {
    		verifyResult = RsaHandler.verifySign(signSrc, signature, bankPublicKey, charset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LG.error("Failed to verify sign,sign_src:"+signSrc+"signature:"+signature);
    		throw new InnerException(BizError.VERIFY_SIGN_ERROR,"Failed to verify sign.");		}
    	return verifyResult;
    }
    public static String encrypt(String bankCrtId,int bankCrtVer, String plainTextData,String charset) throws InnerException, KeyException{
    	PublicKey bankPublicKey = getBankPublicKey(bankCrtId,bankCrtVer);
    	
    	return RsaHandler.encrypt(bankPublicKey,plainTextData, charset);
    } 
    
    public static String decrypt(String privateKeyId,int privateKeyVer, String encrptedData, String charset) throws InnerException, KeyException{
    	PrivateKey bankPrivateKey = getCftPrivateKey(privateKeyId,privateKeyVer);
    	return RsaHandler.decrypt(bankPrivateKey,encrptedData,charset);
    }
    
}
