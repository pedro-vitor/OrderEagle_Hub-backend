package com.order.eagle.hub.back.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.Store;

public interface StoreRepository extends JpaRepository<Store, UUID>{

	@Query("SELECT s FROM Store s WHERE s.status.situation <> DISABLED")
	List<Store> findAllNotDisabled();
	
	@Query("SELECT s FROM Store s WHERE s.id=:id AND s.status.situation <> DISABLED")
	Optional<Store> findByIdNotDisabled(UUID id);
	
	Store findByPhone(String phone);
	
	Store findByEmail(String email);
	
	boolean existsByPhone(String phone);
	
	boolean existsByEmail(String email);
}
