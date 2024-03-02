package com.order.eagle.hub.back.controllers;

import java.net.URI;
import java.util.List;
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

import com.order.eagle.hub.back.entities.SocialMedia;
import com.order.eagle.hub.back.entities.dto.store.SocialMediaGetDTO;
import com.order.eagle.hub.back.services.SocialMediaService;

@RestController
@RequestMapping("/stores/socialMedias")
public class SocialMediaController {

	@Autowired
	private SocialMediaService socialMediaService;
	
	@GetMapping
	public ResponseEntity<List<SocialMedia>> findByStore(@RequestParam("store") UUID idStore){
		var result = socialMediaService.findByStore(idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<List<SocialMedia>> insert(@RequestBody SocialMediaGetDTO dto, @RequestParam("store") UUID idStore){
		var result = socialMediaService.insertByStore(dto, idStore);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("stores").replaceQueryParam("store")
				.path("/{id}").buildAndExpand(result.get(0).getStore().getId()).toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping("/{idSocialMedia}")
	public ResponseEntity<SocialMedia> update(@RequestBody SocialMediaGetDTO dto, @RequestParam("store") UUID idStore, 
			@PathVariable UUID idSocialMedia){
		var result = socialMediaService.updateByStore(dto, idSocialMedia, idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{idSocialMedia}")
	public ResponseEntity<Void> update(@RequestParam("store") UUID idStore, @PathVariable UUID idSocialMedia){
		socialMediaService.deleteSocialMedia(idSocialMedia, idStore);
		return ResponseEntity.noContent().build();
	}
}
