package com.myRetail.mapper;

import static javax.ws.rs.core.Response.Status.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    private static final Logger LOG = Logger.getLogger(IllegalArgumentException.class);

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        LOG.error(exception.getMessage(), exception);

        return Response.status(BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}


