package com.iktpreobuka.konacnoKonacni.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OcenaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	@Column
	@JsonView(Views.Public.class)
	private Integer vrednost;
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@JsonView(Views.Public.class)
	private Date datum; 
	@Column
	@JsonView(Views.Public.class)
	private Integer polugodiste;
	@Column
	@JsonView(Views.Public.class)
	private Boolean zakljucna;
	@Version
	private Integer version;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ucenik")
	private UcenikEntity ucenik;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "predmet")
	@JsonView(Views.Private.class)
	private PredmetEntity predmet;
	
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "nastavnik")
	private NastavnikEntity nastavnik;
	
	
	
	
	public OcenaEntity() {
	
	}



	public OcenaEntity(Integer id, Integer vrednost, Date datum, Integer polugodiste, Boolean zakljucna,
			Integer version,UcenikEntity ucenik,PredmetEntity predmet, NastavnikEntity nastavnik) {
		super();
		this.id = id;
		this.vrednost = vrednost;
		this.datum = datum;
		this.polugodiste = polugodiste;
		this.zakljucna = zakljucna;
		this.version = version;
		this.ucenik=ucenik;
		this.predmet=predmet;
		this.nastavnik=nastavnik;
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



	public Integer getVersion() {
		return version;
	}



	public void setVersion(Integer version) {
		this.version = version;
	}



	public UcenikEntity getUcenik() {
		return ucenik;
	}



	public void setUcenik(UcenikEntity ucenik) {
		this.ucenik = ucenik;
	}



	public PredmetEntity getPredmet() {
		return predmet;
	}



	public void setPredmet(PredmetEntity predmet) {
		this.predmet = predmet;
	}



	public NastavnikEntity getNastavnik() {
		return nastavnik;
	}



	public void setNastavnik(NastavnikEntity nastavnik) {
		this.nastavnik = nastavnik;
	}
	
	
	
	
	
	
	
	
	

}
