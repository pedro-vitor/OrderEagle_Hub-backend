package com.order.eagle.hub.back.entities.dto.storehour;

import com.order.eagle.hub.back.entities.enums.WeekDay;

public record StoreHourGetDTO(
		WeekDay weekDay,
		int openingHour,
		int openingMinute,
		int closingHour,
		int closingMinute
) {}
