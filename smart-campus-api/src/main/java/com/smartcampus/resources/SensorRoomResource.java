package com.smartcampus.resources;

import com.smartcampus.datastore.DataStore;
import com.smartcampus.exceptions.RoomNotEmptyException;
import com.smartcampus.models.Room;

import com.smartcampus.exceptions.ErrorMessage;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {

    private final DataStore dataStore = DataStore.getInstance();

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(dataStore.getRooms().values());
        return Response.ok(rooms).build();
    }

    @POST
    public Response createRoom(Room room) {
        if (room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(400, "Room ID is required")).build();
        }

        dataStore.getRooms().put(room.getId(), room);
        URI location = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.status(Response.Status.CREATED).entity(room).location(location).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = dataStore.getRooms().get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(404, "Room not found: " + roomId)).build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = dataStore.getRooms().get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(404, "Room not found: " + roomId)).build();
        }
        
        // Business Logic Constraint: Room cannot be deleted if active sensors are assigned
        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Cannot delete room " + roomId + " because it still has active sensors assigned to it.");
        }
        
        dataStore.getRooms().remove(roomId);
        return Response.noContent().build();
    }
}
