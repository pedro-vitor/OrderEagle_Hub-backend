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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.order.eagle.hub.back.entities.Product;
import com.order.eagle.hub.back.entities.dto.product.ProductGetDTO;
import com.order.eagle.hub.back.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> findByStore(@RequestParam("store") UUID idStore){
		var result = productService.findByStore(idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody ProductGetDTO dto, @RequestParam("store") UUID idStore){
		var result = productService.insertByStore(dto, idStore);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(uri).body(result);
	}
	
	@PutMapping("/{idProduct}")
	public ResponseEntity<Product> update(@RequestBody ProductGetDTO dto, @PathVariable UUID idProduct,
			@RequestParam("store") UUID idStore){
		var result = productService.updateByStore(dto, idProduct, idStore);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{idProduct}")
	public ResponseEntity<Void> delete(@PathVariable UUID idProduct, @RequestParam("store") UUID idStore){
		productService.deleteByStore(idProduct, idStore);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("upload/img/{productId}")
	public ResponseEntity<Void> uploadBanner(@RequestParam("image") MultipartFile image, @PathVariable UUID productId){
		var result = productService.uploadImgProduct(productId, image);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("products").
				replaceQueryParam("store", result.getStore().getId()).build().toUri();
		return ResponseEntity.created(uri).build();
	}
}
