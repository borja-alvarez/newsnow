
# Architect Practical Test Case

In this test case we will be working under the pretense that you have been hired by a newspaper that wants to set up a website to gain wider audiences, the company is called NewsNow. NewsNow wants to do an MVP of a simple system that will serve news to users, for that they have distinguished the following technical components:
- A frontend that is designed as a static website that will show the content in an appealing and user friendly way.
- A REST API that will work as the backend for the whole system, storing and retrieving data.
 
Another static frontend that will require users to be logged in to add, edit and delete news articles that will be shown via the first App. Despite its early stage, NewsNow foresees that there will be huge traffic spikes since they will be relying heavily on influencers to create content and advertise it.

Assuming that they want to be as reliant as possible on PaaS (Platform as a Service) and that NewsNow has made the corporate decision to work with cloud providers please answer the following:

## Infrastructure
### 1. Please create a diagram of the architecture you would propose to them (we recommend using a tool such as draw.io or similar). In addition, please explain the thought process you have followed to reach this decision.
#### Answer

![diagram](./doc/01.jpg)

For the solution architecture, I propose the following approach:
##### Public Frontend
Given that the public-facing website is static, I recommend using a Content Delivery Network (CDN) like Akamai. This will geographically distribute the content to ensure fast download speeds, as resources will be served from locations closer to the users, improving performance. Additionally, it will provide multiple replicas, helping to efficiently manage traffic by balancing the load across different regions, especially during peak traffic times.
##### Private Website
For the private website, I suggest adopting the same CDN approach as with the public frontend. Additionally, to offload responsibility from the backend, I recommend integrating an external authentication service. This will allow us to delegate the scalability of the authentication functionality, avoiding the need to manage this logic internally within the backend.
To securely serve private files (such as user-specific content or downloadable assets), signed URLs can be used in the CDN. These URLs include time-limited tokens that restrict access to authorized users only, allowing secure and temporary access to resources without exposing them publicly.
##### Backend
The backend will consist of multiple instances with auto-scaling capabilities to handle varying traffic loads, and can be deployed on OpenShift for container orchestration and scalable infrastructure management. These instances will be distributed via a load balancer to ensure efficient traffic distribution. The backend will leverage the external authentication service to authorize requests as needed, maintaining a secure and scalable system.

To ensure efficient and performant interaction with the database, a connection pool will be used. Instead of opening and closing a new database connection for each request, the application will reuse a set of pre-established connections. This reduces overhead, improves response time, and limits the number of concurrent connections hitting the database, which is crucial for maintaining stability under high load.

This architecture ensures performance, scalability, and security by utilizing modern services like CDNs and external authentication providers, while also maintaining flexibility in handling traffic peaks.

### 2. How would this system be able to handle the massive traffic NewsNow is expecting? What possible pain points do you foresee?
#### Answer: Handling Massive Traffic for NewsNow
##### Content Delivery Network (CDN) for Static Frontends
Both the public and private frontends are served through a CDN, which will significantly reduce load times and distribute traffic efficiently. The CDN ensures that static assets (HTML, CSS, JavaScript, images) are cached and served from edge locations closest to users, reducing the load on the origin servers. This approach minimizes the impact of high traffic spikes, as the majority of requests will not reach the backend. For protected or user-specific files, signed URLs can be used to provide time-limited, secure access to resources via the CDN, preventing unauthorized access to private content.

##### Auto-Scaling Backend with Load Balancing
The REST API backend is designed with auto-scaling to dynamically adjust the number of instances based on demand. This ensures that during traffic spikes, additional resources are provisioned automatically. A load balancer will distribute requests efficiently across backend instances, preventing any single server from being overwhelmed. The database layer should also be designed with read replicas or a caching layer (e.g., Redis, Cloudflare Cache, or AWS ElastiCache) to reduce the number of expensive database queries. Additionally, using a database connection pool ensures efficient reuse of database connections, reducing overhead and improving performance under high concurrency.
 
##### External Authentication Service
Delegating authentication to an external service ensures that the backend does not need to handle user session management, login requests, or security concerns, offloading these responsibilities to a scalable third-party provider. This prevents the authentication system from becoming a bottleneck during peak traffic times.

##### Asynchronous Processing & Caching
To improve response times and reduce backend load, caching mechanisms such as CDN caching, API response caching, and database query caching will be implemented. Asynchronous processing (e.g., message queues like ArtemisAMQ, or Kafka) can be used for background tasks such as analytics tracking, notifications, and content indexing, preventing API endpoints from slowing down under heavy traffic.

#### Answer: Possible Pain Points & Challenges
##### CDN Cache Invalidation
While CDNs improve performance, managing cache expiration for dynamic news content can be challenging. If updates to news articles need to be reflected in real time, an effective cache invalidation strategy must be in place to refresh content appropriately. Additionally, for secure or time-sensitive content (such as premium or user-specific media), signed URLs should be used to control access at the CDN level, ensuring secure, time-limited delivery of protected assets.

##### Database Bottlenecks
Under heavy traffic, database queries can become a performance bottleneck. Solutions include:
- Read replicas to distribute query loads.
- Database sharding if write-heavy operations become a concern.
- Using caching layers (e.g., Redis or Memcached) to reduce database hits.
- Employing a connection pool to efficiently manage and reuse database connections, reducing the overhead of frequent connection creation and improving response times under high concurrency.

##### Authentication Rate Limits & Dependency on Third-Party Providers
If many users attempt to log in simultaneously, the external authentication provider may become a bottleneck. It’s crucial to choose a provider that supports rate limiting and auto-scaling. A failover mechanism should be in place in case of authentication service outages.

##### Backend Auto-Scaling Costs & Cold Starts
While auto-scaling ensures system availability, cold starts for new instances could introduce latency. Using warm pools or pre-warmed instances can help mitigate delays. Optimizing backend responses and database queries will also reduce the need for excessive auto-scaling, lowering operational costs.

## Development
### 3. In order to serve Images to clients on different devices, NewsNow decides to create a simple service that dynamically rescales images to different resolutions. Your task as the lead architect is to make sure the structure is well defined and provide a functional PoC to the team.
In order to accomplish this task, you decide to create a REST API with the following endpoints:
- POST /task – This receives an image and specific dimensions to rescale it, and outputs the image in the desired new dimensions (as an extra, you can store the image somewhere and just return the URL of the resource). In addition, the system will persist the current information associated with the task:
    - Timestamp of when the task was created
    - MD5 of the original file
    - Resolution applied to the image
    - OPTIONAL: URL of the stored image
- GET /task/:taskId
    - This endpoint will return the information associated with the task associated with the corresponding Id.

In order to do the image rescaling you can use a framework like [Thumbnailator](https://github.com/coobird/thumbnailator) for Java or [Sharp](https://github.com/lovell/sharp) for node.js.

#### Answer

This project is a Java-based application built with Maven and Spring Boot, following a Hexagonal Architecture approach to ensure modularity, maintainability, and separation of concerns. The system is structured into multiple submodules, each serving a distinct role in the architecture:

boot 
This module is responsible for the application's initial setup and configuration, including dependency injection, environment settings, and any global configurations needed for the system to run efficiently.
- apirest 
Contains the REST controllers that expose the application's endpoints.
Implements DTO-to-entity mapping using MapStruct, ensuring clean and efficient data transformation.
OpenAPI is used to define and document the REST API endpoints, improving clarity and facilitating API consumption by external clients.
- application
Houses the business logic implementations for the various use cases of the system.
Includes helpers and service implementations that orchestrate interactions between the domain and infrastructure layers while ensuring adherence to business rules.
- domain
Defines the entity classes, encapsulating the core business logic of the system.
Contains the interfaces for use cases, repository interactions, and third-party services, ensuring decoupling from specific framework implementations.
Acts as the pure domain layer, maintaining business rules without dependencies on infrastructure or external technologies.
- infrastructure  
Implements the third-party service integrations, repository persistence mechanisms, and external system interactions.
This module ensures that external dependencies (databases, APIs, messaging systems, etc.) are abstracted from the business logic, promoting flexibility and easier adaptability to changes in external services.

### 4. To finish the PoC and allow the team to test it, would you be able to deploy it somewhere where it can be tested? Please detail the process.

#### Answer
To ensure a fast and simple deployment of the NewsNow system, Heroku is a suitable Platform as a Service (PaaS) option that allows seamless integration with GitHub for automated deployments. The deployment process follows these steps:

- GitHub Integration & Deployment
The application repository is linked to Heroku, enabling continuous deployment whenever changes are pushed to the main branch.
Environment variables are configured directly in Heroku’s settings to securely store credentials and API keys.

- Configuration for Third-Party Services
The application integrates with Cloudinary for media storage and MongoDB for database management.
Environment variables are used to store Cloudinary API keys and the MongoDB connection string, ensuring secure access without hardcoding sensitive information.

- Procfile & System Properties for Java Execution
A Procfile is required to specify the command for executing the Java application, ensuring it runs correctly on Heroku’s environment. 
A system.properties file is added to define the Java version required for the project. This ensures that Heroku provisions the correct runtime environment to execute the application.

This endpoint can be accessed via the URL https://vast-headland-88301-061ea46e93be.herokuapp.com

