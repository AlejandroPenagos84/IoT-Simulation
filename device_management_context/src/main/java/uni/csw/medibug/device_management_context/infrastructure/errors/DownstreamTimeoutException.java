package uni.csw.medibug.device_management_context.infrastructure.errors;

public class DownstreamTimeoutException extends RuntimeException {

    public DownstreamTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}

