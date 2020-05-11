package com.example.springdata;

import com.example.springdata.model.Country;
import com.example.springdata.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringdataApplicationTests {

	public static final String[][] COUNTRY_INIT_DATA = {{"Australia", "AU"},
			{"Canada", "CA"}, {"France", "FR"}, {"Hong Kong", "HK"},
			{"Iceland", "IC"}, {"Japan", "JP"}, {"Nepal", "NP"},
			{"Russian Federation", "RU"}, {"Sweden", "SE"},
			{"Switzerland", "CH"}, {"United Kingdom", "GB"},
			{"United States", "US"}};

	@BeforeEach
	public void setup() {
		initExpectedCountryLists();
	}


	@Autowired
	private CountryRepository countryRepository;

	private List<Country> expectedCountryList = new ArrayList<Country>();
	private List<Country> expectedCountryListStartsWithA = new ArrayList<Country>();
	private Country countryWithChangedName = new Country(1, "Russia", "RU");




	@Test
	@DirtiesContext
	public void testCountryList() {
		List<Country> countryList = countryRepository.findAll();
		assertNotNull(countryList);
		assertEquals(expectedCountryList.size(), countryList.size());
		for (int i = 0; i < expectedCountryList.size(); i++) {
			assertEquals(expectedCountryList.get(i), countryList.get(i));
		}
	}

	@Test
	@DirtiesContext
	public void testCountryListStartsWithA() {
		List<Country> countryList = countryRepository.getCountriesByNameStartingWith("A");
		assertNotNull(countryList);
		assertEquals(expectedCountryListStartsWithA.size(), countryList.size());
		for (int i = 0; i < expectedCountryListStartsWithA.size(); i++) {
			assertEquals(expectedCountryListStartsWithA.get(i), countryList.get(i));
		}
	}

	@Test
	@DirtiesContext
	public void testCountryChange() {
		Country forChanging = countryRepository.getCountriesByCodeName("RU").get(0);
		forChanging.setName("Russia");
		countryRepository.save(forChanging);
		assertEquals(countryWithChangedName, countryRepository.getCountriesByCodeName("RU").get(0));
	}

	private void initExpectedCountryLists() {
		for (int i = 0; i < COUNTRY_INIT_DATA.length; i++) {
			String[] countryInitData = COUNTRY_INIT_DATA[i];
			Country country = new Country(i, countryInitData[0], countryInitData[1]);
			expectedCountryList.add(country);
			if (country.getName().startsWith("A")) {
				expectedCountryListStartsWithA.add(country);
			}
		}
	}

}
