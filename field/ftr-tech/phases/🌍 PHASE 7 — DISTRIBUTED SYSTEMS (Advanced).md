## 1️⃣2️⃣ Distributed Systems Theory

# Network partitions

## 1️⃣ What Is a Network Partition?

> A **network partition** happens when a distributed system is **split into two or more groups of nodes that cannot communicate with each other**, even though the nodes themselves are still running.

In simple words:

- The system is **alive**
    
- The nodes are **alive**
    
- But the **network between them is broken**
    

---

## 2️⃣ What Causes Network Partitions?

Common real-world causes:

- 🚫 Router or switch failure
    
- 🌍 Data center or region outage
    
- 🔌 Cable cut or ISP failure
    
- 🔥 Firewall or misconfiguration
    
- ⏳ Extreme network latency (effectively a partition)
    
- 🛠️ Bad deployments / DNS issues
    

---

## 3️⃣ What Makes Partitions So Dangerous?

Because during a partition:

- Each side **thinks the other side is down**
    
- Both sides may **continue working independently**
    
- This causes:
    
    - ❌ Data inconsistency
        
    - ❌ Split-brain scenarios
        
    - ❌ Conflicting writes
        

Example:

- User updates balance on Node A
    
- Another update happens on Node B at the same time
    
- When the network heals → **conflicting data**
    

---

## 4️⃣ Network Partitions & the CAP Theorem

A partition directly triggers the **P** in **CAP**:

|CAP Letter|Meaning|
|---|---|
|**C**|Consistency|
|**A**|Availability|
|**P**|Partition tolerance|

👉 **Key Law:**

> When a network partition happens, a distributed system must choose between **Consistency (C)** or **Availability (A)** — it cannot provide both.

### The Two Choices During a Partition:

#### ✅ CP System (Consistency + Partition Tolerance)

- Rejects requests on one side
    
- Keeps data correct but becomes **partially unavailable**
    
- Used in:
    
    - Zookeeper
        
    - Etcd
        
    - HBase
        

✅ Strong correctness  
❌ Lower availability during failures

---

#### ✅ AP System (Availability + Partition Tolerance)

- Accepts requests on all sides
    
- Data becomes **eventually consistent**
    
- Used in:
    
    - Cassandra
        
    - DynamoDB
        
    - CouchDB
        

✅ Always available  
❌ Temporary inconsistency

---

## 5️⃣ Partition vs Node Failure

|Scenario|Node Failure|Network Partition|
|---|---|---|
|Node status|Node down|Node is alive|
|Communication|Impossible|Broken between groups|
|Recovery|Restart node|Heal network|
|Risk|Data loss|Data conflicts|

💡 **Partition is usually more dangerous than a crash.**

---

## 6️⃣ Real-World Example (Bank System)

You have:

- Bank servers in **US-East**
    
- Bank servers in **US-West**
    

A fiber cut separates them.

### If system is AP:

- Both regions continue accepting transactions
    
- User spends money **twice**
    
- Conflict resolved later → potential financial inconsistency
    

### If system is CP:

- One region **stops accepting transactions**
    
- No double spending
    
- System is **temporarily unavailable**
    

---

## 7️⃣ How Systems Handle Network Partitions

### ✅ Replication + Quorums

- Use majority voting (Quorum = ⌈N/2 + 1⌉)
    
- Prevents split-brain
    

### ✅ Leader Election

- Only the leader accepts writes
    
- Followers reject during partition
    

### ✅ Vector Clocks / Versioning

- Track conflicting updates
    

### Eventual Consistency

- Allow temporary divergence and reconcile later
    

### Circuit Breakers & Timeouts

- Prevent cascading failures
    

---

## 8️⃣ One-Line Interview Answers

- **Network partition** → A state where parts of a distributed system cannot communicate due to network failure
    
- **CAP relation** → During a partition, a system must choose between consistency and availability
    
- **Biggest risk** → Data inconsistency and split-brain
    
- **Fix techniques** → Quorums, leader election, replication strategies
    

---

## 9️⃣ Final Summary (Strong & Clear)

> A network partition occurs when communication breaks between parts of a distributed system while the nodes remain operational. During a partition, systems must choose between consistency and availability as dictated by the CAP theorem. Handling partitions safely requires quorums, leader election, replication strategies, and conflict resolution mechanisms.


# Replication models
# 🧬 REPLICATION MODELS — Distributed Systems Theory

Replication = **keeping multiple copies of the same data across different nodes**, so the system can survive failures, scale reads, and stay highly available.

There are several replication **strategies**, each with different guarantees and trade-offs.

---

## 1️⃣ Synchronous Replication

### 🔹 How it works

- A write is considered **successful only after being written to ALL replicas**.
    
- Client waits for all replicas → strong consistency.
    

### 🔹 Pros

- Strong consistency
    
- No data loss if a node fails
    

### 🔹 Cons

- Slower writes
    
- If one replica is slow → entire system is slow
    
- Lower availability during failures
    

### 🔹 Used in

- Traditional RDBMS (PostgreSQL synchronous replication)
    
- Financial systems where correctness > speed
    

---

## 2️⃣ Asynchronous Replication

### 🔹 How it works

- Master/leader writes data locally
    
- Then sends updates to replicas **in the background**
    

Write is acknowledged **before** replicas receive it → fast.

### 🔹 Pros

- Very fast writes
    
- High availability
    

### 🔹 Cons

- Risk of replication lag
    
- Possibility of data loss if leader crashes before replication
    

### 🔹 Used in

- MySQL async replication
    
- Redis
    
- Many NoSQL systems
    

---

## 3️⃣ Semi-Synchronous Replication

Middle ground between sync and async.

### 🔹 How it works

- Leader waits for **at least one** follower to acknowledge
    
- But not _all_ followers
    
- Others catch up later asynchronously
    

### 🔹 Pros

- Reduces risk of data loss compared to async
    
- Faster than fully synchronous
    

### 🔹 Cons

- Still slower than pure async
    
- Small risk of data loss remains
    

### 🔹 Used in

- MySQL semi-sync mode
    
- Some Raft configurations
    

---

## 4️⃣ Single-Leader Replication (Primary-Replica)

One node = **leader**

- Accepts all writes  
    Other nodes = **followers**
    
- Replicate from the leader
    
- Serve reads
    

### 🔹 Pros

- Simple mental model
    
- Strong consistency (if synchronous)
    
- High read scalability
    

### 🔹 Cons

- Leader is bottleneck
    
- Failovers required on leader crash
    
- Write scalability limited
    

### 🔹 Used in

- PostgreSQL
    
- MongoDB (before multi-primary)
    
- MySQL
    

---

## 5️⃣ Multi-Leader Replication (Multi-Master)

Multiple nodes can **accept writes**.

### 🔹 Pros

- High availability (even during partitions)
    
- Geographic distribution
    
- Better write throughput
    

### 🔹 Cons

- Conflicts! (same row updated on two nodes)
    
- Requires conflict resolution strategies
    

### 🔹 Used in

- CouchDB
    
- MongoDB (multi-primary clusters)
    
- Active-active setups
    

### 🔹 Conflict resolution techniques:

- Last-write-wins (LWW)
    
- Vector clocks
    
- CRDTs
    

---

## 6️⃣ Leaderless Replication

No leader at all. Any node can accept reads or writes.

### 🔹 How it works

- A write is sent to **N** replicas
    
- Client considers write successful after reaching **W** replicas
    
- Reads happen from **R** replicas
    

If **W + R > N** → strong consistency  
If not → eventual consistency

### 🔹 Pros

- High availability (even under partitions)
    
- No single point of failure
    
- Scaling is easier
    

### 🔹 Cons

- Conflict resolution required
    
- Might read stale data if quorum not satisfied
    

### 🔹 Used in

- DynamoDB
    
- Cassandra
    
- Riak
    

---

## 📌 Replication & Consistency Spectrum

|Model|Consistency|Availability|Notes|
|---|---|---|---|
|Synchronous|Strong|Lower|Safe, slow|
|Async|Weak|High|Fast, risk of data loss|
|Semi-sync|Medium|Medium|Trade-off|
|Single-leader|Strong (or eventual)|Medium|Simple, safe|
|Multi-leader|Eventual (or custom)|High|Conflicts possible|
|Leaderless|Eventual / Tunable|Very High|Quorum-based|

---

## 🚦 How Replication Relates to CAP

During a **network partition**:

- **CP** systems → prioritize consistency → typically leader-based
    
- **AP** systems → prioritize availability → leaderless or multi-leader
    

---

## 🧪 Real-World Examples for Each Model

|System|Replication Type|
|---|---|
|PostgreSQL|Single-leader (sync or async)|
|MySQL|Single-leader (with async/semi-sync)|
|MongoDB|Single- or multi-leader|
|Cassandra|Leaderless quorum|
|DynamoDB|Leaderless quorum|
|Redis (Cluster)|Leader-based async|
|Kafka|Leader-based with ISR (in-sync replicas)|

---

## 💡 One-Line Interview Definitions

- **Synchronous replication** → write waits for all replicas → strong consistency
    
- **Asynchronous replication** → write returns before replicas update → fast but risky
    
- **Single-leader** → one writer, many readers
    
- **Multi-leader** → multiple writers → conflicts must be resolved
    
- **Leaderless** → no single writer → quorum-based consistency
    

---

## 🧩 Final Summary (Clean & Strong)

> Replication models determine **how data is copied across nodes**, affecting consistency, availability, performance, and failure behavior.  
> They range from **synchronous strong consistency** to fully **leaderless quorum-based replication** enabling massive availability and scale.  
> Choosing a model depends on application needs, latency, tolerance for stale reads, and operational complexity.

---

# Leader election

## 👑 Leader Election — Distributed Systems Theory

## 1️⃣ What Is Leader Election?

> **Leader election** is the process by which nodes in a distributed system **dynamically choose one node to act as the coordinator (leader)**.

The **leader** is responsible for:

- Coordinating writes
    
- Maintaining consistency
    
- Managing replicas
    
- Handling global decisions
    

All other nodes become **followers**.

---

## 2️⃣ Why Leader Election Is Needed

Leader election solves these critical problems:

✅ Prevents **split-brain**  
✅ Ensures **single source of truth for writes**  
✅ Enables **strong consistency**  
✅ Allows **automatic failover**  
✅ Simplifies **distributed coordination**

Without leader election:

- Multiple nodes may accept writes
    
- Data conflicts become frequent
    
- System becomes unpredictable
    

---

## 3️⃣ When Leader Election Happens

Leader election happens when:

1. **System starts**
    
2. **Leader crashes**
    
3. **Network partition heals**
    
4. **Cluster membership changes**
    
5. **Heartbeat timeout occurs**
    

---

## 4️⃣ Leader Responsibilities

A typical leader handles:

- ✅ Accepting **all write requests**
    
- ✅ **Replicating data** to followers
    
- ✅ **Maintaining logs** (e.g., Raft log)
    
- ✅ **Coordinating transactions**
    
- ✅ **Managing cluster membership**
    

Followers:

- Serve reads (sometimes)
    
- Replicate from leader
    
- Monitor leader health
    

---

## 5️⃣ How Leader Election Works (Conceptually)

### Step-by-step:

1. All nodes start as **followers**
    
2. Leader sends **heartbeats**
    
3. If followers **stop receiving heartbeats**
    
4. They start an **election**
    
5. One node **wins majority votes**
    
6. That node becomes the **new leader**
    

This must ensure:

- ✅ Only **one leader at a time**
    
- ✅ Majority agreement
    
- ✅ Safe under failures & partitions
    

---

## 6️⃣ Major Leader Election Algorithms

### 🟢 1. Raft (Most Popular Today)

- Majority voting
    
- Randomized timeouts prevent split votes
    
- Used in:
    
    - etcd
        
    - Consul
        
    - CockroachDB
        
    - Kubernetes control plane
        

**Key idea:**

> Leader must have votes from **majority of nodes (quorum)**

---

### 🟡 2. Paxos (Academic & Complex)

- Strong theoretical guarantees
    
- Hard to implement
    
- Used in:
    
    - Google Chubby (older)
        
    - Some legacy systems
        

---

### 🔵 3. Zookeeper (ZAB Protocol)

- Uses **ephemeral nodes**
    
- First node to create `/leader` node becomes leader
    
- Used in:
    
    - HBase
        
    - Kafka (older versions)
        

---

## 7️⃣ Quorum & Leader Election

Leader must be elected by a **quorum (majority)**:

```
Quorum = floor(N/2) + 1
```

|Nodes|Quorum|
|---|---|
|3|2|
|5|3|
|7|4|

Why quorum matters:  
✅ Prevents **two leaders**  
✅ Prevents **split-brain**  
✅ Guarantees **safety under partitions**

---

## 8️⃣ Leader Election & Network Partitions

If a partition happens:

- Majority side ✅ can elect a leader
    
- Minority side ❌ **cannot elect a leader → becomes unavailable**
    

This is how **CP systems** preserve consistency.

---

## 9️⃣ Failure Handling (Automatic Failover)

When a leader fails:

1. Heartbeats stop
    
2. Timeout fires
    
3. Election begins
    
4. Majority picks a new leader
    
5. Service continues
    

This is called **automatic failover**.

---

## 🔟 Leader Election vs Leaderless Systems

|Feature|Leader-Based|Leaderless|
|---|---|---|
|Writes|Only leader|Any node|
|Consistency|Strong|Eventual / Quorum|
|Failover|Required|Not required|
|Complexity|Medium|High|
|Examples|PostgreSQL, Kafka, Raft|DynamoDB, Cassandra|

---

## 1️⃣1️⃣ Real-World Examples

|System|Leader Election Used?|
|---|---|
|Kubernetes|✅ (etcd via Raft)|
|Kafka|✅ (Controller leader)|
|PostgreSQL HA|✅|
|MongoDB|✅ (Primary election)|
|Zookeeper|✅|
|Cassandra|❌ (Leaderless)|

---

## 1️⃣2️⃣ Split-Brain — The Biggest Risk

**Split-brain = two leaders at the same time**

Cause:

- Network partition
    
- No quorum enforcement
    

Effects:

- Data corruption
    
- Conflicting writes
    
- Inconsistent state
    

✅ Prevented by:

- Quorum
    
- Fencing tokens
    
- Leases
    
- Term numbers (Raft)
    

---

## 1️⃣3️⃣ One-Line Interview Definitions

- **Leader election** → Process of selecting a single coordinating node in a distributed system
    
- **Quorum** → Majority required to safely elect a leader
    
- **Split-brain** → Multiple leaders due to network failure
    
- **Raft** → Popular leader-based consensus algorithm
    

---

## ✅ Final Summary (Strong & Clean)

> Leader election is the mechanism by which distributed systems select a single coordinating node to ensure consistency, prevent conflicts, and enable safe replication. It relies on quorum-based voting and heartbeat monitoring, and is foundational to systems like Kubernetes, Kafka, MongoDB, and PostgreSQL.

---


# Distributed consensus

# 🧠 Distributed Consensus — Complete Theory

## 1️⃣ What Is Distributed Consensus?

> **Distributed consensus** is the process by which multiple nodes in a distributed system **agree on a single value or decision**, even in the presence of:

- Failures
    
- Network partitions
    
- Delays
    
- Crashed nodes
    

In simple words:

> “All healthy nodes must agree on the same truth.”

Examples of what must be agreed on:

- Who is the **leader**
    
- Order of **database writes**
    
- Whether a **transaction is committed**
    
- Cluster membership
    
- Configuration values
    

---

## 2️⃣ Why Consensus Is So Hard

Because distributed systems suffer from:

- ❌ Network **unreliability**
    
- ❌ Nodes **crash at any time**
    
- ❌ Messages are **delayed or lost**
    
- ❌ No **global clock**
    
- ❌ Network partitions
    

A single wrong decision can cause:

- Split-brain
    
- Data corruption
    
- Infinite inconsistency
    
- Total system failure
    

---

## 3️⃣ The Core Properties of Consensus

A correct consensus algorithm must satisfy:

### 1. Safety (Correctness)

> The system never makes conflicting decisions.

- No two nodes decide different values.
    

### 2. Liveness (Progress)

> The system eventually makes a decision.

- It must not get stuck forever.
    

### 3. Fault Tolerance

> The system must tolerate **node and network failures**.

---

## 4️⃣ The FLP Impossibility Theorem (Very Important)

> In a **purely asynchronous distributed system**, it is **impossible to guarantee consensus if even one node can fail**.

This means:

- You **cannot have perfect safety + liveness + failure tolerance simultaneously** without making timing assumptions.
    

👉 All real-world consensus systems **cheat** by:

- Using timeouts
    
- Using failure detectors
    
- Assuming “eventual synchrony”
    

---

## 5️⃣ What Consensus Is Used For

|Use Case|Why Consensus Is Needed|
|---|---|
|Leader Election|Only one leader allowed|
|Distributed Databases|Ordered, consistent writes|
|Locking|Only one owner per lock|
|Configuration Management|All nodes must see same config|
|Service Discovery|Consistent cluster view|
|Kubernetes Control Plane|Entirely built on consensus|

---

## 6️⃣ Major Distributed Consensus Algorithms

---

## 🟢 RAFT (Most Important for Industry)

> **Raft = Practical, understandable, production-grade consensus**

### Key Ideas:

- One **leader**
    
- All writes go through leader
    
- Leader replicates logs to followers
    
- Entries are committed after **majority (quorum)** acknowledgement
    

### Guarantees:

- Strong consistency
    
- Automatic failover
    
- No split-brain
    

### Used In:

- etcd (Kubernetes)
    
- Consul
    
- CockroachDB
    
- RethinkDB
    

### Why Raft Won:

✅ Easy to understand  
✅ Easier to implement than Paxos  
✅ Excellent safety guarantees

---

## 🟡 PAXOS (Theoretical Foundation)

> Paxos is the **original mathematical solution to consensus**.

### Characteristics:

- Extremely powerful
    
- Extremely hard to understand
    
- Many variants (Multi-Paxos, Fast Paxos)
    

### Used In:

- Google Spanner (internals)
    
- Older distributed coordination systems
    

### Reality:

- Rarely implemented directly by most engineers today
    
- Raft replaced Paxos as the **practical choice**
    

---

## 🔵 ZAB (ZooKeeper Atomic Broadcast)

> ZAB = Consensus + replication protocol used by ZooKeeper.

- Leader-based
    
- Total ordering of updates
    
- Strong consistency
    

### Used In:

- ZooKeeper
    
- HBase
    
- Old Kafka clusters
    

---

## 🔴 Viewstamped Replication (VR)

- Similar to Paxos & Raft
    
- Used in some academic systems
    
- Influenced Raft design
    

---

## 7️⃣ Quorum & Majority (Critical Concept)

Consensus requires **majority agreement**:

```
Quorum = ⌊N / 2⌋ + 1
```

Why majority matters:

- Prevents **two different leaders**
    
- Prevents **split-brain**
    
- Guarantees **overlapping agreements**
    

Example:

- 5 nodes → quorum = 3
    
- Any two quorums will always share at least 1 node  
    → prevents conflicting decisions
    

---

## 8️⃣ How Consensus Works (High-Level Flow)

Using Raft-style flow:

1. Nodes start as **followers**
    
2. Leader sends **heartbeats**
    
3. If heartbeats stop:
    
    - Followers start **election**
        
4. A node gets **majority votes**
    
5. It becomes **leader**
    
6. All writes go through leader
    
7. Leader replicates to followers
    
8. When majority ACK → commit
    

---

## 9️⃣ Consensus vs Replication vs Leader Election

|Concept|Purpose|
|---|---|
|Replication|Copying data|
|Leader Election|Picking a coordinator|
|Consensus|Agreement on order & content of updates|

👉 **Leader election is just one special case of consensus.**

---

## 🔟 Consensus & CAP Theorem

During a **network partition**:

- CP systems:
    
    - Use **consensus**
        
    - Prefer **consistency**
        
    - Minority side becomes unavailable
        
- AP systems:
    
    - Do **not** rely on consensus
        
    - Allow divergence
        
    - Resolve later
        

So:

- **Raft / Paxos → CP**
    
- **Cassandra / DynamoDB → AP**
    

---

## 1️⃣1️⃣ What Happens If Consensus Breaks?

If consensus fails:

- Data corruption
    
- Two leaders (split-brain)
    
- Lost writes
    
- Permanent inconsistency
    

This is why:

> ✅ Consensus layers are treated as **sacred infrastructure**

---

## 1️⃣2️⃣ Real-World Systems Built on Consensus

|System|Consensus Used|
|---|---|
|Kubernetes|etcd (Raft)|
|Kafka|Raft (new versions)|
|MongoDB|Raft-like|
|CockroachDB|Raft|
|Spanner|Paxos|
|ZooKeeper|ZAB|

---

## 1️⃣3️⃣ Interview-Ready One-Liners

- **Distributed consensus** → Agreement among multiple nodes on a single value despite failures
    
- **Raft** → Leader-based practical consensus algorithm
    
- **Paxos** → Theoretical foundation of consensus
    
- **Quorum** → Majority required to safely reach agreement
    
- **FLP theorem** → Perfect consensus is impossible in fully asynchronous systems with failures
    

---

## ✅ Final Summary (Power Statement)

> Distributed consensus is the fundamental process that allows multiple unreliable nodes to agree on a single consistent decision in the presence of failures. It guarantees safety, progress, and fault tolerance, and is implemented by algorithms like **Raft, Paxos, and ZAB**, forming the backbone of systems such as **Kubernetes, Kafka, MongoDB, and Spanner**.

---

# Sharding

# 🧩 SHARDING — Distributed Systems & Database Theory

## 1️⃣ What Is Sharding?

> **Sharding is the process of splitting a large dataset into smaller, independent pieces (called shards) and distributing them across multiple machines.**

Each shard:

- Holds **only a subset of the total data**
    
- Is stored on a **different node**
    
- Works independently for reads and writes
    

This is also called:

> ✅ **Horizontal Partitioning**

---

## 2️⃣ Why Sharding Is Needed

Without sharding:

- One database server handles **all data**
    
- You hit limits on:
    
    - CPU
        
    - RAM
        
    - Disk
        
    - Network I/O
        

With sharding:  
✅ Storage scales  
✅ Write throughput scales  
✅ Read throughput scales  
✅ System avoids single-node bottlenecks

---

## 3️⃣ Sharding vs Replication (Critical Difference)

|Feature|Sharding|Replication|
|---|---|---|
|Purpose|Scale data size & writes|High availability & reads|
|Data|Split into parts|Copied entirely|
|Each node has|Partial data|Full data|
|Failure protection|❌ No (by itself)|✅ Yes|
|Primary benefit|Scalability|Fault tolerance|

✅ **In real systems, sharding + replication are combined.**

---

## 4️⃣ How Sharding Works (Conceptually)

1. Choose a **shard key** (partition key)
    
2. Based on the key:
    
    - Data is routed to a specific shard
        
3. Each shard:
    
    - Handles its own reads & writes
        
4. Application or router decides:
    
    - Which shard to talk to
        

Example:

```
Users 1–1,000,000 → Shard A
Users 1,000,001–2,000,000 → Shard B
Users 2,000,001–3,000,000 → Shard C
```

---

## 5️⃣ Shard Key (Most Important Design Decision)

> A **shard key** determines **how data is distributed across shards**.

A good shard key:  
✅ Evenly distributes data  
✅ Avoids hot spots  
✅ Is frequently used in queries  
✅ Is immutable

Bad shard key:  
❌ Too many records go to one shard  
❌ Uneven traffic  
❌ Massive load imbalance

---

## 6️⃣ Types of Sharding

---

### 🟢 1. Range-Based Sharding

Data is split by **value ranges**

Example:

```
UserID 1–1000   → Shard 1
UserID 1001–2000 → Shard 2
```

✅ Pros:

- Easy to understand
    
- Efficient range queries
    

❌ Cons:

- Hotspot risk (new users all go to last shard)
    
- Uneven distribution
    

---

### 🟡 2. Hash-Based Sharding (Most Common)

Shard = `hash(shard_key) % N`

✅ Pros:

- Very even data distribution
    
- Avoids hot spots
    

❌ Cons:

- Range queries become expensive
    
- Re-sharding when N changes is difficult
    

---

### 🔵 3. Directory-Based Sharding

A lookup table stores:

```
UserID → ShardID
```

✅ Pros:

- Fully flexible
    
- Easy rebalancing
    

❌ Cons:

- Directory becomes a bottleneck
    
- Extra network hop
    

---

### 🔴 4. Geo-Based Sharding

Data is split by **location**

Examples:

- US users → US shard
    
- EU users → EU shard
    

✅ Pros:

- Low latency
    
- Legal compliance
    

❌ Cons:

- Cross-region queries are expensive
    

---

## 7️⃣ Sharding & writes (How Writes Scale)

Without sharding:

- All writes go to **one node**
    

With sharding:

- Writes are spread across:
    
    - Shard A
        
    - Shard B
        
    - Shard C
        

✅ Massive write scalability  
✅ No single write bottleneck

---

## 8️⃣ Sharding & Queries (The Hard Part)

###Single-shard Queries

Fast ✅  
Example:

```sql
SELECT * FROM users WHERE user_id = 9123
```

### ❌ Cross-shard Queries

Slow ❌  
Example:

```sql
SELECT COUNT(*) FROM users
```

This must:

- Query **all shards**
    
- Aggregate results
    
- Merge responses
    

This is called:

> ✅ **Scatter-Gather Query**

---

## 9️⃣ Re-sharding (Painful but Necessary)

As data grows:

- Shards fill up
    
- Load becomes uneven
    

You must:

- Create new shards
    
- Move data between nodes
    
- Update routing rules
    

This is:  
❌ Operationally complex  
❌ Risky during production  
✅ But unavoidable at massive scale

---

## 🔟 Sharding + Replication (Real-World Setup)

Each shard is usually **replicated**:

```
Shard 1 → 3 replicas
Shard 2 → 3 replicas
Shard 3 → 3 replicas
```

This gives:  
✅ Scalability (shards)  
✅ Fault tolerance (replication)  
✅ High availability

---

## 1️⃣1️⃣ Real-World Systems That Use Sharding

|System|Sharding|
|---|---|
|MongoDB|✅ Built-in auto-sharding|
|Cassandra|✅ Token-based sharding|
|DynamoDB|✅ Partition-based|
|Elasticsearch|✅ Index shards|
|MySQL|✅ Manual sharding|
|PostgreSQL|✅ Citus, Yugabyte|

---

## 1️⃣2️⃣ Sharding & CAP Theorem

Sharded systems usually aim for:

- **AP (Availability + Partition Tolerance)** → DynamoDB, Cassandra
    
- Or **CP with consensus per shard** → CockroachDB, Yugabyte
    

---

## 1️⃣3️⃣ One-Line Interview Definitions

- **Sharding** → Splitting large datasets across multiple machines to scale horizontally
    
- **Shard key** → Field used to determine data placement
    
- **Range sharding** → Data split by value ranges
    
- **Hash sharding** → Data split by hash of the key
    
- **Cross-shard query** → Query that touches multiple shards
    

---

## ✅ Final Summary (Power Statement)

> Sharding is a horizontal scaling technique that partitions large datasets across multiple machines using a shard key. It enables massive scalability for storage and writes but introduces complexity in querying, transactions, and operational management. In real-world systems, sharding is almost always combined with replication for fault tolerance.

---

# Consistent hashing

# 🔁 Consistent Hashing — Complete Theory

## 1️⃣ The Core Problem It Solves

Imagine you distribute data like this:

```
shard = hash(key) % N
```

Now what happens if:

- You had **4 servers**
    
- You add **1 new server**
    
- Now N changes from 4 → 5
    

👉 **Almost ALL keys get remapped to different servers.**

This causes:  
❌ Massive data movement  
❌ Cache invalidation storms  
❌ Network overload  
❌ Downtime risk

This is known as the **Re-sharding Problem**.

---

## 2️⃣ What Is Consistent Hashing?

> **Consistent hashing is a hashing strategy that minimizes the number of keys that must be remapped when nodes are added or removed.**

With consistent hashing:

- Only **~1/N of keys move**
    
- Instead of **almost all keys moving**
    

---

## 3️⃣ Intuition: The Hash Ring (Most Important Concept)

Instead of mapping to:

```
0 → N-1
```

Consistent hashing maps everything onto a **circular ring**:

```
0 --------------------------> 2³²-1
↑                              ↓
 └────────────── Ring ─────────┘
```

On this ring:

- ✅ **Servers are hashed onto the ring**
    
- ✅ **Keys are hashed onto the ring**
    
- ✅ A key is always assigned to:
    

> 👉 **The first server found when moving clockwise**

---

## 4️⃣ Step-by-Step How It Works

1. Hash each **server ID** → place it on the ring
    
2. Hash each **data key** → place it on the same ring
    
3. To find where a key belongs:
    
    - Move clockwise
        
    - First server hit = **owner of that key**
        

---

## 5️⃣ What Happens When a Server Is Added?

Only:

- The keys between the **new server and its previous neighbor** move
    

✅ About **1/N data moves**  
✅ All other keys stay untouched

This is the **magic of consistent hashing**.

---

## 6️⃣ What Happens When a Server Is Removed?

Same logic:

- Only keys that belonged to that server move
    
- They move to the next server clockwise
    

✅ No global reshuffle

---

## 7️⃣ Virtual Nodes (vNodes) — Load Balancing the Ring

### The Problem:

If servers are placed randomly:

- Some may get **huge portions of the ring**
    
- Others get **tiny portions**  
    → Load imbalance
    

### The Solution:

Each **physical server gets multiple virtual nodes**

Example:

```
Server A → A1, A2, A3, A4
Server B → B1, B2, B3, B4
```

Now:  
✅ Data is evenly distributed  
✅ Failures affect smaller segments  
✅ Better load balancing

---

## 8️⃣ Consistent Hashing vs Modulo Hashing

|Feature|Modulo Hashing (`% N`)|Consistent Hashing|
|---|---|---|
|Scaling|❌ Expensive|✅ Cheap|
|Node addition|❌ Almost all keys move|✅ ~1/N keys move|
|Node removal|❌ Almost all keys move|✅ Only affected keys move|
|Fault tolerance|❌ Poor|✅ Excellent|
|Used in production|❌ Rare|✅ Everywhere|

---

## 9️⃣ Where Consistent Hashing Is Used

|System|Usage|
|---|---|
|Cassandra|Data partitioning|
|DynamoDB|Partition routing|
|Riak|Key distribution|
|Memcached|Cache sharding|
|Redis Cluster|Slot-based variant|
|Akamai CDN|Edge routing|
|Load balancers|Server routing|

---

## 🔟 Consistent Hashing & Sharding

- **Sharding** = splitting data across nodes
    
- **Consistent hashing** = strategy to decide **which shard gets which data**
    

It solves:  
✅ Dynamic scaling  
✅ Node failures  
✅ Hot-spot reduction  
✅ Smooth rebalancing

---

## 1️⃣1️⃣ Consistent Hashing & Fault Tolerance

When a node fails:

- Only its key range is affected
    
- Traffic is redirected to neighbors
    
- System continues operating
    

✅ Zero global rebalancing  
✅ High availability

---

## 1️⃣2️⃣ Time & Space Complexity

- Lookup: **O(log N)** (binary search on ring)
    
- With vNodes:
    
    - More memory
        
    - Much better distribution
        

---

## 1️⃣3️⃣ One-Line Interview Definitions

- **Consistent hashing** → Hashing strategy that minimizes key movement when nodes change
    
- **Hash ring** → Logical circle for placing nodes and keys
    
- **Virtual nodes** → Multiple positions per physical server for load balancing
    
- **Why important?** → Enables scalable, fault-tolerant distributed systems
    

---

## ✅ Final Summary (Power Statement)

> Consistent hashing is a distributed hashing technique that assigns both servers and keys onto a circular hash ring, ensuring that when a node is added or removed, only a small fraction of data is remapped. It is a foundational mechanism behind scalable caching systems, distributed databases, and modern load balancing.

---


# Eventual consistency

# ⏳ Eventual Consistency — Distributed Systems Theory

## 1️⃣ What Is Eventual Consistency?

> **Eventual consistency** means that **if no new updates are made, all replicas in a distributed system will eventually converge to the same final value**.

Key idea:

- ✅ The system allows **temporary inconsistency**
    
- ✅ But guarantees **final convergence**
    

There is **no guarantee** that all reads return the latest value at any given moment.

---

## 2️⃣ Why Eventual Consistency Exists

Eventual consistency exists because of the **CAP Theorem**.

When a **network partition (P)** happens, a system must choose:

- ✅ **Consistency (C)** → reject some requests
    
- ✅ **Availability (A)** → accept all requests
    

> **Eventual consistency chooses Availability + Partition Tolerance (AP).**

---

## 3️⃣ Strong vs Eventual Consistency

|Feature|Strong Consistency|Eventual Consistency|
|---|---|---|
|Read behavior|Always latest data|May return stale data|
|Availability|Lower|Very high|
|Partition behavior|Becomes unavailable|Keeps working|
|Complexity|Simple to reason|Harder reasoning|
|Write conflicts|No|Yes (must resolve)|
|Used in|Banking, payments|Social media, caches|

---

## 4️⃣ What Happens During Eventual Consistency

1. Client writes data to **Node A**
    
2. Node A accepts the write immediately ✅
    
3. Update is sent to other nodes **asynchronously**
    
4. During this time:
    
    - Node B may return **old data**
        
5. After replication finishes:
    
    - All nodes return the same value ✅
        

This temporary mismatch is called:

> ✅ **Inconsistency Window**

---

## 5️⃣ Eventual Consistency & Replication Models

Eventual consistency is commonly used with:

- ✅ **Asynchronous replication**
    
- ✅ **Multi-leader replication**
    
- ✅ **Leaderless replication**
    

Used in systems like:

- DynamoDB
    
- Cassandra
    
- CouchDB
    
- Riak
    

---

## 6️⃣ Eventual Consistency & Quorums (Tunable Consistency)

Many systems allow **tunable consistency** using:

```
N = total replicas
W = write replicas required
R = read replicas required
```

Rule:

```
If (W + R > N) → Strong consistency
If (W + R <= N) → Eventual consistency possible
```

Example:

- N = 3, W = 1, R = 1  
    → Eventual consistency  
    → Fast writes + possible stale reads
    

---

## 7️⃣ Problems Caused by Eventual Consistency

### ❌ Stale Reads

You may read **old data**

### ❌ Write Conflicts

Two nodes accept different updates at the same time

### ❌ Read-Your-Writes Violation

You write data but immediately read and **don’t see your own update**

### ❌ Out-of-Order Updates

Later changes appear before earlier ones

---

## 8️⃣ How Eventual Consistency Is Made Safe

Since conflicts are allowed, systems must use:

### Last-Write-Wins (LWW)

- Based on timestamps
    
- Simple but can lose updates
    

### Vector Clocks

- Track causality
    
- Detect conflicts
    

### CRDTs

- Data structures that merge automatically without conflicts
    

### Anti-Entropy (Sync Mechanisms)

- Periodic background repair using:
    
    - Merkle trees
        
    - Gossip protocols
        

---

## 9️⃣ Real-World Examples

|System|Behavior|
|---|---|
|DynamoDB|Eventual by default|
|Cassandra|Tunable (eventual → strong)|
|Redis|Eventual in replicas|
|DNS|Eventual by nature|
|Social media likes|Eventually consistent|
|CDN cache updates|Eventually consistent|

---

## 🔟 Where Eventual Consistency Is Acceptable

✅ Social feeds  
✅ Likes & views counters  
✅ Analytics  
✅ Caches  
✅ Logging systems  
✅ Message delivery systems

❌ Banking  
❌ Stock trading  
❌ Payment processing  
❌ Identity & authentication

---

## 1️⃣1️⃣ Eventual Consistency vs Strong Consistency vs Causal

|Model|Guarantee|
|---|---|
|Eventual|Final convergence only|
|Strong|Always latest|
|Causal|Order preserved for related events|

---

## 1️⃣2️⃣ Interview-Ready One-Liners

- **Eventual consistency** → The system allows temporary inconsistency but guarantees eventual convergence
    
- **Why used?** → High availability and partition tolerance
    
- **Biggest risk?** → Stale reads and write conflicts
    
- **How fixed?** → Conflict resolution using vector clocks, CRDTs, or LWW
    

---

## ✅ Final Summary (Power Statement)

> Eventual consistency is a consistency model where updates propagate asynchronously, allowing temporary inconsistencies in exchange for high availability and partition tolerance. It is the foundation of massively scalable systems like Cassandra and DynamoDB but requires sophisticated conflict resolution to remain correct.

---

# Delivery guarantees

# 📦 Delivery Guarantees — Distributed Systems & Messaging Theory

## 1️⃣ What Are Delivery Guarantees?

> **Delivery guarantees define how many times a message may be delivered to a consumer in the presence of failures** (network issues, crashes, retries, partitions).

They answer this critical question:

> ❓ _“If something goes wrong, will a message be lost, duplicated, or delivered exactly once?”_

---

# 2️⃣ The Three Core Delivery Guarantees

There are **exactly three** fundamental models:

|Guarantee|Meaning|
|---|---|
|**At-Most-Once**|Message is delivered **zero or one time**|
|**At-Least-Once**|Message is delivered **one or more times**|
|**Exactly-Once**|Message is delivered **once and only once**|

---

## 3️⃣ At-Most-Once Delivery

> **Messages are delivered at most one time — but may be lost.**

### 🔹 How it works:

- No retries
    
- No acknowledgment tracking
    
- Fire-and-forget model
    

### Pros:

- Extremely fast
    
- Low latency
    
- No overhead
    

### ❌ Cons:

- Messages **can be lost**
    
- No reliability
    

### Used when:

- Logs
    
- Metrics
    
- Telemetry
    
- Monitoring heartbeats
    

### Real-world analogy:

> Sending a postcard — it might arrive, it might not.

---

## 4️⃣ At-Least-Once Delivery (Most Common)

> **Messages are delivered one or more times — duplicates are possible.**

### 🔹 How it works:

1. Producer sends message
    
2. Consumer processes it
    
3. Consumer sends an **ACK**
    
4. If ACK is missing → message is **retried**
    

### Pros:

- No message loss
    
- Reliable
    

### ❌ Cons:

- **Duplicate messages possible**
    
- Consumer must be **idempotent**
    

### Used in:

- Kafka (default)
    
- RabbitMQ
    
- AWS SQS
    
- Most production systems
    

### Real-world analogy:

> You keep calling someone until they confirm they got the message — they may hear it twice.

---

## 5️⃣ Exactly-Once Delivery (Hard & Expensive)

> **Each message is processed once and only once — no loss, no duplicates.**

### ⚠️ Reality Check:

> **True end-to-end exactly-once is extremely difficult and expensive.**  
> Most systems only offer:

- Exactly-once in **limited scope**
    
- Or **effectively-once** with idempotency + transactions
    

### How it’s achieved:

- Distributed transactions
    
- Idempotent producers
    
- Transactional consumers
    
- Offset + state atomically committed
    

### ❌ Cons:

- High latency
    
- Complex implementation
    
- Lower throughput
    

### Used in:

- Financial systems
    
- Payment pipelines
    
- Inventory & billing systems
    
- Kafka + Flink exactly-once pipelines
    

---

# 6️⃣ Message Deduplication & Idempotency

At-least-once → requires **idempotent consumers**

### Techniques:

- Unique message ID
    
- Deduplication table
    
- Idempotent database writes
    
- Upserts instead of inserts
    

This gives:

> ✅ **Effective exactly-once semantics on top of at-least-once systems**

---

# 7️⃣ Delivery Guarantees vs Message Loss vs Duplication

|Guarantee|Message Loss|Duplicates|
|---|---|---|
|At-most-once|✅ Possible|❌ No|
|At-least-once|❌ No|✅ Possible|
|Exactly-once|❌ No|❌ No|

---

# 8️⃣ Delivery Guarantees in Popular Systems

|System|Default Guarantee|
|---|---|
|Kafka|At-least-once|
|Kafka (transactions)|Exactly-once (within Kafka)|
|RabbitMQ|At-least-once|
|SQS Standard|At-least-once|
|SQS FIFO|Exactly-once (best-effort)|
|Redis Pub/Sub|At-most-once|
|UDP|At-most-once|
|TCP|At-least-once (from app POV)|

---

#  9️⃣ Delivery Guarantees & Eventual Consistency

At-least-once + retries → causes:

- Duplicate events
    
- Out-of-order events
    

This is why:  
✅ **Eventual consistency models are commonly paired with at-least-once delivery**

---

# 🔟 Delivery Guarantees & CAP

- **Exactly-once** → favors **Consistency** (CP)
    
- **At-least-once** → favors **Availability** (AP)
    
- **At-most-once** → favors **Performance**
    

---

# 1️⃣1️⃣ Why Exactly-Once Is So Hard (Deep Reason)

To achieve true exactly-once:

- Network must never duplicate
    
- Consumer must never crash mid-processing
    
- Storage must commit atomically
    
- Offsets + business state must update together
    

👉 This requires **distributed transactions (2PC)** → slow and fragile.

This is why:

> ✅ Most real systems choose **at-least-once + idempotency**

---

# 1️⃣2️⃣ One-Line Interview Definitions

- **At-most-once** → Fast but unreliable (messages may be lost)
    
- **At-least-once** → Reliable but duplicates possible
    
- **Exactly-once** → No loss, no duplication, very expensive
    
- **Why idempotency matters?** → To safely handle at-least-once delivery
    

---

# ✅ Final Summary (Power Statement)

> Delivery guarantees define how reliably messages are delivered in the presence of failures. At-most-once favors speed but risks loss, at-least-once ensures reliability but allows duplication, and exactly-once provides perfect correctness at high complexity and cost. In practice, most large-scale systems rely on at-least-once delivery combined with idempotent processing for safety and scalability.

---

# Distributed locking

# 🔐 Distributed Locking — Complete Theory

## 1️⃣ What Is a Distributed Lock?

> A **distributed lock** is a synchronization mechanism that ensures **only one process across multiple machines can access a shared resource at a time**.

It’s the distributed version of:

- `mutex`
    
- `synchronized`
    
- database row locks
    

But now:  
✅ Multiple machines  
✅ Network failures  
✅ Partial crashes  
✅ Clock differences

---

## 2️⃣ Why Distributed Locking Is Needed

Distributed locks are required when multiple services try to:

- Update the **same DB record**
    
- Run the **same scheduled job**
    
- Process the **same event**
    
- Access a **shared file or cache**
    
- Perform **leader-only operations**
    

Without locks:  
❌ Race conditions  
❌ Data corruption  
❌ Duplicate processing  
❌ Double payments  
❌ Inconsistent state

---

## 3️⃣ Why Distributed Locking Is Hard

Local locks are easy because:

- One memory space
    
- One clock
    
- No network
    

Distributed locks are hard because of:

|Problem|Why It Breaks Locks|
|---|---|
|Network partitions|Nodes can’t see each other|
|Process crashes|Lock holder may die|
|Message delays|Lock expiry becomes tricky|
|Clock skew|Time-based locks become unsafe|
|Split brain|Two nodes think they hold the lock|

---

## 4️⃣ Basic Requirements of a Correct Distributed Lock

A correct distributed lock must guarantee:

1. ✅ **Mutual Exclusion**  
    Only one client holds the lock
    
2. ✅ **Deadlock Freedom**  
    If the lock holder crashes, others must recover it
    
3. ✅ **Fault Tolerance**  
    System must tolerate node failures
    
4. ✅ **No Split-Brain**  
    Two clients must NEVER think they both own the lock
    

---

## 5️⃣ Common Distributed Lock Implementations

---

### 🔹 1. Database-Based Locking

Example:

```sql
SELECT * FROM job_lock WHERE name='job1' FOR UPDATE;
```

✅ Pros:

- Simple
    
- ACID guarantees
    

❌ Cons:

- DB becomes a bottleneck
    
- Not designed for high-scale distributed locking
    

---

### 🔹 2. Redis-Based Locking (Most Popular)

Basic idea:

```bash
SET lock_key value NX PX 30000
```

Meaning:

- `NX` → only set if not exists
    
- `PX` → auto-expire after 30s
    

✅ Fast  
✅ Simple  
✅ Widely used

❌ Dangerous if implemented naïvely  
❌ Split-brain possible

---

### 🔹 3. ZooKeeper / etcd / Consul (Coordination Systems)

They provide:

- Strong consistency
    
- Leader election
    
- Built-in distributed locking
    

✅ Extremely safe  
✅ Proven in production  
❌ High latency  
❌ Operational complexity

Used in:

- Kubernetes
    
- Hadoop
    
- Kafka (earlier versions)
    

---

## 6️⃣ The Redis Redlock Algorithm (Famous but Controversial)

Redlock uses:

- **5 independent Redis nodes**
    
- Lock is acquired only if:
    
    - Majority (3/5) grant it
        

Goal:  
✅ Avoid single Redis failure  
✅ Prevent split brain

⚠️ Highly debated:

- Works in practice
    
- But theoretical correctness under partitions is controversial
    

Many companies still use:  
✅ **Single Redis + Replication + TTL**

---

## 7️⃣ Lock Expiry & The Big Danger (Critical Concept)

Locks **must expire** to avoid deadlocks:

```
Client A gets lock → crashes → lock never released ❌
```

With TTL:

```
Lock auto-expires → another client can proceed ✅
```

### ⚠️ But TTL causes a dangerous race:

1. Client A holds lock
    
2. A pauses (GC, network)
    
3. Lock expires
    
4. Client B acquires lock
    
5. Client A resumes and still thinks it owns the lock ❌
    

Result:  
❌ **Two writers at the same time → data corruption**

---

## 8️⃣ How Correct Systems Prevent Lock Reuse

### ✅ Use Unique Lock Tokens

When acquiring a lock:

```
SET lock_key randomUUID NX PX 30000
```

When releasing:

```
Only delete if value == myUUID
```

This prevents:  
✅ One client deleting another client’s lock

---

## 9️⃣ Distributed Locking vs Leader Election

|Feature|Distributed Lock|Leader Election|
|---|---|---|
|Duration|Short-lived|Long-lived|
|Purpose|Guard critical section|Coordinate whole system|
|Example|Process one job|Run scheduler|
|Tools|Redis, ZK|ZK, etcd, Raft|

---

## 🔟 Where Distributed Locks Are Commonly Used

✅ Job schedulers  
✅ Distributed cron systems  
✅ Payment processing  
✅ Inventory updates  
✅ Cache rebuilds  
✅ Schema migrations  
✅ Leader-only services

---

## 1️⃣1️⃣ Failure Scenarios & What Can Go Wrong

|Failure|Result|
|---|---|
|Network partition|Split-brain|
|Long GC pause|Lock expires early|
|Redis restart|Lock lost|
|Clock drift|TTL becomes unsafe|
|Client crash|Deadlock without TTL|

---

## 1️⃣2️⃣ The CAP Trade-Off in Locks

Distributed locks usually favor:

- ✅ **Consistency**
    
- ✅ **Partition tolerance**
    
- ❌ **Availability**
    

During a partition:

> It is **safer to reject all lock requests than risk two clients holding the lock.**

---

## 1️⃣3️⃣ Correct Mental Model

> A distributed lock is not a “guarantee of safety” — it is a **best-effort coordination primitive that must be reinforced with idempotency and safe retries**.

In other words:  
✅ Lock for coordination  
✅ Idempotency for correctness  
✅ Transactions for safety

---

## 1️⃣4️⃣ One-Line Interview Definitions

- **Distributed lock** → A mechanism that ensures only one process across a distributed system accesses a shared resource at a time
    
- **Why hard?** → Because of crashes, network partitions, and clock skew
    
- **Most common tools?** → Redis, ZooKeeper, etcd
    
- **Biggest risk?** → Split-brain and lock expiry races
    

---

## ✅ Final Summary (Power Statement)

> Distributed locking ensures mutual exclusion across multiple machines but is fundamentally hard due to failures, partitions, and clock uncertainty. While systems like Redis, ZooKeeper, and etcd are widely used for implementing distributed locks, no solution is perfectly safe under all failure conditions, so locks must always be combined with idempotent operations and safe recovery logic.

---
