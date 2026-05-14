package uni.csw.medibug.device_management_context.infrastructure.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import uni.csw.medibug.device_management_context.infrastructure.DTO.ErrorRespondDTO;

@RestControllerAdvice
public class GlobalDeviceManagementExceptionHandler {

    @ExceptionHandler(DownstreamClientException.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleDownstreamClient(DownstreamClientException ex) {
        String message = "Error de validacion del microservicio externo (" + ex.getDownstreamStatus() + "): " + ex.getMessage();
        return Mono.just(
                ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ErrorRespondDTO(message))
        );
    }

    @ExceptionHandler(DownstreamServerException.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleDownstreamServer(DownstreamServerException ex) {
        String message = "Fallo del microservicio externo (" + ex.getDownstreamStatus() + "): " + ex.getMessage();
        return Mono.just(
                ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ErrorRespondDTO(message))
        );
    }

    @ExceptionHandler(DownstreamTimeoutException.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleDownstreamTimeout(DownstreamTimeoutException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                        .body(new ErrorRespondDTO(ex.getMessage()))
        );
    }

    @ExceptionHandler(DownstreamUnavailableException.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleDownstreamUnavailable(DownstreamUnavailableException ex) {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ErrorRespondDTO(ex.getMessage()))
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleBadRequest(IllegalArgumentException ex) {
        return Mono.just(
                ResponseEntity.badRequest()
                        .body(new ErrorRespondDTO(ex.getMessage()))
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorRespondDTO>> handleGeneral(Exception ex) {
        return Mono.just(
                ResponseEntity.status(500)
                        .body(new ErrorRespondDTO("Error interno"))
        );
    }
}