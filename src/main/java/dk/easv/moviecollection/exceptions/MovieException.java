package dk.easv.moviecollection.exceptions;

public class MovieException extends RuntimeException {

    public MovieException(String e) {
        super(e);
    }

    // Constructor with message and cause
    public MovieException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with only a cause
    public MovieException(Throwable cause) {
        super(cause);
    }

    // Constructor with no arguments
    public MovieException() {
        super("An error occurred in the Movie Collection application.");
    }

    @Override
    public String toString() {
        return "MovieException: " + getMessage();
    }
}
