package com.smartcampus.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscoveryInfo() {
        Map<String, Object> discoveryMap = new HashMap<>();
        
        discoveryMap.put("version", "v1.0");
        discoveryMap.put("contact", "admin@smartcampus.university.edu");
        
        Map<String, String> collections = new HashMap<>();
        collections.put("rooms", "/api/v1/rooms");
        collections.put("sensors", "/api/v1/sensors");
        
        discoveryMap.put("collections", collections);
        
        return Response.ok(discoveryMap).build();
    }
}
