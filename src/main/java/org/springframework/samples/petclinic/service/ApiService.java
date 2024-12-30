package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ApiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Récupère le son d'un animal donné.
	 * @param animal Le nom de l'animal.
	 * @return Le son de l'animal.
	 */
	public String getAnimalSound(String animal) {
		if (animal.equalsIgnoreCase("Dog")) {
			LOGGER.info("Dog");
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
	public List<Owner> getOwners() {
		return jdbcTemplate.query("select id, first_name, last_name from owners", (rs, rowNum) -> {
			Owner o = new Owner();
			o.setId(rs.getInt("id"));
			o.setFirstName(rs.getString("first_name"));
			o.setLastName(rs.getString("last_name"));
			return o;
		}).stream().toList();
	}

	/**
	 * Récupère une liste d'animaux de compagnie depuis la base de données.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	public List<Map<String, Object>> getPets() {
		return jdbcTemplate.queryForList("select id, name, birth_date from pets");
	}

	/**
	 * Récupère une liste d'animaux de compagnie par nom depuis la base de données.
	 * @param name Le nom de l'animal de compagnie.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	public List<Map<String, Object>> getPetsByName(String name) {
		// CVE-2017-8046: SQL Injection vulnerability
		return jdbcTemplate.queryForList("select id, name, birth_date from pets where name = ?", name);
	}

}
