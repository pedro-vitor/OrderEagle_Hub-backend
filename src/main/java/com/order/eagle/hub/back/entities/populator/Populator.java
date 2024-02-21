package com.order.eagle.hub.back.entities.populator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Configuration
@Profile("test")
public class Populator implements CommandLineRunner{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Store s1 = new Store(null, "Ana Acessórios", "ana", "123456", "Loja de semijoias", "85999999999",
				"resource/static/img/logo.png", "resource/static/img/banner.png");
		
		Store s2 = new Store(null, "Lord dos Imports", "lord", "123456", "Loja de roupas importadas", "85977777777",
				"resource/static/img/logo.png", "resource/static/img/banner.png");
		
		Store s3 = new Store(null, "Rei do frango", "rei", "123456", "Loja de frango", "85988888888",
				"resource/static/img/logo.png", "resource/static/img/banner.png");
		
		storeRepository.saveAll(List.of(s1,s2,s3));
		
	}

}
