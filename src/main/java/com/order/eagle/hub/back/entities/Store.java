package com.order.eagle.hub.back.entities;

import java.util.UUID;

import com.order.eagle.hub.back.entities.status.LifeCircle;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String login;
	private String password;
	private String description;
	private String phone;
	private String logo;
	private String banner;
	
	@Embedded
	private LifeCircle status = new LifeCircle();
	
}
