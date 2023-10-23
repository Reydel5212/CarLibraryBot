package ReydelBot.ReydelBot;

import org.ReydelBot.model.CarModel;
import org.ReydelBot.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;

public class CarModelTest {

    @Test
    public void testGetCarInfo() throws IOException, ParseException {
        String message = "BMW";
        CarModel carModel = new CarModel();

        CarService.getCarInfo(message, carModel);

    }

    @Test
    public void testGetCarList() throws Exception {
        try{
            CarService.getCarList();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
