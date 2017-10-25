package com.sisound.model;

public class City {

	private long cityID;
	private String cityName;
	private Country country;
	
	public City(String cityName, Country country) {
		super();
		this.cityName = cityName;
		this.country = country;
	}
	
	
	public City(long cityID, String cityName, Country country) {
		this(cityName, country);
		this.cityID = cityID;
	}


	public void setCityID(long cityID) {
		this.cityID = cityID;
	}
}
