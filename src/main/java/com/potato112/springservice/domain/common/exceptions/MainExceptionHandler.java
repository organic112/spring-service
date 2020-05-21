package com.potato112.springservice.domain.common.exceptions;


import com.potato112.springservice.domain.user.SysValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@ControllerAdvice
@Slf4j
public class MainExceptionHandler implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public MainExceptionHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResult> requestExceptionHandler(Exception ex) {

        log.info("Exception Handler" + ex.getMessage());

        ErrorResult validationError = new ErrorResult(ex.getMessage());
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {SysValidationException.class})
    public ResponseEntity<ErrorResult> validationExceptionHandler(SysValidationException ex) {

        log.info("Exception Handler" + ex.getMessage());

        ErrorResult actionResult = new ErrorResult(ex.getMessage(), ex.getValidationErrors());
        return new ResponseEntity<>(actionResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResult> noDataExceptionHandler() {
        log.info("Exception Handler: no data exception");
        return new ResponseEntity<>(new ErrorResult("Resource not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> otherExceptionHandler(Exception ex) {
        log.info("Exception Handler" + ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResult(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        log.info("Exception Handler" + ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> fieldError = fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        ErrorResult validationResult = new ErrorResult("Validation error. Please provide correct data.", fieldError);
        return new ResponseEntity<>(validationResult, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,
            RequestMethod.PATCH}, value = "/error")
    @ResponseBody
    public ResponseEntity<ErrorResult> handleError(WebRequest webRequest, HttpServletResponse response) {
        Map<String, Object> errorAttributeMap = this.errorAttributes.getErrorAttributes(webRequest, false);
        int status = response.getStatus();
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        ErrorResult errorResult = new ErrorResult((String) errorAttributeMap.get("message"));
        return new ResponseEntity<>(errorResult, httpStatus);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
