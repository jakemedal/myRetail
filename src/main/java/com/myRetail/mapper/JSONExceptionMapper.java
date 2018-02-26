package com.myRetail.mapper;

import static javax.ws.rs.core.Response.Status.*;

import java.util.NoSuchElementException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

@Provider
public class JSONExceptionMapper implements ExceptionMapper<JSONException> {
    private static final Logger LOG = Logger.getLogger(IllegalArgumentException.class);

    @Override
    public Response toResponse(JSONException exception) {
        LOG.error(exception.getMessage(), exception);

        return Response.status(INTERNAL_SERVER_ERROR)
                .entity("Unable to parse JSON from external API call: " + exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}



