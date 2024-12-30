package org.springframework.samples.petclinic.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class for handling API-related database operations.
 */
@Service
public class ApiService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ApiService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Retrieves the sound of a given animal.
	 * @param animal the name of the animal
	 * @return the sound of the animal
	 */
	public String getAnimalSound(String animal) {
		if (animal == null) {
			return "Unknown";
		}
		switch (animal.toLowerCase()) {
		case "dog":
			return "Bark";
		case "cat":
			return "Meow";
		case "bird":
			return "Tweet";
		default:
			return "Unknown";
		}
	}

	/**
	 * Retrieves a list of owners from the database.
	 * @return A list of Owner objects representing the owners.
	 */
	public List<Owner> getOwners() {
		return jdbcTemplate.query("select id, first_name, last_name from owners", (rs, rowNum) -> {
			Owner o = new Owner();
			o.setId(rs.getInt("id"));
			o.setFirstName(rs.getString("first_name"));
			o.setLastName(rs.getString("last_name"));
			return o;
		});
	}

	/**
	 * Retrieves a list of pets from the database.
	 * @return A list of maps representing the pets.
	 */
	public List<Map<String, Object>> getPets() {
		return jdbcTemplate.queryForList("select id, name, birth_date from pets");
	}

	/**
	 * Retrieves a list of pets by their name from the database.
	 * @param name the name of the pet
	 * @return A list of maps representing the pets with the given name.
	 */
	public List<Map<String, Object>> getPetsByName(String name) {
		// Prevent SQL injection by using parameterized queries
		return jdbcTemplate.queryForList("select id, name, birth_date from pets where name = ?", name);
	}

}
