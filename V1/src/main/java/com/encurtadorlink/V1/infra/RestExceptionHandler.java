package com.encurtadorlink.V1.infra;


import com.encurtadorlink.V1.dto.ErrorDTO;
import com.encurtadorlink.V1.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> errorNotFound(NotFoundException e){
        ErrorDTO errorDTO = new ErrorDTO("404", "Not Found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO) ;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> errorMethod(MethodArgumentNotValidException ex){

       List<String> messages = new ArrayList<>();

       for (FieldError error: ex.getBindingResult().getFieldErrors()){
           messages.add(error.getDefaultMessage());
       }

       Map<String,Object> stringObjectMap = new HashMap<>();
       stringObjectMap.put("erros", stringObjectMap);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stringObjectMap);
    }

}
