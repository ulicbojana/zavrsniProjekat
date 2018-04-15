package com.iktpreobuka.konacnoKonacni.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.controllers.util.RESTError;
import com.iktpreobuka.konacnoKonacni.entities.OdeljenjeEntity;
import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;
import com.iktpreobuka.konacnoKonacni.entities.RoditeljEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.OdeljenjeDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.RoditeljDTO;
import com.iktpreobuka.konacnoKonacni.repositories.NastavnikRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OdeljenjeRepository;
import com.iktpreobuka.konacnoKonacni.repositories.RazredRepository;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;
import com.iktpreobuka.konacnoKonacni.services.OdeljenjeDao;



@RestController
@RequestMapping(path = "/api/v1/odeljenje")

public class OdeljenjeController {
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;

	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private RazredRepository razredRepository;
	
	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	@Autowired
	private OdeljenjeDao odeljenjedao;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createNewOdelj(@RequestBody OdeljenjeDTO odeljenje) {
       OdeljenjeEntity od = new OdeljenjeEntity();
		
		od.setOznaka(odeljenje.getOznaka());
		odeljenjeRepository.save(od);

		return new ResponseEntity<OdeljenjeEntity>(od, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	public Iterable<OdeljenjeEntity> getAllodelj() {
		return odeljenjeRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getOdeljByID(@PathVariable Integer id) {
		try {
			Iterable<OdeljenjeEntity> odeljenje = getAllodelj();
			for (OdeljenjeEntity odeljenjeEntity : odeljenje) {
				if (odeljenjeEntity.getId().equals(id)) {

					return new ResponseEntity<OdeljenjeEntity>(odeljenjeEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije nadjeno"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateOdeljenje(@PathVariable Integer id, @RequestParam String oznaka) {
		try {
			Iterable<OdeljenjeEntity> odeljenje = getAllodelj();
			for (OdeljenjeEntity odeljenjeEntity : odeljenje) {
				if (odeljenjeEntity.getId().equals(id)) {

					OdeljenjeEntity od = odeljenjeRepository.findOne(id);
					if (oznaka != null) {
						od.setOznaka(oznaka);
					}

					odeljenjeRepository.save(od);
					return new ResponseEntity<OdeljenjeEntity>(od, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije nadjeno"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<OdeljenjeEntity> deleteOdeljenje(@PathVariable Integer id) {

		OdeljenjeEntity od = odeljenjeRepository.findOne(id);
		odeljenjeRepository.delete(id);
		return new ResponseEntity<OdeljenjeEntity>(od, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/razred")
	public ResponseEntity<?> addRazredToOdeljenje(@PathVariable Integer id, @RequestParam Integer razred) {
		try {
			Iterable<OdeljenjeEntity> odeljenje = getAllodelj();
			for (OdeljenjeEntity odeljenjeEntity : odeljenje) {
				if (odeljenjeEntity.getId().equals(id)) {
					OdeljenjeEntity ode = odeljenjeRepository.findOne(id);
					RazredEntity raz = razredRepository.findOne(razred);
					ode.setRazred(raz);
					odeljenjeRepository.save(ode);
					return new ResponseEntity<OdeljenjeEntity>(ode, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Odeljenje nije pronadjeno"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/nastavnik/{id}")
	public List<OdeljenjeEntity> addOdeljenjeByNastavnik(@PathVariable Integer id) {
	return odeljenjedao.findOdeljenjeByNastavnik(id);
	}



}
