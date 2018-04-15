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
import com.iktpreobuka.konacnoKonacni.entities.RoditeljEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.RoditeljDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.UcenikDTO;
import com.iktpreobuka.konacnoKonacni.repositories.RoditeljRepozitory;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;

@RestController
@RequestMapping(path = "/api/v1/roditelj")

public class RoditeljController {
	
	@Autowired
	private UcenikRepository ucenikRepository;
	
	@Autowired
	private RoditeljRepozitory roditeljRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public  ResponseEntity<?> createNewRod(@RequestBody RoditeljDTO roditelj) {
        RoditeljEntity rod = new RoditeljEntity();
		
		rod.setIme(roditelj.getIme());
		rod.setPrezime(roditelj.getPrezime());
		rod.setJmbg(roditelj.getJmbg());
		rod.setEmail(roditelj.getEmail());
		
		roditeljRepository.save(rod);

		return new ResponseEntity<RoditeljEntity>(rod, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<RoditeljEntity> getAllrod() {
		return roditeljRepository.findAll();
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getRodByID(@PathVariable Integer id) {
		try {
			Iterable<RoditeljEntity> roditelj = getAllrod();
			for (RoditeljEntity roditeljEntity : roditelj) {
				if (roditeljEntity.getId().equals(id)) {

					return new ResponseEntity<RoditeljEntity>(roditeljEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
   @RequestMapping(method = RequestMethod.GET, value = "/jmbg/{jmbg}")
	
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> getRoditeljByJmbg(@PathVariable String jmbg) {
		try {
			Iterable<RoditeljEntity> roditelji = getAllrod();
			for (RoditeljEntity roditeljEntity : roditelji) {
				if (roditeljEntity.getJmbg().equals(jmbg)) {

					return new ResponseEntity<RoditeljEntity>(roditeljEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRoditelj(@PathVariable Integer id, @RequestParam String ime,
			@RequestParam String prezime, @RequestParam String jmbg, @RequestParam String email) {
		try {
			Iterable<RoditeljEntity> roditelj = getAllrod();
			for (RoditeljEntity roditeljEntity : roditelj) {
				if (roditeljEntity.getId().equals(id)) {
					RoditeljEntity rod = roditeljRepository.findOne(id);
					if (ime != null) {
						rod.setIme(ime);
					}
					if (prezime != null) {
						rod.setPrezime(prezime);
					}
					if (jmbg != null) {
						rod.setJmbg(jmbg);
					}
					if (email != null) {
						rod.setEmail(email);
					}

					roditeljRepository.save(rod);
					return new ResponseEntity<RoditeljEntity>(rod, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<RoditeljEntity> deleteRoditelj(@PathVariable Integer id) {

		RoditeljEntity rod = roditeljRepository.findOne(id);
		roditeljRepository.delete(id);
		return new ResponseEntity<RoditeljEntity>(rod, HttpStatus.OK);
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/ucenik")
	public ResponseEntity<?> addUcenikToRoditelj(@PathVariable Integer id, @RequestParam Integer ucenik) {
		try {
			Iterable<RoditeljEntity> roditelj = getAllrod();
			for (RoditeljEntity roditeljEntity : roditelj) {
				if (roditeljEntity.getId().equals(id)) {
					RoditeljEntity rod = roditeljRepository.findOne(id);

					UcenikEntity uce = ucenikRepository.findOne(ucenik);

					List<UcenikEntity> ucenici = rod.getUcenik();
					ucenici.add(uce);
					rod.setUcenik(ucenici);
					roditeljRepository.save(rod);
					return new ResponseEntity<RoditeljEntity>(rod, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Roditelj nije pronadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}