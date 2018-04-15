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
import com.iktpreobuka.konacnoKonacni.entities.NastavnikEntity;
import com.iktpreobuka.konacnoKonacni.entities.OdeljenjeEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.RazredEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.PredmetDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.RazredDTO;
import com.iktpreobuka.konacnoKonacni.repositories.NastavnikRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OcenaRepository;
import com.iktpreobuka.konacnoKonacni.repositories.PredmetRepository;
import com.iktpreobuka.konacnoKonacni.repositories.RazredRepository;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;
import com.iktpreobuka.konacnoKonacni.services.PredmetDao;
import com.iktpreobuka.konacnoKonacni.services.UcenikDao;


@RestController
@RequestMapping(path = "/api/v1/predmet")

public class PredmetController {
	
	@Autowired

	private RazredRepository razredRepository;

	@Autowired

	private PredmetRepository predmetRepository;
	
	@Autowired

	private OcenaRepository ocenaRepository;
	
	@Autowired
	private PredmetDao predmetdao;
	
	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createNewPredmet(@RequestBody PredmetDTO predmet) {
	      PredmetEntity pred = new PredmetEntity();
			pred.setNaziv(predmet.getNaziv());
			pred.setFond(predmet.getFond());
			predmetRepository.save(pred);

			return new ResponseEntity<PredmetEntity>(pred, HttpStatus.OK);
		}


	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	public Iterable<PredmetEntity> getAllpred() {
		return predmetRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getPredByID(@PathVariable Integer id) {
		try {
			Iterable<PredmetEntity> predmet = getAllpred();
			for (PredmetEntity predmetEntity : predmet) {
				if (predmetEntity.getId().equals(id)) {

					return new ResponseEntity<PredmetEntity>(predmetEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updatePredmet(@PathVariable Integer id, @RequestParam String naziv,
			@RequestParam Integer fond) {
		try {
			
			Iterable<PredmetEntity> predmet = getAllpred();
			for (PredmetEntity predmetEntity : predmet) {
				if (predmetEntity.getId().equals(id)) {
			PredmetEntity pred = predmetRepository.findOne(id);
			if (naziv != null) {
				pred.setNaziv(naziv);
			}
			if (fond != null) {
				pred.setFond(fond);
			}

			predmetRepository.save(pred);
			return new ResponseEntity<PredmetEntity>(pred, HttpStatus.OK);
				}}
			return new ResponseEntity<RESTError>(new RESTError(1, "Predmet nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<PredmetEntity> deletePredmet(@PathVariable Integer id) {

		PredmetEntity pred = predmetRepository.findOne(id);
		razredRepository.delete(id);
		return new ResponseEntity<PredmetEntity>(pred, HttpStatus.OK);
	}
	
	@JsonView(Views.Public.class)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{id}")
	public List<PredmetEntity> addPredmetiByUcenik(@PathVariable Integer id) {
		UcenikEntity ucenik = ucenikRepository.findOne(id);
		RazredEntity razred = ucenik.getOdeljenje().getRazred();
		return razred.getPredmet();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value="/nastavnik/{id}")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<PredmetEntity>> getPredmetByNastavnik(@PathVariable Integer id) {
		NastavnikEntity nastavnik = nastavnikRepository.findOne(id);
		return new ResponseEntity<>(predmetRepository.findByNastavnik(nastavnik), HttpStatus.OK);
	}

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET, value="/razred/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<PredmetEntity>> getPredmetByRazred(@PathVariable Integer id) {
        RazredEntity razred = razredRepository.findOne(id);
        return new ResponseEntity<>(predmetRepository.findByRazred(razred), HttpStatus.OK);
    }

}
