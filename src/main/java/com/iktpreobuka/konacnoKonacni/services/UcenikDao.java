package com.iktpreobuka.konacnoKonacni.services;

import java.util.List;


import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;

public interface UcenikDao {
	 public List<UcenikEntity> findUcenikByodeljenje(Integer id) ;
	 
	 
	 public List<UcenikEntity> findUcenikByodeljenjeSort(Integer id) ;


     public List<UcenikEntity> findUcenikByroditelj(Integer id) ;
}

