package com.hhh.fund.security;


import java.security.MessageDigest;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String tokenCredentials = this.encrypt(String.valueOf(token.getPassword()));
		Object accountCredentials = this.getCredentials(info);
		return this.equals(tokenCredentials, accountCredentials);
	}

	private String encrypt(String data) {
		MessageDigest md5 = null;

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception arg6) {
			System.out.println(arg6.toString());
			arg6.printStackTrace();
			return "";
		}

		byte[] md5Bytes = md5.digest(data.getBytes());
		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; ++i) {
			int val = md5Bytes[i] & 255;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString().toUpperCase();
	}
}