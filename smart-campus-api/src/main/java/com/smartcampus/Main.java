package com.smartcampus;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.io.IOException;
import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static HttpServer startServer() {
        // Create a ResourceConfig that initializes from our JAX-RS
        // SmartCampusApplication
        final ResourceConfig rc = ResourceConfig.forApplication(new SmartCampusApplication());

        // Register Jackson for automatically mapping JSON to Java POJOs
        rc.register(JacksonFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey Smart Campus API started at %s%nHit Enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}
