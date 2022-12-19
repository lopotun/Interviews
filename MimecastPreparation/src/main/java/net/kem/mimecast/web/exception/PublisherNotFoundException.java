package net.kem.mimecast.web.exception;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException() {
        super();
    }

    public PublisherNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PublisherNotFoundException(final String message) {
        super(message);
    }

    public PublisherNotFoundException(final Throwable cause) {
        super(cause);
    }
}