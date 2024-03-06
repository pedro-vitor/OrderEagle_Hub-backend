package com.order.eagle.hub.back.entities.dto.product;

import java.util.UUID;

public record ProductGetDTO(
		String name,
		String description,
		Double price,
		UUID category
) {}
