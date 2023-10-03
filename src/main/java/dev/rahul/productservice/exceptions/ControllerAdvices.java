package dev.rahul.productservice.exceptions;

import dev.rahul.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    //The above code will call the handleNotFoundException method whenever any of our controller methods throw a "NotFoundException" error
    //We need to add throws NotFoundException in all of the controller methods

    //The responseEntity of type <ExceptionDto> returns a new ResponseEntity which has 2 constructor parameters
    //the class and HttpStatus
    private ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity(
                //Our ExceptionDto constructor takes 2 paremters, HttpStatus and message
                new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
