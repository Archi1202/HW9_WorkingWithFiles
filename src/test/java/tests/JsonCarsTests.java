package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Cars;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonCarsTests {
    private ClassLoader cl = JsonCarsTests.class.getClassLoader();

    @Test
    @DisplayName("Test to verify Cars.json file data")
    void jsonFileTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("Cars.json")
        )) {
            ObjectMapper mapper = new ObjectMapper();
            Cars cars = mapper.readValue(reader, Cars.class);

            assertThat(cars.getCars().get(1).getBrand()).isEqualTo("Toyota");
            assertThat(cars.getCars().get(0).getModels()).extracting("name").contains("Accord");
            assertThat(cars.getCars().get(2).getBrand()).startsWith("F");
            Cars.Car honda = cars.getCars().get(0); // Honda brand
            Cars.Car.Model hondaCivic = honda.getModels().stream()
                    .filter(model -> model.getName().equals("Civic"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Honda Civic model not found"));
            assertThat(hondaCivic.getDetails().getYear()).isEqualTo(2024);
            assertThat(hondaCivic.getDetails().getEngine()).isEqualTo("1.5L Turbocharged I4");
            assertThat(hondaCivic.getDetails().getHorsepower()).isEqualTo(180);
            assertThat(hondaCivic.getDetails().getTransmission()).isEqualTo("CVT");
            assertThat(hondaCivic.getDetails().getFuelEconomy().getCity()).isEqualTo("32 mpg");
            assertThat(hondaCivic.getDetails().getFuelEconomy().getHighway()).isEqualTo("42 mpg");
            assertThat(hondaCivic.getDetails().getFeatures())
                    .containsAnyOf("Apple CarPlay", "Lane Keeping Assist");
            assertThat(hondaCivic.getDetails().getAvailableColors())
                    .contains("Crystal Black Pearl", "Rallye Red");
        }
    }
}
