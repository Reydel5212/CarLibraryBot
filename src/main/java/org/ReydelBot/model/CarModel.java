package org.ReydelBot.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CarModel {

    @JsonProperty("carId")
    private Integer carId;
    @JsonProperty("carCountry")
    private String carCountry;
    @JsonProperty("carBrand")
    private String carBrand;
    @JsonProperty("carBrandModel")
    private String carBrandModel;
    @JsonProperty("carBodyIndex")
    private String carBodyIndex;
    @JsonProperty("carBodyType")
    private String carBodyType;
    @JsonProperty("carYearStart")
    private Integer carYearStart;
    @JsonProperty("carYearEnd")
    private Integer carYearEnd;
    @JsonProperty("carDescription")
    private String carDescription;

    @JsonGetter
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
    @JsonGetter
    public String getCarCountry() {
        return carCountry;
    }
    @JsonSetter
    public void setCarCountry(String carCountry) {
        this.carCountry = carCountry;
    }
    @JsonGetter
    public String getCarBrand() {
        return carBrand;
    }
    @JsonSetter
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }
    @JsonGetter
    public String getCarBrandModel() {
        return carBrandModel;
    }
    @JsonSetter
    public void setCarBrandModel(String carBrandModel) {
        this.carBrandModel = carBrandModel;
    }
    @JsonGetter
    public String getCarBodyIndex() {
        return carBodyIndex;
    }
    @JsonSetter
    public void setCarBodyIndex(String carBodyIndex) {
        this.carBodyIndex = carBodyIndex;
    }
    @JsonGetter
    public String getCarBodyType() {
        return carBodyType;
    }
    @JsonSetter
    public void setCarBodyType(String carBodyType) {
        this.carBodyType = carBodyType;
    }
    @JsonGetter
    public Integer getCarYearStart() {
        return carYearStart;
    }
    @JsonSetter
    public void setCarYearStart(Integer carYearStart) {
        this.carYearStart = carYearStart;
    }
    @JsonGetter
    public Integer getCarYearEnd() {
        return carYearEnd;
    }
    @JsonSetter
    public void setCarYearEnd(Integer carYearEnd) {
        this.carYearEnd = carYearEnd;
    }
    @JsonGetter
    public String getCarDescription() {
        return carDescription;
    }
    @JsonSetter
    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

}
