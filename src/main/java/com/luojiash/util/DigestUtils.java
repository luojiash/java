package com.luojiash.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.luojiash.binary.Hex;

/**
 * {@link org.apache.commons.codec.digest.DigestUtils} 
 */
public class DigestUtils {
	
	public static byte[] md5(final String data) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		return digest.digest(StringUtils.getBytesUtf8(data));
	}
	
	public static String md5Hex(final String data) {
		return Hex.encodeHexString(md5(data));
	}
}
