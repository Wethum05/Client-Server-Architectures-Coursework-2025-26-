# Client-Server-Architectures-Coursework

# Smart Campus RESTful API

This project contains the backend implementation for the University's "Smart Campus" Sensor and Room Management system. This robust RESTful API is built iteratively utilizing standard Java POJOs, mapped resources through JAX-RS API specs, and deployed with a highly efficient embedded Grizzly Web Server to maintain in-memory states synchronously.
**No external relational databases or Spring Boot dependencies were used**

## Overview of API Design
The API utilizes a logical `/api/v1/` sub-domain standard structure ensuring scalability through nested endpoints mapping hierarchical infrastructure. The core structures pivot around two data POJOs linked relationally: `Rooms` containing one-to-many `Sensors`, which concurrently link one-to-many historical `SensorReadings`.

Endpoints design implemented:
*   `GET /api/v1/`: Discovery root providing structural hypermedia meta-data collections.
*   `/api/v1/rooms`: Endpoints administering physical building regions ensuring referential safety triggers blocking deletions upon populated rooms.
*   `/api/v1/sensors`: Queryable endpoints registering tracking devices leveraging Sub-Resource Locators branching structurally down into dynamically appended `/readings` logs enforcing temporal consistency flags. 

All endpoints incorporate targeted programmatic structured exceptions ensuring strict server reliability and cybersecurity data leaking mitigation by intercepting runtime anomalies.

## Instructions to Build and Launch

1. **Verify Prerequisites:** Ensure Java JDK 11 (or higher) and Maven (`mvn`) are installed correctly within your system environment.
2. **Navigate to the root directory:** containing the `pom.xml`.
    ```bash
    cd smart-campus-api
    ```
3. **Download libraries and compile the source Java applications:**
    ```bash
    mvn clean compile
    ```
4. **Deploy the Grizzly embedded server instance:**
    ```bash
    mvn exec:java
    ```
    The application will immediately spin up exposing ports securely at `http://localhost:8080/api/v1/`.

## Sample cURL testing 

You can test interactions directly against the embedded host instance using the five verified templates below:

1. **Discover Core Application Schema**
```bash
curl -X GET http://localhost:8080/api/v1/
```
2. **Provision A Newly Established Room Topology**
```bash
curl -X POST http://localhost:8080/api/v1/rooms -H "Content-Type: application/json" -d "{\"id\":\"LIB-301\", \"name\":\"Library Quiet Study\", \"capacity\":50}"
```
3. **Register Edge Hardware Tracking Device Linking Inherently To Room**
```bash
curl -X POST http://localhost:8080/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"TEMP-001\", \"type\":\"Temperature\", \"status\":\"ACTIVE\", \"roomId\":\"LIB-301\"}"
```
4. **Validate Advanced Filtration Parsing Parameters Processing Query Injections**
```bash
curl -X GET "http://localhost:8080/api/v1/sensors?type=Temperature"
```
5. **Commit Temporal Logging Action Triggering Side Effects Through Nested Locator Pattern Navigation**
```bash
curl -X POST http://localhost:8080/api/v1/sensors/TEMP-001/readings -H "Content-Type: application/json" -d "{\"value\":22.5}"
