package com.iktpreobuka.konacnoKonacni.controllers;

import java.util.Date;
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
import com.iktpreobuka.konacnoKonacni.entities.OcenaEntity;
import com.iktpreobuka.konacnoKonacni.entities.PredmetEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.OcenaDTO;
import com.iktpreobuka.konacnoKonacni.repositories.NastavnikRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OcenaRepository;
import com.iktpreobuka.konacnoKonacni.repositories.PredmetRepository;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;
import com.iktpreobuka.konacnoKonacni.services.OcenaDao;

@RestController
@RequestMapping(path = "/api/v1/ocena")

public class OcenaController {

	@Autowired
	private OcenaRepository ocenaRepository;

	@Autowired
	private UcenikRepository ucenikRepository;

	@Autowired
	private PredmetRepository predmetRepository;

	@Autowired
	private NastavnikRepository nastavnikRepository;

	@Autowired
	private OcenaDao ocenadao;

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ResponseEntity<?>
	 * createNewOcena(@RequestBody OcenaDTO ocena) { OcenaEntity oc = new
	 * OcenaEntity(); oc.setVrednost(ocena.getVrednost());
	 * oc.setDatum(ocena.getDatum()); oc.setPolugodiste(ocena.getPolugodiste());
	 * oc.setZakljucna(ocena.getZakljucna()); ocenaRepository.save(oc);
	 * 
	 * return new ResponseEntity<OcenaEntity>(oc, HttpStatus.OK); }
	 */
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewOcena(@RequestParam Integer predmetId, @RequestParam Integer ucenikId,
			@RequestParam Integer nastavnikId, @RequestBody OcenaDTO ocena) {
		if (predmetRepository.exists(predmetId)) {
			if (ucenikRepository.exists(ucenikId)) {
				if (nastavnikRepository.exists(nastavnikId)) {
					if ((ocena.getVrednost() >= 1) && (ocena.getVrednost() <= 5)) {
						PredmetEntity predmet = predmetRepository.findOne(predmetId);
						UcenikEntity ucenik = ucenikRepository.findOne(ucenikId);
						NastavnikEntity nastavnik = nastavnikRepository.findOne(nastavnikId);
						OcenaEntity oc = new OcenaEntity();
						oc.setPredmet(predmet);
						oc.setUcenik(ucenik);
						oc.setNastavnik(nastavnik);
						oc.setVrednost(ocena.getVrednost());
						oc.setDatum(ocena.getDatum());
						oc.setPolugodiste(ocena.getPolugodiste());
						oc.setZakljucna(ocena.getZakljucna());
						// oc.setDeleted(false);
						ocenaRepository.save(oc);
						return new ResponseEntity<OcenaEntity>(oc, HttpStatus.OK);
					}
					return new ResponseEntity<RESTError>(new RESTError(1, "Ocena mora biti izmedju 1 i 5"),
							HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<RESTError>(new RESTError(2, "Nastavnik ne postoji u bazi"),
						HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(3, "Ucenik ne postoji u bazi"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RESTError>(new RESTError(4, "Predmet ne postoji u bazi"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	public Iterable<OcenaEntity> getAlloce() {
		return ocenaRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getOcenByID(@PathVariable Integer id) {
		try {
			Iterable<OcenaEntity> ocena = getAlloce();
			for (OcenaEntity ocenaEntity : ocena) {
				if (ocenaEntity.getId().equals(id)) {

					return new ResponseEntity<OcenaEntity>(ocenaEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije nadjena"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateOcena(@PathVariable Integer id, @RequestParam Integer vrednost,
			@RequestParam Date datum, @RequestParam Integer polugodiste, @RequestParam Boolean zakljucna) {
		try {
			Iterable<OcenaEntity> ocena = getAlloce();
			for (OcenaEntity ocenaEntity : ocena) {
				if (ocenaEntity.getId().equals(id)) {

					OcenaEntity oc = ocenaRepository.findOne(id);
					if (vrednost != null) {
						oc.setVrednost(vrednost);
					}
					if (datum != null) {
						oc.setDatum(datum);
					}
					if (datum != null) {
						oc.setPolugodiste(polugodiste);
					}
					if (zakljucna != null) {
						oc.setZakljucna(zakljucna);
					}

					ocenaRepository.save(oc);
					return new ResponseEntity<OcenaEntity>(oc, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije nadjena"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<OcenaEntity> deleteOcena(@PathVariable Integer id) {

		OcenaEntity oc = ocenaRepository.findOne(id);
		ocenaRepository.delete(id);
		return new ResponseEntity<OcenaEntity>(oc, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/ucenik")
	public ResponseEntity<?> addUcenikToOcena(@PathVariable Integer id, @RequestParam Integer ucenik) {
		try {
			Iterable<OcenaEntity> ocena = getAlloce();
			for (OcenaEntity ocenaEntity : ocena) {
				if (ocenaEntity.getId().equals(id)) {
					OcenaEntity oce = ocenaRepository.findOne(id);
					UcenikEntity uce = ucenikRepository.findOne(ucenik);
					oce.setUcenik(uce);
					ocenaRepository.save(oce);
					return new ResponseEntity<OcenaEntity>(oce, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/nastavnik")
	public ResponseEntity<?> addNastavnikToOcena(@PathVariable Integer id, @RequestParam Integer nastavnik) {
		try {
			Iterable<OcenaEntity> ocena = getAlloce();
			for (OcenaEntity ocenaEntity : ocena) {
				if (ocenaEntity.getId().equals(id)) {
					OcenaEntity oce = ocenaRepository.findOne(id);
					NastavnikEntity nast = nastavnikRepository.findOne(nastavnik);
					oce.setNastavnik(nast);
					ocenaRepository.save(oce);
					return new ResponseEntity<OcenaEntity>(oce, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/predmet")
	public ResponseEntity<?> addPredmetToOcena(@PathVariable Integer id, @RequestParam Integer predmet) {
		try {
			Iterable<OcenaEntity> ocena = getAlloce();
			for (OcenaEntity ocenaEntity : ocena) {
				if (ocenaEntity.getId().equals(id)) {
					OcenaEntity oce = ocenaRepository.findOne(id);
					PredmetEntity pred = predmetRepository.findOne(predmet);
					oce.setPredmet(pred);
					ocenaRepository.save(oce);
					return new ResponseEntity<OcenaEntity>(oce, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ocena nije pronadjena"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@JsonView(Views.Private.class)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{id}")
	public List<OcenaEntity> addOcenatoUcenik(@PathVariable Integer id) {
		return ocenadao.findOcenaByUcenikID(id);
	}

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik")
	public List<OcenaEntity> addOcenatoUcenik(@RequestParam String ime, @RequestParam String prezime) {
		return ocenadao.findOcenaByUcenik(ime, prezime);
	}

	@JsonView(Views.Private.class)
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{ucenikId}/predmet/{predmetId}")
	public List<OcenaEntity> addOcenatoUcenik(@PathVariable Integer ucenikId, @PathVariable Integer predmetId) {
		PredmetEntity predmet = predmetRepository.findOne(predmetId);
		UcenikEntity ucenik = ucenikRepository.findOne(ucenikId);
		return ocenaRepository.findByPredmetAndUcenik(predmet, ucenik);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET, value = "/ucenik/{ucenikId}/nastavnik/{nastavnikId}")
	@JsonView(Views.Public.class)
	public ResponseEntity<List<OcenaEntity>> getOcenaByUcenikAndNastavnik(@PathVariable Integer ucenikId,
			@PathVariable Integer nastavnikId) {
		UcenikEntity ucenik = ucenikRepository.findOne(ucenikId);
		NastavnikEntity nastavnik = nastavnikRepository.findOne(nastavnikId);

		return new ResponseEntity<>(ocenaRepository.findByUcenikAndNastavnik(ucenik, nastavnik), HttpStatus.OK);
	}

}
