package com.order.eagle.hub.back.entities.dto.storePix;

import com.order.eagle.hub.back.entities.enums.TypePix;

public record StorePixGetDTO(
		String key,
		TypePix type,
		String owner,
		String bank
) {}
