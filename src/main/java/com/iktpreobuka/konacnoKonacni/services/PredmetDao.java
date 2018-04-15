package com.iktpreobuka.konacnoKonacni.services;

import java.util.List;

import com.iktpreobuka.konacnoKonacni.entities.OcenaEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;

public interface PredmetDao {
	
	public List<PredmetEntity> findPredmetByUcenik(Integer id) ;

	

}


