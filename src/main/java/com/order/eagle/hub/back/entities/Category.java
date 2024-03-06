package com.order.eagle.hub.back.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.eagle.hub.back.entities.enums.Situations;
import com.order.eagle.hub.back.entities.status.LifeCircle;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "tb_category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@NotBlank
	@EqualsAndHashCode.Include
	private String name;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Embedded
	private LifeCircle status = new LifeCircle();

	@ManyToOne
	@JoinColumn(name = "store_id")
	@JsonBackReference
	private Store store;
	
	@OneToMany(mappedBy = "category")
	@Setter(AccessLevel.NONE)
	@JsonBackReference
	private List<Product> products;
	
	public Category(UUID id, @NotBlank String name, @NotBlank Store store) {
		this.id = id;
		this.name = name;
		this.store = store;
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
