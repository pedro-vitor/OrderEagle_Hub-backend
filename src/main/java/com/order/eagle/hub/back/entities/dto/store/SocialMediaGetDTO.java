package com.order.eagle.hub.back.entities.dto.store;

import com.order.eagle.hub.back.entities.enums.SocialMediaType;

public record SocialMediaGetDTO(
		SocialMediaType type,
		String url
) {}
