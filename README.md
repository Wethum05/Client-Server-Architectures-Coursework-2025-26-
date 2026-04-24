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

## Demo Video Link
url: https://drive.google.com/file/d/1vU1aVwM1Zcq_28kHaz-QQUerR0mQG-8Y/view?usp=sharing
## Instructions to Build and Launch

1. **Verify Prerequisites:** Ensure Java JDK 23 and Maven (`mvn`) are installed correctly within your system environment.
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

## Report Question 

### 1.1	Question
The default Resource class lifecycle in JAX-RS is per-request. The JAX-RS runtime creates a new instance of the resource class each time an individual incoming HTTP request is received and then destroys it once the response has been delivered. This ensures thread safety because local variables within the resource are not being shared during concurrent requests. As this eliminates the opportunity to use instance variables to store persisting domain data directly, developers have to structurally decouple their persistence elements using either Singletons (‘DataStore.getInstance()’ ), thread safe dependencies implementation (‘ConcurrentHashMap’), or an external database layer to coordinate persistent arrays in a systematic way without the possibility of race conditions.

### 1.2 Question
By offering native "Hypermedia" (HATEOAS) (navigational) links that are mapped directly within normal system responses to move from fixed bindings to discoverable ones. Interacting clients do not necessarily need to explicitly encode all the system endpoint path URLs in their code. Rather, pulling in a centralized Discovery response, developers can natively query dynamic sub route sets dynamically altering UI layers as needed. This has a radical positive impact on long term architectural scaling; developers no longer need to be very dependent on traditional documentation that ensures that dynamically updating endpoint versions do not permanently destroy hardcoded client applications that are inherent parts of their infrastructures.

### 2.1 Question
The simple fact of a list of partial relational IDs returned optimally uses server bandwidth and significantly caps internal memory overhead using considerably dense nested recursive entities dynamically through the web. Recalling deep objects with a comprehensive query results in huge bloated megabytes of queries that request thousands of structural buildings natively. Sparing ID mapping, however, explicitly causes client-side processes to iteratively generate ‘N+1’ iterative requests that sequentially solve those nesting relations that contribute to spikes in latency at the local level. Architectural choices should therefore be made with keen consideration of reducing request round-trip dynamically at the expense of maintaining a tight network transfer allowance.

### 2.2 Question
Yes. The REST architectural paradigm requires that the operations of ‘DELETE’ be purely idempotent. In our structural implementation, when a client successfully makes a request to delete a building, say, by sending a request to: DELETE / roomId the building model in question is wiped away by the in memory array naturally giving a response of ‘204 No Content’. When they make the same DELETE request recursively and erroneously a second, third, or infinite time in succession on the same URL, the array will make a deterministic analysis of the data to produce a standardized custom 404 Not Found response since the identifier specified explicitly no longer logically exists. The system state is never changed in any way, no matter how many times it is deleted by redundant secondary deletions.

### 3.1 Question
Our framework supports input serialization only with strictly structured byte payloads of the JSON format by relying on the annotation, ‘@Consumes(MediaType.APPLICATION_JSON)’. When a naive client directly tries to append incompatible strings which have been formatted as ‘text/plain’ or verbose nested formats which use ‘application/xml’ as their native format the internal JAX-RS Servlet container interferes with the parsing of network Headers. A dynamic check of structural inconsistency, Jersey ensures that controller routing algorithms that bypass resource initialisation are immediately terminated by a proactive standardised HTTP 415 Unsupported Media Type response exception, and ensures that malicious/corrupted injections that would otherwise cause internal framework failures are by default blocked.

### 3.2 Question 
Using structured searches with the help of ‘@QueryParam("type"))’ literally abides by the pure RESTful URL philosophy that expresses explicitly parameter states that change the viewpoint of the same absolute collection space (‘/sensors’). When architectural paths are modeled using deep dynamic directories that combine URLs in a linear fashion like ‘/api/sensors/type/CO2’ they give misleading information of explicit unique logical sub entities structurally defining hard unscalable hierarchies naturally. The query strings are flexible to allow infinite complex combinations that can be naturally appended by logic (‘?type=CO2&status=ACTIVE’) and logically not possible in strictly hierarchical URL conventions of deep directory trees.

### 4.1 Question
Using large API with deep path hierarchies like `/sensors/{id}/readings/{rid}/maintenance/{mid}` and operating solely in monolithic structural controller classes is bound to cause catastrophic recursive "Code Smells" under the pretense of APIs. Radically applying strict standard Separation of Concerns, developers use Sub Resource Locator annotations that locally resolve mapping segments hierarchically to delegated contextual sub classes of the dynamically instantiated delegated contextual ‘SensorReadingResource’ . Maintenance routing layers are kept natively isolated so that granular configurations of classes scale elegantly modularly and contextual state of path is inherited naturally in a recursive manner.

### 5.1 Question
The use of HTTP 422 (Unprocessable Entity) is more semantically precise than a 404 since it is able to differentiate between a lost communication route and an error in valid data. A client can send a POST request to the /sensors endpoint, and the request will be sent to a valid resource, but when the JSON message includes a roomId, which is absent in the system, the error is not locational but semantic. It would be misleading to return a 404 to indicate that the endpoint of /sensors was not found, when the 422 status code used through ‘LinkedResourceNotFoundExceptionMapper’ is more appropriate because it informs the client that the server knows the request and the syntax is correct, but cannot execute the sensor registration because a violation of referential integrity in the payload data occurred.

### 5.2 Question
Explicitly broadcasting the deepest raw Java Exception stack trace of internal Java Exception traces is a strong indicator to alert the more advanced interceptors of backend infrastructure versions that explicitly reveal the exact vulnerable topology of libraries. The standard unhandled parameter injection anomalies are specifically targeted with the aim of generating trace responses expressly collecting data like the persistence frameworks, dependency injection settings, or local system structural directory namespaces, which happen to be structurally configured to support highly specific recursive zero day exploits in particular. Introduce dynamically configured ‘GlobalExceptionMappers’ that suppress raw server diagnostics without providing API consumers with anything other than unconditionally generalized native HTTP 500 error responses as a reliable way of masking backend architectures.

### 5.3 Question
Manually adding repeated logger statements literally sprinkled between each local method greatly contaminates core implementation pipelines recursively obscuring logic visibility that is inherently incompatible with pure `DRY` settings. The developers beautifully impose purely uncoupled Aspect-Oriented Programming (AOP) configurations through dynamic configurations of centralized interceptors filter layers by programmatic ‘ContainerRequestFilter’ integrations recursively intercepting all incoming/outgoing protocol traffic dynamically without being destructively invasive to initially instantiate endpoint routing classes which intrinsically track metadata.
