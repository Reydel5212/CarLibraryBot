package ReydelBot.ReydelBot;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.ReydelBot.model.CarModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

public class CarModelTest {

    @Test
    public void testCarModel() throws IOException {
        URL url = new URL("http://localhost:8080/CarRestController/1/showCar");
        ObjectMapper objectMapper = new ObjectMapper();
        CarModel carModel = objectMapper.readValue(url, CarModel.class);
        System.out.println(carModel.getCarBrand());
    }
}
