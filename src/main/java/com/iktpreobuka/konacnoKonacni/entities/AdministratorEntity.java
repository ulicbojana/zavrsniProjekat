package com.iktpreobuka.konacnoKonacni.entities;



import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class AdministratorEntity extends Osoba {
	

	public AdministratorEntity() {
		
	}

	public AdministratorEntity(Integer id, String ime, String prezime, String jmbg ) {
		super(id, ime, prezime, jmbg);
		
	}


	
	

}
