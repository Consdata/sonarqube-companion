package pl.consdata.ico.sqcompanion;

public class SQCompanionException extends RuntimeException {

    public SQCompanionException() {
    }

    public SQCompanionException(String message) {
        super(message);
    }

    public SQCompanionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQCompanionException(Throwable cause) {
        super(cause);
    }

}
