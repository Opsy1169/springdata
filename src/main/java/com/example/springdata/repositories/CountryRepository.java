package com.example.springdata.repositories;

import com.example.springdata.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> getCountriesByNameStartingWith(String name);

    List<Country> getCountriesByCodeName(String codeName);

    List<Country> getCountriesByName(String name);
}
