package com.order.eagle.hub.back.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.entities.enums.TypePix;
import com.order.eagle.hub.back.entities.status.LifeCircle;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_store_pix")
@NoArgsConstructor
@Getter
@Setter
public class StorePix {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@NotBlank
	private String pixKey;
	
	@NotNull
	private TypePix typePix;
	
	@NotBlank
	private String owner;
	
	@NotBlank
	private String bank;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Embedded
	private LifeCircle status = new LifeCircle();
	
	@OneToOne
	@JoinColumn(name = "store_id")
	@JsonBackReference
	private Store store;
	
	public StorePix(UUID id, String pixKey, TypePix typePix, String owner, String bank, Store store) {
		this.id = id;
		this.pixKey = pixKey;
		this.typePix = typePix;
		this.owner = owner;
		this.bank = bank;
		this.store = store;
		setSituation(Situations.ACTIVATED);
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
