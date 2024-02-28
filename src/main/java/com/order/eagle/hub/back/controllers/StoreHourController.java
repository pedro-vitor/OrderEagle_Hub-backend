package com.order.eagle.hub.back.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.order.eagle.hub.back.entities.StoreHour;
import com.order.eagle.hub.back.entities.dto.storehour.StoreHourGetDTO;
import com.order.eagle.hub.back.services.StoreHourService;

@RestController
@RequestMapping("/stores/times")
public class StoreHourController {

	@Autowired
	private StoreHourService storeHourService;
	
	@GetMapping
	public ResponseEntity<List<StoreHour>> findAllStoreHoursByStore(@RequestParam("store") UUID idStore){
		var result = storeHourService.findAllStoreHourByStore(idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<List<StoreHour>> insertStoreHourByStore(@RequestBody List<StoreHourGetDTO>  listDto, @RequestParam("store") UUID idStore){
		var result = storeHourService.insertStoreHoursByStore(listDto,idStore);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("stores").replaceQueryParam("store")
				.path("/{id}").buildAndExpand(result.get(0).getStore().getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping
	public ResponseEntity<StoreHour> updateStoreHourByStore(@RequestBody StoreHourGetDTO  dto, @RequestParam("store") UUID idStore){
		var result = storeHourService.updateStoreHoursByStore(dto,idStore);
		return ResponseEntity.ok().body(result);
	}
	
	/*
	 * Horário de funcionamento não pode ser deletado, somente alterado para "Loja fechada"
	 */
}
