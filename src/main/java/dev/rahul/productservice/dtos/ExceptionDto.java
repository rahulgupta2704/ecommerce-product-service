package dev.rahul.productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;

    //This is a constructor
    public ExceptionDto(HttpStatus status, String message) {
        this.errorCode = status;
        this.message = message;
    }
}
