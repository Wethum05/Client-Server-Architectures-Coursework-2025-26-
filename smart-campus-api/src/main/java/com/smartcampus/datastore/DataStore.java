package com.smartcampus.datastore;

import com.smartcampus.models.Room;
import com.smartcampus.models.Sensor;
import com.smartcampus.models.SensorReading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {
    private static DataStore instance;

    // Use ConcurrentHashMap to manage in-memory data safely across multiple asynchronous JAX-RS requests
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    private final Map<String, List<SensorReading>> readings = new ConcurrentHashMap<>();

    private DataStore() {
        // Seed with some initial data for testing
        Room room1 = new Room("LIB-301", "Library Quiet Study", 50);
        Room room2 = new Room("SC-101", "Science Lab", 30);
        rooms.put(room1.getId(), room1);
        rooms.put(room2.getId(), room2);
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }

    public Map<String, List<SensorReading>> getReadings() {
        return readings;
    }
    
    // Helper to add reading in thread-safe manner
    public void addReading(String sensorId, SensorReading reading) {
        readings.computeIfAbsent(sensorId, k -> Collections.synchronizedList(new ArrayList<>())).add(reading);
    }
}
