package com.order.eagle.hub.back.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.StorePix;
import com.order.eagle.hub.back.entities.dto.storePix.StorePixGetDTO;
import com.order.eagle.hub.back.repositories.StorePixRepository;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Service
public class StorePixService {

	@Autowired
	private StorePixRepository storePixRepository;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreRepository storeRepository;
	
	public StorePix findByStore(UUID storeID) {
		var result = storePixRepository.findByStoreId(storeID);
		return result.orElseThrow(() -> new IllegalArgumentException("Pix não encontrado com a loja de ID:" + storeID));
	}
	
	public StorePix insert(StorePixGetDTO dto, UUID idStore) {
		var store = storeService.findById(idStore);
		
		if(store.getPix() != null)
			throw new IllegalArgumentException("Loja Já possui um Pix cadastrado");
		
		store.setPix(toStorePix(dto, store));
		var result = storeRepository.save(store);
		return result.getPix();
	}
	
	public StorePix update(StorePixGetDTO dto, UUID idStore) {
		var currentStorePix = this.findByStore(idStore);
		var newDatas = toStorePix(dto, null);
		updateDatasStorePix(currentStorePix, newDatas);
		return storePixRepository.save(currentStorePix);
	}
	
	public void delete(UUID idStore) {
		var store = storeService.findById(idStore);
		if(store.getPix() == null)
			throw new IllegalArgumentException("Loja como o id: '" + idStore + "' não possui Pix cadastrado");
		store.setPix(null);
		storeRepository.save(store);
	}
	
	private StorePix toStorePix(StorePixGetDTO dto, Store store) {
		return new StorePix(null, dto.key(), dto.type(), dto.owner(), dto.bank(), store);
	}

	private void updateDatasStorePix(StorePix storePixCurrent, StorePix newDatas) {
		storePixCurrent.setPixKey(newDatas.getPixKey());
		storePixCurrent.setTypePix(newDatas.getTypePix());
		storePixCurrent.setOwner(newDatas.getOwner());
		storePixCurrent.setBank(newDatas.getBank());
	}

}
