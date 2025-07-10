package com.mitocode.exception;

import com.mitocode.dto.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.BindException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice // El objetivo de esta clase es interceptar cualquier exception del proyecto
public class GlobalErrorHandler { //extends ResponseEntityExceptionHandler {

    // Esta sería la exception por defecto si es que no cayese en ninguna de las que tenemos definidas para capturar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleDefaultException(Exception exception, WebRequest request) {
        CustomErrorResponse cer = new CustomErrorResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(cer, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<GenericResponse<CustomErrorResponse>> handleModelNotFoundException(ModelNotFoundException exception, WebRequest request) {
        // Usando el Record
        CustomErrorResponse cer = new CustomErrorResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(new GenericResponse<>(404, "not-found", List.of(cer)), HttpStatus.NOT_FOUND);
    }

    // Esta clase si conocemos el nombre
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        // Usando el Record
        CustomErrorResponse cer = new CustomErrorResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(cer, HttpStatus.BAD_REQUEST);
    }

    // Podemos usar también los métodos de la clase heredada ResponseEntityExceptionHandler y sobre escribir el metodo que necesitemos
    // Sobre escribiendo el metodo handleMethodArgumentNotValid
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        CustomErrorResponse cer = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<>(cer, HttpStatus.BAD_REQUEST);
//    }

    // Se puede manejar mas tipos de errores si así se necesitara
    @ExceptionHandler({ArithmeticException.class, BindException.class})
    public ResponseEntity<CustomErrorResponse> handleArithmeticException(ArithmeticException exception, WebRequest request) {
        // Usando el Record
        CustomErrorResponse cer = new CustomErrorResponse(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(cer, HttpStatus.CONFLICT);
    }
}
