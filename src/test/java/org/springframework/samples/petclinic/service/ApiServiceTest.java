package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.samples.petclinic.owner.Owner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApiServiceTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private ApiService apiService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAnimalSound() {
		assertEquals("Bark", apiService.getAnimalSound("Dog"));
		assertEquals("Meow", apiService.getAnimalSound("Cat"));
		assertEquals("Tweet", apiService.getAnimalSound("Bird"));
		assertEquals("Unknown", apiService.getAnimalSound("Lion"));
	}

	@Test
	public void testGetOwners() {
		Map<String, Object> owner = Map.of("id", 1, "first_name", "John", "last_name", "Doe");

		when(jdbcTemplate.queryForList("select id, first_name, last_name from owners")).thenReturn(List.of(owner));

		List<Map<String, Object>> owners = apiService.getOwners();
		assertEquals(1, owners.size());
		assertEquals("John", owners.get(0).get("first_name"));
	}

	@Test
	public void testGetPets() {
		Map<String, Object> pet = Map.of("id", 1, "name", "Buddy", "birth_date", "2020-01-01");

		when(jdbcTemplate.queryForList("select id, name, birth_date from pets")).thenReturn(List.of(pet));

		List<Map<String, Object>> pets = apiService.getPets();
		assertEquals(1, pets.size());
		assertEquals("Buddy", pets.get(0).get("name"));
	}

	@Test
	public void testGetPetsByName() {
		Map<String, Object> pet = Map.of("id", 1, "name", "Buddy", "birth_date", "2020-01-01");

		when(jdbcTemplate.queryForList("select id, name, birth_date from pets where name = ?", "Buddy"))
				.thenReturn(List.of(pet));

		List<Map<String, Object>> pets = apiService.getPetsByName("Buddy");
		assertEquals(1, pets.size());
		assertEquals("Buddy", pets.get(0).get("name"));
	}

}
