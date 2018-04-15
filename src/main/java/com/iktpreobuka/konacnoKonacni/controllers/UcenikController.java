package com.iktpreobuka.konacnoKonacni.controllers;

import java.util.ArrayList;
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
import com.iktpreobuka.konacnoKonacni.entities.dto.RoditeljDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.UcenikDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.UcenikRoditeljDTO;
import com.iktpreobuka.konacnoKonacni.repositories.OcenaRepository;
import com.iktpreobuka.konacnoKonacni.repositories.OdeljenjeRepository;
import com.iktpreobuka.konacnoKonacni.repositories.RazredRepository;
import com.iktpreobuka.konacnoKonacni.repositories.RoditeljRepozitory;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;
import com.iktpreobuka.konacnoKonacni.services.UcenikDao;


@RestController
@RequestMapping(path = "/api/v1/ucenik")

public class UcenikController {

	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private RoditeljRepozitory roditeljRepository;
	
	@Autowired
	private OdeljenjeRepository odeljenjeRepository;
	
	@Autowired
	private RazredRepository razredRepository;
	
	@Autowired
	private OcenaRepository ocenaRepository;
	
	@Autowired
	private UcenikDao ucenikdao;
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUcenik(@RequestBody UcenikRoditeljDTO ucenik_roditelj,
											@RequestParam Integer razred, @RequestParam String odeljenje) {
		RazredEntity razr = razredRepository.findByGodina(razred);
		OdeljenjeEntity odelj = odeljenjeRepository.findByOznakaAndRazred(odeljenje, razr);
		
		UcenikDTO ucenik = ucenik_roditelj.getUcenik();
		RoditeljDTO roditelj = ucenik_roditelj.getRoditelj();
		
		RoditeljEntity rod = new RoditeljEntity();
		rod.setIme(roditelj.getIme());
		rod.setPrezime(roditelj.getPrezime());
		rod.setJmbg(roditelj.getJmbg());
		rod.setEmail(roditelj.getEmail());

		rod = roditeljRepository.save(rod);

		List<RoditeljEntity> roditelji = new ArrayList<RoditeljEntity>();
		roditelji.add(rod);
		
		UcenikEntity uce = new UcenikEntity();
		
		uce.setIme(ucenik.getIme());
		uce.setPrezime(ucenik.getPrezime());
		uce.setJmbg(ucenik.getJmbg());
		uce.setAdresa(ucenik.getAdresa());
		uce.setTelefon(ucenik.getTelefon());
		uce.setOdeljenje(odelj);
		uce.setRoditelj(roditelji);

		List<UcenikEntity> ucenici = new ArrayList<UcenikEntity>();
		ucenici.add(uce);
		rod.setUcenik(ucenici);
		ucenikRepository.save(uce);



		return new ResponseEntity<UcenikEntity>(uce, HttpStatus.OK);
	
	}

	
	

	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<UcenikEntity> getAllUsers(){ 
		return ucenikRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@JsonView(Views.Admin.class)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> getUserByID(@PathVariable Integer id) {
		try {
			Iterable<UcenikEntity> ucenici = getAllUsers();
			for (UcenikEntity ucenikEntity : ucenici) {
				if (ucenikEntity.getId().equals(id)) {

					return new ResponseEntity<UcenikEntity>(ucenikEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/jmbg/{jmbg}")
	@JsonView(Views.Admin.class)
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> getUserByJmbg(@PathVariable String jmbg) {
		try {
			Iterable<UcenikEntity> ucenici = getAllUsers();
			for (UcenikEntity ucenikEntity : ucenici) {
				if (ucenikEntity.getJmbg().equals(jmbg)) {

					return new ResponseEntity<UcenikEntity>(ucenikEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@JsonView(Views.Public.class)
	public ResponseEntity<?> updateUcenik(@PathVariable Integer id, @RequestParam String ime,
			@RequestParam String prezime, @RequestParam String jmbg, @RequestParam String adresa,
			@RequestParam String telefon) {
		try {
			Iterable<UcenikEntity> uce = getAllUsers();

			for (UcenikEntity ucenikEntity : uce) {
				if (ucenikEntity.getId().equals(id)) {
					UcenikEntity ucenik = ucenikRepository.findOne(id);
					if (ime != null) {
						ucenik.setIme(ime);
						;
					}
					if (prezime != null) {
						ucenik.setPrezime(prezime);
					}

					if (jmbg != null) {
						ucenik.setJmbg(jmbg);
					}
					if (adresa != null) {
						ucenik.setAdresa(adresa);
						;
					}
					if (telefon != null) {
						ucenik.setTelefon(telefon);
						;
					}

					ucenikRepository.save(ucenik);
					return new ResponseEntity<UcenikEntity>(ucenik, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<UcenikEntity> deleteUcenik(@PathVariable Integer id) {

		UcenikEntity uce = ucenikRepository.findOne(id);
		ucenikRepository.delete(id);
		return new ResponseEntity<UcenikEntity>(uce, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/odeljenje")
	public ResponseEntity<?> addOdeljenjeToAUcenik(@PathVariable Integer id, @RequestParam Integer odeljenje) {
		try {
			Iterable<UcenikEntity> ucenik = getAllUsers();

			for (UcenikEntity ucenikEntity : ucenik) {
				if (ucenikEntity.getId().equals(id)) {

					UcenikEntity uce = ucenikRepository.findOne(id);
					OdeljenjeEntity odelj = odeljenjeRepository.findOne(odeljenje);
					uce.setOdeljenje(odelj);
					ucenikRepository.save(uce);
					return new ResponseEntity<UcenikEntity>(uce, HttpStatus.OK);
			
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Ucenik nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/{id}")
	public List<UcenikEntity> addUceniktoOdeljenje(@PathVariable Integer id) {
	return ucenikdao.findUcenikByodeljenje(id);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/roditelj/{id}")
	public List<UcenikEntity> addUcenikByroditelj(@PathVariable Integer id) {
	return ucenikdao.findUcenikByroditelj(id);
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/odeljenje/sort/{id}")
	public List<UcenikEntity> addUceniktoOdeljenjeSort(@PathVariable Integer id) {
	return ucenikdao.findUcenikByodeljenjeSort(id);
	}
}
