package com.order.eagle.hub.back.entities.status;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.eagle.hub.back.entities.enums.Situations;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LifeCircle {

	@CreationTimestamp
	public LocalDateTime created_at;
	
	@UpdateTimestamp
	public LocalDateTime updated_at;
	
	public Situations situation;
}
