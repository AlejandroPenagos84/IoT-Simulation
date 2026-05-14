package pro.ms.simulador.infrastructure.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pro.ms.simulador.infrastructure.DTO.respond.ErrorRespondDTO;

import java.util.logging.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorRespondDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorRespondDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespondDTO> handleGeneral(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ErrorRespondDTO("Error interno"));
    }
}