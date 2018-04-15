package com.iktpreobuka.konacnoKonacni.services;

import java.util.List;

import com.iktpreobuka.konacnoKonacni.entities.OcenaEntity;



public interface OcenaDao {
	public List<OcenaEntity> findOcenaByUcenik(String ime,String prezime) ;

	public List<OcenaEntity> findOcenaByUcenikID(Integer id);

}