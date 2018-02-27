package com.myRetail.mapper;

import static javax.ws.rs.core.Response.Status.*;

import java.util.NoSuchElementException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {
    private static final Logger LOG = Logger.getLogger(NoSuchElementExceptionMapper.class);

    @Override
    public Response toResponse(NoSuchElementException exception) {
        LOG.error(exception.getMessage(), exception);

        return Response.status(NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}



