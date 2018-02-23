package com.myRetail.mapper;


import static javax.ws.rs.core.Response.Status.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {
    private static final Logger LOG = Logger.getLogger(IllegalStateExceptionMapper.class);

    @Override
    public Response toResponse(IllegalStateException exception) {
        LOG.error(exception.getMessage(), exception);

        return Response.status(INTERNAL_SERVER_ERROR)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}

