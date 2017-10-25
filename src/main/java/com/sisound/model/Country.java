package com.sisound.model;

import java.util.HashSet;

public class Country {

	private long countryID;
	private String countryName;
	private HashSet<City> cities;
	
	public Country(String countryName) {
		this.countryName = countryName;
		this.cities=new HashSet<>();
	}
	
	
	public Country(long countryID, String countryName) {
		this(countryName);
		this.countryID = countryID;
	}

	public void setCountryID(long countryID) {
		this.countryID = countryID;
	}
}
