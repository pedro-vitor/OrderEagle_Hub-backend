package com.order.eagle.hub.back.controllers;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.store.StoreGetDTO;
import com.order.eagle.hub.back.services.StoreService;

@RestController
@RequestMapping("/stores")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@GetMapping
	public ResponseEntity<List<Store>> findAll(){
		var result = storeService.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Store> findbyId(@PathVariable UUID id){
		var result = storeService.findById(id);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<Store> insert(@RequestBody StoreGetDTO dto){
		var result = storeService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<Store> update(@RequestBody StoreGetDTO dto, @PathVariable UUID id){
		var result = storeService.update(dto, id);
		return ResponseEntity.ok().body(result);
	} 
}
