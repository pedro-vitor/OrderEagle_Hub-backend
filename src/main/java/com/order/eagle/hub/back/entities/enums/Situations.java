package com.order.eagle.hub.back.entities.enums;

public enum Situations {

	ACTIVATED(1),
	DISABLED(0);
	
	private Integer situation;
	
	private Situations(Integer situation) {
		this.situation = situation;
	}
	
	public Integer getSituation() {
		return this.situation;
	}
}
