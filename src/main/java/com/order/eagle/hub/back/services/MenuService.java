package com.order.eagle.hub.back.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.Menu;
import com.order.eagle.hub.back.repositories.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;
	
	public Menu findById(UUID idStore){
		return menuRepository.findById(idStore)
				.orElseThrow(() -> new IllegalArgumentException("Menu Não encontrado para essa a loja com o ID:" + idStore));
	}
}
