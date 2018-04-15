package com.iktpreobuka.konacnoKonacni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class RoditeljEntity extends Osoba {
	 @Column(nullable=false)
	 @JsonView(Views.Private.class)
	 private String email;
	 @Version
	 private Integer version;
	 @JsonView(Views.Public.class)
	 @JsonManagedReference
		@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
		@JoinTable(name = "Roditelj_Ucenik", joinColumns =
		{@JoinColumn(name = "Roditelj_id", nullable = false, updatable = false) },
		inverseJoinColumns = { @JoinColumn(name = "Ucenik_id",
		nullable = false, updatable = false) })
		private List<UcenikEntity> ucenik = new ArrayList<>();
		
	 
	 
	 public RoditeljEntity() {
			
		}

		public RoditeljEntity(Integer id, String ime, String prezime, String jmbg,String email,Integer version,
				List<UcenikEntity> ucenik) {
			super(id, ime, prezime, jmbg);
			this.email=email;
			this.version=version;
			this.ucenik=ucenik;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		
		
		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

		public List<UcenikEntity> getUcenik() {
			return ucenik;
		}

		public void setUcenik(List<UcenikEntity> ucenik) {
			this.ucenik= ucenik;
		}
		
		
		

	}
