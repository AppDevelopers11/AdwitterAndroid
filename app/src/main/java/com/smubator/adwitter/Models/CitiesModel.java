package com.smubator.adwitter.Models;

/**
 * Created by HP_PC on 12/6/2017.
 */

public class CitiesModel {
    /* "id": "1",
      "CityName": "Bombuflat",
      "state_id": "1"
*/
    private int id;
    private int state_id;
    private String CityName;

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
