package com.order.eagle.hub.back.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order.eagle.hub.back.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{

	@Query("SELECT c FROM Category c WHERE c.store.id=:idStore AND c.status.situation <> DISABLED")
	List<Category> findByStore(UUID idStore);
}
