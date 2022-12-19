package net.kem.mimecast.web.exception;

public class PublisherIdMismatchException extends RuntimeException {

    public PublisherIdMismatchException() {
        super();
    }

    public PublisherIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PublisherIdMismatchException(final String message) {
        super(message);
    }

    public PublisherIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
