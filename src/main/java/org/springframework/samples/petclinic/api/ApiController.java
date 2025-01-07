/**
 * Cette classe représente le contrôleur API pour l'application Pet Clinic.
 * Elle gère les requêtes liées aux propriétaires et aux animaux de compagnie.
 */
package org.springframework.samples.petclinic.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ApiService;
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
	 * Récupère le son d'un animal donné.
	 * @param animal Le nom de l'animal.
	 * @return Le son de l'animal.
	 */
	@GetMapping(value = "/animalSound/{animal}", produces = "application/json")
	public String getSound(@PathVariable String animal) {
		if (animal == null) {
			System.out.println("Oops! Un animal null?");
		}
		else if (animal.equalsIgnoreCase("Dog")) {
			System.out.println("Dog");
			return "Bark";
		}
		else if (animal.equalsIgnoreCase("Cat")) {
			return "Meow";
		}
		else if (animal.equalsIgnoreCase("Bird")) {
			return "Tweet";
		}
		return "Unknown";
	}

	/**
	 * Récupère une liste de propriétaires depuis la base de données.
	 * @return Une liste d'objets Owner représentant les propriétaires.
	 */
	@GetMapping(value = "/owners", produces = "application/json")
	public List<Owner> getOwners() {
		return apiService.getOwners();
	}

	/**
	 * Récupère une liste d'animaux de compagnie depuis la base de données.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	@GetMapping(value = "/pets", produces = "application/json")
	public List<Map<String, Object>> getPets() {
		return apiService.getPets();
	}

	/**
	 * Récupère une liste d'animaux de compagnie par nom depuis la base de données.
	 * @param name Le nom de l'animal de compagnie.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	@GetMapping(value = "/pets/{name}", produces = "application/json")
	public List<Map<String, Object>> getPetsByName(@PathVariable("name") String name) {
		return apiService.getPetsByName(name);
	}
}