package com.order.eagle.hub.back.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.eagle.hub.back.entities.Menu;
import com.order.eagle.hub.back.services.MenuService;

@RestController
@RequestMapping("/stores/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping
	public ResponseEntity<Menu> findByStore(@RequestParam("store") UUID idStore){
		var result = menuService.findById(idStore);
		return ResponseEntity.ok().body(result);
	}
}
