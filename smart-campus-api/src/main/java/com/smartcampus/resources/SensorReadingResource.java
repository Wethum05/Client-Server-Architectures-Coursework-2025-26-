package com.smartcampus.resources;

import com.smartcampus.datastore.DataStore;
import com.smartcampus.exceptions.SensorUnavailableException;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;

import com.smartcampus.exceptions.ErrorMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Note: No @Path here, as the path was provided by the Sub-Resource Locator in SensorResource
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;
    private final DataStore dataStore = DataStore.getInstance();

    @Context
    private UriInfo uriInfo;

    public SensorReadingResource(String sensorId, UriInfo uriInfo) {
        this.sensorId = sensorId;
        this.uriInfo = uriInfo;
    }

    @GET
    public Response getReadings() {
        Sensor sensor = dataStore.getSensors().get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(404, "Sensor not found: " + sensorId)).build();
        }

        List<SensorReading> readings = dataStore.getReadings().getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(readings).build();
    }

    @POST
    public Response appendReading(SensorReading reading) {
        Sensor sensor = dataStore.getSensors().get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(404, "Sensor not found: " + sensorId)).build();
        }
        
        // State Constraint: A sensor marked as MAINTENANCE cannot accept readings
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("Sensor " + sensorId + " is currently in MAINTENANCE mode and cannot accept new readings.");
        }
        
        // Ensure Reading has an ID and Timestamp if the client missed it
        if (reading.getId() == null) {
            reading.setId(UUID.randomUUID().toString());
        }
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        // Side Effect: Update the parent sensor's currentValue
        sensor.setCurrentValue(reading.getValue());
        
        // Save the reading into the DataStore
        dataStore.addReading(sensorId, reading);

        URI location = uriInfo.getAbsolutePathBuilder().path(reading.getId()).build();
        return Response.status(Response.Status.CREATED).entity(reading).location(location).build();
    }
}
