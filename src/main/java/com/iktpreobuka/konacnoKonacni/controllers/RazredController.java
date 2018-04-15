package com.iktpreobuka.konacnoKonacni.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.konacnoKonacni.controllers.util.RESTError;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.RazredDTO;
import com.iktpreobuka.konacnoKonacni.repositories.OdeljenjeRepository;
import com.iktpreobuka.konacnoKonacni.repositories.PredmetRepository;
import com.iktpreobuka.konacnoKonacni.repositories.RazredRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;



@RestController
@RequestMapping(path = "/api/v1/razred")

public class RazredController {
	
	@Autowired

	private RazredRepository razredRepository;

	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired

	private PredmetRepository predmetRepository;

	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createRazred(@RequestBody RazredDTO razred) {
	      RazredEntity raz = new RazredEntity();
			raz.setGodina(razred.getGodina());
			razredRepository.save(raz);

			return new ResponseEntity<RazredEntity>(raz, HttpStatus.OK);
		}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	public Iterable<RazredEntity> getAllraz() {
		return razredRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getRazByID(@PathVariable Integer id) {
		try {
			Iterable<RazredEntity> razred = getAllraz();
			for (RazredEntity razredEntity : razred) {
				if (razredEntity.getId().equals(id)) {

					return new ResponseEntity<RazredEntity>(razredEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRazred(@PathVariable Integer id, @RequestParam Integer godina) {
		try {
			Iterable<RazredEntity> razred = getAllraz();
			for (RazredEntity razredEntity : razred) {
				if (razredEntity.getId().equals(id)) {
					RazredEntity raz = razredRepository.findOne(id);
					if (godina != null) {
						raz.setGodina(godina);
					}

					razredRepository.save(raz);
					return new ResponseEntity<RazredEntity>(raz, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<RazredEntity> deleteRazred(@PathVariable Integer id) {

		RazredEntity raz = razredRepository.findOne(id);
		razredRepository.delete(id);
		return new ResponseEntity<RazredEntity>(raz, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/predmet")
	public ResponseEntity<?> addPredmetToRazred(@PathVariable Integer id, @RequestParam Integer predmet) {
		try {
			Iterable<RazredEntity> razred = getAllraz();
			for (RazredEntity razredEntity : razred) {
				if (razredEntity.getId().equals(id)) {

					RazredEntity raz = razredRepository.findOne(id);

					PredmetEntity pred = predmetRepository.findOne(predmet);

					List<PredmetEntity> predmeti = raz.getPredmet();
					predmeti.add(pred);
					raz.setPredmet(predmeti);
					razredRepository.save(raz);
					return new ResponseEntity<RazredEntity>(raz, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Razred nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}




