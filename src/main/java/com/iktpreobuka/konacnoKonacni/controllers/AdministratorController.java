package com.iktpreobuka.konacnoKonacni.controllers;

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
import com.iktpreobuka.konacnoKonacni.entities.AdministratorEntity;
import com.iktpreobuka.konacnoKonacni.entities.RoditeljEntity;
import com.iktpreobuka.konacnoKonacni.entities.UcenikEntity;
import com.iktpreobuka.konacnoKonacni.entities.dto.AdministratorDTO;
import com.iktpreobuka.konacnoKonacni.entities.dto.UcenikDTO;
import com.iktpreobuka.konacnoKonacni.repositories.AdministratorRepository;
import com.iktpreobuka.konacnoKonacni.repositories.UcenikRepository;
import com.iktpreobuka.konacnoKonacni.security.Views;

@RestController
@RequestMapping(path = "/api/v1/administrator")

public class AdministratorController {
	
	@Autowired

	private AdministratorRepository administratorRepository;
	

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> cerateAdministrator(@RequestBody AdministratorDTO administrator) {
	       AdministratorEntity admin = new AdministratorEntity();
			
	        admin.setIme(administrator.getIme());
			admin.setPrezime(administrator.getPrezime());
			admin.setJmbg(administrator.getJmbg());
			
			
			administratorRepository.save(admin);

			return new ResponseEntity<AdministratorEntity>(admin, HttpStatus.OK);
		}

	@RequestMapping(method = RequestMethod.GET)
	@JsonView(Views.Public.class)
	@CrossOrigin(origins = "http://localhost:4200")
	public Iterable<AdministratorEntity> getAlladmin() {
		return administratorRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")

	public ResponseEntity<?> getAdminByID(@PathVariable Integer id) {
		try {
			Iterable<AdministratorEntity> administrator = getAlladmin();
			for (AdministratorEntity administratorEntity : administrator) {
				if (administratorEntity.getId().equals(id)) {

					return new ResponseEntity<AdministratorEntity>(administratorEntity, HttpStatus.OK);
				}
			}

			return new ResponseEntity<RESTError>(new RESTError(1, "Administrator nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	 @RequestMapping(method = RequestMethod.GET, value = "/jmbg/{jmbg}")
		
		@CrossOrigin(origins = "http://localhost:4200")
		public ResponseEntity<?> getAdministratorByJmbg(@PathVariable String jmbg) {
			try {
				Iterable<AdministratorEntity> administratori = getAlladmin();
				for (AdministratorEntity administratorEntity : administratori) {
					if (administratorEntity.getJmbg().equals(jmbg)) {

						return new ResponseEntity<AdministratorEntity>(administratorEntity, HttpStatus.OK);
					}
				}

				return new ResponseEntity<RESTError>(new RESTError(1, "Administrator nije nadjen"), HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAdministrator(@PathVariable Integer id, @RequestParam String ime,
			@RequestParam String prezime,@RequestParam String jmbg) {
		try {
			Iterable<AdministratorEntity> administrator = getAlladmin();
			for (AdministratorEntity administratorEntity : administrator) {
				if (administratorEntity.getId().equals(id)) {
					AdministratorEntity admin = administratorRepository.findOne(id);
					if (ime != null) {
						admin.setIme(ime);
					}
					if (prezime != null) {
						admin.setPrezime(prezime);
					}
					
					if (jmbg != null) {
						admin.setJmbg(jmbg);
					}
					

					administratorRepository.save(admin);
					return new ResponseEntity<AdministratorEntity>(admin, HttpStatus.OK);
				}
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Administrator nije nadjen"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occurred: " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<AdministratorEntity> deleteAdministrator(@PathVariable Integer id) {

		AdministratorEntity admin = administratorRepository.findOne(id);
		administratorRepository.delete(id);
		return new ResponseEntity<AdministratorEntity>(admin, HttpStatus.OK);
	}
	
	
}
