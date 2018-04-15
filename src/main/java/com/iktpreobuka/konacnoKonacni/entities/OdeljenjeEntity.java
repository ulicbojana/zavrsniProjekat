package com.iktpreobuka.konacnoKonacni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class OdeljenjeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private String oznaka;
	@Version
	private Integer version;
	@JsonView(Views.Public.class)
	@JsonBackReference
	@OneToMany(mappedBy = "odeljenje", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<UcenikEntity> users = new ArrayList<>();
	@JsonView(Views.Public.class)
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "razred")
	private RazredEntity razred;
	@JsonView(Views.Public.class)
	@JsonBackReference
	@ManyToMany(mappedBy = "odeljenje")
	
	private List<NastavnikEntity> nastavnik = new ArrayList<>();

	public OdeljenjeEntity() {

	}

	public OdeljenjeEntity(Integer id, String oznaka, Integer version, List<UcenikEntity> users, RazredEntity razred,
			List<NastavnikEntity> nastavnik) {

		this.id = id;
		this.oznaka = oznaka;
		this.version = version;
		this.users = users;
		this.razred=razred;
		this.nastavnik=nastavnik;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOznaka() {
		return oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<UcenikEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UcenikEntity> users) {
		this.users = users;
	}

	public RazredEntity getRazred() {
		return razred;
	}

	public void setRazred(RazredEntity razred) {
		this.razred = razred;
	}

	public List<NastavnikEntity> getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(List<NastavnikEntity> nastavnik) {
		this.nastavnik = nastavnik;
	}
	
	
	

}


