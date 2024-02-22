package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.AddressStore;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.store.AddressGetDTO;
import com.order.eagle.hub.back.entities.dto.store.StoreGetDTO;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	public List<Store> findAll(){
		return storeRepository.findAllNotDisabled();
	}
	
	public Store findById(UUID id) {
		var result = storeRepository.findByIdNotDisabled(id);
		return result.orElseThrow(() -> new IllegalArgumentException());
	}
	
	public Store insert(StoreGetDTO dto) {
		var result = toStore(dto);
		result = storeRepository.save(result);
		return result;
	}
	
	private Store toStore(StoreGetDTO dto) {
		Store store = new Store(null, dto.name(), dto.email(), dto.password(), dto.description(), dto.phone());
		store.setAddress(toAddressStore(dto.address(), store));
		
		return store;
	}

	private AddressStore toAddressStore(AddressGetDTO address, Store store) {
		return new AddressStore(null, address.street(), address.number(), address.neighborhood(),
				address.city(), address.state(), address.postalCode(), store);
	}
}
