package com.example.apirestsoccerplayers.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NameNotFoundException;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleUsernameNotFoundException(UsernameNotFoundException ex){
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleUsernameNotFoundException(NotFoundException ex){
        return new Result(false, StatusCode.NOT_FOUND, "Data not found");
    }

    @ExceptionHandler(NameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleUsernameNotFoundException(NameNotFoundException ex){
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException ex){
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach(error -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            map.put(key, value);
        });

        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided arguments valid", map);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleSqlException(SQLException ex){
        return new Result(false, StatusCode.INVALID_ARGUMENT, ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleConstraintViolationException(ConstraintViolationException ex){
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        Map<String,String> messages = new HashMap<>();
        errors.forEach(elem -> messages.put(elem.getPropertyPath().toString(), elem.getMessage()));
        return new Result(false, StatusCode.INVALID_ARGUMENT, "Invalid data", messages);
    }

    @ExceptionHandler(Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAuthenticationException(Exception ex){
        return new Result(false, StatusCode.UNAUTHORIZED, "username or password wrong");
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleFilesLoad(Exception ex){
        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR, "Error to save or load file");
    }
    
}
