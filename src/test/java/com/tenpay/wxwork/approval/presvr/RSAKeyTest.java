package com.tenpay.wxwork.approval.presvr;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.tenpay.wxwork.approval.presvr.common.error.BizError;
import com.tenpay.wxwork.approval.presvr.common.exception.InnerException;

public class RSAKeyTest {
	static String privateKeyPath = "D://cmbPrv2.pem";
	static String publicKeyPath = "D://cmbPub.pem";

	public static void main(String[] args) throws InnerException, Exception {
		String signSrc = "hello";
		String sign = genSign(signSrc, getPemPrivateKey(privateKeyPath,"RSA"), "UTF-8");
		System.out.println("sign："+sign);
		boolean verifyResult = verifySign(signSrc,sign, getPemPublicKey(publicKeyPath,"RSA"), "UTF-8");
		System.out.println("verifyResult："+verifyResult);

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
				throw new InnerException(BizError.GEN_SIGN_ERROR,"Genenate sign error.");
			}
			encodeSign = new String(Base64.encodeBase64(sign));

			if (encodeSign.length() == 0) {
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
	
	public static boolean verifySign(String signSrc,String encodedSign, PublicKey pubKey,String charset) throws Exception
	{
		try
		{	
			if(encodedSign.isEmpty())
			{
				throw new Exception("Sign field is empty");
			}
			else
			{
				byte[]  decodedSign = Base64.decodeBase64(encodedSign.getBytes());

				if(0 == decodedSign.length)
				{
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
			 throw e;
		 }	
	}
	
	 public static PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
		 File f = new File(filename);
		 FileInputStream fis = new FileInputStream(f);
		 DataInputStream dis = new DataInputStream(fis);
		 byte[] keyBytes = new byte[(int) f.length()];
		 dis.readFully(keyBytes);
		 dis.close();
		 String temp = new String(keyBytes);
		 System.out.println("privKeyPEM："+temp);
		 String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----","");
		 privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----","");
		//System.out.println("Private keyn"+privKeyPEM);
		 Base64 b64 = new Base64();
		 byte [] decoded = b64.decode(privKeyPEM);
		 PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
		 KeyFactory kf = KeyFactory.getInstance(algorithm);
		 return kf.generatePrivate(spec);
	}
	 public static PublicKey getPemPublicKey(String filename, String algorithm) throws Exception {
		 File f = new File(filename);
		 FileInputStream fis = new FileInputStream(f);
		 DataInputStream dis = new DataInputStream(fis);
		 byte[] keyBytes = new byte[(int) f.length()];
		 dis.readFully(keyBytes);
		 dis.close();
		 String temp = new String(keyBytes);
		 String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----","");
		 publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----","");
		 publicKeyPEM = publicKeyPEM.replace("\n","");

		 System.out.println("publicKeyPEM:"+publicKeyPEM);
		 Base64 b64 = new Base64();
		 byte [] decoded = b64.decode(publicKeyPEM);
		 X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
		 KeyFactory kf = KeyFactory.getInstance(algorithm);
		 return kf.generatePublic(spec);
	 }
}
