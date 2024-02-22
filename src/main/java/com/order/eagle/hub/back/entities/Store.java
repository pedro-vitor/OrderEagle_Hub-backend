package com.order.eagle.hub.back.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.entities.status.LifeCircle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "tb_store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Getter
	@Setter
	private UUID id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String login;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private String phone;
	
	@Getter
	@Setter
	private String logo;
	
	@Getter
	@Setter
	private String banner;
	
	@Embedded
	private LifeCircle status = new LifeCircle();
	
	@Getter
	@Setter
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private AddressStore address;
	
	public Store(UUID id, String name, String login, String password, String description, String phone) {
		
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.description = description;
		this.phone = phone;
		this.setSituation(Situations.ACTIVATED);
	}
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "America/Sao_Paulo")
	public LocalDateTime getCreated_at() {
		return this.status.getCreated_at();
	}
	
	public void setCreated_at(LocalDateTime created_at) {
		this.status.setCreated_at(created_at);
	}
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "America/Sao_Paulo")
	public LocalDateTime getUpdated_at() {
		return this.status.getUpdated_at();
	}
	
	public void setUpdated_at(LocalDateTime updated_at) {
		this.status.setUpdated_at(updated_at);
	}
	
	public Situations getSituation() {
		return this.status.getSituation();
	}
	
	public void setSituation(Situations situation) {
		this.status.setSituation(situation);
	}
}
