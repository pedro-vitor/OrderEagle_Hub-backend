package com.order.eagle.hub.back.entities.enums;

public enum TypePix {

	PHONE(0),
	RANDOM_KEY(1),
	CPF(2),
	CNPJ(3),
	EMAIL(4);
	
	private Integer code;
	
	private TypePix(Integer code){
		this.code = code;
	}
	
	public Integer getCode() {
		return this.code;
	}
}
