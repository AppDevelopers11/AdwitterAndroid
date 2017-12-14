package com.smubator.adwitter.Models;

/**
 * Created by HP_PC on 12/6/2017.
 */

public class StatesModel {
    /*"id": "1",
      "name": "Andaman and Nicobar Islands",
      "country_id": "101
*/
    private int id;
    private int country_id;

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String countryName) {
        StateName = countryName;
    }

    private String StateName;

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
