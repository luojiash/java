package com.luojiash.util;

import java.nio.charset.StandardCharsets;

public class StringUtils {
	public static byte[] getBytesUtf8(final String string) {
		if (string == null) {
			return null;
		}
		return string.getBytes(StandardCharsets.UTF_8);
	}
}
