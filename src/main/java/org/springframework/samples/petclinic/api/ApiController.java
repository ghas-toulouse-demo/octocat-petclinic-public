/**
 * This class represents the API controller for the Pet Clinic application.
 * It handles requests related to owners and pets.
 */
package org.springframework.samples.petclinic.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ApiController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@RequestMapping(value = "/animalSound/{animal}", produces = "application/json")
	@GetMapping
	public String getSound(@PathVariable String animal) {
		if (animal == null) {
			logger.info("Oops! A null animal?");
		}
		else if (animal.equalsIgnoreCase("Dog")) {
			logger.info("Dog");
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
	 * Retrieves a list of owners from the database.
	 * @return A list of Owner objects representing the owners.
	 */
	@RequestMapping(value = "/owners", produces = "application/json")
	public List<Owner> getOwners() {
		return jdbcTemplate.query("select id, first_name, last_name from owners", (rs, rowNum) -> {
			Owner o = new Owner();
			o.setId(rs.getInt("id"));
			o.setFirstName(rs.getString("first_name"));
			o.setLastName(rs.getString("last_name"));
			return o;
		}).stream().toList();
	}

	@RequestMapping(value = "/pets", produces = "application/json")
	public List<Map<String, Object>> getPets() {
		return jdbcTemplate.queryForList("select id, name, birth_date from pets");
	}

	@RequestMapping(value = "/pets/{name}", produces = "application/json")
	public List<Map<String, Object>> getPetsByName(@PathVariable("name") String name) {
		String sql = "select id, name, birth_date from pets where name = ?";
		return jdbcTemplate.queryForList(sql, new Object[] { name });
	}

}
