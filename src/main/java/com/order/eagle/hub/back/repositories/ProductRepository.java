package com.order.eagle.hub.back.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>{

	@Query("SELECT p FROM Product p WHERE p.store.id=:storeId AND p.store.status.situation <> DISABLED AND"
			+ " p.status.situation <> DISABLED")
	List<Product> findByStore(UUID storeId);
}
