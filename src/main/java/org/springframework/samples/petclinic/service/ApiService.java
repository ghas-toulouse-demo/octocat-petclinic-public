/**
 * Cette classe représente le service API pour l'application Pet Clinic.
 * Elle gère les opérations de base de données liées aux propriétaires et aux animaux de compagnie.
 */
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApiService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Récupère une liste de propriétaires depuis la base de données.
	 * @return Une liste d'objets Owner représentant les propriétaires.
	 */
	public List<Owner> getOwners() {
		List<Owner> ownerList = jdbcTemplate.query("select id, first_name, last_name from owners", (rs, rowNum) -> {
			Owner o = new Owner();
			o.setId(rs.getInt("id"));
			o.setFirstName(rs.getString("first_name"));
			o.setLastName(rs.getString("last_name"));
			return o;
		}).stream().toList();
		return ownerList;
	}

	/**
	 * Récupère une liste d'animaux de compagnie depuis la base de données.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	public List<Map<String, Object>> getPets() {
		List<Map<String, Object>> pets = jdbcTemplate.queryForList("select id, name, birth_date from pets ");
		return pets;
	}

	/**
	 * Récupère une liste d'animaux de compagnie par nom depuis la base de données.
	 * @param name Le nom de l'animal de compagnie.
	 * @return Une liste de Map représentant les animaux de compagnie.
	 */
	public List<Map<String, Object>> getPetsByName(String name) {
		// CVE-2022-22965: Prevent SQL Injection by using parameterized query
		List<Map<String, Object>> pets = jdbcTemplate
				.queryForList("select id, name, birth_date from pets where name = ?", name);
		return pets;
	}

}
