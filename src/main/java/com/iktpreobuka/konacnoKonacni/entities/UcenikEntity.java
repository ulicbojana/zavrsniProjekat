package com.iktpreobuka.konacnoKonacni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class UcenikEntity extends Osoba {
	@JsonView(Views.Private.class)
	@Column(nullable=false)
	private String adresa;
	@JsonView(Views.Private.class)
	@Column(nullable=false)
	private String telefon;
	@JsonView(Views.Private.class)
	@Version
	private Integer version;
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "odeljenje")
	private OdeljenjeEntity odeljenje;
	@JsonView(Views.Admin.class)
	@JsonBackReference
	@ManyToMany(mappedBy = "ucenik", cascade = { CascadeType.ALL })
	
	private List<RoditeljEntity> roditelj = new ArrayList<>();
	@JsonView(Views.Admin.class)
	@JsonBackReference
	@OneToMany(mappedBy = "ucenik", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<OcenaEntity> ocena = new ArrayList<>();
	
	

	public UcenikEntity() {

	}

	public UcenikEntity(Integer id, String ime, String prezime, String jmbg, String adresa, String telefon,
			Integer version, OdeljenjeEntity odeljenje,List<RoditeljEntity> roditelj, List<OcenaEntity> ocena
			) {
		super(id, ime, prezime, jmbg);
		this.adresa = adresa;
		this.telefon = telefon;
		this.version=version;
		this.odeljenje=odeljenje;
		this.roditelj=roditelj;
		this.ocena=ocena;
		

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
	
	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public OdeljenjeEntity getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(OdeljenjeEntity odeljenje) {
		this.odeljenje = odeljenje;
	}

	public List<RoditeljEntity> getRoditelj() {
		return roditelj;
	}

	public void setRoditelj(List<RoditeljEntity> roditelj) {
		this.roditelj = roditelj;
	}

	public List<OcenaEntity> getOcena() {
		return ocena;
	}

	public void setOcena(List<OcenaEntity> ocena) {
		this.ocena = ocena;
	}



	
	
	
	

}
