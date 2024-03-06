package com.order.eagle.hub.back.entities.populator;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.order.eagle.hub.back.entities.AddressStore;
import com.order.eagle.hub.back.entities.Category;
import com.order.eagle.hub.back.entities.Product;
import com.order.eagle.hub.back.entities.SocialMedia;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.StoreHour;
import com.order.eagle.hub.back.entities.StorePix;
import com.order.eagle.hub.back.entities.enums.SocialMediaType;
import com.order.eagle.hub.back.entities.enums.TypePix;
import com.order.eagle.hub.back.entities.enums.WeekDay;
import com.order.eagle.hub.back.repositories.CategoryRepository;
import com.order.eagle.hub.back.repositories.ProductRepository;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Configuration
@Profile("test")
public class Populator implements CommandLineRunner{

	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Store s1 = new Store(null, "Ana Acessórios", "ana@gmail.com", "123456", "Loja de semijoias", "85999999999");
		
		Store s2 = new Store(null, "Lord dos Imports", "lord@gmail.com", "123456", "Loja de roupas importadas", "85977777777");
		
		Store s3 = new Store(null, "Rei do frango", "rei@gmail.com", "123456", "Loja de frango", "85988888888");
		

		
		AddressStore ad1 = new AddressStore(null, "Olimpio Ribeiro", "81", "Conjunto Palmeiras", "Fortaleza", "Ceara",
				"60870340", s1);
		
		s1.setAddress(ad1);
		
		
		StorePix sp1 = new StorePix(null, "06498125306", TypePix.CPF, "pedro vitor silva de sousa", "Nu Bank", s1);
		
		s1.setPix(sp1);
		
		
		StoreHour sh1 = new StoreHour(null, WeekDay.SUNDAY, null, null, s1);
		StoreHour sh2 = new StoreHour(null, WeekDay.MONDAY, LocalTime.of(8, 0),  LocalTime.of(18, 0), s1);
		StoreHour sh3 = new StoreHour(null, WeekDay.TUESDAY, LocalTime.of(8, 0),  LocalTime.of(18, 0), s1);
		StoreHour sh4 = new StoreHour(null, WeekDay.WEDNESDAY, LocalTime.of(8, 0),  LocalTime.of(18, 0), s1);
		StoreHour sh5 = new StoreHour(null, WeekDay.THURSDAY, LocalTime.of(8, 0),  LocalTime.of(18, 0), s1);
		StoreHour sh6 = new StoreHour(null, WeekDay.FRIDAY, LocalTime.of(8, 0),  LocalTime.of(18, 0), s1);
		StoreHour sh7 = new StoreHour(null, WeekDay.SATURDAY, LocalTime.of(8, 0),  LocalTime.of(13, 0), s1);
		
		s1.getStoreHours().addAll(List.of(sh1, sh2, sh3, sh4, sh5, sh6, sh7));
		
		
		SocialMedia sm1 = new SocialMedia(null, SocialMediaType.WHATSAPP, "http://whatsapp.com/chat?num=85994104003", s1);
		SocialMedia sm2 = new SocialMedia(null, SocialMediaType.INSTAGRAM, "http://instagam.com/teste1", s1);
		SocialMedia sm3 = new SocialMedia(null, SocialMediaType.FACEBOOK, "http://facebook.com/test1", s1);
		
		s1.getSocialMedias().addAll(List.of(sm1, sm2, sm3));
		
		storeRepository.saveAll(List.of(s1,s2,s3));
		
		Category cat1 = new Category(null, "Eletrônico".toUpperCase(), s1);
		Category cat2 = new Category(null, "Comida".toUpperCase(), s1);
		
		Product p1 = new Product(null, "Teclado", "Teclado Bom", 200.0, s1, cat1);
		Product p2 = new Product(null, "Pastel", "Pastel Bom", 20.0, s1, cat1);
		
		categoryRepository.saveAll(List.of(cat1, cat2));
		productRepository.saveAll(List.of(p1,p2));
	}

}
