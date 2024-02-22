package com.order.eagle.hub.back.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.eagle.hub.back.entities.abstracts.Address;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "tb_address_store")
public class AddressStore extends Address{

	@Getter
	@Setter
	@OneToOne
	@JoinColumn(name = "store_id")
	@JsonBackReference
	private Store store;

	public AddressStore(UUID id, String street, String number, String neighborhood, String city, String state,
			String postalCode, Store store) {
		super(id, street, number, neighborhood, city, state, postalCode);
		this.store = store;
	}
}
