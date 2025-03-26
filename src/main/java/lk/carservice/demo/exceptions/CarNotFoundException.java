package lk.carservice.demo.exceptions;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException(Long id) {
        super("Car not found with id: " + id);
    }
    public CarNotFoundException(String message){
        super(message);
    }
}
