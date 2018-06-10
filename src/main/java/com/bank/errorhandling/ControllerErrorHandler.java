package com.bank.errorhandling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bank.models.ErrorDetails;

@ControllerAdvice
public class ControllerErrorHandler{
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<List<FieldError>> handleExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
	  List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
	  List<ErrorDetails> errorDetailsCollection = new ArrayList<ErrorDetails>();
	  for (FieldError fieldError : fieldErrors){
		  ErrorDetails errordetails = new ErrorDetails(fieldError.getField(), fieldError.getDefaultMessage());
		  errorDetailsCollection.add(errordetails);
	  }
	  return new ResponseEntity(errorDetailsCollection, HttpStatus.BAD_REQUEST);
  }
  
  
}