package pl.messier.enterthedragon.service.exceptions;

public class EnterTheDragonException extends Exception{
    public EnterTheDragonException(String message) {
        super(message);
    }
    public EnterTheDragonException(String message, Throwable cause) {
        super(message, cause);
    }
}
