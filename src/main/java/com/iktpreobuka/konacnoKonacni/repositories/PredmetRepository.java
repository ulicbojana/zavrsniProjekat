package com.iktpreobuka.konacnoKonacni.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.konacnoKonacni.entities.NastavnikEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;

public interface PredmetRepository extends CrudRepository<PredmetEntity, Integer> {
	
	List<PredmetEntity> findByNastavnik(NastavnikEntity nastavnik);
    List<PredmetEntity> findByRazred(RazredEntity razred);

}
