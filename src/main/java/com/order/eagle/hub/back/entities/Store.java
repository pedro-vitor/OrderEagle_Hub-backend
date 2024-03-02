package com.order.eagle.hub.back.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.entities.status.LifeCircle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_store")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@EqualsAndHashCode.Include
	private UUID id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Email
	@Column(unique = true)
	private String email;
	
	@NotBlank
	@JsonIgnore
	private String password;
	
	@NotBlank
	private String description;
	
	@NotBlank
	@Column(unique = true)
	private String phone;
	
	private String logo;
	
	private String banner;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Embedded
	private LifeCircle status = new LifeCircle();

	/*
	 * Associações
	 */
	
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private AddressStore address;
	
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private StorePix pix;
	
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Setter(AccessLevel.NONE)
	private List<StoreHour> storeHours = new ArrayList<>();
	
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Setter(AccessLevel.NONE)
	private List<SocialMedia> socialMedias = new ArrayList<>();
	
	@OneToOne(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Menu menu;
	
	public Store(UUID id, String name, String email, String password, String description, String phone) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.description = description;
		this.phone = phone;
		this.menu = new Menu(null, this);
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
	
	@JsonIgnore
	public Situations getSituation() {
		return this.status.getSituation();
	}
	
	public void setSituation(Situations situation) {
		this.status.setSituation(situation);
	}
}
