package com.order.eagle.hub.back.entities.dto.store;

public record AddressGetDTO(
		String street,
		String number,
		String neighborhood,
		String city,
		String state,
		String postalCode
) {}
