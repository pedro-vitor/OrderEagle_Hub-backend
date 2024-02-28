package com.order.eagle.hub.back.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.StoreHour;
import com.order.eagle.hub.back.entities.enums.WeekDay;

public interface StoreHourRepository extends JpaRepository<StoreHour, UUID>{

	@Query("SELECT sh FROM StoreHour sh WHERE sh.store.id=:storeId AND sh.store.status.situation <> DISABLED")
	List<StoreHour> findAllHoursByStore(UUID storeId);
	
	@Query("SELECT sh FROM StoreHour sh WHERE sh.weekDay=:day AND sh.store.id=:storeId AND sh.store.status.situation <> DISABLED")
	Optional<StoreHour> findByIdAndByStore(WeekDay day, UUID storeId);
}
