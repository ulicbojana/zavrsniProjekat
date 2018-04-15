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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class PredmetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	private String naziv;
	@Column(nullable=false)
	private Integer fond;
	@Version
	private Integer version;
	
	@JsonBackReference
	@ManyToMany(mappedBy = "predmet")
	
	private List<RazredEntity> razred = new ArrayList<>();
	
	@JsonBackReference
	@ManyToMany(mappedBy = "predmet")
	
	private List<NastavnikEntity> nastavnik = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "predmet", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<OcenaEntity> ocena = new ArrayList<>();

	
	public PredmetEntity() {

	}


	public PredmetEntity(Integer id, String naziv, Integer fond, Integer version,List<RazredEntity> razred, 
			List<NastavnikEntity> nastavnik,List<OcenaEntity> ocena) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.fond = fond;
		this.version=version;
		this.razred=razred;
		this.nastavnik=nastavnik;
		this.ocena=ocena;
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


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public List<RazredEntity> getRazred() {
		return razred;
	}


	public void setRazred(List<RazredEntity> razred) {
		this.razred = razred;
	}


	public List<NastavnikEntity> getNastavnik() {
		return nastavnik;
	}


	public void setNastavnik(List<NastavnikEntity> nastavnik) {
		this.nastavnik = nastavnik;
	}


	public List<OcenaEntity> getOcena() {
		return ocena;
	}


	public void setOcena(List<OcenaEntity> ocena) {
		this.ocena = ocena;
	}
	
	
	
	
	
	


}
