package org.ReydelBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ReydelBot.model.CarModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

@Service
public class CarService {

    public static String getCarInfo(String message, CarModel carModel) throws IOException, ParseException{
        URL url = new URL("http://localhost:8081/CarRestController/"+ message +"/showCarByBrand");

        ObjectMapper objectMapper = new ObjectMapper();

        CarModel CarUrlObject = objectMapper.readValue(url, CarModel.class);

        carModel.setCarId(CarUrlObject.getCarId());
        carModel.setCarCountry(CarUrlObject.getCarCountry());
        carModel.setCarBrand(CarUrlObject.getCarBrand());
        carModel.setCarBrandModel(CarUrlObject.getCarBrandModel());
        carModel.setCarBodyIndex(CarUrlObject.getCarBodyIndex());
        carModel.setCarBodyType(CarUrlObject.getCarBodyType());
        carModel.setCarYearStart(CarUrlObject.getCarYearStart());
        carModel.setCarYearEnd(CarUrlObject.getCarYearEnd());
        carModel.setCarDescription(CarUrlObject.getCarDescription());

        return "Страна производитель: " + carModel.getCarCountry() + "\n" +
                "Бренд: " + carModel.getCarBrand() + "\n" +
                "Модель: " + carModel.getCarBrandModel() + "\n" +
                "Индекс кузова: " + carModel.getCarBodyIndex() + "\n" +
                "Тип кузова: " + carModel.getCarBodyType() + "\n" +
                "Начало производства: " + carModel.getCarYearStart() + "\n" +
                "Конец производства: " + carModel.getCarYearEnd() + "\n" +
                "Описание: " + "\n" +
                carModel.getCarDescription();

    }
    public static List<CarModel> getCarList() throws IOException {
        URL url = new URL("http://localhost:8081/CarRestController/getCarInfo");

        ObjectMapper objectMapper = new ObjectMapper();

        List<CarModel> carModelList = objectMapper.readValue(url, new TypeReference<List<CarModel>>(){});

        return carModelList;
    }

}
