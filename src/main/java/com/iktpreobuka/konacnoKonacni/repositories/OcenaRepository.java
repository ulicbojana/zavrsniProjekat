package com.iktpreobuka.konacnoKonacni.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.konacnoKonacni.entities.NastavnikEntity;
import com.iktpreobuka.konacnoKonacni.entities.OcenaEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;

public interface OcenaRepository extends CrudRepository<OcenaEntity, Integer>{
	List<OcenaEntity> findByPredmetAndUcenik(PredmetEntity predmet, UcenikEntity ucenik);
	List<OcenaEntity> findByUcenikAndNastavnik(UcenikEntity ucenik, NastavnikEntity nastavnik);
}
