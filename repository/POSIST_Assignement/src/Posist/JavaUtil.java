package Posist;

import java.text.DecimalFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public class JavaUtil {


	void float2(double decimalNumber)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		System.out.println(df.format(decimalNumber));
	}
	
	static String hashString(String message) {
	 
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
	 
	        return convertByteArrayToHexString(hashedBytes);
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
	    	return null;
	    }
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	
	public String getEncrypt(String Message,SecretKey sk) throws Exception
	{

		byte[] plainTextByte = Message.getBytes("UTF8");
		byte[] encryptedBytes = encrypt(plainTextByte, sk);
		String encryptedText = new String(encryptedBytes, "UTF8");
		return encryptedText;
	}
	
	public String getDecrypt(String Message,SecretKey sk) throws Exception
	{
		
		byte[] encryptedBytes = Message.getBytes("UTF8");
		byte[] decryptedBytes = decrypt(encryptedBytes, sk);
		String decryptedText = new String(decryptedBytes, "UTF8");
		return decryptedText;
	}
	
	static byte[] encrypt(byte[] plainTextByte, SecretKey secretKey)
			throws Exception {
		Cipher cipher = null;
		cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plainTextByte);
		return encryptedBytes;
	}
	
	static byte[] decrypt(byte[] encryptedBytes, SecretKey secretKey)
			throws Exception {
		Cipher cipher = null;
		cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return decryptedBytes;
	}

}


