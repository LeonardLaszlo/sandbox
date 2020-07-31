package hu.java.eight.optional;

import hu.java.eight.optional.domain.Car;
import hu.java.eight.optional.domain.Insurance;
import hu.java.eight.optional.domain.Person;
import org.testng.annotations.Test;

public final class OptionalsTest {
    @Test
    public void shouldGetNameOfInsurance() {
        final Insurance insurance = new Insurance();
        final Car car = new Car(insurance);
        final Person alex = new Person(car);

        assert "Tony Hoare".equals(alex.getCar()
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown")
        );
    }

    @Test
    public void shouldNotGetNameOfNonExistentInsurance() {
        final Car car = new Car();
        final Person alex = new Person(car);

        assert "Unknown".equals(alex.getCar()
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown")
        );
    }

    @Test
    public void shouldNotGetNameOfInsuranceWithNonExistentCar() {
        final Person alex = new Person();

        assert "Unknown".equals(alex.getCar()
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown")
        );
    }
}
