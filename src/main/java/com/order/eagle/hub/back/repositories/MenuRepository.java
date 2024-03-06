package com.order.eagle.hub.back.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.eagle.hub.back.entities.Menu;

public interface MenuRepository extends JpaRepository<Menu, UUID>{

}
