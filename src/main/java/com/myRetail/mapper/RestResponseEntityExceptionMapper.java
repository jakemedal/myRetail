package com.myRetail.mapper;

import com.myRetail.repository.exception.ProductPriceNotFoundException;
import com.myRetail.service.exception.ProductTitleNotFoundException;
import com.myRetail.service.exception.UnexpectedExternalApiException;
import com.myRetail.web.exception.NoProductPriceRequestBodyException;
import com.myRetail.web.exception.RequestPathParmaMismatchException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionMapper {

    @ExceptionHandler({IllegalArgumentException.class,
                       IOException.class,
                       RequestPathParmaMismatchException.class,
                       NoProductPriceRequestBodyException.class})
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({ProductTitleNotFoundException.class, ProductPriceNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({IllegalStateException.class, UnexpectedExternalApiException.class})
    public ResponseEntity<String> handleInternalServerErrorException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<String> buildResponse(HttpStatus status, Exception ex) {
        log.error("Exception was mapped: status={}, message={}", status, ex);
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
