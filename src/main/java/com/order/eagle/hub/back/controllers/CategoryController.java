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

import com.order.eagle.hub.back.entities.Category;
import com.order.eagle.hub.back.entities.dto.category.CategoryGetDTO;
import com.order.eagle.hub.back.services.CategoryService;

@RestController
@RequestMapping("/stores/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findByStore(@RequestParam("store") UUID idStore){
		var result = categoryService.findByStore(idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<Category> insert(@RequestBody CategoryGetDTO dto, @RequestParam("store") UUID idStore){
		var result = categoryService.insertByStore(dto, idStore);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping("/{idCategory}")
	public ResponseEntity<Category> update(@RequestBody CategoryGetDTO dto, @PathVariable UUID idCategory,
			@RequestParam("store") UUID idStore){
		var result = categoryService.updateByStore(dto, idCategory, idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{idCategory}")
	public ResponseEntity<Void> delete(@PathVariable UUID idCategory, @RequestParam("store") UUID idStore){
		categoryService.deleteByStore(idCategory, idStore);
		return ResponseEntity.noContent().build();
	}
}
