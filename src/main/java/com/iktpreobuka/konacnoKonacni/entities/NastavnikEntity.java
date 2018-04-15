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
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class NastavnikEntity extends Osoba {
	@Column(nullable=false)
	private String email;
	
	@Version
	private Integer version;
	
	@JsonView(Views.Public.class)
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Nastavnik_Predmet", joinColumns =
	{@JoinColumn(name = "Nastavnik_id", nullable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "Predmet_id",
	nullable = false, updatable = false) })
	private List<PredmetEntity> predmet = new ArrayList<>();
	
	@JsonView(Views.Public.class)
	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Nastavnik_Odeljenje", joinColumns =
	{@JoinColumn(name = "Nastavnik_id", nullable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "Odeljenje_id",
	nullable = false, updatable = false) })
	private List<OdeljenjeEntity> odeljenje = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "nastavnik", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<OcenaEntity> ocena = new ArrayList<>();
	

	public NastavnikEntity() {

	}

	public NastavnikEntity(Integer id, String ime, String prezime,String jmbg, String email,Integer version, 
			List<PredmetEntity> predmet,List<OdeljenjeEntity> odeljenje, List<OcenaEntity> ocena) {
		super(id, ime, prezime, jmbg);
		this.email = email;
		this.version=version;
		this.predmet=predmet;
		this.odeljenje=odeljenje;
		this.ocena=ocena;
		

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

	public List<PredmetEntity> getPredmet() {
		return predmet;
	}

	public void setPredmet(List<PredmetEntity> predmet) {
		this.predmet = predmet;
	}

	public List<OdeljenjeEntity> getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(List<OdeljenjeEntity> odeljenje) {
		this.odeljenje = odeljenje;
	}

	public List<OcenaEntity> getOcena() {
		return ocena;
	}

	public void setOcena(List<OcenaEntity> ocena) {
		this.ocena = ocena;
	}
	
	
	
	
	
	


	}
