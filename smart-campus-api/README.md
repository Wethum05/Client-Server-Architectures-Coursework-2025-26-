# Smart Campus RESTful API

This project contains the backend implementation for the University's "Smart Campus" Sensor & Room Management system. This robust RESTful API is built iteratively utilizing standard Java POJOs, mapped resources through JAX-RS API specs, and deployed with a highly efficient embedded Grizzly Web Server to maintain in-memory states synchronously.
**No external relational databases or Spring Boot dependencies were utilized in order to comply with the 5COSC022W coursework guidelines.**

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
```

## Conceptual Report

#### Question 1.1: Default Lifecycle of a JAX-RS Resource
In JAX-RS, the default lifecycle of a Resource class is per-request. A new instance of the resource class is instantiated by the JAX-RS runtime for every single incoming HTTP request, and it is subsequently destroyed immediately upon returning the response. This guarantees thread safety as local variables inside the resource aren't shared across simultaneous requests. Since this prevents us from using instance variables to cache persisting domain data natively, developers must structurally decouple their persistence components utilizing Singletons (like our `DataStore.getInstance()`), thread-safe dependencies implementations (`ConcurrentHashMap`), or an external database layer to systematically synchronize persistent arrays without race conditions.

#### Question 1.2: HATEOAS and Hypermedia 
By providing native "Hypermedia" (HATEOAS) navigational links mapped directly inside standard system responses, an API transitions from static bindings to dynamic discoverability. Clients interacting do not have to inherently hardcode every system endpoint path URL in their code. Instead, by fetching a centralized Discovery response, developers can parse dynamic sub-route collections natively modifying UI layers dynamically. This radically benefits long-term architectural scaling; developers no longer rely tightly on static documentation ensuring that changing endpoint versions dynamically doesn't irreparably shatter hardcoded client applications natively integrating within their infrastructures.

#### Question 2.1: Returning Room IDs vs Full Room Objects
Returning only a list of partial relational IDs fundamentally optimizes server bandwidth and substantially limits internal memory overhead parsing significantly dense nested recursive entities dynamically over the web. Returning comprehensive deep objects causes enormous bloated megabytes for queries requesting thousands of structural buildings natively. However, providing sparse ID mapping explicitly forces client-side processes to recursively generate "N+1" iterative requests individually resolving those nested relations compounding latency spikes locally. Consequently, architectural decisions must carefully balance minimizing request roundtrips dynamically against preserving limited network transfer allowances.

#### Question 2.2: Is DELETE Idempotent logically?
Yes. The REST architectural paradigm mandates that `DELETE` operations remain strictly idempotent. In our structural implementation, if a client effectively sends a `DELETE /{roomId}` accurately, the targeted building model is natively wiped from the in-memory array returning a `204 No Content`. If they recursively mistakenly submit that identical DELETE request a second, third, or infinite times sequentially against the same URL, the array will deterministically analyze the data and generate a standardized custom `404 Not Found` response because the referenced identifier explicitly no longer logically exists. The system state remains permanently unaltered regardless of redundant secondary deletion attempts.

#### Question 3.1: Handling Content Types via JAX-RS Consumes
By utilizing `@Consumes(MediaType.APPLICATION_JSON)`, our framework formally restricts input serialization solely allowing strictly formatted JSON byte payloads. If a naive client explicitly attempts appending incompatible strings formatted as `text/plain` or verbose nested formats natively utilizing `application/xml`, the internal JAX-RS Servlet container intervenes parsing network Headers. Detecting structural inconsistency dynamically, Jersey immediately aborts controller routing algorithms bypassing resource initialization ultimately throwing a proactive standardized `HTTP 415 Unsupported Media Type` response exception instantly preventing malicious/corrupted injections natively causing internal framework failures.

#### Question 3.2: Filtering Design Path versus Query Strategies
Implementing structured searches leveraging `@QueryParam("type")` explicitly obeys pure RESTful URL philosophy inherently representing parameter states altering the perspective of the *same absolute collection space* (`/sensors`). If architectural paths were modeled utilizing deep dynamic directories integrating URLs linearly such as `/api/sensors/type/CO2`, they falsely imply explicit unique logical sub-entities structurally establishing rigid unscalable hierarchies natively. Query strings flexibly permit endless complex combinations natively appending logic naturally (`?type=CO2&status=ACTIVE`) logically impossible within strictly chained deep directory hierarchical URL conventions.

#### Question 4.1: Architecture of Sub-Resource Locator Pattern
Operating expansive APIs nesting deep path hierarchies such as `/sensors/{id}/readings/{rid}/maintenance/{mid}` within purely monolithic structural controller classes inevitably instigates disastrous recursive "Code Smells". By utilizing Sub-Resource Locator annotations locally resolving mapping segments hierarchically towards delegated contextual `SensorReadingResource` sub-classes dynamically, developers radically enforce strict standard `Separation of Concerns`. Maintenance routing layers remain isolated natively ensuring granular class configurations elegantly scale modularly while naturally inheriting contextual path states recursively.

#### Question 5.1: Cybersecurity Threats Leaking Traces
Broadcasting deeply nested internal raw Java Exception stack traces explicitly alerts sophisticated interceptors mapping backend infrastructure versions intimately exposing precise vulnerable library topologies. Attackers deliberately exploit standard unhandled parameter injection anomalies intentionally provoking trace responses explicitly gathering data such as utilized persistence frameworks, dependency injection configurations, or local system structural directory namespaces structurally enabling highly targeted recursive zero-day exploits specifically. Implement `GlobalExceptionMappers` dynamically suppressing raw server diagnostics ensuring API consumers merely receive strictly generalized native `HTTP 500` error formats unconditionally masking backend architectures reliably.

#### Question 5.2: JAX-RS Interceptors versus Logger Injections
Manually appending repetitive logger statements directly interspersed within every local method significantly clutters core implementation pipelines recursively muddying logic visibility natively contradicting pure `DRY` configurations. By actively configuring centralized interceptor filter layers via programmatic `ContainerRequestFilter` integrations recursively intercepting all incoming/outgoing protocol traffic dynamically, developers perfectly enforce purely uncoupled `Aspect-Oriented Programming (AOP)` configurations enabling comprehensive API observable diagnostic aggregations elegantly modifying logging heuristics globally from a singular location without destructively tampering initialized endpoint routing classes natively tracking metadata intrinsically.
