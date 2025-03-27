
# Architect Practical Test Case

In this test case we will be working under the pretense that you have been hired by a newspaper that wants to set up a website to gain wider audiences, the company is called NewsNow. NewsNow wants to do an MVP of a simple system that will serve news to users, for that they have distinguished the following technical components:
- A frontend that is designed as a static website that will show the content in an appealing and user friendly way.
- A REST API that will work as the backend for the whole system, storing and retrieving data.
 
Another static frontend that will require users to be logged in to add, edit and delete news articles that will be shown via the first App. Despite its early stage, NewsNow foresees that there will be huge traffic spikes since they will be relying heavily on influencers to create content and advertise it.

Assuming that they want to be as reliant as possible on PaaS (Platform as a Service) and that NewsNow has made the corporate decision to work with cloud providers please answer the following:

## Infrastructure
### 1. Please create a diagram of the architecture you would propose to them (we recommend using a tool such as draw.io or similar). In addition, please explain the thought process you have followed to reach this decision.

For the solution architecture, I propose the following approach:

Public Frontend:

Given that the public-facing website is static, I recommend using a Content Delivery Network (CDN). This will geographically distribute the content to ensure fast download speeds, as resources will be served from locations closer to the users, improving performance. Additionally, it will provide multiple replicas, helping to efficiently manage traffic by balancing the load across different regions, especially during peak traffic times.

Private Website:

For the private website, I suggest adopting the same CDN approach as with the public frontend. Additionally, to offload responsibility from the backend, I recommend integrating an external authentication service. This will allow us to delegate the scalability of the authentication functionality, avoiding the need to manage this logic internally within the backend.

Backend:

The backend will consist of multiple instances with auto-scaling capabilities to handle varying traffic loads. These instances will be distributed via a load balancer to ensure efficient traffic distribution. The backend will leverage the external authentication service to authorize requests as needed, maintaining a secure and scalable system.

This architecture ensures performance, scalability, and security by utilizing modern services like CDNs and external authentication providers, while also maintaining flexibility in handling traffic peaks.

### 2. How would this system be able to handle the massive traffic NewsNow is expecting? What possible pain points do you foresee?
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
### 4. To finish the PoC and allow the team to test it, would you be able to deploy it somewhere where it can be tested? Please detail the process.