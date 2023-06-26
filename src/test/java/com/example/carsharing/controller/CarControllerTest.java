package com.example.carsharing.controller;

import com.example.carsharing.dto.request.CarRequestDto;
import com.example.carsharing.model.Car;
import com.example.carsharing.service.CarService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @MockBean
    private CarService carService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void save() {
        Car carToSave = new Car("Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));
        Car carToReturn = new Car(14L, "Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));

        Mockito.when(carService.save(carToSave)).thenReturn(carToReturn);
        CarRequestDto carRequestDto = new CarRequestDto(carToSave.getModel(), carToSave.getBrand(),
                carToSave.getInventory(), carToSave.getType().name(), carToSave.getDailyFee());

        RestAssuredMockMvc.given()
                .auth().with(user("user").roles("MANAGER"))
                .contentType(ContentType.JSON)
                .body(carRequestDto)
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(14))
                .body("model", Matchers.equalTo("Camry"))
                .body("brand", Matchers.equalTo("Toyota"))
                .body("type", Matchers.equalTo(Car.CarType.SEDAN.name()))
                .body("inventory", Matchers.equalTo(1))
                .body("dailyFee", Matchers.equalTo(50));
    }

    @Test
    public void getAll() {
        List<Car> mockedCars = List.of(
                new Car(14L, "Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50)),
                new Car(15L, "Civic", "Honda", Car.CarType.SEDAN, 4, BigDecimal.valueOf(100))
        );

        Mockito.when(carService.getAll()).thenReturn(mockedCars);

        RestAssuredMockMvc.when()
                .get("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", Matchers.equalTo(2))
                .body("[0].id", Matchers.equalTo(14))
                .body("[0].model",Matchers.equalTo("Camry"))
                .body("[0].brand", Matchers.equalTo("Toyota"))
                .body("[0].type", Matchers.equalTo("SEDAN"))
                .body("[0].inventory", Matchers.equalTo(1))
                .body("[0].dailyFee", Matchers.equalTo(50))
                .body("[1].id", Matchers.equalTo(15))
                .body("[1].model", Matchers.equalTo("Civic"))
                .body("[1].brand", Matchers.equalTo("Honda"))
                .body("[1].type", Matchers.equalTo("SEDAN"))
                .body("[1].inventory", Matchers.equalTo(4))
                .body("[1].dailyFee", Matchers.equalTo(100));
    }

    @Test
    public void getById() {
        Car car = new Car(14L, "Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));

        Mockito.when(carService.getById(14L)).thenReturn(car);

        RestAssuredMockMvc.given()
                .when()
                .get("/cars/{id}", 14L)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(14))
                .body("model", Matchers.is("Camry"))
                .body("brand", Matchers.is("Toyota"))
                .body("type", Matchers.is("SEDAN"))
                .body("inventory", Matchers.is(1))
                .body("dailyFee", Matchers.is(50));
    }

    @Test
    public void update() {
        Car carToUpdate = new Car("Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));
        Car updatedCar = new Car(14L, "Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));

        Mockito.when(carService.updateById(14L, carToUpdate)).thenReturn(updatedCar);

        CarRequestDto carRequestDto = new CarRequestDto(updatedCar.getModel(), updatedCar.getBrand(),
                updatedCar.getInventory(), updatedCar.getType().name(), updatedCar.getDailyFee());

        RestAssuredMockMvc.given()
                .auth().with(user("user").roles("MANAGER"))
                .contentType(ContentType.JSON)
                .body(carRequestDto)
                .when()
                .put("/cars/{id}", 14)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(14))
                .body("model", Matchers.is("Camry"))
                .body("brand", Matchers.is("Toyota"))
                .body("type", Matchers.is("SEDAN"))
                .body("inventory", Matchers.is(1))
                .body("dailyFee", Matchers.is(50));
    }

    @Test
    public void delete() {
        Mockito.doNothing().when(carService).deleteById(14L);

        RestAssuredMockMvc.given()
                .auth().with(user("user").roles("MANAGER"))
                .when()
                .delete("/cars/{id}", 14)
                .then()
                .statusCode(200);
    }
}
