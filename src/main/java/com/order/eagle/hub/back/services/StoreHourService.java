package com.order.eagle.hub.back.services;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.StoreHour;
import com.order.eagle.hub.back.entities.dto.storehour.StoreHourGetDTO;
import com.order.eagle.hub.back.entities.enums.WeekDay;
import com.order.eagle.hub.back.repositories.StoreHourRepository;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Service
public class StoreHourService {

	@Autowired
	private StoreHourRepository storeHourRepository;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreRepository storeRepository;
	
	public List<StoreHour> findAllStoreHourByStore(UUID storeId){
		var listHours = storeHourRepository.findAllHoursByStore(storeId);
		if(listHours.isEmpty())
			throw new IllegalArgumentException("Sem registro de horários para a loja com o ID: '" + storeId + "'");
		return listHours;		
	}
	
	public List<StoreHour> insertStoreHoursByStore(List<StoreHourGetDTO> listDto, UUID idStore) {
		final var store = storeService.findById(idStore);
		
		if(!store.getStoreHours().isEmpty())
			throw new IllegalArgumentException("Loja já possui Horários cadastrados");
		
		var listStoreHour = listDto.stream().map(sh -> this.toStoreHour(sh, store)).collect(Collectors.toList());
		this.verifyExistsAllDays(listStoreHour);
		this.verifyRepeatedDays(listStoreHour);
		
		store.getStoreHours().addAll(listStoreHour);
		var result = storeRepository.save(store);
		
		return result.getStoreHours();
	}
	
	public StoreHour updateStoreHoursByStore(StoreHourGetDTO dto, UUID idStore) {
		var storeHour = storeHourRepository.findByIdAndByStore(dto.weekDay(), idStore)
				.orElseThrow(() -> new IllegalArgumentException("Horário de funcionamento não cadastrado"));
		
		var newDatas = this.toStoreHour(dto, null);
		this.changeValuesStoreHourForUpdate(storeHour, newDatas);
		return storeHourRepository.save(storeHour);
	}
	
	private StoreHour toStoreHour(StoreHourGetDTO dto, Store store) {
		return new StoreHour(null, dto.weekDay(), LocalTime.of(dto.openingHour(), dto.openingMinute()),
				LocalTime.of(dto.closingHour(), dto.closingMinute()), store);
	}
	
	private void verifyRepeatedDays(List<StoreHour> listStoreHour) {
		Set<WeekDay> seenDays = new HashSet<>();
		
		for(StoreHour storeHour : listStoreHour) {
			WeekDay day = storeHour.getWeekDay();
			
			if(seenDays.contains(day))
				throw new IllegalArgumentException("Não permitido cadastro de dias repetidos");
			
			seenDays.add(day);
		}
	}
	
	private void verifyExistsAllDays(List<StoreHour> listStoreHour) {
		if(listStoreHour.size() != 7)
			throw new IllegalArgumentException("A lista deve conter os sete dias da semana");
	}
	
	private void changeValuesStoreHourForUpdate(StoreHour destity, StoreHour newDatas) {
		destity.setOpeningTime(newDatas.getOpeningTime());
		destity.setClosingTime(newDatas.getClosingTime());
	}
}
