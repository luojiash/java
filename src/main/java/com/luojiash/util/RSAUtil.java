package com.luojiash.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtil {
	private static String RSA = "RSA";
	private static String SIGN_ALGORITHM = "SHA1withRSA";
	
	public static byte[] encrypt(PrivateKey privateKey, byte[] text) throws Exception{
		Cipher cipher = Cipher.getInstance(RSA);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(text);
	}
	public static PrivateKey loadPrivateKey(String privateKey) throws Exception{
		byte [] bytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return keyFactory.generatePrivate(spec);
	}
	
	public static byte[] decrypt(PublicKey publicKey, byte[] text)throws Exception{
		Cipher cipher =Cipher.getInstance(RSA);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(text);
	}
	
	public static PublicKey loadPublicKey(String publicKey)throws Exception{
		byte[] bytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		return keyFactory.generatePublic(spec);
	}
	
	public static String sign(String text, PrivateKey privateKey)throws Exception{
		Signature signature = Signature.getInstance(SIGN_ALGORITHM);
		signature.initSign(privateKey);
		signature.update(text.getBytes());
		return Base64.encodeBase64String(signature.sign());
	}
	
	public static void main(String[] args) throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
//		generator.initialize(keysize, random);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		
		String text = "a是谁佛isabcd你是谁佛is";
		byte[] enBytes =encrypt(privateKey, text.getBytes());
		System.out.println(Base64.encodeBase64String(enBytes));
		
		String sign = sign(text, privateKey);
		System.out.println(sign);
	}
}
