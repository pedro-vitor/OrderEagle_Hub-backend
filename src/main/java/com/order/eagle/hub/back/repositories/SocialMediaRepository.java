package com.order.eagle.hub.back.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.SocialMedia;
import com.order.eagle.hub.back.entities.enums.SocialMediaType;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, UUID>{

	@Query("SELECT sm FROM SocialMedia sm WHERE sm.status.situation <> DISABLED AND sm.store.id=:storeId "
			+ "AND sm.store.status.situation <> DISABLED")
	List<SocialMedia> findByStore(UUID storeId);
	
	@Query("SELECT sm FROM SocialMedia sm WHERE sm.status.situation <> DISABLED AND sm.type=:type "
			+ "AND sm.store.status.situation <> DISABLED AND sm.store.id=:storeId")
	Optional<SocialMedia> findByTypeAndStore(SocialMediaType type, UUID storeId);
}
