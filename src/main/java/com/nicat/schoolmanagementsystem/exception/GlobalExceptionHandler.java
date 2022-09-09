package com.nicat.schoolmanagementsystem.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nicat.schoolmanagementsystem.exception.HttpResponseConstants.*;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    private final MessageSource messageSource;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handle(HttpMessageNotReadableException ex,
                                                      WebRequest request) {
        log.trace("Required request body is missing {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Map<String, Object>> handle(NotFoundException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Map<String, Object>> handle(ResourceNotFoundException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<Map<String, Object>> handle(AppException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Map<String, Object>> handle(ConstraintViolationException ex,
                                                            WebRequest request) {
        log.trace("Resource not found {}", ex.getMessage());
        List<ConstraintsViolationError> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ConstraintsViolationError(violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .collect(Collectors.toList());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), validationErrors);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public final ResponseEntity<Map<String, Object>> handle(MaxUploadSizeExceededException ex,
                                                            WebRequest request) {
        log.trace("Resource not found : {}", ex);
        return ofType(request, HttpStatus.PAYLOAD_TOO_LARGE, ex);
    }

    @ExceptionHandler(InvalidStateException.class)
    public final ResponseEntity<Map<String, Object>> handle(InvalidStateException ex,
                                                            WebRequest request) {
        log.trace("Request is in invalid state : {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Map<String, Object>> handle(MethodArgumentTypeMismatchException ex,
                                                            WebRequest request) {
        log.trace("Method arguments are not valid : {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(MismatchedInputException.class)
    public final ResponseEntity<Map<String, Object>> handle(MismatchedInputException ex,
                                                            WebRequest request) {
        log.trace("Mismatched inout : {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(ServerErrorException.class)
    public final ResponseEntity<Map<String, Object>> handle(ServerErrorException ex, WebRequest request) {
        log.trace("Service unavailable : {}", ex.getMessage());
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(BindException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            BindException ex,
            WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ofType(request, HttpStatus.BAD_REQUEST, getLocalizedMessage(ex), validationErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ofType(request, HttpStatus.BAD_REQUEST, getLocalizedMessage(ex), validationErrors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handle(RuntimeException ex,
                                                      WebRequest request) {
        log.error("Server failure", ex);
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, Exception ex) {
        return ofType(request, status, getLocalizedMessage(ex), Collections.EMPTY_LIST);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message,
                                                       List validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(STATUS, status.value());
        attributes.put(ERROR, status.getReasonPhrase());
        attributes.put(MESSAGE, message);
        attributes.put(ERRORS, validationErrors);
        attributes.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }

    private String getLocalizedMessage(Exception ex) {
        Locale locale = LocaleContextHolder.getLocale();
        var key = ex.getClass().getName() + ".message";
        try {
            return messageSource.getMessage(key, new Object[]{}, locale);
        } catch (NoSuchMessageException exception) {
            log.warn("Please consider adding localized message for key {} and locale {}", key, locale);
        }
        return ex.getMessage();
    }
}

