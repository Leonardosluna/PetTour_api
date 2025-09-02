package com.pettour.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Indica que esta classe irá tratar exceções de todos os controllers
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // Define o status HTTP da resposta como 400
    @ExceptionHandler(MethodArgumentNotValidException.class) // Especifica o tipo de exceção que este método trata
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Pega todos os erros de validação e formata em um mapa (chave: nome do campo, valor: mensagem de erro)
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}