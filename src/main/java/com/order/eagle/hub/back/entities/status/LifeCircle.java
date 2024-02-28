package com.order.eagle.hub.back.entities.status;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.order.eagle.hub.back.entities.enums.Situations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class LifeCircle {

	@Column(updatable = false)
	@CreationTimestamp
	public LocalDateTime created_at;
	
	@UpdateTimestamp
	public LocalDateTime updated_at;
	
	public Situations situation;
}
