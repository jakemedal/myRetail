package com.myRetail.mapper;


import static javax.ws.rs.core.Response.Status.*;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class IOExceptionMapper implements ExceptionMapper<IOException> {
    private static final Logger LOG = Logger.getLogger(IOExceptionMapper.class);
    @Override
    public Response toResponse(IOException exception) {
        LOG.info(exception.getMessage(), exception);

        return Response.status(BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
