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
import com.iktpreobuka.konacnoKonacni.entities.RoditeljEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.NastavnikDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.OdeljenjeDTO;
import com.iktpreobuka.konacnoKonacni.repositories.NastavnikRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OcenaRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OdeljenjeRepository;
import com.iktpreobuka.konacnoKonacni.repositories.PredmetRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;

@RestController
@RequestMapping(path = "/api/v1/nastavnik")


public class NastavnikController {
	@Autowired
	private PredmetRepository predmetRepository;

	@Autowired
	private NastavnikRepository nastavnikRepository;
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired

	private OcenaRepository ocenaRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> cerateNastavnik(@RequestBody NastavnikDTO nastavnik) {
	       NastavnikEntity nast = new NastavnikEntity();
			
	        nast.setIme(nastavnik.getIme());
			nast.setPrezime(nastavnik.getPrezime());
			nast.setJmbg(nastavnik.getJmbg());
			nast.setEmail(nastavnik.getEmail());
			
			nastavnikRepository.save(nast);

			return new ResponseEntity<NastavnikEntity>(nast, HttpStatus.OK);
		}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<NastavnikEntity> getAllnast() {
		return nastavnikRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getNastByID(@PathVariable Integer id) {
		try {
			Iterable<NastavnikEntity> nastavnik = getAllnast();
			for (NastavnikEntity nastavnikEntity : nastavnik) {
				if (nastavnikEntity.getId().equals(id)) {

					return new ResponseEntity<NastavnikEntity>(nastavnikEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @RequestMapping(method = RequestMethod.GET, value = "/jmbg/{jmbg}")
	 @JsonView(Views.Admin.class)
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseEntity<?> getNastavnikByJmbg(@PathVariable String jmbg) {
			try {
				Iterable<NastavnikEntity> nastavnici = getAllnast();
				for (NastavnikEntity nastavnikEntity : nastavnici) {
					if (nastavnikEntity.getJmbg().equals(jmbg)) {

						return new ResponseEntity<NastavnikEntity>(nastavnikEntity, HttpStatus.OK);
					}
				}

				return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije nadjen"), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateNastavnik(@PathVariable Integer id, @RequestParam String ime,
			@RequestParam String prezime,@RequestParam String jmbg,@RequestParam String email) {
		try {
			Iterable<NastavnikEntity> nastavnik = getAllnast();
			for (NastavnikEntity nastavnikEntity : nastavnik) {
				if (nastavnikEntity.getId().equals(id)) {
					NastavnikEntity nast = nastavnikRepository.findOne(id);
					if (ime != null) {
						nast.setIme(ime);
					}
					if (prezime != null) {
						nast.setPrezime(prezime);
					}
					
					if (jmbg != null) {
						nast.setJmbg(jmbg);
					}
					if (email != null) {
						nast.setEmail(email);
					}

					nastavnikRepository.save(nast);
					return new ResponseEntity<NastavnikEntity>(nast, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<NastavnikEntity> deleteNastavnik(@PathVariable Integer id) {

		NastavnikEntity nast = nastavnikRepository.findOne(id);
		nastavnikRepository.delete(id);
		return new ResponseEntity<NastavnikEntity>(nast, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/predmet")
	public ResponseEntity<?> addPredmetToNastavnik(@PathVariable Integer id, @RequestParam Integer predmet) {
		try {
			Iterable<NastavnikEntity> nastavnik = getAllnast();
			for (NastavnikEntity nastavnikEntity : nastavnik) {
				if (nastavnikEntity.getId().equals(id)) {

					NastavnikEntity nast = nastavnikRepository.findOne(id);

					PredmetEntity pred = predmetRepository.findOne(predmet);

					List<PredmetEntity> predmeti = nast.getPredmet();
					predmeti.add(pred);
					nast.setPredmet(predmeti);
					nastavnikRepository.save(nast);
					return new ResponseEntity<NastavnikEntity>(nast, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/odeljenje")
	public ResponseEntity<?> addOdeljenjeToNastavnik(@PathVariable Integer id, @RequestParam Integer odeljenje) {
		try {
			Iterable<NastavnikEntity> nastavnik = getAllnast();
			for (NastavnikEntity nastavnikEntity : nastavnik) {
				if (nastavnikEntity.getId().equals(id)) {

					NastavnikEntity nast = nastavnikRepository.findOne(id);

					OdeljenjeEntity odelj =odeljenjeRepository.findOne(odeljenje);

					List<OdeljenjeEntity> odeljenja = nast.getOdeljenje();
					odeljenja.add(odelj);
					nast.setOdeljenje(odeljenja);;
					nastavnikRepository.save(nast);
					return new ResponseEntity<NastavnikEntity>(nast, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Nastavnik nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(1, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
