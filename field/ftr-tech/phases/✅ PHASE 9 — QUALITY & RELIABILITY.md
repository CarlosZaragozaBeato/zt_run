## 1️⃣4️⃣ Software Testing Theory
# 🧪 Unit vs Integration vs System Testing — Complete Theory

### 1️⃣ The Big Picture (Test Pyramid)

Testing is usually organized like this:

```
        System Tests  (few, slow, expensive)
     Integration Tests (medium)
  Unit Tests (many, fast, cheap)
```

This is called the **Test Pyramid**:

- Most tests → **Unit**
    
- Some → **Integration**
    
- Few → **System / End-to-End**
    

---

## 2️⃣ Unit Testing

### 🔹 Definition

> **Unit testing verifies the smallest testable piece of code in isolation.**

A “unit” is usually:

- A function
    
- A method
    
- A class
    

---

### ✅ What It Tests

✅ Business logic  
✅ Edge cases  
✅ Calculations  
✅ Decision branches

---

### ❌ What It Does NOT Test

❌ Databases  
❌ APIs  
❌ File systems  
❌ Network  
❌ External services

All of these are **mocked or stubbed**.

---

### ✅ Key Characteristics

|Property|Unit Test|
|---|---|
|Speed|⚡ Very fast|
|Scope|Very small|
|Dependencies|Mocked|
|Flakiness|Very low|
|Cost|Very cheap|
|Runs on|Every commit|

---

### ✅ Example

Testing:

```python
def add(a, b): return a + b
```

Unit test:

```
assert add(2, 3) == 5
```

---

## 3️⃣ Integration Testing

### 🔹 Definition

> **Integration testing verifies how multiple components work together.**

It tests:  
✅ Service → Database  
✅ API → Service  
✅ Service → Message Broker  
✅ Multiple internal modules together

---

### ✅ What It Tests

✅ Data flow between components  
✅ Configuration correctness  
✅ Serialization / deserialization  
✅ Network behavior  
✅ Transactions

---

### ❌ What It Usually Avoids

❌ Full user journey  
❌ UI behavior  
❌ External third-party services (often still mocked)

---

### ✅ Key Characteristics

|Property|Integration Test|
|---|---|
|Speed|⚡ Medium|
|Scope|Moderate|
|Dependencies|Real (DB, cache, queue)|
|Flakiness|Medium|
|Cost|Medium|
|Runs on|CI pipeline|

---

### ✅ Example

- Test that:
    

```
API → Service → Database → Response
```

works correctly.

---

## 4️⃣ System Testing (End-to-End / E2E)

### 🔹 Definition

> **System testing validates the entire application as a complete, real-world system.**

It tests:  
✅ Full user workflows  
✅ UI → Backend → Database → External APIs  
✅ Authentication  
✅ Payment flow  
✅ Notifications

---

### ✅ What It Tests

✅ Real production-like behavior  
✅ Real integrations  
✅ Complete business journeys  
✅ Non-functional behavior (performance, security, usability)

---

### ✅ Key Characteristics

|Property|System Test|
|---|---|
|Speed|🐌 Slow|
|Scope|Very large|
|Dependencies|All real|
|Flakiness|High|
|Cost|Very expensive|
|Runs on|Pre-release / staging|

---

### ✅ Example

Testing:

> “User signs up → logs in → adds items → checks out → receives email”

---

## 5️⃣ Side-by-Side Comparison Table

|Feature|Unit|Integration|System|
|---|---|---|---|
|Scope|Single function/class|Multiple modules|Full application|
|Speed|Fastest|Medium|Slowest|
|Dependencies|Mocked|Partially real|Fully real|
|Cost|Cheapest|Medium|Most expensive|
|Flakiness|Very low|Medium|High|
|Purpose|Logic correctness|Component interaction|Business flow validation|

---

## 6️⃣ Real-World Example (E-Commerce App)

|Feature|Unit Test|Integration Test|System Test|
|---|---|---|---|
|Add to cart|Test cart logic|Cart → DB|Full shopping flow|
|Login|Password validation|API → Auth service|UI login with browser|
|Payment|Price calculation|Service → Payment Gateway (sandbox)|Real transaction simulation|

---

## 7️⃣ When Each Test Type Finds Bugs

|Bug Type|Found By|
|---|---|
|Logic bug|✅ Unit|
|Wrong DB query|✅ Integration|
|Broken API wiring|✅ Integration|
|UI button broken|✅ System|
|Auth flow broken|✅ System|
|Performance bottleneck|✅ System|

---

## 8️⃣ Common Mistakes

❌ Only writing system tests (too slow & fragile)  
❌ No unit tests (logic breaks easily)  
❌ Mocking everything (fake confidence)  
❌ Skipping integration tests (deployment surprises)

---

## 9️⃣ Ideal Testing Strategy (Best Practice)

A healthy project usually has:

✅ **60–80% Unit tests**  
✅ **15–30% Integration tests**  
✅ **5–10% System tests**

This gives:

- Fast feedback ✅
    
- High confidence ✅
    
- Low maintenance cost ✅
    

---

## 🔟 Interview-Ready One-Liners

- **Unit tests** validate individual functions/classes in isolation
    
- **Integration tests** validate collaboration between components
    
- **System tests** validate the full application as a real user sees it
    
- **Test Pyramid** → Many unit, fewer integration, fewest system
    
- **Cheapest bugs** → Found by unit tests
    

---

## ✅ Final Power Summary

> Unit tests verify individual pieces of logic in isolation, integration tests verify the correctness of interactions between multiple components, and system tests validate the full application behavior in real-world conditions. Together, they form a layered testing strategy that ensures correctness, reliability, and production confidence.

---

# 🧪 The Test Pyramid — Complete Theory

## 1️⃣ What Is the Test Pyramid?

> **The Test Pyramid is a testing strategy that defines how many tests you should have at each level:**

- Many **Unit Tests**
    
- Fewer **Integration Tests**
    
- Very few **System (End-to-End) Tests**
    

It’s about:  
✅ Speed  
✅ Cost  
✅ Reliability  
✅ Fast developer feedback

---

## 2️⃣ The Shape & Meaning

```
            System / E2E Tests   🐌 (few, slow, expensive)
         Integration Tests       ⚡ (some, medium)
      Unit Tests                 🚀 (many, fast, cheap)
```

As you go **up** the pyramid:

- Tests become ❌ slower
    
- ❌ more expensive
    
- ❌ more flaky
    
- ✅ more realistic
    

As you go **down** the pyramid:

- Tests are ✅ faster
    
- ✅ cheaper
    
- ✅ more stable
    
- ❌ less realistic
    

The pyramid balances **speed vs confidence**.

---

## 3️⃣ Why the Test Pyramid Exists

If you rely mainly on:

- ❌ Manual tests → too slow
    
- ❌ Only system tests → flaky & expensive
    
- ❌ Only unit tests → false confidence
    

The pyramid solves this by:  
✅ Catching most bugs early (unit)  
✅ Verifying wiring (integration)  
✅ Validating user behavior (system)

---

## 4️⃣ Each Layer’s Responsibility

### 🟩 Unit Tests (Base of Pyramid)

✅ Test:

- Business logic
    
- Calculations
    
- Conditions
    
- Edge cases
    

✅ Properties:

- Fast (milliseconds)
    
- No real DB or network
    
- Run on every commit
    
- Should be **60–80% of all tests**
    

❌ Do NOT test:

- APIs
    
- Databases
    
- UI
    
- External services
    

---

### 🟨 Integration Tests (Middle Layer)

✅ Test:

- Service ↔ Database
    
- API ↔ Service
    
- Service ↔ Message broker
    
- Serialization & config
    

✅ Properties:

- Medium speed
    
- Real infrastructure dependencies
    
- Run in CI pipelines
    
- Should be **15–30% of tests**
    

❌ Still avoid:

- Full browsers
    
- Full user journey
    

---

### 🟥 System / End-to-End Tests (Top)

✅ Test:

- Real user workflows
    
- UI → Backend → DB → External APIs
    
- Authentication & payments
    
- Notifications
    

✅ Properties:

- Very slow
    
- Very expensive
    
- Very flaky
    
- Should be **5–10% of tests**
    
- Run before releases
    

---

## 5️⃣ What Each Layer Is Best At Catching

|Bug Type|Unit|Integration|System|
|---|---|---|---|
|Logic errors|✅✅✅|❌|❌|
|Data mapping issues|❌|✅✅✅|❌|
|Misconfigured DB|❌|✅✅✅|✅|
|API contract mismatch|❌|✅✅✅|✅|
|UI broken|❌|❌|✅✅✅|
|Auth flow broken|❌|❌|✅✅✅|
|Performance under load|❌|❌|✅✅✅|

---

## 6️⃣ Why “More System Tests” Is a Bad Idea

Teams often think:

> “Let’s just test everything with end-to-end tests.”

This fails because:

❌ They are slow  
❌ They break randomly (flaky)  
❌ They are hard to debug  
❌ They slow down deployments  
❌ They are expensive to maintain

This creates:

> ❌ **Slow feedback loops = slow engineering**

---

## 7️⃣ The Cost Curve of Bugs

|When Bug Is Found|Cost|
|---|---|
|During unit test|$|
|During integration test|$$|
|During system test|$$$|
|In production|$$$$$$$|

The pyramid exists to:

> ✅ Catch the cheapest bugs as early as possible

---

## 8️⃣ Common Anti-Patterns (Very Important)

### ❌ Ice-Cream Cone Anti-Pattern

```
Many System Tests
Few Integration Tests
Almost No Unit Tests
```

This leads to:

- Slow pipelines
    
- Flaky builds
    
- Developer pain
    
- Low confidence
    
- High cost
    

---

### ❌ Testing Trophy (Modern Debate)

Some teams adopt:

- More integration & contract tests
    
- Fewer classic unit tests
    

This can work **only if**:  
✅ Services are small  
✅ Contracts are strictly enforced  
✅ Tooling is excellent

For most teams:

> ✅ **Classic Test Pyramid is still the safest model**

---

## 9️⃣ Test Pyramid in Microservices

Each microservice should have:

✅ Unit tests for business logic  
✅ Integration tests with:

- Its own DB
    
- Its own message broker  
    ✅ Contract tests for APIs  
    ✅ A few cross-service E2E tests
    

Never rely on:  
❌ One massive system test suite for everything

---

## 🔟 Test Pyramid & CI/CD Pipeline

|Stage|Test Type|
|---|---|
|Local dev|Unit tests|
|Pull request|Unit + some integration|
|CI pipeline|Full integration suite|
|Pre-release|System / E2E tests|
|Production|Monitoring & canary tests|

---

## 1️⃣1️⃣ Interview-Ready One-Liners

- **Test Pyramid** → Strategy with many unit, some integration, few system tests
    
- **Why it exists** → Balance confidence, speed, and cost
    
- **Biggest risk** → Too many slow E2E tests
    
- **Most valuable layer** → Unit tests (fastest feedback)
    
- **Anti-pattern** → Ice-cream cone testing
    

---

## ✅ Final Power Summary

> The Test Pyramid is a testing strategy that emphasizes a large base of fast, isolated unit tests, a smaller number of integration tests for component interaction, and a very small number of slow, expensive system tests for full user validation. This balance maximizes reliability while keeping feedback fast and testing costs low.

---

✅ With this, you now fully understand:

- Unit vs Integration vs System Tests
    
- The Test Pyramid
    
- Cost vs confidence trade-offs
    
- Real-world CI/CD application
---

# 🧪 Mocking vs Stubbing — Complete Testing Theory

## 1️⃣ The Core Idea (One-Liner Each)

- **Stub** → Provides _fake data_ to help a test run
    
- **Mock** → Verifies _how a dependency is used_
    

> ✅ **Stubs return values**  
> ✅ **Mocks verify behavior**

---

## 2️⃣ Why We Use Mocks & Stubs

In unit testing, we want to:  
✅ Test only **our unit’s logic**  
✅ Avoid slow or unreliable dependencies  
✅ Avoid databases, APIs, file systems, networks

So we **replace real dependencies with test doubles**:

- Stub → controls input
    
- Mock → validates interactions
    

---

## 3️⃣ What Is a Stub?

> A **stub** is a fake object that returns **predefined data** when called.

It does:  
✅ Gives predictable output  
✅ Helps the test proceed  
❌ Does NOT verify how it was used

---

### ✅ Stub Example

Real dependency:

```python
user_repo.get_user(id)  → calls database
```

Stub version:

```python
stub_user_repo.get_user(1) → returns { "id": 1, "name": "Alice" }
```

Test focuses on:  
✅ Business logic  
✅ Not the database

---

### ✅ When to Use a Stub

✅ External APIs  
✅ Databases  
✅ File systems  
✅ Time / random generators  
✅ Configuration services

Use a stub when:

> ✅ You only care about the returned value

---

## 4️⃣ What Is a Mock?

> A **mock** is a fake object that **records how it was used** and lets you assert behavior.

It does:  
✅ Checks if a method was called  
✅ Checks how many times  
✅ Checks with what parameters

---

### ✅ Mock Example

```python
mock_email_service.send("Welcome")
```

Test verifies:

```python
assert send() was called once
assert with correct email address
```

Here, you are testing:  
✅ That the interaction happened  
✅ Not just the final output

---

### ✅ When to Use a Mock

✅ Sending emails  
✅ Publishing events  
✅ Calling payment gateways  
✅ Logging  
✅ Side effects

Use a mock when:

> ✅ You care about **how something was used**

---

## 5️⃣ Side-by-Side Comparison

|Feature|Stub|Mock|
|---|---|---|
|Purpose|Provide fake data|Verify behavior|
|Returns values|✅ Yes|✅ Yes|
|Verifies calls|❌ No|✅ Yes|
|Checks parameters|❌ No|✅ Yes|
|Checks call count|❌ No|✅ Yes|
|Used for|Inputs|Interactions|
|Focus|State|Behavior|

---

## 6️⃣ Simple Real-World Example

### 🎯 Scenario:

You’re testing an **Order Service** that:

- Gets user from DB
    
- Charges payment
    
- Sends confirmation email
    

---

### ✅ Use a STUB for:

- User Repository (returns user data)
    
- Product Price Service (returns price)
    

Because:

> ✅ You only need data to proceed

---

### ✅ Use a MOCK for:

- Payment Gateway (verify it was charged)
    
- Email Service (verify email was sent)
    

Because:

> ✅ You must verify side effects happened

---

## 7️⃣ Stub vs Mock in One Sentence

- A **stub** helps your test **run**
    
- A **mock** helps your test **assert behavior**
    

---

## 8️⃣ Common Mistakes (Very Important)

❌ Using mocks when a stub is enough  
❌ Mocking internal logic instead of boundaries  
❌ Over-mocking → fragile tests  
❌ Asserting too many interactions  
❌ Mocking value objects instead of real ones

---

## 9️⃣ Best Practices

✅ Stub:

- External services
    
- Time
    
- Randomness
    
- Databases in unit tests
    

✅ Mock:

- Notifications
    
- Events
    
- Payments
    
- Side effects
    

✅ Avoid:

- Mocking everything
    
- Mocking your own logic
    
- Mocking simple data objects
    

---

## 🔟 Relationship with Test Pyramid

|Test Layer|Stub|Mock|
|---|---|---|
|Unit Tests|✅✅✅|✅✅✅|
|Integration Tests|⚠️ Limited|⚠️ Rare|
|System Tests|❌ Never|❌ Never|

Mocks & stubs belong mainly in:

> ✅ **Unit testing**

---

## 1️⃣1️⃣ Interview-Ready One-Liners

- **Stub** → Returns controlled data
    
- **Mock** → Verifies calls and behavior
    
- **Main difference** → Data vs Interaction
    
- **Use stub when** → You only need inputs
    
- **Use mock when** → You need to verify side effects
    
- **Over-mocking causes** → Fragile tests
    

---

## ✅ Final Power Summary

> A stub is a simple test double that returns predefined data to help a test execute, while a mock is a test double that records and verifies how it was used. Stubs support test inputs, while mocks validate interactions and side effects. Knowing when to use each is key to writing fast, reliable, and maintainable unit tests.

---

✅ You now fully understand:

- Mocking vs Stubbing
    
- Their purpose
    
- When to use each
    
- How they fit into the Test Pyramid
    

---

# 🧾 Contract Testing — Complete Theory

## 1️⃣ What Is Contract Testing?

> **Contract testing verifies that two systems (a consumer and a provider) agree on how they communicate.**

Instead of testing:  
❌ The full system end-to-end  
It tests:  
✅ The **agreement (contract)** between services

---

## 2️⃣ What Is a “Contract”?

A **contract** defines:

✅ Request format  
✅ Response format  
✅ Field types  
✅ Required vs optional fields  
✅ Status codes  
✅ Error formats

### Example API Contract

```json
GET /users/{id}

Response:
{
  "id": number,
  "name": string,
  "email": string
}
```

If either side breaks this → production failure ❌

---

## 3️⃣ The Big Problem Contract Testing Solves

In microservices:

- Service A depends on Service B
    
- Team B changes the API
    
- Team A breaks in production
    

Traditional testing problems:  
❌ Unit tests won’t catch this  
❌ Integration tests are slow  
❌ End-to-end tests are flaky

✅ **Contract testing catches this before deployment**

---

## 4️⃣ Consumer vs Provider (Very Important)

|Role|Meaning|
|---|---|
|**Consumer**|Service that calls an API|
|**Provider**|Service that exposes the API|

Example:

- Frontend → Consumer
    
- Backend API → Provider
    

Or:

- Order Service → Consumer
    
- Payment Service → Provider
    

---

## 5️⃣ How Contract Testing Actually Works (Workflow)

### 🟦 Step 1: Consumer Defines Expectations

The consumer defines:  
✅ What request it sends  
✅ What response it expects

This becomes the **contract file**.

---

### 🟦 Step 2: Provider Verifies the Contract

The provider runs tests to verify:  
✅ It can satisfy all consumer contracts

If not → build fails ❌

---

### 🟦 Step 3: Independent Deployments

Now:  
✅ Consumer can deploy safely  
✅ Provider can deploy safely  
✅ Both know they are compatible

---

## 6️⃣ Visual Flow (Conceptual)

```
Consumer Test → Contract File → Provider Verification
```

Instead of:

```
Consumer → Provider → Full System Test
```

---

## 7️⃣ What Contract Testing Tests (and Does NOT)

✅ Tests:

- API shape (JSON structure)
    
- Field names & types
    
- Status codes
    
- Required vs optional fields
    

❌ Does NOT test:

- Database logic
    
- Internal service implementation
    
- Business rules deep inside the provider
    
- UI behavior
    

---

## 8️⃣ Contract Testing vs Integration vs System Tests

|Feature|Contract|Integration|System|
|---|---|---|---|
|Scope|API agreement|Multiple components|Full app|
|Speed|⚡ Fast|⚡ Medium|🐌 Slow|
|Flakiness|✅ Very low|⚠️ Medium|❌ High|
|Needs real provider running|❌ No|✅ Yes|✅ Yes|
|Finds breaking API changes|✅✅✅|✅|✅|
|Tests business workflows|❌|❌|✅✅✅|

---

## 9️⃣ Why Contract Testing Is So Valuable in Microservices

Without contract testing:  
❌ One service deploy breaks many others  
❌ Teams block each other  
❌ Release coordination becomes painful  
❌ Breaking changes reach production

With contract testing:  
✅ Teams deploy independently  
✅ No surprise API breaks  
✅ Faster CI/CD  
✅ Safer refactoring

---

## 🔟 Provider-Driven vs Consumer-Driven Contracts

### ✅ Consumer-Driven Contracts (Most Common)

- Consumers define expectations
    
- Provider must satisfy them
    
- Best for microservices
    

### ✅ Provider-Driven Contracts

- Provider publishes a schema (OpenAPI)
    
- Consumers must follow it
    

Most modern systems use:

> ✅ **Consumer-Driven Contract Testing (CDC)**

---

## 1️⃣1️⃣ Popular Contract Testing Tools

|Tool|Used For|
|---|---|
|Pact|Consumer-driven contracts|
|Spring Cloud Contract|Java microservices|
|OpenAPI + Schema Tests|Provider-driven contracts|
|Postman Contracts|API testing|
|Dredd|API validation|

---

## 1️⃣2️⃣ Contract Testing in the Test Pyramid

Contract testing usually sits:

```
         System Tests
     Integration Tests
  Contract Tests   ✅  (between unit & integration)
Unit Tests
```

It replaces many:  
❌ Slow API integration tests  
✅ With fast, reliable compatibility checks

---

## 1️⃣3️⃣ Common Mistakes

❌ Treating contract tests as full integration tests  
❌ Putting business logic in contract tests  
❌ Not versioning contracts  
❌ Ignoring backward compatibility  
❌ Only provider testing, no consumer testing

---

## 1️⃣4️⃣ Contract Testing & Versioning

Best practices:  
✅ Add new fields, don’t break old ones  
✅ Never remove required fields without a new version  
✅ Support two versions during migration  
✅ Validate both versions with contracts

---

## 1️⃣5️⃣ Interview-Ready One-Liners

- **Contract Testing** → Verifies API compatibility between services
    
- **Goal** → Prevent breaking changes in distributed systems
    
- **Most common type** → Consumer-Driven Contracts
    
- **Where it fits** → Between unit and integration tests
    
- **Key benefit** → Independent deployments with confidence
    

---

## ✅ Final Power Summary

> Contract testing verifies that consumers and providers agree on API request and response formats without requiring full end-to-end system tests. By validating service compatibility early in CI pipelines, contract testing prevents breaking changes, enables independent deployments, and significantly increases reliability in microservice architectures.

---

✅ At this point, your **PHASE 9 — Quality & Reliability** knowledge includes:

- Unit vs Integration vs System Tests
    
- Test Pyramid
    
- Mocking vs Stubbing
    
- Contract Testing
    

---

# ⚡ Load Testing vs Stress Testing — Complete Theory

## 1️⃣ Core One-Line Difference

- **Load Testing** → Tests system behavior under **expected normal and peak load**
    
- **Stress Testing** → Pushes the system **beyond limits to find its breaking point**
    

In short:

> ✅ Load Testing checks **performance**  
> ✅ Stress Testing checks **resilience & failure behavior**

---

## 2️⃣ What Is Load Testing?

> **Load testing measures how a system performs under realistic, expected traffic.**

### ✅ Purpose of Load Testing

✅ Validate response times  
✅ Check throughput (requests/sec)  
✅ Verify scalability  
✅ Ensure SLAs/SLOs are met  
✅ Find performance bottlenecks

---

### ✅ Example Load Test Scenarios

- 10,000 users browsing simultaneously
    
- 2,000 checkouts per minute
    
- 500 API requests per second
    

This simulates:

> ✅ **Real-life production usage**

---

### ✅ Key Questions Load Testing Answers

- Can the system handle expected traffic?
    
- Are response times within limits?
    
- Does CPU, memory, DB, and network stay healthy?
    
- Does auto-scaling work correctly?
    

---

## 3️⃣ What Is Stress Testing?

> **Stress testing pushes the system beyond its safe capacity until it fails.**

You intentionally overload:

✅ Users  
✅ Requests  
✅ Data size  
✅ Message volume  
✅ Network traffic

To see:  
✅ When it breaks  
✅ How it breaks  
✅ How it recovers

---

### ✅ Example Stress Test Scenarios

- Gradually increase users from 10k → 100k
    
- Spike traffic 10× in 1 minute
    
- Flood the message queue
    
- Overload the database with writes
    

This simulates:

> ✅ **Traffic spikes, DDoS, viral growth, hardware failure**

---

### ✅ Key Questions Stress Testing Answers

- Where is the breaking point?
    
- Does the system fail gracefully or crash?
    
- Does it recover automatically?
    
- Is data corrupted under stress?
    
- Are alerts triggered on time?
    

---

## 4️⃣ Side-by-Side Comparison Table

|Feature|Load Testing|Stress Testing|
|---|---|---|
|Goal|Validate performance|Find breaking point|
|Traffic Level|Expected + peak|Extreme / beyond limits|
|When Used|Before production & scaling|Before launch & for resilience|
|Focus|Speed & throughput|Stability & recovery|
|System Behavior|Should remain stable|Expected to fail|
|Outcome|SLA validation|Failure mode analysis|
|Risk Type Tested|Performance risk|Reliability & outage risk|

---

## 5️⃣ Real-World Example (E-Commerce System)

### ✅ Load Test Example

Test with:

- 20,000 concurrent users
    
- Normal shopping + checkout
    

Goal:  
✅ Confirm site works during Black Friday

---

### ✅ Stress Test Example

Test with:

- Increase from 20k → 200k users
    
- Sudden 10× spike in orders
    

Goal:  
✅ Find:

- When DB crashes
    
- When payment service fails
    
- When auto-scaling breaks
    

---

## 6️⃣ Load vs Stress vs Production Outage

|Scenario|Covered By|
|---|---|
|Slow website at peak hours|✅ Load Testing|
|Site crashes during viral spike|✅ Stress Testing|
|DB throttles under traffic|✅ Both|
|Auto-scaling fails|✅ Stress Testing|
|Latency increases under normal use|✅ Load Testing|

---

## 7️⃣ Key Metrics Measured in Both

✅ Response time (p95, p99)  
✅ Throughput (RPS, TPS)  
✅ Error rate  
✅ CPU usage  
✅ Memory usage  
✅ DB connections  
✅ Queue depth  
✅ Thread pool saturation

---

## 8️⃣ Relationship to Reliability Engineering

|Test Type|Reliability Benefit|
|---|---|
|Load Testing|Ensures performance SLAs|
|Stress Testing|Ensures graceful degradation|
|Both|Prevent production outages|

---

## 9️⃣ Common Mistakes

❌ Only doing load testing, skipping stress testing  
❌ Not running tests with production-like data  
❌ Ignoring database and cache limits  
❌ Not testing auto-scaling  
❌ No alerting during stress tests  
❌ Running on shared weak environments

---

## 🔟 How Load & Stress Fit with Other Test Types

|Test Type|Focus|
|---|---|
|Unit Tests|Logic|
|Integration Tests|Service wiring|
|System Tests|User behavior|
|**Load Tests**|Performance|
|**Stress Tests**|Breaking point & recovery|

---

## 1️⃣1️⃣ Interview-Ready One-Liners

- **Load testing** → Verifies performance under expected traffic
    
- **Stress testing** → Pushes system beyond limits to observe failure
    
- **Load = performance**, **Stress = resilience**
    
- **Load ensures speed**, **Stress ensures survival**
    
- **Stress testing validates failover & auto-recovery**
    

---

## ✅ Final Power Summary

> Load testing evaluates how a system performs under expected and peak traffic conditions by measuring response time, throughput, and resource usage. Stress testing deliberately overwhelms the system beyond its capacity to identify breaking points, failure modes, and recovery behavior. Together, they ensure both performance reliability and system resilience in production environments.

---


# 🎯 Flaky Tests — Complete Theory Guide

## 1️⃣ What Is a Flaky Test?

> A **flaky test** is a test that **sometimes passes and sometimes fails without any code changes**.

That means:

- ✅ Same code
    
- ✅ Same test
    
- ❌ Different result
    

This is dangerous because:

> ❌ You can no longer trust test failures  
> ❌ Developers start ignoring test results

---

## 2️⃣ Why Flaky Tests Are a Serious Problem

Flaky tests cause:

❌ False failures  
❌ Wasted debugging time  
❌ Slower CI/CD pipelines  
❌ Blocked deployments  
❌ Loss of confidence in automation  
❌ Teams rerun builds instead of fixing real issues

Worst case:

> ❌ **Real bugs get ignored because “it’s probably just a flaky test.”**

---

## 3️⃣ Most Common Causes of Flaky Tests

### 🟥 1. Timing & Race Conditions (Most Common)

- Async operations not awaited
    
- Fixed `sleep()` instead of proper waits
    
- Background jobs finishing late
    

✅ Example:

- Test expects result in 1s
    
- Sometimes it takes 1.2s → fails
    

---

### 🟥 2. Shared State Between Tests

- Global variables
    
- Shared databases
    
- Static caches
    
- Files reused between tests
    

One test pollutes the environment for the next one.

---

### 🟥 3. Order-Dependent Tests

- Test A must run before Test B
    
- Parallel test execution breaks this
    

This is a **huge red flag**.

---

### 🟥 4. External Dependencies

- Real APIs
    
- Network calls
    
- Third-party services
    
- Clock/time services
    

If they are slow or unstable → tests become flaky.

---

### 🟥 5. Environment Differences

- Local vs CI behaves differently
    
- Different OS, CPU, memory, time zones
    

---

### 🟥 6. Randomness Without Control

- Random IDs
    
- Random delays
    
- Random data generation without fixed seeds
    

---

### 🟥 7. Resource Constraints

- CPU starvation in CI
    
- Memory limits
    
- Too many parallel tests
    

---

## 4️⃣ Flaky Tests vs Real Failing Tests

|Real Failing Test ✅|Flaky Test ❌|
|---|---|
|Always fails|Sometimes fails|
|Reproducible|Hard to reproduce|
|Indicates real bug|Indicates test problem|
|Must be fixed immediately|Must be stabilized or removed|

---

## 5️⃣ How Flaky Tests Damage the Test Pyramid

- Unit tests should be: ✅ fast, ✅ deterministic
    
- Flaky unit tests destroy their core purpose
    

Worst outcomes:

- Developers re-run tests until green ❌
    
- CI becomes slow and unreliable ❌
    
- Teams stop writing new tests ❌
    

---

## 6️⃣ How to Detect Flaky Tests

✅ Re-run failed tests multiple times  
✅ Run tests in random order  
✅ Run tests in parallel  
✅ Track “pass-rate per test”  
✅ Monitor CI failure history

If a test:

> Passes 1 time, fails the next → **it’s flaky**

---

## 7️⃣ How to Fix Flaky Tests (Best Practices)

### ✅ 1. Eliminate Timing Assumptions

❌ Avoid fixed sleeps  
✅ Use explicit waits  
✅ Wait for events, not time

---

### ✅ 2. Isolate Test Data

✅ Unique test data per test  
✅ Reset DB between tests  
✅ Use transactions that roll back

---

### ✅ 3. Remove Shared Global State

✅ No static mutable state  
✅ No shared caches  
✅ No file reuse between tests

---

### ✅ 4. Mock External Dependencies

✅ Stub APIs  
✅ Mock time  
✅ Mock random generators

---

### ✅ 5. Make Tests Order-Independent

✅ Any test can run alone  
✅ Any test can run first  
✅ Any test can run in parallel

---

### ✅ 6. Control Randomness

✅ Fixed random seeds  
✅ Deterministic test data

---

### ✅ 7. Allocate Proper CI Resources

✅ Enough memory  
✅ Enough CPU  
✅ Controlled parallelization

---

## 8️⃣ What To Do When You Find a Flaky Test

You have **3 correct options**:

1️⃣ **Fix it immediately** ✅ (best option)  
2️⃣ **Quarantine it** ✅ (temporary isolation)  
3️⃣ **Delete it** ✅ (if it provides no value)

❌ NEVER ignore it  
❌ NEVER keep rerunning the pipeline as a solution

---

## 9️⃣ Flaky Tests & Reliability Engineering

Flaky tests are a form of:

- ❌ **Test system unreliability**
    
- ❌ **Feedback loop corruption**
    

Just like production outages:

> ✅ You must treat flaky tests as **incidents** and remove root causes.

---

## 🔟 Interview-Ready One-Liners

- **Flaky test** → A test that passes and fails inconsistently without code changes
    
- **Main causes** → Timing issues, shared state, external dependencies
    
- **Biggest danger** → Loss of trust in test automation
    
- **Best fix** → Deterministic tests + isolation + proper async handling
    
- **Never acceptable** → Rerunning CI until green
    

---

## ✅ Final Power Summary

> Flaky tests are nondeterministic tests that produce inconsistent results without any change in code, usually caused by timing issues, shared state, external dependencies, or environmental instability. They are extremely harmful because they erode trust in automation, slow down CI/CD pipelines, and mask real defects. Eliminating flakiness through deterministic design, isolation, mocking, and proper async handling is essential for reliable software delivery.

---

✅ At this point, your **testing & reliability foundation is very strong**:

- Unit vs Integration vs System
    
- Test Pyramid
    
- Mocking vs Stubbing
    
- Contract Testing
    
- Load vs Stress Testing
    
- Flaky Tests
    

---

