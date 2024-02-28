package com.order.eagle.hub.back.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.order.eagle.hub.back.entities.StorePix;
import com.order.eagle.hub.back.entities.dto.storePix.StorePixGetDTO;
import com.order.eagle.hub.back.services.StorePixService;

@RestController
@RequestMapping("/stores/pix")
public class StorePixController {

	@Autowired
	private StorePixService storePixService;
	
	@GetMapping("/{idStore}")
	public ResponseEntity<StorePix> findStorePixByStore(@PathVariable UUID idStore){
		var result = storePixService.findByStore(idStore);
		return ResponseEntity.ok().body(result);
	}
	
	//.replaceQueryParam("store") -> Remove o parâmetro "store" da URL
	@PostMapping
	public ResponseEntity<StorePix> insertStorePixByStore(@RequestBody StorePixGetDTO dto ,@RequestParam UUID store){
		var result = storePixService.insert(dto, store);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("stores").replaceQueryParam("store")
				.path("/{id}").buildAndExpand(result.getStore().getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping
	public ResponseEntity<StorePix> updateStorePixByStore(@RequestBody StorePixGetDTO dto ,@RequestParam UUID store){
		var result = storePixService.update(dto, store);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{idStore}")
	public ResponseEntity<StorePix> updateStorePixByStore(@PathVariable UUID idStore){
		storePixService.delete(idStore);
		return ResponseEntity.noContent().build();
	}
}
