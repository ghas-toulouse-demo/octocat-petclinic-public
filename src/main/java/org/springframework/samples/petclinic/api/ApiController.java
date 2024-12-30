/**
 * This class represents the API controller for the Pet Clinic application.
 * It handles requests related to owners and pets.
 */
package org.springframework.samples.petclinic.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ApiService apiService;

	/**
	 * Retrieves the sound of a given animal.
	 * @param animal the name of the animal
	 * @return the sound of the animal
	 */
	@GetMapping("/animalSound/{animal}")
	public String getSound(@PathVariable String animal) {
		return apiService.getAnimalSound(animal);
	}

	/**
	 * Retrieves a list of owners from the database.
	 * @return A list of Owner objects representing the owners.
	 */
	@GetMapping("/owners")
	public List<Owner> getOwners() {
		return apiService.getOwners();
	}

	/**
	 * Retrieves a list of pets from the database.
	 * @return A list of maps representing the pets.
	 */
	@GetMapping("/pets")
	public List<Map<String, Object>> getPets() {
		return apiService.getPets();
	}

	/**
	 * Retrieves a list of pets by their name from the database.
	 * @param name the name of the pet
	 * @return A list of maps representing the pets with the given name.
	 */
	@GetMapping("/pets/{name}")
	public List<Map<String, Object>> getPetsByName(@PathVariable("name") String name) {
		return apiService.getPetsByName(name);
	}

}
