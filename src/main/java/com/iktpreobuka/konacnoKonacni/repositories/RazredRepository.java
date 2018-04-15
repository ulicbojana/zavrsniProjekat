package com.iktpreobuka.konacnoKonacni.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;

public interface RazredRepository extends CrudRepository<RazredEntity, Integer>{
	RazredEntity findByGodina(Integer godina);
}
