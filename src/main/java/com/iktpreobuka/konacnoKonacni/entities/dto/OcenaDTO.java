package com.iktpreobuka.konacnoKonacni.entities.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OcenaDTO {
	
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("vrednost")
	private Integer vrednost;
	@JsonProperty("datum")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date datum;
	@JsonProperty("polugodiste")
	private Integer polugodiste;
	@JsonProperty("zakljucna")
	private Boolean zakljucna;
	
	
	public OcenaDTO() {
		
	}


	public OcenaDTO(Integer id, Integer vrednost, Date datum, Integer polugodiste, Boolean zakljucna) {
		super();
		this.id = id;
		this.vrednost = vrednost;
		this.datum = datum;
		this.polugodiste = polugodiste;
		this.zakljucna = zakljucna;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getVrednost() {
		return vrednost;
	}


	public void setVrednost(Integer vrednost) {
		this.vrednost = vrednost;
	}


	public Date getDatum() {
		return datum;
	}


	public void setDatum(Date datum) {
		this.datum = datum;
	}


	public Integer getPolugodiste() {
		return polugodiste;
	}


	public void setPolugodiste(Integer polugodiste) {
		this.polugodiste = polugodiste;
	}


	public Boolean getZakljucna() {
		return zakljucna;
	}


	public void setZakljucna(Boolean zakljucna) {
		this.zakljucna = zakljucna;
	}
	
	
	
	

}
