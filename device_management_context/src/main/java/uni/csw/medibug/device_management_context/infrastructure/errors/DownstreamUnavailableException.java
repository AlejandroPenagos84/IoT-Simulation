package uni.csw.medibug.device_management_context.infrastructure.errors;

public class DownstreamUnavailableException extends RuntimeException {

    public DownstreamUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

