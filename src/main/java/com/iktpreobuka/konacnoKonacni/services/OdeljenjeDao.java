package com.iktpreobuka.konacnoKonacni.services;

import java.util.List;


import com.iktpreobuka.konacnoKonacni.entities.OdeljenjeEntity;

public interface OdeljenjeDao {
	public List<OdeljenjeEntity> findOdeljenjeByNastavnik(Integer id) ;
}
