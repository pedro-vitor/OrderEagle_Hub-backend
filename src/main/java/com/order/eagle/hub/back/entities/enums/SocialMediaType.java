package com.order.eagle.hub.back.entities.enums;

public enum SocialMediaType {

	FACEBOOK(0),
	INSTAGRAM(1),
	WHATSAPP(2),
	TIKTOK(3),
	KWAI(4),
	TELEGRAM(5);
	
	private int code;
	
	private SocialMediaType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
