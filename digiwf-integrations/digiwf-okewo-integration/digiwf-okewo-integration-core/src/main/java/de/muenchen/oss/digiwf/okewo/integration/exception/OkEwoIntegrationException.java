package de.muenchen.oss.digiwf.okewo.integration.exception;

public class OkEwoIntegrationException extends Exception {

    public OkEwoIntegrationException(final String message, final Exception exception) {
        super(message, exception);
    }

}
