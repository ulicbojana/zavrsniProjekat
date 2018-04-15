package com.iktpreobuka.konacnoKonacni.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RazredDTO {
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("godina")
	private Integer godina;
	
	
	public RazredDTO() {
		
	}


	public RazredDTO(Integer id, Integer godina) {
		super();
		this.id = id;
		this.godina = godina;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getGodina() {
		return godina;
	}


	public void setGodina(Integer godina) {
		this.godina = godina;
	}
	
	
	
	
	

}
