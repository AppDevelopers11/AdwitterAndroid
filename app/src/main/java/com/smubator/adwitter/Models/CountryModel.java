package com.smubator.adwitter.Models;

/**
 * Created by HP_PC on 12/6/2017.
 */

public class CountryModel {
    private int id;
    private String shortName;
    private String CountryName;

//    CountryModel(String id, String CountryName) {
//        this.CountryName = CountryName;
//        this.id = id;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }
}
