package com.scholastic.intl.esb.integration.mediator;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

public class AuthorizationHeaderClass {

	private String authorizationHeader;

	public  String mediate(String urlWithParameters) {

		//String url = (String) context.getProperty("urlWithParameters");
		//String apiKey = "78ca6121-763c-41c6-8dfe-d73e761b9989";
		//String secret = (String) context.getProperty("secret");
		String apiKey = "78ca6121-763c-41c6-8dfe-d73e761b9989";
		String secret = "BA68EAD13AF25DD55584B3FDBFC2A";
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE MMM dd YYYY HH:mm:ss");
		String timestamp = sdf.format(new Date());

		SimpleDateFormat sdf1 = new SimpleDateFormat("zzzz");
		String timeZoneLong=sdf1.format(new Date());

		SimpleDateFormat sdf2 = new SimpleDateFormat("Z");
		String timeZoneShort=sdf2.format(new Date());

		String finalTimeStamp = timestamp+" GMT"+timeZoneShort+" ("+timeZoneLong+")";

		String message = apiKey + ";" + urlWithParameters + ";" + finalTimeStamp;
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
			String encodedSignature = hash;

			authorizationHeader = message+";"+encodedSignature;
			authorizationHeader = authorizationHeader.trim();//final authorization header
			//System.out.println(hash);


		}catch (Exception e){
			System.out.println("Error");
		}
		//context.setProperty("authorizationHeader", authorizationHeader);

		return authorizationHeader;
	}

	public String getAuthorizationHeader() {
		return authorizationHeader;
	}

	public void setAuthorizationHeader(String authorizationHeader) {
		this.authorizationHeader = authorizationHeader;
	}

}
