package com.luojiash.util;

import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

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
		PrivateKey priv = keyFactory.generatePrivate(spec);
		Arrays.fill(bytes, (byte) 0);
		return priv;
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
	
	public static String savePrivateKey(PrivateKey priv) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = fact.getKeySpec(priv, PKCS8EncodedKeySpec.class);
        byte[] packed = spec.getEncoded();
        String key64 = Base64.encodeBase64String(packed);
        Arrays.fill(packed, (byte) 0);
        return key64;
    }

    public static String savePublicKey(PublicKey publ) throws GeneralSecurityException {
        KeyFactory fact = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = fact.getKeySpec(publ, X509EncodedKeySpec.class);
        return Base64.encodeBase64String(spec.getEncoded());
    }
	
	public static String sign(String text, PrivateKey privateKey)throws Exception{
		Signature signature = Signature.getInstance(SIGN_ALGORITHM);
		signature.initSign(privateKey);
		signature.update(text.getBytes());
		return Base64.encodeBase64String(signature.sign());
	}
	
	public static boolean verifySign(String text, String sign, PublicKey publicKey) throws Exception {
	    Signature signature = Signature.getInstance(SIGN_ALGORITHM);
	    signature.initVerify(publicKey);
	    signature.update(text.getBytes());
	    return signature.verify(Base64.decodeBase64(sign));
	}

	public static String longTextEncode(String text, PrivateKey privateKey) throws Exception {
	    byte[] bytes=text.getBytes();
        int len=117;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; i+=len) {
            baos.write(encrypt(privateKey, Arrays.copyOfRange(bytes, i, i+Integer.min(len, bytes.length-i))));
        }
        byte[] enBytes = baos.toByteArray();
        return Base64.encodeBase64String(enBytes);
	}

	public static String longTextDecode(String text, PublicKey publicKey) throws Exception {
	    byte[] enBytes=Base64.decodeBase64(text);
	    int decLen=128;
	    ByteArrayOutputStream baos=new ByteArrayOutputStream();
        for (int i = 0; i < enBytes.length; i+=decLen) {
            baos.write(decrypt(publicKey, Arrays.copyOfRange(enBytes, i, i+Integer.min(decLen, enBytes.length-i))));
        }
        return new String(baos.toByteArray());
    }

	public static void main(String[] args) throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
//		generator.initialize(keysize, random);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		
		String text = "a是谁佛isabcd你是谁佛is";
		byte[] enBytes =encrypt(privateKey, text.getBytes());
		System.out.println(Base64.encodeBase64String(enBytes));
	    System.out.println(new String(decrypt(pair.getPublic(), enBytes)));
		
		/* 长文本加密。为了提高速度，可以采用另一种加密方式：
		 * 长文本使用对称加密算法加密，把对称密钥使用非对称算法加密，
		 * 将加密的长文本和加密的对称密钥一起发送。
		 */
		String encoded = longTextEncode(text, privateKey);
		String decoded = longTextDecode(encoded, publicKey);
		System.out.println(encoded);
		System.out.println(decoded);

		// 签名生成和验证
		String sign = sign(text, privateKey);
		System.out.println(sign);
		System.out.println(verifySign(text, sign, pair.getPublic()));
	}
}
