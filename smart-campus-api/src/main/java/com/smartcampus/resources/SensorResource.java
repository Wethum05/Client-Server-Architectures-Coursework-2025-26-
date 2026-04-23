package com.smartcampus.resources;

import com.smartcampus.datastore.DataStore;
import com.smartcampus.exceptions.LinkedResourceNotFoundException;
import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;

import com.smartcampus.exceptions.ErrorMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private final DataStore dataStore = DataStore.getInstance();

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> allSensors = new ArrayList<>(dataStore.getSensors().values());
        
        // Filtered Retrieval & Search logic
        if (type != null && !type.trim().isEmpty()) {
            List<Sensor> filtered = allSensors.stream()
                    .filter(s -> type.equalsIgnoreCase(s.getType()))
                    .collect(Collectors.toList());
            return Response.ok(filtered).build();
        }
        
        return Response.ok(allSensors).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON) // Redundant but explicit as per spec
    public Response registerSensor(Sensor sensor) {
        if (sensor.getId() == null || sensor.getRoomId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(400, "Sensor ID and Room ID are required")).build();
        }

        Room room = dataStore.getRooms().get(sensor.getRoomId());
        if (room == null) {
            // Task: Throw custom Exception LinkedResourceNotFoundException returning a 422
            throw new LinkedResourceNotFoundException("The specified roomId '" + sensor.getRoomId() + "' does not exist.");
        }

        dataStore.getSensors().put(sensor.getId(), sensor);

        // Add sensor to the room's list of sensors to keep references synchronized
        room.addSensorId(sensor.getId());

        URI location = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();
        return Response.status(Response.Status.CREATED).entity(sensor).location(location).build();
    }

    // Sub-Resource Locator
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        // We pass the contextual parent sensor ID and UriInfo to the sub-resource
        return new SensorReadingResource(sensorId, uriInfo);
    }
}
