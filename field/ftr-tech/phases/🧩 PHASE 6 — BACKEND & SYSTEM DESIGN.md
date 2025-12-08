# Backend Architecture Theory

# Monolith vs Microservices
# 1️⃣ Monolithic Architecture

### 🔹 Definition:

> A **monolithic architecture** is a **single unified application** where all components—UI, business logic, and database access—are **tightly coupled and deployed as one unit**.

### 🔹 Key Features:

- Single codebase
    
- Single deployment
    
- Shared memory and database
    
- Tight coupling between modules
    

### 🔹 Example:

- Traditional e-commerce app:
    
    - User authentication
        
    - Product catalog
        
    - Order processing
        
    - Payment handling  
        All in **one application/service**
        

---

# 2️⃣ Microservices Architecture

### 🔹 Definition:

> **Microservices architecture** breaks an application into **independent, loosely coupled services**, each handling a **specific business capability**, communicating via **APIs or messaging**.

### 🔹 Key Features:

- Small, independent services
    
- Each service has its **own database / storage** (polyglot persistence possible)
    
- Services communicate via REST, gRPC, or message queues
    
- Each service can be deployed independently
    

### 🔹 Example:

- E-commerce app split into microservices:
    
    - Auth service
        
    - Product service
        
    - Order service
        
    - Payment service
        
- Each service can be developed, deployed, and scaled independently
    

---

# 3️⃣ Monolith vs Microservices Table

| Feature               | Monolith                       | Microservices                                   |
| --------------------- | ------------------------------ | ----------------------------------------------- |
| **Codebase**          | Single                         | Multiple small services                         |
| **Deployment**        | One unit                       | Independent per service                         |
| **Coupling**          | Tight                          | Loose                                           |
| **Scaling**           | Vertical (scale whole app)     | Horizontal (scale services individually)        |
| **Technology Stack**  | Usually single stack           | Can use polyglot (different stacks per service) |
| **Fault Isolation**   | Low (one crash may affect all) | High (one service crash doesn’t break all)      |
| **Development Speed** | Simple for small apps          | Faster for large teams / large apps             |
| **Complexity**        | Low initially                  | High (distributed system challenges)            |
| **Testing**           | Easier                         | More complex (integration testing)              |
| **Use Case**          | Small apps, MVPs               | Large, complex, high-traffic apps               |

---

# 4️⃣ Advantages of Monolith

- ✅ Simple to develop, test, and deploy initially
    
- ✅ Easier debugging and logging
    
- ✅ Less infrastructure overhead
    

---

# 5️⃣ Disadvantages of Monolith

- ❌ Hard to scale parts independently
    
- ❌ Slower development for large teams (merge conflicts, tight coupling)
    
- ❌ Hard to adopt new technologies in parts of the app
    
- ❌ A bug can bring down the entire system
    

---

# 6️⃣ Advantages of Microservices

- ✅ Independent deployment of services
    
- ✅ Technology flexibility per service
    
- ✅ Easier horizontal scaling
    
- ✅ Fault isolation
    
- ✅ Better for large, distributed teams
    

---

# 7️⃣ Disadvantages of Microservices

- ❌ Complex architecture (service discovery, load balancing, monitoring)
    
- ❌ Network latency due to inter-service communication
    
- ❌ Difficult distributed transactions
    
- ❌ Logging, debugging, and testing are more complex
    

---

# 8️⃣ Real-World Analogy

- **Monolith** → Apartment building where **all rooms are connected internally**; one water pipe problem affects the whole building
    
- **Microservices** → Independent houses in a neighborhood; one house’s plumbing issue **doesn’t affect others**
    

---

# 9️⃣ When to Use Which

| Scenario                                  | Recommended Architecture    |
| ----------------------------------------- | --------------------------- |
| Small apps, MVPs                          | Monolith                    |
| Early-stage startups                      | Monolith (simpler to start) |
| Large-scale apps, enterprise systems      | Microservices               |
| Frequent feature updates & scaling needed | Microservices               |

---

# 🔟 One-Line Interview Definitions

- **Monolith** → Single unified application, tightly coupled modules
    
- **Microservices** → Collection of independent, loosely coupled services communicating via APIs
    

---

# 1️⃣1️⃣ Final Summary (Powerful & Short)

> Monolithic architecture is a single, tightly coupled application that is simple for small apps but hard to scale for large systems. Microservices break an application into independent, loosely coupled services that can be developed, deployed, and scaled separately, suitable for complex, high-traffic applications, but with higher operational complexity.
---

# Stateless vs Stateful

# 1️⃣ What Does "State" Mean?

- **State** refers to **information about the current status of a client or session** stored on the server.
    
- Examples: User login session, shopping cart contents, transaction progress
    

---

# 2️⃣ Stateless Architecture

### 🔹 Definition:

> A **stateless system** does **not store any client information (state) between requests**. Each request is **independent** and contains all necessary information.

### 🔹 Key Features:

- Server **doesn’t remember past requests**
    
- Each request must contain all **context/data**
    
- Easier to scale horizontally
    

### 🔹 Example:

- REST APIs are typically **stateless**:
    
    - Request: `GET /user/123?token=xyz` → server responds based on token
        
    - No session stored on server
        

---

# 3️⃣ Stateful Architecture

### 🔹 Definition:

> A **stateful system** **remembers client information (state) between requests**, typically stored in server memory or session.

### 🔹 Key Features:

- Server keeps track of **sessions** or **previous interactions**
    
- Requests depend on **stored state**
    
- Scaling requires **session replication** or sticky sessions
    

### 🔹 Example:

- Traditional web apps using server-side sessions:
    
    - User logs in → server stores session ID in memory
        
    - Subsequent requests reference session → server knows user is authenticated
        

---

# 4️⃣ Stateless vs Stateful Table

| Feature              | Stateless                      | Stateful                                         |
| -------------------- | ------------------------------ | ------------------------------------------------ |
| Server memory        | No state stored                | Stores session/state                             |
| Scalability          | Easy horizontal scaling        | Harder, needs session replication                |
| Request independence | Each request contains all info | Requests depend on previous state                |
| Fault tolerance      | Easy recovery                  | Harder recovery (session lost if server crashes) |
| Examples             | REST APIs, CDN, DNS            | Web app sessions, FTP connections                |

---

# 5️⃣ Pros & Cons

### Stateless Pros:

- ✅ Easy to scale horizontally
    
- ✅ Easy failover & recovery
    
- ✅ Simple load balancing
    

### Stateless Cons:

- ❌ Client must send all context in each request
    
- ❌ Some operations require **extra effort** (e.g., authentication token in every request)
    

### Stateful Pros:

- ✅ Server can maintain complex **sessions & transactions**
    
- ✅ Easier for **multi-step workflows**
    

### Stateful Cons:

- ❌ Hard to scale horizontally
    
- ❌ Server failure may lead to **lost sessions**
    
- ❌ More complex load balancing
    

---

# 6️⃣ Real-World Analogy

- **Stateless** → Ordering pizza via self-service kiosk every time; you provide all info on every order.
    
- **Stateful** → Talking to a waiter who remembers your previous orders and preferences for the session.
    

---

# 7️⃣ When to Use Which

| Scenario                                             | Recommended         |
| ---------------------------------------------------- | ------------------- |
| REST APIs, microservices                             | Stateless           |
| Shopping carts, multi-step forms, FTP sessions       | Stateful            |
| Load-balanced, cloud-native apps                     | Stateless preferred |
| Legacy applications, multiplayer games with sessions | Stateful            |

---

# 8️⃣ One-Line Interview Definitions

- **Stateless** → Server does not remember past requests; each request is independent
    
- **Stateful** → Server remembers session or client state across multiple requests
    

---

# 9️⃣ Final Summary (Powerful & Short)

> Stateless systems treat each request independently, making them easy to scale, fault-tolerant, and simple for distributed architectures. Stateful systems maintain client context between requests, useful for complex sessions, but harder to scale and recover from failures.

---

# Sync vs Async communication

# 1️⃣ Definition

| Term                           | Definition                                                                                                                     |
| ------------------------------ | ------------------------------------------------------------------------------------------------------------------------------ |
| **Synchronous Communication**  | The sender waits for the receiver to **process the request and respond** before continuing.                                    |
| **Asynchronous Communication** | The sender sends the request and **does not wait**; the response can come later or be handled via a callback, queue, or event. |

---

# 2️⃣ Synchronous Communication

### 🔹 Key Features:

- Request-response pattern
    
- **Blocking**: sender waits for response
    
- Typically used in **real-time operations**
    

### 🔹 Examples:

- HTTP request-response (REST API)
    
- RPC (Remote Procedure Call)
    
- Database query in traditional apps
    

### 🔹 Real-World Analogy:

- Calling a friend on the phone → you **wait for their answer** before doing anything else
    

### 🔹 Pros:

- ✅ Simple & intuitive
    
- ✅ Easier error handling & debugging
    
- ✅ Immediate results
    

### 🔹 Cons:

- ❌ Blocks the sender → inefficient for long-running tasks
    
- ❌ Harder to scale under high load
    

---

# 3️⃣ Asynchronous Communication

### 🔹 Key Features:

- Request is **sent without waiting**
    
- Response is handled later via **callback, message queue, or event system**
    
- Non-blocking
    

### 🔹 Examples:

- Message queues (RabbitMQ, Kafka)
    
- Event-driven microservices
    
- Webhooks / Server-Sent Events / Push notifications
    

### 🔹 Real-World Analogy:

- Sending an email → you **don’t wait** for a response; the recipient replies later
    

### 🔹 Pros:

- ✅ Efficient resource utilization (no waiting)
    
- ✅ Scales well for high-load, long-running tasks
    
- ✅ Decouples services in distributed systems
    

### 🔹 Cons:

- ❌ More complex architecture
    
- ❌ Error handling and debugging are harder
    
- ❌ Responses may be delayed → eventual consistency
    

---

# 4️⃣ Sync vs Async Table

| Feature     | Synchronous                           | Asynchronous                            |
| ----------- | ------------------------------------- | --------------------------------------- |
| Waiting     | Sender waits for response             | Sender continues immediately            |
| Blocking    | Yes                                   | No                                      |
| Scalability | Limited under heavy load              | High scalability                        |
| Complexity  | Simple                                | More complex                            |
| Use Case    | Real-time requests, immediate results | Background jobs, event-driven workflows |
| Examples    | REST API calls, RPC                   | Kafka, RabbitMQ, Webhooks, Email        |

---

# 5️⃣ Hybrid Patterns

- **Async inside Sync**: Microservice receives a synchronous request but internally processes tasks asynchronously
    
- **Sync over Async**: Client waits on a long-running async process via polling or webhooks
    

✅ Useful for **long-running operations like file processing, notifications, ML inference**

---

# 6️⃣ When to Use Which

| Scenario                                         | Recommended            |
| ------------------------------------------------ | ---------------------- |
| Real-time payment processing                     | Synchronous            |
| Sending notifications, logs, or analytics events | Asynchronous           |
| CRUD API calls                                   | Synchronous            |
| Background tasks / batch jobs                    | Asynchronous           |
| High-concurrency microservices                   | Asynchronous preferred |

---

# 7️⃣ One-Line Interview Definitions

- **Synchronous** → Request blocks until response is received
    
- **Asynchronous** → Request does not block; response handled later
    

---

# 8️⃣ Final Summary (Powerful & Short)

> Synchronous communication waits for a response before proceeding, making it simple but potentially blocking under high load. Asynchronous communication allows the sender to continue without waiting, improving scalability and decoupling services, but adds architectural complexity and delayed responses.

---

# Event-driven systems

# 1️⃣ What Is an Event-Driven System?

### 🔹 Definition:

> An **event-driven system** is a software architecture where **components (services or modules) communicate by producing and consuming events**, rather than direct synchronous calls.

- **Event**: A significant change or occurrence in the system (e.g., order placed, user logged in).
    
- **Producers** generate events.
    
- **Consumers** react to events.
    
- Communication is often **asynchronous**.
    

---

# 2️⃣ Key Features

- **Loose coupling** → Producers and consumers don’t need to know about each other
    
- **Asynchronous processing** → Events can be processed later
    
- **Scalable** → Services can scale independently
    
- **Reactive** → Responds to changes in real time
    

---

# 3️⃣ Architecture Components

| Component                  | Role                                                  |
| -------------------------- | ----------------------------------------------------- |
| **Event Producer**         | Generates events (e.g., Order Service)                |
| **Event Channel / Broker** | Transports events (e.g., Kafka, RabbitMQ, SNS/SQS)    |
| **Event Consumer**         | Listens and reacts to events (e.g., Email Service)    |
| **Event Store (Optional)** | Stores events for auditing or replay (event sourcing) |

---

# 4️⃣ Examples in Real Systems

- **E-commerce platform**:
    
    - Event: `OrderPlaced`
        
    - Consumers: Inventory Service, Shipping Service, Email Notification Service
        
- **Social media**:
    
    - Event: `NewPostCreated`
        
    - Consumers: Feed Service, Notification Service, Analytics Service
        
- **Payment processing**:
    
    - Event: `PaymentCompleted`
        
    - Consumers: Accounting Service, Fraud Detection, Email Notification
        

---

# 5️⃣ Event-Driven Patterns

1. **Pub/Sub (Publish/Subscribe)**
    
    - Producers publish events to a channel/broker
        
    - Multiple consumers subscribe to relevant events
        
    - Example: Kafka, RabbitMQ, Google Pub/Sub
        
2. **Event Sourcing**
    
    - State of a system is stored as a **sequence of events**
        
    - Example: Banking transaction logs
        
3. **CQRS (Command Query Responsibility Segregation)**
    
    - Commands (write) and Queries (read) are separated
        
    - Often combined with event-driven architecture
        

---

# 6️⃣ Advantages

- ✅ Loose coupling → services can evolve independently
    
- ✅ Scalability → async processing reduces bottlenecks
    
- ✅ Resilience → failures in one consumer don’t stop the producer
    
- ✅ Real-time processing → faster reactions to changes
    

---

# 7️⃣ Disadvantages / Trade-offs

- ❌ Complexity in debugging & monitoring
    
- ❌ Eventual consistency → data may be temporarily inconsistent
    
- ❌ Ordering of events may require careful handling
    
- ❌ Potential for message duplication → idempotent consumers required
    

---

# 8️⃣ Event-Driven vs Request-Driven

| Feature       | Event-Driven    | Request-Driven (Sync) |
| ------------- | --------------- | --------------------- |
| Communication | Asynchronous    | Synchronous           |
| Coupling      | Loose           | Tight                 |
| Response      | Not immediate   | Immediate             |
| Scalability   | High            | Limited by blocking   |
| Examples      | Kafka, RabbitMQ | REST API calls, RPC   |

---

# 9️⃣ Real-World Analogy

- **Event-driven** → Fire alarm system:
    
    - Alarm triggers → sprinkler, security, and notification systems respond independently
        
- **Request-driven** → Calling a single person to report a fire → sequential, blocking
    

---

# 🔟 One-Line Interview Definitions

- **Event-driven system** → Components communicate asynchronously by producing and consuming events
    
- **Event producer** → Generates events
    
- **Event consumer** → Reacts to events
    
- **Event broker** → Transports events between producers and consumers
    

---

# 1️⃣1️⃣ Final Summary (Powerful & Short)

> Event-driven systems are loosely coupled, asynchronous architectures where services communicate via events. They enable **scalable, reactive, and resilient systems**, but introduce complexity in monitoring, debugging, and maintaining eventual consistency.

---
    
# Message brokers

# 1️⃣ What Is a Message Broker?

### 🔹 Definition:

> A **message broker** is a **middleware component** that **receives, stores, and forwards messages** between services, applications, or systems, enabling **decoupled communication**.

- It ensures **reliable, asynchronous communication** between producers and consumers.
    
- Key in **event-driven systems, microservices, and distributed architectures**.
    

---

# 2️⃣ Key Features

- **Decoupling** → Producers and consumers don’t need to know about each other
    
- **Asynchronous messaging** → Senders don’t block while waiting for a response
    
- **Reliable delivery** → Ensures messages aren’t lost (acknowledgment, retry)
    
- **Message routing** → Direct, topic-based, or queue-based delivery
    
- **Persistence** → Messages can be stored until successfully delivered
    

---

# 3️⃣ How It Works

1. **Producer** sends a message to the broker
    
2. Broker stores the message in a **queue or topic**
    
3. **Consumer** retrieves and processes the message
    
4. Broker can handle **multiple producers and consumers**
    

**Flow Diagram (Simplified)**:

```
Producer → Broker (Queue/Topic) → Consumer
```

---

# 4️⃣ Types of Messaging

| Type                          | Description                                             | Example                      |
| ----------------------------- | ------------------------------------------------------- | ---------------------------- |
| **Point-to-Point (Queue)**    | One producer, one consumer per message                  | RabbitMQ, Amazon SQS         |
| **Publish/Subscribe (Topic)** | One producer, multiple subscribers receive messages     | Kafka, MQTT, SNS             |
| **Streaming / Event Log**     | Messages stored in order, multiple consumers can replay | Apache Kafka, Amazon Kinesis |

---

# 5️⃣ Popular Message Brokers

| Broker            | Type                | Key Features                                          |
| ----------------- | ------------------- | ----------------------------------------------------- |
| **RabbitMQ**      | Queue-based         | Reliable delivery, supports complex routing, TTL, ack |
| **Apache Kafka**  | Log/Streaming       | High throughput, partitioned logs, replayable events  |
| **Amazon SQS**    | Queue-based         | Fully managed, scalable, durable                      |
| **Redis Pub/Sub** | In-memory Pub/Sub   | Fast, ephemeral, not persistent                       |
| **NATS / MQTT**   | Lightweight Pub/Sub | IoT, low-latency messaging                            |

---

# 6️⃣ Advantages

- ✅ Decouples producers and consumers
    
- ✅ Enables **asynchronous processing**
    
- ✅ Improves **scalability and fault tolerance**
    
- ✅ Supports **load leveling** (buffering spikes in traffic)
    
- ✅ Durable delivery ensures messages aren’t lost
    

---

# 7️⃣ Disadvantages / Trade-offs

- ❌ Additional **operational complexity**
    
- ❌ Message ordering can be tricky
    
- ❌ Possible **duplicate delivery** → consumers must be **idempotent**
    
- ❌ Latency higher than direct calls for real-time synchronous operations
    

---

# 8️⃣ Real-World Analogy

- **Post office analogy**:
    
    - Producer → Person sending a letter
        
    - Broker → Post office (stores, routes, delivers)
        
    - Consumer → Recipient picking up the letter
        
- Advantages: No need for sender and receiver to meet in real time
    

---

# 9️⃣ Use Cases

- **Order processing**: E-commerce systems process orders asynchronously
    
- **Notifications**: Email, SMS, push notifications
    
- **Log aggregation**: Centralized logging systems (ELK + Kafka)
    
- **IoT systems**: Sensor data ingestion and processing
    
- **Event-driven microservices**: Decoupled services communicate reliably
    

---

# 🔟 One-Line Interview Definitions

- **Message Broker** → Middleware that routes, stores, and delivers messages between decoupled services
    
- **Producer** → Sends messages to the broker
    
- **Consumer** → Receives and processes messages from the broker
    
- **Queue** → Messages delivered to one consumer
    
- **Topic** → Messages delivered to multiple subscribers
    

---

# 11️⃣ Final Summary (Powerful & Short)

> Message brokers are middleware systems that enable **reliable, asynchronous, decoupled communication** between producers and consumers. They are foundational in **event-driven and microservices architectures**, supporting **queues, topics, streaming**, and ensuring **scalability, fault tolerance, and load leveling**.

---

# High availability

# 1️⃣ What Is High Availability?

### 🔹 Definition:

> **High Availability (HA)** is the design and implementation of a system that ensures it remains **operational and accessible for a very high percentage of time**, minimizing downtime.

- Often measured as **uptime percentage** per year:
    

| Availability         | Downtime per year |
| -------------------- | ----------------- |
| 99% (two nines)      | ~3.65 days        |
| 99.9% (three nines)  | ~8.76 hours       |
| 99.99% (four nines)  | ~52.56 minutes    |
| 99.999% (five nines) | ~5.26 minutes     |

---

# 2️⃣ Key Principles of High Availability

1. **Redundancy** → Duplicate critical components to prevent single points of failure
    
2. **Failover** → Automatic switching to backup systems during failure
    
3. **Load Balancing** → Distribute traffic to avoid overloading any node
    
4. **Monitoring & Alerts** → Detect failures quickly
    
5. **Decoupling** → Loosely coupled components reduce cascading failures
    

---

# 3️⃣ High Availability vs Fault Tolerance vs Disaster Recovery

| Feature  | High Availability                         | Fault Tolerance                | Disaster Recovery           |
| -------- | ----------------------------------------- | ------------------------------ | --------------------------- |
| Goal     | Minimize downtime                         | No interruption during failure | Recover from major disaster |
| Approach | Redundancy + failover                     | Immediate redundancy & backup  | Backup & restore systems    |
| Downtime | Very low                                  | Zero                           | Potentially higher          |
| Example  | Multi-node web servers with load balancer | Hot standby database           | Daily backup restore        |

---

# 4️⃣ Strategies for High Availability

### 4.1 Redundancy

- Use **multiple instances** of servers, databases, or services
    
- Active-active or active-passive setups
    

### 4.2 Load Balancing

- Distribute traffic across **multiple servers**
    
- Prevents single server overload and improves HA
    

### 4.3 Failover Mechanisms

- **Automatic failover** → traffic rerouted if a server/node fails
    
- **Manual failover** → human intervention (less ideal)
    

### 4.4 Clustering

- Nodes work together to provide **continuous service**
    
- Example: Database clusters (MySQL Group Replication, PostgreSQL Patroni)
    

### 4.5 Replication

- **Data replication** across nodes ensures no single point of failure
    
- Example: Master-slave or master-master replication
    

### 4.6 Health Checks & Monitoring

- Automated **heartbeats** to detect failures
    
- Alerts for operators to intervene quickly
    

---

# 5️⃣ HA vs Scalability vs Performance

| Feature    | High Availability      | Scalability                 | Performance                   |
| ---------- | ---------------------- | --------------------------- | ----------------------------- |
| Goal       | Reduce downtime        | Handle more load            | Faster response               |
| Focus      | Redundancy & failover  | Horizontal/vertical scaling | Optimize processing & latency |
| Trade-offs | Extra cost, complexity | Resource usage              | May not improve HA            |

---

# 6️⃣ Real-World Analogy

- **HA** → Commercial airline system:
    
    - Multiple planes on standby (redundancy)
        
    - Flight rerouting if airport closed (failover)
        
    - Continuous monitoring for safety (health checks)
        
- **Without HA** → Single plane; any problem cancels flights
    

---

# 7️⃣ Examples in Technology

| Layer       | Example                                             |
| ----------- | --------------------------------------------------- |
| Web servers | Multiple EC2 instances behind ALB (AWS)             |
| Databases   | MySQL master-slave replication, PostgreSQL clusters |
| Storage     | Distributed storage (S3, HDFS)                      |
| Messaging   | Kafka clusters, RabbitMQ mirrored queues            |
| DNS         | Failover DNS, multi-region setup                    |

---

# 8️⃣ Pros of High Availability

- ✅ Minimal downtime → improved user experience
    
- ✅ System resilience → withstands failures
    
- ✅ Supports business-critical applications
    
- ✅ Often combined with disaster recovery for robustness
    

---

# 9️⃣ Cons / Trade-Offs

- ❌ Higher infrastructure cost → multiple nodes, replicas
    
- ❌ Increased complexity → monitoring, failover, replication
    
- ❌ Requires careful design → avoid single points of failure
    
- ❌ Potential consistency challenges in distributed HA systems
    

---

# 🔟 One-Line Interview Definitions

- **High Availability** → System design ensuring minimal downtime and continuous access
    
- **Failover** → Automatic switch to backup components during failure
    
- **Redundancy** → Duplicate components to eliminate single points of failure
    
- **Active-Active / Active-Passive** → HA deployment strategies
    

---

# 1️⃣1️⃣ Final Summary (Powerful & Short)

> High Availability ensures systems remain operational with minimal downtime using **redundancy, replication, clustering, load balancing, and failover mechanisms**. It is essential for **mission-critical and distributed applications**, though it increases cost and complexity.

---

# Fault tolerance
# 1️⃣ What Is Fault Tolerance?

### 🔹 Definition:

> **Fault Tolerance (FT)** is the ability of a system to **continue operating correctly even if some components fail**.

- Unlike high availability (which minimizes downtime), fault tolerance aims for **zero interruption**, keeping services running seamlessly.
    
- Essential in **mission-critical systems** like banking, aviation, or medical applications.
    

---

# 2️⃣ Key Principles of Fault Tolerance

1. **Redundancy** → Duplicate critical components (servers, databases, network links)
    
2. **Error detection** → Systems detect failures automatically (heartbeat checks, monitoring)
    
3. **Recovery** → Automatic correction or switching to backup components
    
4. **Isolation** → Failures in one component **don’t cascade** to others
    
5. **Graceful degradation** → System may reduce functionality but remain operational
    

---

# 3️⃣ Fault Tolerance vs High Availability

| Feature    | Fault Tolerance                             | High Availability                       |
| ---------- | ------------------------------------------- | --------------------------------------- |
| Goal       | Continue operating **without interruption** | Minimize downtime                       |
| Recovery   | Instant / automatic                         | May involve failover delay              |
| Redundancy | Often active-active                         | Can be active-passive                   |
| Cost       | Higher (more redundancy)                    | Medium                                  |
| Example    | Flight control systems                      | Web server cluster behind load balancer |

---

# 4️⃣ Strategies for Fault Tolerance

### 4.1 Redundancy

- **Active-Active** → Both components handle requests simultaneously
    
- **Active-Passive** → Backup is idle but ready to take over
    

### 4.2 Replication

- **Data replication** across multiple nodes ensures no data loss
    
- Synchronous replication → strong consistency
    
- Asynchronous replication → eventual consistency
    

### 4.3 Error Detection & Correction

- Heartbeats, health checks, monitoring
    
- Automated retries, rollback mechanisms
    

### 4.4 Graceful Degradation

- Reduce non-critical functionality while keeping core services alive
    
- Example: Video streaming may lower quality instead of stopping playback
    

### 4.5 Consensus Protocols (Distributed Systems)

- Ensure consistency despite node failures
    
- Examples: Paxos, Raft
    

---

# 5️⃣ Examples in Technology

| Layer       | Example                                                       |
| ----------- | ------------------------------------------------------------- |
| Web servers | Multiple nodes with active-active load balancer               |
| Databases   | Multi-master replication (MySQL Group Replication, Cassandra) |
| Messaging   | Kafka clusters with replication factor > 1                    |
| Storage     | Distributed storage (S3, HDFS, Ceph)                          |
| Network     | Redundant routers, multiple ISPs                              |

---

# 6️⃣ Advantages of Fault Tolerance

- ✅ **Zero downtime** → critical for mission-critical applications
    
- ✅ **Resilient to hardware/software failures**
    
- ✅ **Improved user experience** → seamless operation
    
- ✅ Supports **high reliability and SLA guarantees**
    

---

# 7️⃣ Disadvantages / Trade-offs

- ❌ Higher cost → duplicate hardware/software
    
- ❌ More complex design and maintenance
    
- ❌ Potential performance overhead due to redundancy and synchronization
    
- ❌ Complexity in error detection and recovery logic
    

---

# 8️⃣ Real-World Analogy

- **Fault-tolerant elevator system**:
    
    - Multiple motors and brakes
        
    - If one fails, others take over seamlessly
        
    - Passengers experience **no downtime**
        
- **Non-fault-tolerant elevator**:
    
    - Single motor → failure stops the elevator completely
        

---

# 9️⃣ One-Line Interview Definitions

- **Fault Tolerance** → Ability of a system to **operate correctly even when components fail**
    
- **Redundancy** → Duplicate components to eliminate single points of failure
    
- **Graceful Degradation** → System reduces functionality but stays operational
    
- **Active-Active / Active-Passive** → FT deployment strategies
    

---

# 🔟 Final Summary (Powerful & Short)

> Fault tolerance ensures systems continue operating correctly despite component failures using **redundancy, replication, error detection, and graceful degradation**. It is critical for **mission-critical and distributed systems**, offering higher reliability than high availability but at increased cost and complexity.

---

# Horizontal vs Vertical scaling
# 1️⃣ What Is Scaling?

**Scaling** is the process of **increasing the capacity of a system** to handle more load (users, requests, or data).

Two main strategies:

1. **Vertical Scaling (Scale Up)**
    
2. **Horizontal Scaling (Scale Out)**
    

---

# 2️⃣ Vertical Scaling (Scale Up)

### 🔹 Definition:

> Vertical scaling means **adding more resources (CPU, RAM, storage)** to a **single server** to improve its capacity.

### 🔹 Key Features:

- Single server becomes **more powerful**
    
- Easier to implement (just upgrade hardware)
    
- Limited by **hardware constraints**
    

### 🔹 Example:

- Upgrading a web server from 4 CPU cores → 16 CPU cores
    
- Upgrading RAM from 16GB → 64GB
    
- Faster database server with SSDs instead of HDDs
    

### 🔹 Pros:

- ✅ Simple to implement
    
- ✅ No application architecture changes
    
- ✅ Works well for **monolithic apps**
    

### 🔹 Cons:

- ❌ Single point of failure
    
- ❌ Limited by maximum hardware capacity
    
- ❌ Expensive at high-end hardware
    
- ❌ Downtime may be required for upgrades
    

---

# 3️⃣ Horizontal Scaling (Scale Out)

### 🔹 Definition:

> Horizontal scaling means **adding more servers/nodes** to distribute load across **multiple machines**.

### 🔹 Key Features:

- Multiple servers share the load
    
- Supports **redundancy, fault tolerance, and high availability**
    
- Requires **distributed system design**
    

### 🔹 Example:

- Adding 5 more web servers behind a **load balancer**
    
- Adding database replicas for read-heavy workloads
    
- Microservices deployed across multiple nodes
    

### 🔹 Pros:

- ✅ No single point of failure
    
- ✅ Virtually unlimited scaling (more nodes can be added)
    
- ✅ Works well for **distributed systems and cloud-native apps**
    

### 🔹 Cons:

- ❌ More complex architecture (load balancing, replication, sharding)
    
- ❌ Network latency and consistency challenges
    
- ❌ Requires monitoring and orchestration
    

---

# 4️⃣ Horizontal vs Vertical Table

| Feature           | Vertical Scaling                | Horizontal Scaling                            |
| ----------------- | ------------------------------- | --------------------------------------------- |
| Approach          | Upgrade single machine          | Add more machines                             |
| Complexity        | Low                             | High                                          |
| Cost              | Expensive for high-end hardware | Cost-effective at scale                       |
| Fault Tolerance   | Low                             | High (redundancy)                             |
| Scalability Limit | Hardware limit                  | Virtually unlimited                           |
| Architecture      | Works for monolith              | Works for microservices & distributed systems |
| Examples          | Powerful database server        | Web server cluster, DB replicas               |

---

# 5️⃣ Real-World Analogy

- **Vertical scaling** → Replacing a small truck with a bigger truck to carry more load
    
- **Horizontal scaling** → Adding more trucks to carry load in parallel
    

---

# 6️⃣ When to Use Which

| Scenario                                               | Recommended                  |
| ------------------------------------------------------ | ---------------------------- |
| Monolithic app with simple architecture                | Vertical scaling             |
| Distributed system, cloud-native, or high-traffic apps | Horizontal scaling           |
| Temporary traffic spikes                               | Vertical scaling (quick fix) |
| Long-term growth & fault tolerance                     | Horizontal scaling           |

---

# 7️⃣ One-Line Interview Definitions

- **Vertical Scaling (Scale Up)** → Increasing capacity of a single server by adding CPU, RAM, or storage
    
- **Horizontal Scaling (Scale Out)** → Increasing capacity by adding more servers/nodes to the system
    

---

# 8️⃣ Final Summary (Powerful & Short)

> Vertical scaling upgrades a single machine to handle more load, simple but limited by hardware. Horizontal scaling adds more machines to distribute load, offering better fault tolerance, elasticity, and scalability, but requires a distributed system architecture.

---
# 1️⃣1️⃣ API & Communication Design

# REST principles

#  1️⃣ What Is REST?

### 🔹 Definition:

> **REST (Representational State Transfer)** is an architectural style for designing **networked applications** where clients and servers communicate over **HTTP** using **stateless requests**.

- Introduced by **Roy Fielding** in his dissertation (2000)
    
- Focuses on **resources** and **standard HTTP methods**
    

---

#  2️⃣ Core REST Principles

1. **Client-Server Architecture**
    
    - Separation of concerns:
        
        - Client → UI / frontend logic
            
        - Server → Data storage / business logic
            
    - Allows **independent evolution** of client and server
        
2. **Statelessness**
    
    - Each request must contain **all necessary information**
        
    - Server does **not store session state**
        
    - Example: Each HTTP request carries authentication token
        
3. **Cacheability**
    
    - Responses must declare whether they are **cacheable** or not
        
    - Improves **performance and scalability**
        
4. **Uniform Interface**
    
    - Standardized way to interact with resources
        
    - Includes:
        
        - Resource identification (URIs)
            
        - Resource manipulation through representations (JSON, XML)
            
        - Self-descriptive messages
            
        - Hypermedia as the engine of application state (HATEOAS, optional)
            
5. **Layered System**
    
    - System can be composed of layers (load balancer, proxy, cache)
        
    - Client does not need to know whether it communicates directly with the server or intermediary
        
6. **Code on Demand (Optional)**
    
    - Server can send executable code (like JavaScript) to clients
        
    - Rarely used
        

---

#  3️⃣ Resources and URIs

- Everything in REST is a **resource** (noun), not an action (verb)
    
- Resources are accessed via **URIs**
    
- Examples:
    
    ```
    GET /users           → Retrieve list of users
    GET /users/123       → Retrieve user with ID 123
    POST /users          → Create new user
    PUT /users/123       → Update user 123
    DELETE /users/123    → Delete user 123
    ```
    

---

#  4️⃣ HTTP Methods (CRUD Mapping)

| HTTP Method | CRUD Operation | Description                |
| ----------- | -------------- | -------------------------- |
| GET         | Read           | Retrieve resource(s)       |
| POST        | Create         | Create new resource        |
| PUT         | Update         | Replace resource entirely  |
| PATCH       | Update         | Modify part of resource    |
| DELETE      | Delete         | Remove resource            |
| OPTIONS     | N/A            | Describe available methods |

---

#  5️⃣ Status Codes

- **2xx** → Success (200 OK, 201 Created)
    
- **4xx** → Client error (400 Bad Request, 401 Unauthorized, 404 Not Found)
    
- **5xx** → Server error (500 Internal Server Error)
    

---

#  6️⃣ REST Best Practices

1. Use **nouns** for URIs, not verbs
    
2. Support **stateless requests**
    
3. Use **HTTP status codes properly**
    
4. Version APIs via URL or headers (`/v1/users`)
    
5. Support filtering, sorting, pagination via **query parameters**
    
6. Use **JSON** as the standard representation format
    

---

#  7️⃣ Advantages of REST

- ✅ Simple, lightweight, and widely adopted
    
- ✅ Stateless → easy to scale horizontally
    
- ✅ Standardized over HTTP → supported by all platforms
    
- ✅ Decouples client and server
    

---

#  8️⃣ Disadvantages / Trade-offs

- ❌ Statelessness → repetitive data in requests
    
- ❌ Can be chatty → multiple requests for related data
    
- ❌ Not ideal for **real-time or streaming** use cases
    
- ❌ Lacks formal contract → can lead to inconsistent API implementations
    

---

#  9️⃣ Real-World Analogy

- **REST API** → Library catalog:
    
    - Resources: Books, Authors
        
    - Client requests information (GET)
        
    - Adds books (POST), updates (PUT/PATCH), removes (DELETE)
        

---

#  🔟 One-Line Interview Definitions

- **REST** → Architectural style for stateless, resource-based communication over HTTP
    
- **Resource** → Any entity represented by a URI (users, orders, products)
    
- **Stateless** → Each request contains all information for processing
    
- **CRUD over HTTP** → Standardized method to manage resources
    

---

#  11️⃣ Final Summary (Powerful & Short)

> REST is a stateless, resource-oriented architectural style using standard HTTP methods and URIs to interact with resources. It’s simple, scalable, and widely adopted for designing APIs, though less suited for real-time or chatty operations.

---

# Resource modeling
#  1️⃣ What Is Resource Modeling?

### 🔹 Definition:

> **Resource modeling** is the process of **identifying, structuring, and representing the key entities (resources) in your system** for API design.

- Each **resource** corresponds to a **real-world entity or concept** that the API exposes.
    
- Resource modeling helps in **clean, scalable, and consistent API design**.
    

---

# 2️⃣ Why Resource Modeling Is Important

- Defines **clear API boundaries**
    
- Ensures **reusability and consistency** across endpoints
    
- Helps **map system data to API resources** efficiently
    
- Facilitates **RESTful design** by focusing on nouns (resources) rather than verbs (actions)
    

---

# 3️⃣ Key Steps in Resource Modeling

1. **Identify Resources**
    
    - Look for **nouns** in your domain
        
    - Examples: User, Product, Order, Invoice
        
2. **Define Resource Relationships**
    
    - One-to-one, one-to-many, many-to-many
        
    - Examples:
        
        - One user → many orders
            
        - Product → belongs to one category
            
3. **Design Resource URIs**
    
    - Use **plural nouns** for collections
        
    - Examples:
        
        ```
        GET /users → List of users
        GET /users/123 → Specific user
        POST /orders → Create order
        GET /users/123/orders → Orders for a user
        ```
        
4. **Determine Representations**
    
    - Choose **JSON, XML, or other formats**
        
    - Include necessary **attributes**
        
    - Example JSON for a user:
        
        ```json
        {
          "id": 123,
          "name": "Alice",
          "email": "alice@example.com"
        }
        ```
        
5. **Identify Actions via HTTP Methods**
    
    - Map CRUD operations to HTTP verbs: GET, POST, PUT, PATCH, DELETE
        
6. **Model Relationships and Nesting Carefully**
    
    - Avoid deep nesting; keep URIs **clean and intuitive**
        
    - Example: `/users/123/orders` (acceptable) vs `/users/123/orders/456/items/789` (too deep)
        

---

# 4️⃣ Resource Types

1. **Collection Resource**
    
    - Represents a group of similar entities
        
    - Example: `/users`, `/orders`
        
2. **Singleton Resource**
    
    - Represents a **single instance**
        
    - Example: `/users/123`, `/settings`
        
3. **Sub-resource / Nested Resource**
    
    - Represents a **child entity** associated with a parent
        
    - Example: `/users/123/orders`
        

---

#  5️⃣ Resource Relationships

| Relationship Type | Example                 | Notes                                    |
| ----------------- | ----------------------- | ---------------------------------------- |
| One-to-One        | `/users/123/profile`    | Profile belongs to one user              |
| One-to-Many       | `/users/123/orders`     | User can have multiple orders            |
| Many-to-Many      | `/students/123/courses` | Many students can enroll in many courses |

---

# 6️⃣ Best Practices for Resource Modeling

- Use **plural nouns** for collections (`/users`, `/products`)
    
- Keep URIs **hierarchical but shallow**
    
- Represent **relationships clearly** without over-nesting
    
- Use **consistent naming conventions**
    
- Include **HATEOAS links** if needed to guide clients
    
- Separate **commands (POST/PUT/PATCH)** from **queries (GET)**
    

---

# 7️⃣ Advantages

- ✅ Consistent, predictable API design
    
- ✅ Easier client integration and understanding
    
- ✅ Scalable for future additions
    
- ✅ Facilitates REST principles (statelessness, uniform interface)
    

---

# 8️⃣ Disadvantages / Trade-offs

- ❌ Over-modeling can make APIs complex
    
- ❌ Deeply nested resources → harder to maintain
    
- ❌ Requires good domain knowledge to identify correct resources
    

---

# 9️⃣ Real-World Analogy

- **Library system**:
    
    - Resources: Books, Authors, Categories, Borrowers
        
    - Collection: `/books` → all books
        
    - Singleton: `/books/123` → a specific book
        
    - Nested: `/authors/45/books` → all books by a specific author
        

---

# 🔟 One-Line Interview Definitions

- **Resource Modeling** → Identifying and structuring entities for API endpoints in a clear, consistent, and RESTful way
    
- **Resource** → Any object or concept exposed by an API, represented via URI
    
- **Collection Resource** → Group of similar entities (`/orders`)
    
- **Sub-resource** → Child entity associated with a parent (`/users/123/orders`)
    

---

# 11️⃣ Final Summary (Powerful & Short)

> Resource modeling is the process of defining and structuring entities (resources) in an API, their relationships, URIs, and representations to create a **clean, scalable, and RESTful design**. It ensures consistency, reusability, and ease of client integration.

---

# Stateless design
# 1️⃣ What Is Stateless Design?

### 🔹 Definition:

> **Stateless design** is an architectural principle where **each request from a client to a server contains all the information needed to process that request**, and the server **does not store any client-specific context between requests**.

- The server **does not remember past requests**.
    
- Each request is **independent**.
    

---

# 2️⃣ Key Principles

1. **No Server-Side Session State**
    
    - Server does not store information about clients between requests.
        
2. **Self-Contained Requests**
    
    - All authentication, parameters, and context must be included in the request.
        
3. **Idempotency (for certain operations)**
    
    - Stateless design works best when requests are **idempotent**, meaning multiple identical requests produce the same result.
        
4. **Scalability & Load Balancing**
    
    - Stateless services can be easily **replicated and distributed**, since any server can handle any request.
        

---

# 3️⃣ Examples

### 3.1 Stateless REST API

```http
GET /users/123
Authorization: Bearer <token>
```

- Each request contains all required info (authentication token, resource ID).
    
- Server does not need to remember previous requests.
    

### 3.2 Stateless Services in Microservices

- Each service communicates via HTTP/gRPC with all necessary data in the request.
    
- No shared session stored on the service itself.
    

---

# 4️⃣ Stateless vs Stateful Design

| Feature              | Stateless                   | Stateful                              |
| -------------------- | --------------------------- | ------------------------------------- |
| Server Memory        | Does not store client state | Stores client session info            |
| Request Independence | Each request independent    | Requests depend on prior interactions |
| Scalability          | Easy (horizontal scaling)   | Harder (requires session replication) |
| Fault Tolerance      | High                        | Lower (session lost if server fails)  |
| Examples             | REST APIs, CDNs, DNS        | Web sessions, FTP connections         |

---

# 5️⃣ Advantages of Stateless Design

- ✅ Simple to implement and maintain
    
- ✅ **Easy to scale horizontally** (add more servers)
    
- ✅ Fault-tolerant: any server can handle any request
    
- ✅ Improves **load balancing** efficiency
    
- ✅ Easier to cache responses
    

---

# 6️⃣ Disadvantages / Trade-offs

- ❌ Clients must send **all context in every request**
    
- ❌ Some operations may require more data transfer
    
- ❌ Less convenient for **multi-step workflows** (e.g., shopping cart)
    
- ❌ Complex transactions may require additional mechanisms
    

---

# 7️⃣ Real-World Analogy

- **Stateless** → Ordering pizza via an app where each request contains all info: address, payment, toppings.
    
- **Stateful** → Talking to a waiter who remembers your previous orders throughout the session.
    

---

# 8️⃣ Best Practices

1. Include **all necessary data** in every request (tokens, IDs, parameters).
    
2. Use **JWT or OAuth** for authentication instead of server-side sessions.
    
3. Design APIs to be **idempotent** when possible.
    
4. Combine with **caching** to reduce repeated data transfer.
    
5. Keep services **loosely coupled**, enabling independent deployment.
    

---

# 9️⃣ One-Line Interview Definitions

- **Stateless Design** → System design where each request is self-contained and the server stores no client-specific state.
    
- **Benefits** → Scalability, fault-tolerance, simplicity.
    
- **Trade-offs** → Increased data in requests, complex multi-step operations.
    

---

# 🔟 Final Summary (Powerful & Short)

> Stateless design ensures that each request contains all information needed for processing, eliminating server-side session state. This makes systems **scalable, fault-tolerant, and easy to load balance**, but may require more data transfer and extra design effort for multi-step workflows.

---

# Idempotency

# 1️⃣ What Is Idempotency?

### 🔹 Definition:

> **Idempotency** is a property of an operation where **performing it multiple times has the same effect as performing it once**, regardless of how many times the request is repeated.

- Important for **reliable APIs**, especially in **network failures or retries**.
    
- Ensures **no unintended side effects** when clients retry requests.
    

---

# 2️⃣ Why Idempotency Matters

- **Network failures** can cause clients to retry requests
    
- Without idempotency, retries may **duplicate actions** (e.g., double payments, duplicate orders)
    
- Helps maintain **data consistency** and **predictable system behavior**
    

---

# 3️⃣ Idempotent HTTP Methods

| HTTP Method | Idempotent?  | Explanation                                                                    |
| ----------- | ------------ | ------------------------------------------------------------------------------ |
| GET         | ✅ Yes        | Reading data multiple times does not change it                                 |
| PUT         | ✅ Yes        | Replacing a resource multiple times has same result                            |
| DELETE      | ✅ Yes        | Deleting a resource multiple times results in it being deleted                 |
| POST        | ❌ Usually No | Creating resources multiple times may create duplicates                        |
| PATCH       | ✅ Usually    | Updating a resource is idempotent if repeated updates produce same final state |

---

# 4️⃣ Examples

### 4.1 Idempotent Operation

```http
PUT /users/123
{
  "name": "Alice"
}
```

- Regardless of how many times this request is sent, user 123’s name is **always "Alice"**.
    

### 4.2 Non-Idempotent Operation

```http
POST /orders
{
  "product_id": 456,
  "quantity": 1
}
```

- Each retry creates a **new order**, causing duplicates.
    

### 4.3 Making POST Idempotent

- Include a **client-generated unique ID (idempotency key)** in the request header
    
- Server ignores duplicates with the same key
    

---

# 5️⃣ Benefits

- ✅ Safe retries → network failures or client retries don’t break the system
    
- ✅ Predictable behavior for clients
    
- ✅ Simplifies distributed system design
    
- ✅ Reduces risk of **duplicate transactions or data corruption**
    

---

# 6️⃣ Trade-offs / Challenges

- ❌ Requires **additional logic** on server for idempotency keys
    
- ❌ May increase **storage requirements** to track keys
    
- ❌ Not all operations can be naturally idempotent (e.g., auto-incrementing IDs)
    

---

# 7️⃣ Real-World Analogy

- **Idempotent** → Turning on a light switch that is already on → no effect
    
- **Non-idempotent** → Pressing a button to send money → multiple presses result in multiple transfers
    

---

# 8️⃣ Best Practices

1. Use **PUT or DELETE** for naturally idempotent operations
    
2. For **POST operations**, use **idempotency keys**
    
3. Ensure APIs are **stateless** to simplify idempotency
    
4. Document which operations are **idempotent** for clients
    
5. Track **server-side results** to return consistent responses on retries
    

---

# 9️⃣ One-Line Interview Definitions

- **Idempotency** → Operation can be repeated multiple times with the **same result as a single execution**
    
- **Benefits** → Safe retries, predictable behavior, data consistency
    
- **Non-idempotent operations** → Typically create side effects or duplicate records
    

---

# 🔟 Final Summary (Powerful & Short)

> Idempotency ensures that repeating an operation multiple times does not change the outcome beyond the initial application. It is crucial for **reliable APIs, safe retries, and distributed systems**, helping prevent duplicate actions and maintain predictable behavior.

---

# Pagination
# 1️⃣ What Is Pagination?

### 🔹 Definition:

> **Pagination** is the process of **splitting large datasets into smaller chunks (pages)** so that a client can request and receive data incrementally instead of all at once.

- Helps **reduce response size, network load, and memory usage**.
    
- Improves **user experience** and **API performance**.
    

---

# 2️⃣ Why Pagination Matters

- Prevents **huge responses** that can slow down servers or clients
    
- Reduces **bandwidth usage**
    
- Makes **UI rendering** faster for large datasets
    
- Avoids **timeouts or crashes** in APIs
    

---

# 3️⃣ Common Pagination Strategies

### 3.1 Offset-Based Pagination

- Uses **limit** and **offset** query parameters
    
- Example:
    

```
GET /users?limit=10&offset=20
```

- Returns **10 users**, skipping the first 20
    
- Pros:
    
    - Simple and widely supported
        
- Cons:
    
    - Inefficient for **large offsets**
        
    - Can return inconsistent results if data changes during pagination
        

---

### 3.2 Page-Based Pagination

- Uses **page number** and **page size**
    
- Example:
    

```
GET /users?page=3&size=10
```

- Returns **third page**, 10 users per page
    
- Pros:
    
    - Easy to implement and understand
        
- Cons:
    
    - Similar problems with data changes between pages
        

---

### 3.3 Cursor-Based / Keyset Pagination

- Uses a **cursor (unique identifier or timestamp)** instead of offset
    
- Example:
    

```
GET /users?limit=10&cursor=eyJpZCI6123}
```

- Returns the next 10 users **after the given cursor**
    
- Pros:
    
    - **Efficient for large datasets**
        
    - Handles **real-time data changes** gracefully
        
- Cons:
    
    - Slightly more complex to implement
        

---

### 3.4 Infinite Scroll / Load More

- Often used in UIs like social media feeds
    
- Backend usually uses **cursor-based pagination** under the hood
    
- Pros:
    
    - Smooth user experience
        
- Cons:
    
    - Harder to bookmark or jump to a specific page
        

---

# 4️⃣ HTTP Headers for Pagination (Optional)

- Some APIs use **HTTP headers** instead of query params:
    
    ```
    Link: <https://api.example.com/users?cursor=abc>; rel="next",
          <https://api.example.com/users?cursor=xyz>; rel="prev"
    ```
    
- Useful for **RESTful pagination in APIs**
    

---

# 5️⃣ Best Practices

1. Limit **maximum page size** to prevent huge responses
    
2. Use **cursor-based pagination** for large or frequently changing datasets
    
3. Return **metadata**:
    
    - Total items, current page, next/previous page link, page size
        
    
    ```json
    {
      "data": [...],
      "page": 3,
      "size": 10,
      "total": 105,
      "next": "/users?page=4&size=10"
    }
    ```
    
4. Ensure **consistent ordering** (e.g., by creation timestamp or ID)
    
5. Document your API’s pagination strategy clearly
    

---

# 6️⃣ Real-World Analogy

- **Pagination** → Library bookshelf:
    
    - Instead of taking **all 1,000 books at once**, you pick **10 books per page** and move through shelves gradually.
        

---

# 7️⃣ One-Line Interview Definitions

- **Pagination** → Splitting large datasets into smaller chunks (pages) for efficient retrieval and display
    
- **Offset pagination** → Uses skip/limit to select data
    
- **Cursor pagination** → Uses a pointer to retrieve the next set efficiently
    
- **Best practice** → Always return metadata (page info, next/prev links)
    

---

# 8️⃣ Final Summary (Powerful & Short)

> Pagination allows APIs to return large datasets in **manageable chunks**, improving performance, reducing load, and enabling smoother client experiences. Cursor-based pagination is preferred for **large or dynamic datasets**, while offset or page-based works for simpler scenarios.

---	
# Versioning

# 1️⃣ What Is API Versioning?
### 🔹 Definition:

> **API Versioning** is the practice of **managing changes in an API over time** to ensure **backward compatibility** while allowing **new features or improvements**.

- Prevents breaking existing clients when API changes
    
- Lets clients **migrate gradually**
    

---

# 2️⃣ Why API Versioning Matters

- APIs evolve: new fields, endpoints, or behavior may be added
    
- Without versioning:
    
    - Clients may **break** if API changes
        
    - Maintenance becomes difficult
        
- With versioning:
    
    - Multiple versions can **coexist**
        
    - Developers can **deprecate old versions gradually**
        

---

# 3️⃣ Common API Versioning Strategies

### 3.1 URI / Path Versioning

- Include version in the **URL path**
    

```
GET /v1/users
GET /v2/users
```

- Pros:
    
    - Clear and visible
        
    - Easy to route on server
        
- Cons:
    
    - URLs change → may require client updates
        

---

### 3.2 Query Parameter Versioning

- Version passed as a **query parameter**
    

```
GET /users?version=1
GET /users?version=2
```

- Pros:
    
    - Easy to implement
        
- Cons:
    
    - Less visible; can be ignored accidentally
        

---

### 3.3 Header Versioning

- Version specified in **HTTP headers**
    

```
GET /users
Accept: application/vnd.example.v1+json
```

- Pros:
    
    - Clean URLs
        
    - Can support content negotiation
        
- Cons:
    
    - Harder to test in browsers directly
        
    - Slightly more complex routing
        

---

### 3.4 Content Negotiation / Media Type Versioning

- Version tied to the **media type** in `Accept` header
    

```
Accept: application/vnd.example.user-v1+json
```

- Pros:
    
    - Very flexible
        
    - Works well in enterprise or microservices
        
- Cons:
    
    - Complex for simple APIs
        

---

# 4️⃣ Versioning Guidelines & Best Practices

1. **Start simple** → Only version when changes are necessary
    
2. **Use semantic versioning** (v1, v2…) or date-based versions (2023-12-08)
    
3. **Maintain backward compatibility** when possible
    
4. **Deprecate old versions gracefully** → provide warnings and timelines
    
5. **Document clearly** → let clients know versioning scheme
    
6. **Prefer additive changes** (adding fields) over breaking changes
    

---

# 5️⃣ Breaking Changes vs Non-Breaking Changes

- **Non-breaking changes** → Adding new fields, new endpoints (no version bump needed)
    
- **Breaking changes** → Changing field types, removing endpoints (requires version bump)
    

---

# 6️⃣ Real-World Examples

- **GitHub API**
    

```
GET /repos/octocat/hello-world
Accept: application/vnd.github.v3+json
```

- **Twitter API**
    

```
https://api.twitter.com/2/tweets
```

- **Stripe API**
    
    - Uses header-based versioning with default version and optional override
        

---

# 7️⃣ Advantages of API Versioning

- ✅ Protects existing clients
    
- ✅ Allows API evolution without disruptions
    
- ✅ Makes deprecation and migration manageable
    
- ✅ Improves maintainability and scalability
    

---

# 8️⃣ Disadvantages / Trade-offs

- ❌ Multiple versions → increased **maintenance overhead**
    
- ❌ Can confuse clients if versions are not clearly documented
    
- ❌ Requires careful routing and testing
    

---

# 9️⃣ One-Line Interview Definitions

- **API Versioning** → Strategy to evolve APIs while maintaining backward compatibility
    
- **Path-based versioning** → Version in URL (`/v1/users`)
    
- **Header-based versioning** → Version in HTTP headers (`Accept: application/vnd.api-v1+json`)
    

---

# 🔟 Final Summary (Powerful & Short)

> API versioning ensures that APIs can **evolve without breaking existing clients**, using strategies like **path-based, query parameter, or header versioning**, while allowing smooth deprecation and backward compatibility.

---

# GraphQL philosophy
# 1️⃣ What Is GraphQL?

### 🔹 Definition:

> **GraphQL** is a **query language and runtime for APIs** that allows clients to **request exactly the data they need**, no more, no less.

- Developed by **Facebook** in 2012, open-sourced in 2015
    
- Clients define the **shape of the response**, and the server responds accordingly
    

---

# 2️⃣ Core Philosophy of GraphQL

1. **Client-driven Queries**
    
    - Clients specify exactly **what data they want**
        
    - Avoids over-fetching (getting extra data) and under-fetching (multiple requests)
        
2. **Single Endpoint**
    
    - Unlike REST with multiple endpoints, GraphQL exposes **one endpoint** (`/graphql`)
        
    - Queries, mutations, and subscriptions are sent to the same endpoint
        
3. **Strongly Typed Schema**
    
    - GraphQL uses a **schema to define types, fields, and relationships**
        
    - Enables **introspection, validation, and auto-documentation**
        
4. **Hierarchical / Nested Data**
    
    - Supports **fetching related data in a single query**
        
    - Example: fetching a user and their posts in one request
        
5. **Declarative Data Fetching**
    
    - Clients declare their **data needs**, server returns **exactly that**
        
6. **Real-Time Updates (Optional)**
    
    - GraphQL supports **subscriptions** for real-time updates via WebSockets
        

---

# 3️⃣ GraphQL Operations

1. **Query**
    
    - Fetch data
        
    
    ```graphql
    {
      user(id: 1) {
        name
        email
        posts {
          title
        }
      }
    }
    ```
    
2. **Mutation**
    
    - Modify data (create, update, delete)
        
    
    ```graphql
    mutation {
      createUser(name: "Alice", email: "alice@example.com") {
        id
        name
      }
    }
    ```
    
3. **Subscription**
    
    - Real-time updates
        
    
    ```graphql
    subscription {
      newPost {
        title
        author {
          name
        }
      }
    }
    ```
    

---

# 4️⃣ Advantages of GraphQL

- ✅ **Fetch only what you need** → reduces over-fetching and under-fetching
    
- ✅ **Single endpoint** → simplifies API structure
    
- ✅ **Strongly typed schema** → auto-documentation and validation
    
- ✅ **Flexible queries** → nested and related data in one request
    
- ✅ **Versionless evolution** → schema can evolve without breaking clients
    

---

# 5️⃣ Disadvantages / Trade-offs

- ❌ More **complex server implementation**
    
- ❌ **Query complexity and performance** issues (deep nested queries can be expensive)
    
- ❌ Caching harder than REST (single endpoint for all queries)
    
- ❌ Overhead for **simple APIs** may not be worth it
    

---

# 6️⃣ Real-World Analogy

- **REST** → Ordering a fixed combo meal from a menu (fixed data from fixed endpoint)
    
- **GraphQL** → Ordering a custom meal with **exact items you want**, even combining items from multiple sections of the menu in one order
    

---

# 7️⃣ One-Line Interview Definitions

- **GraphQL** → Client-driven query language for APIs where clients specify exactly the data they need
    
- **Query** → Read/fetch data
    
- **Mutation** → Write/modify data
    
- **Subscription** → Real-time updates
    

---

# 8️⃣ Final Summary (Powerful & Short)

> GraphQL is a **client-driven, strongly typed API paradigm** that allows precise, nested data fetching through a single endpoint. Its philosophy emphasizes **flexibility, efficiency, and schema-driven contracts**, making it ideal for complex, evolving frontends but with added server complexity.

---
# gRPC theory

# 1️⃣ What Is gRPC?

### 🔹 Definition:

> **gRPC** (gRPC Remote Procedure Call) is a **high-performance, open-source RPC framework** developed by Google that allows **communication between client and server** using **strongly-typed contracts** defined in **Protocol Buffers (protobuf)**.

- Supports **multi-language clients and servers**
    
- Uses **HTTP/2** as its transport protocol for efficiency
    

---

# 2️⃣ Core Philosophy of gRPC

1. **RPC-Based Communication**
    
    - Unlike REST/GraphQL (resource-oriented), gRPC is **procedure-oriented**
        
    - Clients call **methods on the server** as if they were local functions
        
2. **Strongly-Typed Contracts**
    
    - APIs defined using **Protocol Buffers (.proto files)**
        
    - Ensures **compile-time type safety** across languages
        
3. **Efficient Binary Serialization**
    
    - Uses **protobuf**, a compact binary format, instead of JSON
        
    - Reduces **payload size and parsing overhead**
        
4. **HTTP/2 Transport**
    
    - Supports **multiplexing**, **streaming**, **flow control**, and **header compression**
        
    - Enables **bidirectional streaming and full-duplex communication**
        
5. **Code Generation**
    
    - Server and client stubs are automatically generated from **.proto files**
        
    - Minimizes boilerplate code and ensures consistency
        

---

# 3️⃣ gRPC Communication Patterns

1. **Unary RPC**
    
    - Single request → single response
        
    
    ```text
    client.Send(request) → server returns response
    ```
    
2. **Server Streaming RPC**
    
    - Single request → stream of responses
        
    
    ```text
    client.Send(request) → server streams multiple responses
    ```
    
3. **Client Streaming RPC**
    
    - Stream of requests → single response
        
    
    ```text
    client streams requests → server responds once
    ```
    
4. **Bidirectional Streaming RPC**
    
    - Both client and server stream messages independently
        
    
    ```text
    client ↔ server streams concurrently
    ```
    

---

# 4️⃣ Advantages of gRPC

- ✅ **High performance** → binary protocol + HTTP/2
    
- ✅ **Language-agnostic** → supports multiple languages
    
- ✅ **Strongly typed APIs** → compile-time checks and code generation
    
- ✅ **Streaming support** → efficient real-time communication
    
- ✅ **Small payloads** → faster over low-bandwidth networks
    
- ✅ Ideal for **microservices and internal APIs**
    

---

# 5️⃣ Disadvantages / Trade-offs

- ❌ **Not human-readable** → binary format harder to debug than JSON
    
- ❌ **Learning curve** → needs protobuf knowledge and tooling
    
- ❌ Less ideal for **public-facing APIs** (compared to REST/GraphQL)
    
- ❌ Browser support is limited → usually requires **gRPC-Web**
    

---

# 6️⃣ Real-World Analogy

- **REST** → Ordering items from a menu (resource-oriented)
    
- **GraphQL** → Ordering a custom meal (flexible data query)
    
- **gRPC** → Calling the chef directly with a **recipe (method + ingredients)** → chef executes and returns results
    

---

# 7️⃣ One-Line Interview Definitions

- **gRPC** → High-performance, RPC-based framework using **HTTP/2 and Protocol Buffers** for communication between client and server
    
- **Unary RPC** → Single request, single response
    
- **Streaming RPC** → Client, server, or bidirectional streams
    
- **Protocol Buffers** → Language-agnostic, compact binary format defining message contracts
    

---

# 8️⃣ Final Summary (Powerful & Short)

> gRPC is a **binary, high-performance RPC framework** using HTTP/2 and Protocol Buffers, enabling strongly-typed, multi-language, and streaming communication. It is ideal for **microservices and internal APIs**, offering efficiency and type safety, but less suited for public-facing or browser-heavy APIs.
---
