package com.iktpreobuka.konacnoKonacni.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;

@MappedSuperclass

public abstract class Osoba {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private Integer id;
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String ime;
	@JsonView(Views.Public.class)
	@Column(nullable=false)
	private String prezime;
	
	@JsonView(Views.Admin.class)
	@Column(nullable=false)
	private String jmbg;

	public Osoba() {

	}

	public Osoba(Integer id, String ime, String prezime, String jmbg) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

}
