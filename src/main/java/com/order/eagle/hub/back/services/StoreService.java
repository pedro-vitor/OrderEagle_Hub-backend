package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.order.eagle.hub.back.entities.AddressStore;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.store.AddressGetDTO;
import com.order.eagle.hub.back.entities.dto.store.StoreGetDTO;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.repositories.StoreRepository;
import com.order.eagle.hub.back.services.util.ToolsService;

@Service
public class StoreService {

	@Autowired
	private StoreRepository storeRepository;
	
	public List<Store> findAll(){
		return storeRepository.findAllNotDisabled();
	}
	
	public Store findById(UUID id) {
		var result = storeRepository.findByIdNotDisabled(id);
		return result.orElseThrow(() -> new IllegalArgumentException("Loja não encontrada com o ID: " + id));
	}
	
	public Store insert(StoreGetDTO dto) {
		verifyExistsStorePhoneToInsert(dto.phone());
		verifyExistsStoreEmailToInsert(dto.email());
		
		var result = toStore(dto);
		result = storeRepository.save(result);
		return result;
	}
	
	public Store update(StoreGetDTO dto, UUID id) {		
		var curentStore = this.findById(id);
		
		verifyExistsStorePhoneToUpdate(dto.phone(), curentStore);
		verifyExistsStoreEmailToUpdate(dto.email(), curentStore);
		
		var datasForUpdateStore = toStore(dto);
		updateValuesStore(curentStore, datasForUpdateStore);
		
		return storeRepository.save(curentStore);
	}
	
	public void delete(UUID id) {
		if(!storeRepository.existsById(id))
			throw new IllegalArgumentException("Loja não encontrada com o ID: " + id);
		
		var store = storeRepository.getReferenceById(id);
		store.setSituation(Situations.DISABLED);
		storeRepository.save(store);
	}

	public Store uploadLogoStore(UUID id, MultipartFile logo) {
		var store = storeRepository.getReferenceById(id);
		var pathLogo = ToolsService.saveImg(logo);
		store.setLogo(pathLogo);
		return storeRepository.save(store);
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
	
	private void updateValuesStore(Store curentStore, Store datasForUpdateStore) {
		curentStore.setName(datasForUpdateStore.getName());
		curentStore.setEmail(datasForUpdateStore.getEmail());
		curentStore.setDescription(datasForUpdateStore.getDescription());
		curentStore.setPhone(datasForUpdateStore.getPhone());
		updateValuesAddressFromStore(curentStore.getAddress(), datasForUpdateStore.getAddress());
	}
	
	private void updateValuesAddressFromStore(AddressStore addressCurentStore, AddressStore datasForUpdateAddressStore) {
		addressCurentStore.setStreet(datasForUpdateAddressStore.getStreet());
		addressCurentStore.setNumber(datasForUpdateAddressStore.getNumber());
		addressCurentStore.setNeighborhood(datasForUpdateAddressStore.getNeighborhood());
		addressCurentStore.setCity(datasForUpdateAddressStore.getCity());
		addressCurentStore.setState(datasForUpdateAddressStore.getState());
		addressCurentStore.setPostalCode(datasForUpdateAddressStore.getPostalCode());
	}
	
	public void verifyExistsStorePhoneToInsert(String phone) {
		if(storeRepository.existsByPhone(phone)) {
			throw new IllegalArgumentException("Telefone ja usado por outra loja");
		}
	}
	
	public void verifyExistsStoreEmailToInsert(String email) {
		if(storeRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("Email ja usado por outra loja");
		}
	}
	
	public void verifyExistsStorePhoneToUpdate(String phone, Store store) {
		var result = storeRepository.findByPhone(phone);
		if(result != null && !result.equals(store) && result.getPhone().equals(phone)) {
			throw new IllegalArgumentException("Telefone já usado por outra loja");
		}
	}
	
	public void verifyExistsStoreEmailToUpdate(String email, Store store) {
		var result = storeRepository.findByEmail(email);
		if(result != null && !result.equals(store) && result.getEmail().equals(email)) {
			throw new IllegalArgumentException("Email já usado por outra loja");
		}
	}
}
