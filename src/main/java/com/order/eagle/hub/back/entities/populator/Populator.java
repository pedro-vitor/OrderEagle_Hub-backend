package com.order.eagle.hub.back.entities.populator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.order.eagle.hub.back.entities.AddressStore;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Configuration
@Profile("test")
public class Populator implements CommandLineRunner{

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Store s1 = new Store(null, "Ana Acessórios", "ana@gmail.com", "123456", "Loja de semijoias", "85999999999");
		
		Store s2 = new Store(null, "Lord dos Imports", "lord@gmail.com", "123456", "Loja de roupas importadas", "85977777777");
		
		Store s3 = new Store(null, "Rei do frango", "rei@gmail.com", "123456", "Loja de frango", "85988888888");
		
		storeRepository.saveAll(List.of(s1,s2,s3));
		
		AddressStore ad1 = new AddressStore(null, "Olimpio Ribeiro", "81", "Conjunto Palmeiras", "Fortaleza", "Ceara",
				"60870340", s1);
		
		s1.setAddress(ad1);
		
		storeRepository.save(s1);
	}

}
