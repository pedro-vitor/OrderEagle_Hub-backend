package com.order.eagle.hub.back.entities.abstracts;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String street;
	private String number;
	private String neighborhood;
	private String city;
	private String state;
	private String postalCode;
	
	@CreationTimestamp
	private LocalDateTime created_at;
	
	@UpdateTimestamp
	private LocalDateTime updated_at;

	public Address(UUID id, String street, String number, String neighborhood, String city, String state,
			String postalCode) {
		this.id = id;
		this.street = street;
		this.number = number;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}
}
