package com.luojiash.binary;

/**
 * {@link org.apache.commons.codec.binary.Hex} 
 */
public class Hex {
	
    private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    public static char[] encodeHex(final byte[] data) {
    	return encodeHex(data, true);
    }
    
    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
    	return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }
    
    private static char[] encodeHex(final byte[] data, final char[] digits) {
    	final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i=0,j=0;i<l;++i) {
			out[j++] = digits[(0XF0 & data[i])>>>4];
			out[j++] = digits[0X0F & data[i]];
		}
		return out;
    }
    
    public static String encodeHexString(final byte[] data) {
    	return new String(encodeHex(data));
    }
}
