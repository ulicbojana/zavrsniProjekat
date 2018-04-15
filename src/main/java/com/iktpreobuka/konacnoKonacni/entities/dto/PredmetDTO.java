package com.iktpreobuka.konacnoKonacni.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PredmetDTO {
	
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("naziv")
	private String naziv;
	@JsonProperty("fond")
	private Integer fond;
	
	
	public PredmetDTO() {


	}


	public PredmetDTO(Integer id, String naziv, Integer fond) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.fond = fond;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public Integer getFond() {
		return fond;
	}


	public void setFond(Integer fond) {
		this.fond = fond;
	}
	
	
	
	

}
