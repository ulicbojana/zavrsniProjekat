package com.iktpreobuka.konacnoKonacni.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.iktpreobuka.konacnoKonacni.entities.OdeljenjeEntity;


@Service

public class OdeljenjeDaoImpl implements OdeljenjeDao{
	
	@PersistenceContext
    EntityManager em;

	@Override
	public List<OdeljenjeEntity> findOdeljenjeByNastavnik(Integer id) {
		String sql = "select a " +
				"from OdeljenjeEntity a " +
				"left join fetch a.nastavnik u " +
				"where u.id= :id  ";
		
		Query query = em.createQuery(sql);
        query.setParameter("id", id);
        
        List<OdeljenjeEntity> result = new ArrayList<OdeljenjeEntity>();
        result = query.getResultList();
        return result;
	}


}
