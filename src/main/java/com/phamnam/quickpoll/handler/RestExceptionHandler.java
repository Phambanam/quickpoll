package com.phamnam.quickpoll.handler;

import java.util.List;

import com.phamnam.quickpoll.dto.error.ErrorDetail;
import com.phamnam.quickpoll.dto.error.ValidationError;
import com.phamnam.quickpoll.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler  {
    @Inject
    private MessageSource messageSource;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (ResourceNotFoundException rnfe, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationError
            (MethodArgumentNotValidException manve, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestPath == null) requestPath = request.getRequestURI();
        System.out.println(requestPath);
        errorDetail.setTitle("Validation Failed");
        errorDetail.setDetail("Input validation failed");
        errorDetail.setDeveloperMessage(manve.getClass().getName());

        List<FieldError> fieldErrorList = manve.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrorList) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<ValidationError>();
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe,null));
            validationErrorList.add(validationError);
            errorDetail.getErrors().put(fe.getField(), validationErrorList);
        }
        return new ResponseEntity<>(errorDetail,null, HttpStatus.BAD_REQUEST);

    }


//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimeStamp(new Date().getTime());
//        errorDetail.setStatus(status.value());
//        errorDetail.setTitle("Message not readable");
//        errorDetail.setDetail(ex.getMessage());
//        errorDetail.setDeveloperMessage(ex.getClass().getName());
//        return handleExceptionInternal(ex,errorDetail,headers,status,request);
//    }
}
