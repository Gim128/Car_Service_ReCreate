package lk.carservice.demo.exceptions;

public class DuplicateNameException extends RuntimeException{
    public DuplicateNameException(String message) {
        super(message);
    }
}
