package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.Store;
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
}
