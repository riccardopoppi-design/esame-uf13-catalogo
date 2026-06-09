package com.riccardopoppi.catalogo_cap.exceptions;

import com.riccardopoppi.catalogo_cap.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Intercetta gli errori di validazione real-time (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Restituisce lo stato "fail" con la mappa degli errori come richiesto dal vincolo
        return new ResponseEntity<>(APIResponse.fail(errors), HttpStatus.BAD_REQUEST);
    }

    // 2. Intercetta le eccezioni di risorsa non trovata (es. 404 Not Found)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<APIResponse<String>> handleResponseStatusException(ResponseStatusException ex) {
        // Tipizzazione esplicita String (evitiamo Void per rispetto dei vincoli generali)
        APIResponse<String> response = new APIResponse<>("fail", null, ex.getReason());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    // 3. Intercettore generico per evitare la fuga di stack trace interni (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<String>> handleGlobalException(Exception ex) {
        APIResponse<String> response = APIResponse.error("Si è verificato un errore interno nel server.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}