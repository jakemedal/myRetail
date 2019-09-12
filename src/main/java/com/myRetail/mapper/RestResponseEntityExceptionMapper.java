package com.myRetail.mapper;

import com.myRetail.repository.ProductPriceNotFoundException;
import com.myRetail.service.ProductTitleNotFoundException;
import com.myRetail.service.UnexpectedExternalApiException;
import com.myRetail.web.NoProductPriceRequestBodyException;
import com.myRetail.web.RequestPathParmaMismatchException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionMapper {
    private static final Logger LOG = Logger.getLogger(RestResponseEntityExceptionMapper.class);

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
        LOG.error("Exception was mapped: status={}, message={}", ex);
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
