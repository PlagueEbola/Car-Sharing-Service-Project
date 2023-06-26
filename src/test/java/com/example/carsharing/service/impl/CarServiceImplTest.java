package com.example.carsharing.service.impl;

import com.example.carsharing.model.Car;
import com.example.carsharing.repository.CarRepository;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Test
    public void updateByExistingCarId() {
        Car carToSave = new Car("Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));
        Long carId = 14L;
        Car savedCar = new Car(carId, "Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));
        Mockito.when(carRepository.existsById(14L)).thenReturn(true);
        Mockito.when(carRepository.save(carToSave)).thenReturn(savedCar);

        Car actual = carService.updateById(14L, carToSave);

        Assertions.assertEquals(14L, actual.getId());
        Assertions.assertEquals(carToSave.getModel(), actual.getModel());
        Assertions.assertEquals(carToSave.getBrand(), actual.getBrand());
        Assertions.assertEquals(carToSave.getType(), actual.getType());
        Assertions.assertEquals(carToSave.getInventory(), actual.getInventory());
        Assertions.assertEquals(carToSave.getDailyFee(), actual.getDailyFee());
    }

    @Test
    public void updateByNonExistingCarId_ThrowsNoSuchElementException() {
        Car carToSave = new Car("Camry", "Toyota", Car.CarType.SEDAN, 1, BigDecimal.valueOf(50));
        Long carId = 1L;
        Mockito.when(carRepository.existsById(carId)).thenReturn(false);

        Assertions.assertThrows(NoSuchElementException.class, () -> carService.updateById(carId, carToSave));
    }
}