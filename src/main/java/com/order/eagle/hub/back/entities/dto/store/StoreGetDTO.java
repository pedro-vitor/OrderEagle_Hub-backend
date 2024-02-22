package com.order.eagle.hub.back.entities.dto.store;

public record StoreGetDTO(
		String name,
		String login,
		String password,
		String description,
		String phone,
		AddressGetDTO address
) {}
