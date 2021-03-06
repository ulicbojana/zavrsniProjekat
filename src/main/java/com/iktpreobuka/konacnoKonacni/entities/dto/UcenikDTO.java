package com.iktpreobuka.konacnoKonacni.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UcenikDTO {
	@JsonProperty("id")
	private Integer id;

	@JsonProperty("ime")
	private String ime;
	@JsonProperty("prezime")
	private String prezime;
	@JsonProperty("jmbg")
	private String jmbg;
	@JsonProperty("adresa")
	private String adresa;
	@JsonProperty("telefon")
	private String telefon;

	public UcenikDTO() {
		super();

	}
	
	

	public UcenikDTO(Integer id, String ime, String prezime, String jmbg, String adresa, String telefon) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
		this.adresa = adresa;
		this.telefon = telefon;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
