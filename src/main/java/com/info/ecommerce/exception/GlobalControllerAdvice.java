package com.info.ecommerce.exception;

import com.info.ecommerce.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        pageNotFoundLogger.warn(ex.getMessage());
        return this.handleExceptionInternal(ex, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getHttpInputMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), "BAD REQUEST", null),
         HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Map<String, Object>> details = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            Map<String, Object> errorsMap = new HashMap<>();
            errorsMap.put("fieldName", error.getField());
            errorsMap.put("errorMsg", error.getDefaultMessage());
            details.add(errorsMap);
        }
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), details.get(0).get("fieldName").toString() + " "
        + details.get(0).get("errorMsg").toString(), details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handelInternalServerError(Exception ex, WebRequest req) {
        ex.printStackTrace();
        return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new String[0]), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleMethodNotFound(Exception ex, WebRequest req) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new String[0]), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response((HttpStatus.BAD_REQUEST.value()), ex.getMessage(), new String[0]), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), new String[0]), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> customConstraintVoilated(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.CONFLICT.value(), ex.getMessage(), new String[0]), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new String[0]), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new String[0]), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    public ResponseEntity<Object> handleBindException(BindException exception, HttpServletRequest request) {
        List<Map<String, Object>> details = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            Map<String, Object> errorsMap = new HashMap<>();
            errorsMap.put("fieldName", error.getField());
            errorsMap.put("errorMsg", error.getDefaultMessage());
            details.add(errorsMap);
        }
        return new ResponseEntity<>(new Response(HttpStatus.BAD_REQUEST.value(),
                details.get(0).get("fieldName").toString() + " " + details.get(0).get("errorMsg").toString(), details),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AlreadyExistException.class})
    protected ResponseEntity<Object> alreadyExistException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.CONFLICT.value(), ex.getMessage(), new String[0]), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> notFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new String[0]),
                HttpStatus.NOT_FOUND);
    }

}
