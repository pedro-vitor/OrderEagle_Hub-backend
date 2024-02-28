package com.order.eagle.hub.back.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.StorePix;

public interface StorePixRepository extends JpaRepository<StorePix, UUID>{

	@Query("SELECT sp FROM StorePix sp WHERE sp.store.id=:storeID AND sp.status.situation <> DISABLED")
	Optional<StorePix> findByStoreId(UUID storeID);
}
