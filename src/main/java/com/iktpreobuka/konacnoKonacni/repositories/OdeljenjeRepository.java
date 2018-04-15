package com.iktpreobuka.konacnoKonacni.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.konacnoKonacni.entities.OdeljenjeEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;

public interface OdeljenjeRepository extends CrudRepository<OdeljenjeEntity, Integer> {
	OdeljenjeEntity findByOznakaAndRazred(String oznaka, RazredEntity razred);
}
