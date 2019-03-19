package com.tenpay.wxwork.approval.presvr.common.utils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class RsaHandler {
	
	private static final Logger LG = LoggerFactory.getLogger(RsaHandler.class);
	//加密时分组
	private static final int ENCRYPT_BLOCK = 245;//256-11分组加密
	//密钥长度
	public static final int KEYSIZE = 2048;
    //解密时必须按照此分组解密
    public static final int DECRYPT_BLOCK = KEYSIZE / 8;
	
	public static String getCharset(Map<String,Object> map)
	{
		String charset = "";
		if(map.containsKey("input_charset"))
		{
			charset = map.get("input_charset").toString();
		}
		if(charset.isEmpty())
		{
			charset = "GBK";
		}
		return charset;
	}
	
    // gen signsrc，由map得到签名源串,map需排序过
    public static String getSignSrc(Map<String, Object> map) {
        // 构造签名键值对的格式  
        StringBuilder stringBuider = new StringBuilder(); 
		Set<Map.Entry<String, Object>> entrySet = map.entrySet();
		Boolean isFirst = true;
        for (Map.Entry<String, Object> entry : entrySet) {
			String mapKey = entry.getKey();
        	if (! mapKey.isEmpty() && !mapKey.equals("sign")) {
				Object val = entry.getValue();
                if ((val != null) && !(val.toString().isEmpty())) {
                	if(!isFirst)
                	{
                		stringBuider.append("&");		
                	}
                	stringBuider.append(mapKey + "=" + val);
                	isFirst = false;
                }  
            }  
        }  
        return stringBuider.toString(); 
    } 	
	
	/*生成签名*/
	public static String genSign(String signSrc, PrivateKey priKey,String charset) throws InnerException
	{
		String encodeSign;
		try
		{
		    // 签名
		    Signature signature = Signature.getInstance("SHA256WithRSA");
		    signature.initSign(priKey);
		    signature.update(signSrc.getBytes(charset));

		    byte[] sign = signature.sign();
			if (sign.length == 0) {
				LG.error("Genenate sign error.sign,length is 0.");
				throw new InnerException(BizError.GEN_SIGN_ERROR,"Genenate sign error.");
			}
			LG.debug("sign length:"+Integer.toString(sign.length));
			encodeSign = new String(Base64.encodeBase64(sign),"UTF-8");

			if (encodeSign.length() == 0) {
				LG.error("Base64 encode sign error.sign,length is 0.");
				throw new InnerException(BizError.ENCODE_SIGN_ERROR,"ENCODE sign error.");
			}
		 }
		 catch(InnerException e)
		 {
			 throw e;
		 }
		 catch(Exception e)
		 {
			 throw new InnerException(BizError.GEN_SIGN_ERROR,"Genenate sign error.");
		 }
	     return encodeSign;
	}
	
	/*生成签名*/
	public static String genSign(Map<String, Object> map, PrivateKey priKey) throws InnerException
	{
		String signSrc = getSignSrc(map);
		LG.info("signSrc:"+signSrc);
		
		String charset = getCharset(map);
		return genSign(signSrc, priKey,charset);
	}
	 /** 
     * 验证签名 
     *  
     * @param map 签名原始map，调用方需确认排序方式      
     * @param encodedSign 签名         
     * @return pubKey 与签名私钥配对的公钥
     */ 
	public static boolean verifySignMap(Map<String, Object> map,String encodedSign, PublicKey pubKey) throws InnerException
	{
		String signSrc;
		try
		{	
			signSrc = getSignSrc(map);
			LG.info("signSrc:"+signSrc);
			LG.info("charset:"+getCharset(map));
			return verifySign(signSrc, encodedSign, pubKey, getCharset(map));
		}
		catch(Exception e)
		{
		    LG.error("Verify sign error.");
			throw new InnerException(BizError.VERIFY_SIGN_ERROR,"failed to verify bank sign.");
		}
		
	}
	
	 /** 
     * 验证签名 
     *  
     * @param signSrc 签名源串       
     * @param encodedSign 签名         
     * @return pubKey 与签名私钥配对的公钥
     */ 
	public static boolean verifySign(String signSrc,String encodedSign, PublicKey pubKey,String charset) throws Exception
	{
		try
		{	
			if(encodedSign.isEmpty())
			{
				LG.error("Sign field is empty.");
				throw new Exception("Sign field is empty");
			}
			else
			{
				byte[]  decodedSign = Base64.decodeBase64(encodedSign.getBytes("UTF-8"));

				if(0 == decodedSign.length)
				{
					LG.error("decode sign error.");
					throw new Exception("decode sign error.");
				}
								
				//验证签名
				Signature signature = Signature.getInstance("SHA256WithRSA");
		    	signature.initVerify(pubKey);
		    	signature.update(signSrc.getBytes(charset));
		    	//decodeSign
		    	boolean verifyResult = signature.verify(decodedSign);
		    	return verifyResult;
			}
		 }
		 catch(Exception e)
		 {
			 LG.error("Verify sign error."+e);
			 throw e;
		 }	
	}
	
	   /** 
     * 私钥加密过程 
     *  
     * @param publicKey
     *            私钥 
     * @param plainTextData 
     *            明文数据 
     * @return 
     * @throws Exception 
     *             加密过程中的异常信息 
     */  
	public static String encrypt(PublicKey publicKey, String plainTextData,String charset) throws InnerException{  
	    if (publicKey == null)
	    {  
	    	LG.error("加密私钥为空.");
	        throw new InnerException(BizError.INVALID_KEY_ERROR,"加密私钥为空");  
		}  
		Cipher cipher = null; 
		
		try
		{  			
			byte[] bytes = plainTextData.getBytes(charset);
	        byte[] encryptedBytes = new byte[] {};
		    // 使用默认RSA 
			for(int encryptedLength = 0; encryptedLength < bytes.length;encryptedLength =encryptedLength + ENCRYPT_BLOCK)
			{
				byte[] subarray = ArrayUtils.subarray(bytes, encryptedLength, encryptedLength + ENCRYPT_BLOCK);
				cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
			    cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
			    byte[] subEncryptedData = cipher.doFinal(subarray); 
			    encryptedBytes = ArrayUtils.addAll(encryptedBytes,subEncryptedData);
			}
			
		    String encryptMsg = new String(Base64.encodeBase64(encryptedBytes),charset);

		    return encryptMsg;  
		}
		catch (NoSuchAlgorithmException e)
		{
			LG.error("catch NoSuchAlgorithmException");
			throw new InnerException(BizError.SIGN_TYPE_ERROR,"无此加密算法.");
		}
		catch (NoSuchPaddingException e)
		{  
			LG.error("catch NoSuchPaddingException.");
			throw new InnerException(BizError.PADDING_TYPE_ERROR,"No such padding.");
		} catch (InvalidKeyException e)
		{  
			LG.error("catch InvalidKeyException.");
		    throw new InnerException(BizError.INVALID_KEY_ERROR,"The public key is invalid.");  
		} catch (IllegalBlockSizeException e)
		{  
			LG.error("catch IllegalBlockSizeException.");
		    throw new InnerException(BizError.ILLEGAL_BLOCK_ERROR,"The blocksize of plainmsg is error.");  
		} catch (BadPaddingException e)
		{ 
			LG.error("明文数据已损坏.");
		    throw new InnerException(BizError.BAD_PADDING_ERROR,"明文数据已损坏.");  

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			LG.error("UnsupportedEncodingException.");
			throw new InnerException(BizError.UNSUPPORTED_ENCODING_ERROR,"不支持的签名类型.");  
		}  
	}
    /** 
     * 公钥解密过程 
     *  
     * @param privateKey
     *            私钥 
     * @param cipherData 
     *            密文数据 
     * @return 明文 
     * @throws Exception 
     *             解密过程中的异常信息 
     */  
    public static String decrypt(PrivateKey privateKey, String cipherData, String charset) throws InnerException{  
        if (privateKey == null)
        {  
	    	LG.error("解密私钥为空.");
	        throw new InnerException(BizError.INVALID_KEY_ERROR,"解密私钥为空");   
        }  
        
        Cipher cipher = null;     
        try
        {  
            byte[] encryptedBytes = Base64.decodeBase64(cipherData.getBytes(charset));
            byte [] decryptedBytes = new byte[]{};
            // 使用默认RSA 
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            for (int decryptedLength = 0; decryptedLength < encryptedBytes.length; decryptedLength += DECRYPT_BLOCK) {
                byte[] subarray = ArrayUtils.subarray(encryptedBytes, decryptedLength, decryptedLength + DECRYPT_BLOCK);
                byte[] subdecryptedBytes = cipher.doFinal(subarray);
                decryptedBytes = ArrayUtils.addAll(decryptedBytes,subdecryptedBytes);
            }
            String retString = new String(decryptedBytes,charset);
            return retString;  
        }
        catch(NoSuchAlgorithmException e)
        {  
			LG.error("catch NoSuchAlgorithmException");
			throw new InnerException(BizError.SIGN_TYPE_ERROR,"无此解密算法.");
        }
        catch(NoSuchPaddingException e)
        {  
			LG.error("catch NoSuchPaddingException.");
			throw new InnerException(BizError.PADDING_TYPE_ERROR,"No such padding.");
        }
        catch(InvalidKeyException e)
        { 
			LG.error("解密公钥非法");
		    throw new InnerException(BizError.INVALID_KEY_ERROR,"The private key is invalid.");  
        }
        catch(IllegalBlockSizeException e)
        {  
    		LG.error("密文长度非法");
		    throw new InnerException(BizError.ILLEGAL_BLOCK_ERROR,"The blocksize of encryptmsg is error.");  
        }
        catch (BadPaddingException e)
        {  
			LG.error("密文数据已损坏.");
		    throw new InnerException(BizError.BAD_PADDING_ERROR,"密文数据已损坏.");   
        } catch (Exception e) {
			// TODO Auto-generated catch block
			LG.error("解密异常.");
		    throw new InnerException(BizError.BAD_PADDING_ERROR,"密文密文IO失败.");  
		}  
    } 
}
