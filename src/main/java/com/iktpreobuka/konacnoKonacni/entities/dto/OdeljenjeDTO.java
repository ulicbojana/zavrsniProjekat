package com.iktpreobuka.konacnoKonacni.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OdeljenjeDTO {
	
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("oznaka")
	private String oznaka;
	
	public OdeljenjeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OdeljenjeDTO(Integer id, String oznaka) {
		super();
		this.id = id;
		this.oznaka = oznaka;
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
	
	
	

}
