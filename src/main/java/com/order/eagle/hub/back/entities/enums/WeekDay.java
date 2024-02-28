package com.order.eagle.hub.back.entities.enums;

public enum WeekDay {

	SUNDAY(0),
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6);
	
	private int code;
	
	private WeekDay(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
