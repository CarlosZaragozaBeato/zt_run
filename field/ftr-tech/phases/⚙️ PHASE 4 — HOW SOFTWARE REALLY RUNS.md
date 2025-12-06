## 7️⃣ Operating Systems Theory

# Process vs Thread

Understanding the difference between processes and threads is fundamental to operating systems, concurrent programming, and building efficient applications.

---

## **Core Definitions**

### **Process**

A **process** is an independent, self-contained execution environment. It's an instance of a running program.

Think of a process as a **complete, separate apartment**:

- Has its own address space (memory)
- Has its own resources (files, network connections)
- Completely isolated from other processes
- Heavy to create and manage

### **Thread**

A **thread** is a lightweight execution unit within a process. Multiple threads share the same process resources.

Think of a thread as a **room within an apartment**:

- Shares the apartment's resources (kitchen, bathroom)
- Has its own private space (bedroom)
- Can easily communicate with other rooms
- Light to create and manage

---

## **Visual Comparison**

```
PROCESS MODEL:
┌─────────────────────────────────┐
│         Process 1               │
│  ┌──────────────────────────┐  │
│  │   Memory Space           │  │
│  │   (isolated)             │  │
│  │                          │  │
│  │   Code, Data, Stack      │  │
│  │   Heap                   │  │
│  └──────────────────────────┘  │
│  Resources: Files, Sockets     │
│  PID: 1234                     │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│         Process 2               │
│  ┌──────────────────────────┐  │
│  │   Memory Space           │  │
│  │   (completely separate)  │  │
│  │                          │  │
│  │   Code, Data, Stack      │  │
│  │   Heap                   │  │
│  └──────────────────────────┘  │
│  Resources: Files, Sockets     │
│  PID: 5678                     │
└─────────────────────────────────┘

THREAD MODEL:
┌─────────────────────────────────────────────┐
│              Process                        │
│  ┌──────────────────────────────────────┐  │
│  │   Shared Memory Space                │  │
│  │   Code, Global Data, Heap            │  │
│  └──────────────────────────────────────┘  │
│                                             │
│  Thread 1       Thread 2       Thread 3    │
│  ┌────────┐    ┌────────┐    ┌────────┐   │
│  │ Stack  │    │ Stack  │    │ Stack  │   │
│  │ Local  │    │ Local  │    │ Local  │   │
│  │ Vars   │    │ Vars   │    │ Vars   │   │
│  └────────┘    └────────┘    └────────┘   │
│                                             │
│  Shared Resources: Files, Sockets          │
│  PID: 1234 (same for all threads)         │
└─────────────────────────────────────────────┘
```

---

## **Key Differences**

|Aspect|Process|Thread|
|---|---|---|
|**Memory**|Separate memory space|Shared memory space|
|**Communication**|IPC (pipes, sockets, shared memory) - complex|Direct access to shared data - simple|
|**Creation**|Expensive (~1000s of CPU cycles)|Cheap (~100s of CPU cycles)|
|**Context Switch**|Slow (save/restore full state)|Fast (save/restore minimal state)|
|**Isolation**|High - crash doesn't affect others|Low - crash can affect all threads|
|**Resource Usage**|Heavy (separate resources)|Light (shared resources)|
|**OS View**|Independent units|Sub-units of a process|
|**ID**|Process ID (PID)|Thread ID (TID)|

---

## **Memory Layout**

### **Process Memory Layout**

Each process has its own complete memory space:

```
High Address
┌─────────────────┐
│  Kernel Space   │  ← OS kernel (protected)
├─────────────────┤
│  Stack          │  ← Local variables, function calls
│      ↓          │     (grows downward)
│                 │
│  (free space)   │
│                 │
│      ↑          │
│  Heap           │  ← Dynamic memory (malloc, new)
├─────────────────┤     (grows upward)
│  BSS            │  ← Uninitialized globals
├─────────────────┤
│  Data           │  ← Initialized globals
├─────────────────┤
│  Text (Code)    │  ← Program instructions
└─────────────────┘
Low Address
```

### **Thread Memory Layout**

Threads within a process share most memory:

```
SHARED BY ALL THREADS:
┌─────────────────┐
│  Text (Code)    │  ← All threads execute same code
├─────────────────┤
│  Data           │  ← All threads access same globals
├─────────────────┤
│  Heap           │  ← All threads share heap
└─────────────────┘

PER-THREAD (PRIVATE):
Thread 1          Thread 2          Thread 3
┌──────────┐     ┌──────────┐     ┌──────────┐
│  Stack   │     │  Stack   │     │  Stack   │
│          │     │          │     │          │
│  Local   │     │  Local   │     │  Local   │
│  Vars    │     │  Vars    │     │  Vars    │
└──────────┘     └──────────┘     └──────────┘
```

---

## **Process Example**

### **Creating Processes**

```python
import os
import time
from multiprocessing import Process

def worker_process(name, work_time):
    """Function that runs in a separate process"""
    print(f"Process {name} started (PID: {os.getpid()})")
    print(f"Parent PID: {os.getppid()}")
    
    # Simulate work
    time.sleep(work_time)
    
    print(f"Process {name} finished")

if __name__ == "__main__":
    print(f"Main process PID: {os.getpid()}")
    
    # Create separate processes
    p1 = Process(target=worker_process, args=("Worker-1", 2))
    p2 = Process(target=worker_process, args=("Worker-2", 3))
    
    # Start processes
    p1.start()
    p2.start()
    
    print("Main process continuing...")
    
    # Wait for processes to complete
    p1.join()
    p2.join()
    
    print("All processes completed")
```

**Output:**

```
Main process PID: 12345
Process Worker-1 started (PID: 12346)
Parent PID: 12345
Process Worker-2 started (PID: 12347)
Parent PID: 12345
Main process continuing...
Process Worker-1 finished
Process Worker-2 finished
All processes completed
```

### **Process Isolation - No Shared Memory**

```python
from multiprocessing import Process
import os

# Global variable
counter = 0

def increment_counter(name):
    """Each process has its own copy of counter"""
    global counter
    print(f"{name} (PID: {os.getpid()}) - Initial counter: {counter}")
    
    for i in range(5):
        counter += 1
    
    print(f"{name} (PID: {os.getpid()}) - Final counter: {counter}")

if __name__ == "__main__":
    print(f"Main counter: {counter}")
    
    p1 = Process(target=increment_counter, args=("Process-1",))
    p2 = Process(target=increment_counter, args=("Process-2",))
    
    p1.start()
    p2.start()
    
    p1.join()
    p2.join()
    
    print(f"Main counter after processes: {counter}")
```

**Output:**

```
Main counter: 0
Process-1 (PID: 12346) - Initial counter: 0
Process-1 (PID: 12346) - Final counter: 5
Process-2 (PID: 12347) - Initial counter: 0
Process-2 (PID: 12347) - Final counter: 5
Main counter after processes: 0
```

**Key observation**: Each process has its own copy of `counter`. Changes in child processes don't affect the parent or each other.

---

## **Thread Example**

### **Creating Threads**

```python
import threading
import time

def worker_thread(name, work_time):
    """Function that runs in a separate thread"""
    print(f"Thread {name} started (Thread ID: {threading.get_ident()})")
    
    # Simulate work
    time.sleep(work_time)
    
    print(f"Thread {name} finished")

# Main thread
print(f"Main thread ID: {threading.get_ident()}")

# Create threads
t1 = threading.Thread(target=worker_thread, args=("Worker-1", 2))
t2 = threading.Thread(target=worker_thread, args=("Worker-2", 3))

# Start threads
t1.start()
t2.start()

print("Main thread continuing...")

# Wait for threads to complete
t1.join()
t2.join()

print("All threads completed")
```

### **Thread Shared Memory**

```python
import threading
import time

# Global variable - SHARED by all threads
counter = 0

def increment_counter(name):
    """All threads share the same counter"""
    global counter
    print(f"{name} (Thread ID: {threading.get_ident()}) - Initial counter: {counter}")
    
    for i in range(5):
        counter += 1
        time.sleep(0.001)  # Small delay to show interleaving
    
    print(f"{name} (Thread ID: {threading.get_ident()}) - Final counter: {counter}")

print(f"Main counter: {counter}")

t1 = threading.Thread(target=increment_counter, args=("Thread-1",))
t2 = threading.Thread(target=increment_counter, args=("Thread-2",))

t1.start()
t2.start()

t1.join()
t2.join()

print(f"Main counter after threads: {counter}")
```

**Output (may vary due to race conditions):**

```
Main counter: 0
Thread-1 (Thread ID: 123456) - Initial counter: 0
Thread-2 (Thread ID: 789012) - Initial counter: 0
Thread-1 (Thread ID: 123456) - Final counter: 8
Thread-2 (Thread ID: 789012) - Final counter: 10
Main counter after threads: 10
```

**Key observation**: All threads share the same `counter` variable. Changes are visible to all threads (though this creates race conditions - see later).

---

## **When to Use Processes**

### **1. CPU-Bound Tasks**

Tasks that require heavy computation:

```python
import multiprocessing
import time

def calculate_sum(start, end):
    """CPU-intensive task"""
    total = 0
    for i in range(start, end):
        total += i ** 2
    return total

if __name__ == "__main__":
    # Using processes (can utilize multiple CPU cores)
    start_time = time.time()
    
    # Split work across processes
    pool = multiprocessing.Pool(processes=4)
    results = pool.starmap(calculate_sum, [
        (0, 25_000_000),
        (25_000_000, 50_000_000),
        (50_000_000, 75_000_000),
        (75_000_000, 100_000_000)
    ])
    
    total = sum(results)
    
    end_time = time.time()
    print(f"Process time: {end_time - start_time:.2f} seconds")
    print(f"Total: {total}")
```

**Why processes**: Each process runs on a separate CPU core, achieving true parallelism for CPU-bound work.

### **2. Isolation Required**

When you need fault isolation:

```python
from multiprocessing import Process

def risky_operation(name):
    """Operation that might crash"""
    print(f"{name} starting...")
    
    if name == "Process-2":
        # This process crashes
        raise Exception("Deliberate crash!")
    
    print(f"{name} completed successfully")

if __name__ == "__main__":
    p1 = Process(target=risky_operation, args=("Process-1",))
    p2 = Process(target=risky_operation, args=("Process-2",))
    p3 = Process(target=risky_operation, args=("Process-3",))
    
    p1.start()
    p2.start()
    p3.start()
    
    p1.join()
    p2.join()
    p3.join()
    
    print("Main process survived!")
```

**Output:**

```
Process-1 starting...
Process-2 starting...
Process-3 starting...
Process-1 completed successfully
Process-3 completed successfully
Main process survived!
```

**Why processes**: Process-2 crashed, but Process-1, Process-3, and the main process continue running.

### **3. Different Programs**

Running completely different executables:

```python
import subprocess

# Run different programs as separate processes
result1 = subprocess.run(["python", "script1.py"])
result2 = subprocess.run(["python", "script2.py"])
result3 = subprocess.run(["node", "app.js"])
```

---

## **When to Use Threads**

### **1. I/O-Bound Tasks**

Tasks that wait for external resources:

```python
import threading
import time
import requests

def download_file(url, filename):
    """I/O-bound task - waiting for network"""
    print(f"Downloading {filename}...")
    # Simulated download
    time.sleep(2)  # Represents network wait
    print(f"Completed {filename}")

urls = [
    ("http://example.com/file1.pdf", "file1.pdf"),
    ("http://example.com/file2.pdf", "file2.pdf"),
    ("http://example.com/file3.pdf", "file3.pdf"),
    ("http://example.com/file4.pdf", "file4.pdf"),
]

start_time = time.time()

# Using threads - can handle multiple downloads concurrently
threads = []
for url, filename in urls:
    t = threading.Thread(target=download_file, args=(url, filename))
    t.start()
    threads.append(t)

for t in threads:
    t.join()

end_time = time.time()
print(f"Total time: {end_time - start_time:.2f} seconds")  # ~2 seconds, not 8!
```

**Why threads**: While one thread waits for I/O, others can continue. Lightweight context switching makes this efficient.

### **2. Shared State Needed**

When you need easy communication between concurrent tasks:

```python
import threading
import queue
import time

# Shared queue
task_queue = queue.Queue()
results = []
results_lock = threading.Lock()

def worker(worker_id):
    """Process tasks from shared queue"""
    while True:
        try:
            task = task_queue.get(timeout=1)
            print(f"Worker {worker_id} processing task: {task}")
            
            # Process task
            result = task * 2
            
            # Add to shared results (with lock for safety)
            with results_lock:
                results.append(result)
            
            task_queue.task_done()
        except queue.Empty:
            break

# Add tasks to queue
for i in range(10):
    task_queue.put(i)

# Create worker threads
threads = []
for i in range(3):
    t = threading.Thread(target=worker, args=(i,))
    t.start()
    threads.append(t)

# Wait for all tasks to complete
task_queue.join()

# Wait for threads to finish
for t in threads:
    t.join()

print(f"Results: {sorted(results)}")
```

**Why threads**: Shared `task_queue` and `results` are easily accessible to all threads. No complex IPC needed.

### **3. GUI Applications**

Keeping UI responsive:

```python
import threading
import time

def long_running_task():
    """Simulate long computation"""
    for i in range(10):
        time.sleep(1)
        print(f"Progress: {(i+1)*10}%")

# In a GUI application (pseudo-code):
def on_button_click():
    # Run task in background thread
    thread = threading.Thread(target=long_running_task)
    thread.daemon = True  # Dies when main thread exits
    thread.start()
    
    # UI remains responsive!
    print("UI is still responsive")

# Without threading, UI would freeze for 10 seconds
```

---

## **Inter-Process Communication (IPC)**

Since processes don't share memory, they need special mechanisms to communicate.

### **1. Pipes**

```python
from multiprocessing import Process, Pipe

def sender(conn):
    """Send data through pipe"""
    conn.send("Hello from child process!")
    conn.close()

def receiver(conn):
    """Receive data through pipe"""
    message = conn.recv()
    print(f"Received: {message}")
    conn.close()

if __name__ == "__main__":
    parent_conn, child_conn = Pipe()
    
    p1 = Process(target=sender, args=(child_conn,))
    p2 = Process(target=receiver, args=(parent_conn,))
    
    p1.start()
    p2.start()
    
    p1.join()
    p2.join()
```

### **2. Queues**

```python
from multiprocessing import Process, Queue

def producer(queue):
    """Add items to queue"""
    for i in range(5):
        queue.put(f"Item {i}")
    queue.put(None)  # Sentinel value

def consumer(queue):
    """Remove items from queue"""
    while True:
        item = queue.get()
        if item is None:
            break
        print(f"Consumed: {item}")

if __name__ == "__main__":
    queue = Queue()
    
    p1 = Process(target=producer, args=(queue,))
    p2 = Process(target=consumer, args=(queue,))
    
    p1.start()
    p2.start()
    
    p1.join()
    p2.join()
```

### **3. Shared Memory**

```python
from multiprocessing import Process, Value, Array

def increment(shared_value, shared_array):
    """Modify shared memory"""
    shared_value.value += 1
    for i in range(len(shared_array)):
        shared_array[i] *= 2

if __name__ == "__main__":
    # Shared integer
    shared_value = Value('i', 0)
    
    # Shared array
    shared_array = Array('i', [1, 2, 3, 4, 5])
    
    print(f"Before: value={shared_value.value}, array={list(shared_array)}")
    
    processes = []
    for _ in range(3):
        p = Process(target=increment, args=(shared_value, shared_array))
        p.start()
        processes.append(p)
    
    for p in processes:
        p.join()
    
    print(f"After: value={shared_value.value}, array={list(shared_array)}")
```

**Note**: IPC is more complex and slower than thread communication.

---

## **Thread Synchronization**

Since threads share memory, we need synchronization to avoid race conditions.

### **Race Condition Example**

```python
import threading

counter = 0

def increment():
    global counter
    for _ in range(100000):
        counter += 1  # NOT atomic! Read-Modify-Write

threads = []
for _ in range(10):
    t = threading.Thread(target=increment)
    t.start()
    threads.append(t)

for t in threads:
    t.join()

print(f"Expected: 1000000, Got: {counter}")
# Output: Expected: 1000000, Got: 873421 (WRONG!)
```

**Problem**: `counter += 1` is actually three operations:

1. Read counter
2. Add 1
3. Write back

Two threads can interleave, causing lost updates.

### **Solution: Lock**

```python
import threading

counter = 0
lock = threading.Lock()

def increment():
    global counter
    for _ in range(100000):
        with lock:  # Only one thread at a time
            counter += 1

threads = []
for _ in range(10):
    t = threading.Thread(target=increment)
    t.start()
    threads.append(t)

for t in threads:
    t.join()

print(f"Expected: 1000000, Got: {counter}")
# Output: Expected: 1000000, Got: 1000000 (CORRECT!)
```

### **Other Synchronization Primitives**

```python
import threading
import time

# 1. Semaphore - limit concurrent access
semaphore = threading.Semaphore(3)  # Max 3 threads

def limited_resource(thread_id):
    with semaphore:
        print(f"Thread {thread_id} acquired resource")
        time.sleep(1)
        print(f"Thread {thread_id} releasing resource")

# 2. Event - signal between threads
event = threading.Event()

def waiter():
    print("Waiting for event...")
    event.wait()  # Block until event is set
    print("Event received!")

def setter():
    time.sleep(2)
    print("Setting event")
    event.set()

# 3. Condition - complex synchronization
condition = threading.Condition()
items = []

def producer():
    with condition:
        items.append("item")
        condition.notify()  # Wake up consumer

def consumer():
    with condition:
        condition.wait()  # Wait for producer
        item = items.pop()
        print(f"Consumed: {item}")
```

---

## **Context Switching**

### **Process Context Switch**

**What needs to be saved/restored**:

- Program counter
- CPU registers
- Memory management info (page tables)
- Open file descriptors
- Signal handlers
- Process state

**Cost**: ~1000-10000 CPU cycles

```
Process A running
    ↓
Interrupt (timer, I/O, etc.)
    ↓
Save Process A state (expensive)
    ↓
Load Process B state (expensive)
    ↓
Process B running
```

### **Thread Context Switch**

**What needs to be saved/restored**:

- Program counter
- CPU registers
- Stack pointer

**Cost**: ~100-1000 CPU cycles

```
Thread A running
    ↓
Interrupt
    ↓
Save Thread A registers (cheap)
    ↓
Load Thread B registers (cheap)
    ↓
Thread B running
(Still in same memory space!)
```

---

## **Performance Comparison**

```python
import time
import threading
from multiprocessing import Process

def simple_task():
    """Simple task for benchmarking"""
    total = 0
    for i in range(1000):
        total += i
    return total

# Benchmark thread creation
start = time.time()
threads = [threading.Thread(target=simple_task) for _ in range(100)]
for t in threads:
    t.start()
for t in threads:
    t.join()
thread_time = time.time() - start

# Benchmark process creation
start = time.time()
processes = [Process(target=simple_task) for _ in range(100)]
for p in processes:
    p.start()
for p in processes:
    p.join()
process_time = time.time() - start

print(f"Thread creation time: {thread_time:.3f}s")
print(f"Process creation time: {process_time:.3f}s")
print(f"Process is {process_time/thread_time:.1f}x slower")
```

**Typical output:**

```
Thread creation time: 0.045s
Process creation time: 1.234s
Process is 27.4x slower
```

---

## **Python's Global Interpreter Lock (GIL)**

**Important for Python**: Python has a GIL that allows only one thread to execute Python bytecode at a time.

### **Impact**

```python
import threading
import time

def cpu_bound():
    """CPU-intensive task"""
    total = 0
    for i in range(50_000_000):
        total += i
    return total

# Threads (GIL limits parallelism)
start = time.time()
t1 = threading.Thread(target=cpu_bound)
t2 = threading.Thread(target=cpu_bound)
t1.start()
t2.start()
t1.join()
t2.join()
thread_time = time.time() - start

# Sequential
start = time.time()
cpu_bound()
cpu_bound()
sequential_time = time.time() - start

print(f"Threads: {thread_time:.2f}s")
print(f"Sequential: {sequential_time:.2f}s")
print(f"Speedup: {sequential_time/thread_time:.2f}x")
# Speedup is close to 1x (no benefit from threads!)
```

**For CPU-bound tasks in Python**: Use processes, not threads!

```python
from multiprocessing import Process
import time

def cpu_bound():
    total = 0
    for i in range(50_000_000):
        total += i
    return total

if __name__ == "__main__":
    start = time.time()
    p1 = Process(target=cpu_bound)
    p2 = Process(target=cpu_bound)
    p1.start()
    p2.start()
    p1.join()
    p2.join()
    process_time = time.time() - start
    
    print(f"Processes: {process_time:.2f}s")
    # Actual speedup: ~1.8-2x (true parallelism!)
```

---

## **Summary Table**

|Feature|Process|Thread|
|---|---|---|
|**Memory**|Isolated|Shared|
|**Creation cost**|High|Low|
|**Context switch**|Slow|Fast|
|**Communication**|IPC (complex)|Direct (simple)|
|**CPU-bound**|✅ True parallelism|❌ GIL limits (Python)|
|**I/O-bound**|✅ Works but heavier|✅ Ideal|
|**Crash isolation**|✅ Independent|❌ All crash together|
|**Resource usage**|Heavy|Light|
|**Use case**|Isolation, CPU work, different programs|Shared data, I/O, UI responsiveness|

---

## **Decision Flowchart**

```
Need concurrency?
    ├─ CPU-bound task?
    │   ├─ Python? → Use Processes
    │   └─ Other languages? → Use Threads (can use all cores)
    │
    ├─ I/O-bound task?
    │   └─ Use Threads (or async/await)
    │
    ├─ Need isolation/fault tolerance?
    │   └─ Use Processes
    │
    ├─ Need to share lots of data?
    │   └─ Use Threads
    │
    └─ Running different programs?
        └─ Use Processes
```

---

## **Key Takeaways**

**Processes**:

- Heavy, isolated, independent
- True parallelism for CPU-bound work
- Fault isolation
- Use when: CPU-intensive, need isolation, different programs

**Threads**:

- Light, shared, dependent
- Great for I/O-bound work
- Easy communication
- Use when: I/O-bound, need shared state, UI responsiveness

**Remember**:

- Processes are like separate apartments (isolated)
- Threads are like rooms in an apartment (shared)
- Choose based on your needs: isolation vs. sharing, CPU vs. I/O

## Scheduling
Scheduling is the process by which an Operating System decides which process or thread gets to use the CPU and for how long. It's one of the most critical functions of an OS, directly impacting system performance, responsiveness, and fairness.

---

## **Core Concepts**

### **What is Scheduling?**

The OS has many processes/threads that want CPU time, but only a limited number of CPU cores. The **scheduler** decides:

1. **Which** process/thread runs next
2. **When** to switch between processes/threads
3. **How long** each gets to run

**Goal**: Maximize CPU utilization, throughput, and responsiveness while being fair.

---

## **CPU Burst vs I/O Burst**

Processes alternate between two states:

### **CPU Burst**

Period where the process is actively using the CPU (computation).

```python
# CPU Burst example
total = 0
for i in range(1000000):
    total += i ** 2  # Pure computation - using CPU
```

### **I/O Burst**

Period where the process is waiting for input/output (disk, network, user input).

```python
# I/O Burst example
file = open("data.txt", "r")  # Waiting for disk
data = file.read()            # CPU idle during disk read
```

**Process behavior**:

```
[CPU Burst] → [I/O Burst] → [CPU Burst] → [I/O Burst] → ...
   Compute      Wait for      Compute      Wait for
                  disk                      network
```

### **Process Types**

**CPU-Bound Process**: Long CPU bursts, short I/O bursts

```
████████████████ (CPU) → ▪ (I/O) → ████████████████ (CPU)
Example: Video encoding, scientific calculations
```

**I/O-Bound Process**: Short CPU bursts, long I/O bursts

```
██ (CPU) → ▪▪▪▪▪▪▪▪▪▪ (I/O) → ██ (CPU) → ▪▪▪▪▪▪▪▪▪▪ (I/O)
Example: Text editors, web servers
```

---

## **Scheduling Criteria**

How do we measure if a scheduling algorithm is good?

### **1. CPU Utilization**

Percentage of time CPU is doing useful work (not idle).

- **Goal**: Keep CPU busy (ideally 40-90%)
- **Formula**: (Busy Time / Total Time) × 100%

### **2. Throughput**

Number of processes completed per unit time.

- **Goal**: Maximize completed processes
- **Formula**: Processes Completed / Time Period

### **3. Turnaround Time**

Total time from process submission to completion.

- **Formula**: Completion Time - Arrival Time
- **Goal**: Minimize

```
Process arrives → [Wait] → [Execute] → [Wait] → [Execute] → Completes
                |←────────── Turnaround Time ─────────→|
```

### **4. Waiting Time**

Total time process spends in ready queue waiting for CPU.

- **Formula**: Turnaround Time - Burst Time
- **Goal**: Minimize

### **5. Response Time**

Time from process submission to first response (first CPU allocation).

- **Formula**: First CPU Time - Arrival Time
- **Goal**: Minimize (critical for interactive systems)

```
Process arrives → [Wait] → First CPU allocation
                |←── Response Time ──→|
```

---

## **Scheduling Algorithms**

### **1. First-Come, First-Served (FCFS)**

**Concept**: Process that arrives first gets CPU first. Simple queue.

**Algorithm**:

- Maintain a FIFO queue
- When CPU is free, take the first process from queue
- Run until completion (non-preemptive)

**Example**:

```
Processes:
P1: Arrival=0, Burst=24
P2: Arrival=0, Burst=3
P3: Arrival=0, Burst=3

Timeline:
0                    24  27  30
|──────── P1 ────────|P2|P3|
```

**Calculations**:

```
P1: Waiting=0,  Turnaround=24
P2: Waiting=24, Turnaround=27
P3: Waiting=27, Turnaround=30

Average Waiting Time = (0 + 24 + 27) / 3 = 17
```

**Advantages**:

- Simple to implement
- Fair (first come, first served)
- No starvation

**Disadvantages**:

- **Convoy Effect**: Short processes stuck behind long ones
- Poor average waiting time
- Not suitable for interactive systems

**Convoy Effect Example**:

```
One CPU-bound process (P1: 100s) arrives first
Then 10 I/O-bound processes (P2-P11: 1s each) arrive

Timeline:
|────────────────── P1 (100s) ──────────────────|P2|P3|...|P11|
                                                  All wait 100s!
```

**Implementation**:

```python
def fcfs_scheduling(processes):
    """
    processes: list of (process_id, arrival_time, burst_time)
    """
    # Sort by arrival time
    processes.sort(key=lambda x: x[1])
    
    current_time = 0
    waiting_times = []
    
    for pid, arrival, burst in processes:
        # If CPU is idle, jump to arrival time
        if current_time < arrival:
            current_time = arrival
        
        waiting_time = current_time - arrival
        waiting_times.append(waiting_time)
        
        print(f"P{pid}: Start={current_time}, Wait={waiting_time}")
        
        current_time += burst
    
    avg_waiting = sum(waiting_times) / len(waiting_times)
    print(f"\nAverage Waiting Time: {avg_waiting:.2f}")

# Example
processes = [
    (1, 0, 24),  # P1: arrival=0, burst=24
    (2, 0, 3),   # P2: arrival=0, burst=3
    (3, 0, 3),   # P3: arrival=0, burst=3
]
fcfs_scheduling(processes)
```

**Output**:

```
P1: Start=0, Wait=0
P2: Start=24, Wait=24
P3: Start=27, Wait=27

Average Waiting Time: 17.00
```

---

### **2. Shortest Job First (SJF)**

**Concept**: Execute the process with the shortest CPU burst time first.

**Algorithm**:

- Pick process with minimum burst time
- Run to completion (non-preemptive version)
- Proven to be **optimal** for minimizing average waiting time

**Example**:

```
Processes:
P1: Arrival=0, Burst=6
P2: Arrival=0, Burst=8
P3: Arrival=0, Burst=7
P4: Arrival=0, Burst=3

SJF Order: P4 → P1 → P3 → P2

Timeline:
0   3      9        16           24
|P4|─ P1 ─|── P3 ──|──── P2 ────|
```

**Calculations**:

```
P4: Waiting=0,  Turnaround=3
P1: Waiting=3,  Turnaround=9
P3: Waiting=9,  Turnaround=16
P2: Waiting=16, Turnaround=24

Average Waiting Time = (0 + 3 + 9 + 16) / 4 = 7
Compare to FCFS: (0 + 6 + 14 + 21) / 4 = 10.25
```

**Advantages**:

- **Optimal** average waiting time
- Better than FCFS for short processes

**Disadvantages**:

- **Starvation**: Long processes may never execute (if short processes keep arriving)
- **Prediction problem**: Hard to know burst time in advance
- Not preemptive (can't switch mid-execution)

**Implementation**:

```python
def sjf_scheduling(processes):
    """
    processes: list of (process_id, arrival_time, burst_time)
    """
    processes.sort(key=lambda x: x[2])  # Sort by burst time
    
    current_time = 0
    waiting_times = []
    
    for pid, arrival, burst in processes:
        if current_time < arrival:
            current_time = arrival
        
        waiting_time = current_time - arrival
        waiting_times.append(waiting_time)
        
        print(f"P{pid}: Start={current_time}, Wait={waiting_time}, Burst={burst}")
        
        current_time += burst
    
    avg_waiting = sum(waiting_times) / len(waiting_times)
    print(f"\nAverage Waiting Time: {avg_waiting:.2f}")

processes = [
    (1, 0, 6),
    (2, 0, 8),
    (3, 0, 7),
    (4, 0, 3),
]
sjf_scheduling(processes)
```

---

### **3. Shortest Remaining Time First (SRTF)**

**Concept**: Preemptive version of SJF. Switch to a new process if it has less remaining time than the current process.

**Algorithm**:

- When new process arrives, compare its burst time with remaining time of current process
- If new process is shorter, preempt current process
- Always run process with shortest remaining time

**Example**:

```
Processes:
P1: Arrival=0, Burst=8
P2: Arrival=1, Burst=4
P3: Arrival=2, Burst=9
P4: Arrival=3, Burst=5

Timeline:
0 1    5   10      17        26
|P1|─P2─|P4|──P1──|───P3────|

Time 0: P1 starts (remaining=8)
Time 1: P2 arrives (burst=4 < 7), P1 preempted
Time 5: P2 done, P4 has shortest remaining (5 < 7)
Time 10: P4 done, P1 resumes
Time 17: P1 done, P3 runs
Time 26: P3 done
```

**Advantages**:

- Even better average waiting time than SJF
- Responsive to short processes

**Disadvantages**:

- More context switches (overhead)
- Still has starvation problem
- Still needs to predict burst time

---

### **4. Round Robin (RR)**

**Concept**: Each process gets a small time slice (quantum). After quantum expires, process is preempted and added to end of queue.

**Algorithm**:

- Define time quantum (e.g., 10ms)
- Use circular queue
- Each process runs for quantum or until completion
- If not done, move to end of queue

**Example**:

```
Processes (all arrive at time 0):
P1: Burst=24
P2: Burst=3
P3: Burst=3

Time Quantum = 4

Timeline:
0  4  8  12 16 20    24  27  30
|P1|P2|P3|P1|P1|P1__|P1|P1|P1|
```

**Round-by-round**:

```
Round 1: P1 runs 4 units (20 left), P2 runs 3 units (done), P3 runs 3 units (done)
Round 2: P1 runs 4 units (16 left)
Round 3: P1 runs 4 units (12 left)
Round 4: P1 runs 4 units (8 left)
Round 5: P1 runs 4 units (4 left)
Round 6: P1 runs 4 units (done)
```

**Choosing Time Quantum**:

- **Too large**: Degenerates to FCFS
- **Too small**: Too many context switches (high overhead)
- **Rule of thumb**: 80% of CPU bursts should be shorter than quantum

**Advantages**:

- Fair (every process gets turns)
- No starvation
- Good response time
- **Best for interactive systems** (time-sharing)

**Disadvantages**:

- Higher average turnaround time than SJF
- Context switch overhead
- Performance depends on quantum size

**Implementation**:

```python
from collections import deque

def round_robin_scheduling(processes, quantum):
    """
    processes: list of (process_id, arrival_time, burst_time)
    quantum: time slice
    """
    queue = deque()
    current_time = 0
    completed = []
    remaining = {pid: burst for pid, _, burst in processes}
    waiting_times = {pid: 0 for pid, _, _ in processes}
    
    # Add processes that have arrived
    for pid, arrival, burst in processes:
        if arrival <= current_time:
            queue.append(pid)
    
    while queue or any(r > 0 for r in remaining.values()):
        if not queue:
            # CPU idle, jump to next arrival
            current_time += 1
            for pid, arrival, burst in processes:
                if arrival == current_time and remaining[pid] > 0:
                    queue.append(pid)
            continue
        
        pid = queue.popleft()
        
        # Execute for quantum or remaining time, whichever is less
        exec_time = min(quantum, remaining[pid])
        
        print(f"Time {current_time}: P{pid} executes for {exec_time} units")
        
        current_time += exec_time
        remaining[pid] -= exec_time
        
        # Check for new arrivals during execution
        for p_id, arrival, burst in processes:
            if arrival > current_time - exec_time and arrival <= current_time:
                if remaining[p_id] > 0 and p_id not in queue and p_id != pid:
                    queue.append(p_id)
        
        # If process not done, add back to queue
        if remaining[pid] > 0:
            queue.append(pid)
        else:
            completed.append((pid, current_time))
            print(f"  P{pid} completed at time {current_time}")

processes = [
    (1, 0, 24),
    (2, 0, 3),
    (3, 0, 3),
]
print("Round Robin Scheduling (Quantum = 4)")
round_robin_scheduling(processes, quantum=4)
```

---

### **5. Priority Scheduling**

**Concept**: Each process has a priority. CPU allocated to highest priority process.

**Types**:

- **Non-preemptive**: Once started, runs to completion
- **Preemptive**: Higher priority process can preempt lower priority

**Priority Assignment**:

- Lower number = Higher priority (convention varies)
- Can be internal (CPU burst, memory requirements)
- Can be external (user-defined, importance)

**Example**:

```
Processes:
P1: Arrival=0, Burst=10, Priority=3
P2: Arrival=0, Burst=1,  Priority=1 (highest)
P3: Arrival=0, Burst=2,  Priority=4
P4: Arrival=0, Burst=1,  Priority=5 (lowest)
P5: Arrival=0, Burst=5,  Priority=2

Order: P2 → P5 → P1 → P3 → P4

Timeline:
0 1     6       16  18 19
|P2|─P5─|──P1──|P3|P4|
```

**The Starvation Problem**:

Low-priority processes may **never** execute if high-priority processes keep arriving.

```
High priority processes keep arriving:
P(high) → P(high) → P(high) → P(high) → ...
          
P(low) waiting... waiting... waiting... (STARVED!)
```

**Solution: Aging**

Gradually increase priority of waiting processes.

```python
def priority_scheduling_with_aging(processes):
    """
    processes: list of (pid, arrival, burst, priority)
    Lower priority number = higher priority
    """
    current_time = 0
    waiting_times = {}
    ready_queue = []
    
    while processes or ready_queue:
        # Add arrived processes to ready queue
        arrived = [p for p in processes if p[1] <= current_time]
        for p in arrived:
            ready_queue.append(list(p) + [0])  # Add age=0
            processes.remove(p)
        
        if not ready_queue:
            current_time += 1
            continue
        
        # Age all waiting processes (decrease priority number)
        for p in ready_queue:
            p[4] += 1  # Increase age
            if p[4] >= 5:  # Every 5 time units
                p[3] = max(1, p[3] - 1)  # Increase priority
                p[4] = 0  # Reset age
        
        # Select highest priority (lowest number)
        ready_queue.sort(key=lambda x: x[3])
        pid, arrival, burst, priority, age = ready_queue.pop(0)
        
        print(f"Time {current_time}: P{pid} (priority={priority}) executes")
        current_time += burst
```

**Advantages**:

- Can prioritize important processes
- Flexible (many priority schemes possible)

**Disadvantages**:

- Starvation of low-priority processes
- Difficult to choose priorities

---

### **6. Multilevel Queue Scheduling**

**Concept**: Multiple queues with different priorities. Processes permanently assigned to one queue.

**Common Setup**:

```
┌─────────────────────────────┐
│ System Processes            │ (Highest Priority)
├─────────────────────────────┤
│ Interactive Processes       │
├─────────────────────────────┤
│ Batch Processes             │ (Lowest Priority)
└─────────────────────────────┘
```

**Each queue** can have its own scheduling algorithm:

- System: FCFS
- Interactive: Round Robin (q=20ms)
- Batch: FCFS

**Between queues**: Usually priority-based

- Process from lower queue only runs if higher queues are empty
- Or time-slicing between queues (e.g., 80% to interactive, 20% to batch)

**Example**:

```python
class MultilevelQueue:
    def __init__(self):
        self.system_queue = []     # Priority 0 (highest)
        self.interactive_queue = [] # Priority 1
        self.batch_queue = []      # Priority 2 (lowest)
    
    def add_process(self, process, queue_type):
        if queue_type == "system":
            self.system_queue.append(process)
        elif queue_type == "interactive":
            self.interactive_queue.append(process)
        elif queue_type == "batch":
            self.batch_queue.append(process)
    
    def get_next_process(self):
        # Priority: system > interactive > batch
        if self.system_queue:
            return self.system_queue.pop(0), "system"
        elif self.interactive_queue:
            return self.interactive_queue.pop(0), "interactive"
        elif self.batch_queue:
            return self.batch_queue.pop(0), "batch"
        return None, None
```

**Disadvantages**:

- Inflexible (process stuck in assigned queue)
- Still has starvation problem

---

### **7. Multilevel Feedback Queue (MLFQ)**

**Concept**: Like Multilevel Queue, but processes can **move between queues** based on behavior.

**Typical Setup**:

```
Queue 0 (RR q=8):  [New processes start here]
       ↓ (if uses full quantum)
Queue 1 (RR q=16): [Processes that need more CPU]
       ↓ (if uses full quantum)
Queue 2 (FCFS):    [Long CPU-bound processes]
```

**Rules**:

1. New process enters highest priority queue
2. If process uses entire quantum → demote to lower queue
3. If process blocks for I/O → promote to higher queue (or stay)
4. Aging: Processes waiting too long get promoted

**Behavior**:

- **I/O-bound** processes stay in high-priority queues (short bursts, frequent blocking)
- **CPU-bound** processes sink to low-priority queues (long bursts)

**Example Timeline**:

```
Process P1 (I/O-bound):
Q0: Run 3ms → Block for I/O → Return to Q0
Q0: Run 4ms → Block for I/O → Return to Q0
(Stays in high priority - good responsiveness)

Process P2 (CPU-bound):
Q0: Run 8ms (full quantum) → Move to Q1
Q1: Run 16ms (full quantum) → Move to Q2
Q2: Run until done
(Sinks to low priority - throughput oriented)
```

**Implementation Sketch**:

```python
class MLFQ:
    def __init__(self):
        self.queues = [
            {"processes": [], "quantum": 8, "algorithm": "RR"},
            {"processes": [], "quantum": 16, "algorithm": "RR"},
            {"processes": [], "quantum": float('inf'), "algorithm": "FCFS"},
        ]
    
    def add_new_process(self, process):
        """New process enters highest priority queue"""
        self.queues[0]["processes"].append(process)
    
    def execute(self, process, queue_level):
        quantum = self.queues[queue_level]["quantum"]
        
        if process.remaining_time <= quantum:
            # Process completes or blocks
            if process.blocked_for_io:
                # I/O bound - keep in same or higher queue
                self.queues[max(0, queue_level - 1)]["processes"].append(process)
            # else: process done
        else:
            # Used full quantum - demote
            process.remaining_time -= quantum
            next_queue = min(queue_level + 1, len(self.queues) - 1)
            self.queues[next_queue]["processes"].append(process)
```

**Advantages**:

- Adapts to process behavior automatically
- Good response time for interactive processes
- Good throughput for CPU-bound processes
- No need to predict burst time

**Disadvantages**:

- Complex to implement
- Can still have starvation (mitigated by aging)

**Real-world usage**: Most modern OS use variants of MLFQ (Windows, Linux, macOS).

---

## **Real-Time Scheduling**

For systems with **deadlines** (embedded systems, robotics, multimedia).

### **Types of Real-Time Systems**

**Hard Real-Time**: Missing deadline is catastrophic

- Examples: Airbag system, nuclear reactor, pacemaker
- Guarantee: Tasks **must** complete by deadline

**Soft Real-Time**: Missing deadline is undesirable but tolerable

- Examples: Video streaming, audio playback
- Goal: Tasks **should** complete by deadline

### **Rate Monotonic Scheduling (RMS)**

**Concept**: Static priority based on period. Shorter period = Higher priority.

**Example**:

```
Task P1: Period=50ms, Execution=20ms
Task P2: Period=100ms, Execution=40ms

P1 has higher priority (shorter period)

Timeline:
0    20   50   70   100  120  150
|─P1─|─P2─|─P1─|─P2─|─P1─|─P2─|...
```

### **Earliest Deadline First (EDF)**

**Concept**: Dynamic priority based on deadline. Earlier deadline = Higher priority.

**Example**:

```
Time 0:
  P1: Deadline=50, Execution=25
  P2: Deadline=80, Execution=30

P1 runs first (earlier deadline)

Time 25:
  P2: Deadline=80, Execution=30 (remaining)
  P3: Deadline=60, Execution=20 (just arrived)

P3 runs first (deadline 60 < 80)
```

---

## **Scheduling in Modern Operating Systems**

### **Linux: Completely Fair Scheduler (CFS)**

**Concept**: Give each process a "fair" share of CPU based on virtual runtime.

**How it works**:

1. Each process has `vruntime` (virtual runtime)
2. Pick process with **smallest** vruntime
3. After running, increase its vruntime
4. Uses **red-black tree** for O(log n) selection

```python
class CFS:
    def __init__(self):
        self.processes = {}  # pid → vruntime
        self.min_granularity = 1  # minimum time slice
    
    def pick_next(self):
        """Select process with minimum vruntime"""
        if not self.processes:
            return None
        return min(self.processes, key=self.processes.get)
    
    def update_vruntime(self, pid, actual_runtime):
        """Update after execution"""
        # Actual implementation also considers nice values (priorities)
        self.processes[pid] += actual_runtime
```

**Nice Values**: -20 (highest priority) to +19 (lowest priority)

- Lower nice → gets more CPU time
- Affects vruntime calculation

### **Windows: Multilevel Feedback Queue**

Windows uses 32 priority levels (0-31):

- **0-15**: Dynamic priorities (can change)
- **16-31**: Real-time priorities (fixed)

**Quantum**: Varies by Windows version and system type

- Client systems: Longer quanta for foreground apps
- Server systems: Equal quanta

**Priority Boosting**:

- I/O completion → temporary priority boost
- Foreground window → priority boost
- After starvation → priority boost

---

## **Scheduling Metrics Comparison**

Let's compare algorithms with a concrete example:

```
Processes:
P1: Arrival=0, Burst=8
P2: Arrival=1, Burst=4
P3: Arrival=2, Burst=9
P4: Arrival=3, Burst=5
```

### **FCFS**

```
Timeline: P1(8) → P2(4) → P3(9) → P4(5)
Waiting Times: P1=0, P2=7, P3=9, P4=15
Average Waiting: 7.75
```

### **SJF** (Non-preemptive)

```
Timeline: P1(8) → P2(4) → P4(5) → P3(9)
Waiting Times: P1=0, P2=7, P4=9, P3=12
Average Waiting: 7.00
```

### **SRTF** (Preemptive SJF)

```
Timeline: P1(1) → P2(4) → P4(5) → P1(7) → P3(9)
Waiting Times: P1=8, P2=0, P4=2, P3=13
Average Waiting: 5.75
```

### **Round Robin** (q=4)

```
Timeline: P1(4) → P2(4) → P1(4) → P3(4) → P4(4) → P3(4) → P4(1) → P3(1)
Waiting Times: P1=4, P2=1, P3=11, P4=8
Average Waiting: 6.00
```

**Summary**:

|Algorithm|Avg Wait|Response|Fairness|Starvation|
|---|---|---|---|---|
|FCFS|Poor|Poor|Good|No|
|SJF|Best|Poor|Poor|Yes|
|SRTF|Better|Good|Poor|Yes|
|RR|Moderate|Best|Best|No|

---

## **Key Takeaways**

**Scheduling Algorithms**:

- **FCFS**: Simple, fair, but convoy effect
- **SJF/SRTF**: Optimal waiting time, but starvation
- **Round Robin**: Fair, good response, best for interactive
- **Priority**: Flexible, but starvation (use aging)
- **MLFQ**: Adaptive, used in real systems

**Trade-offs**:

- Throughput vs Response Time
- Fairness vs Efficiency
- Simplicity vs Performance

**Real Systems**:

- Use complex, adaptive algorithms (MLFQ variants)
- Combine multiple strategies
- Adjust based on workload

**Choose based on**:

- System type (interactive, batch, real-time)
- Workload (CPU-bound, I/O-bound)
- Goals (response time, throughput, fairness)

## Context switching

Context switching is the process of saving the state of a currently running process or thread and loading the state of the next one to run. It's a fundamental operation in multitasking operating systems, but it comes with a cost.

---

## **What is Context Switching?**

**Definition**: The mechanism by which the CPU switches from executing one process/thread to another.

**Analogy**: Imagine you're reading a book, then someone asks you to work on a different task:

1. **Save context**: Mark your page, remember where you were
2. **Switch**: Put book away, get new materials
3. **Load context**: Open new task, remember its state
4. **Resume**: Continue from where you left off

Similarly, the CPU must:

1. Save the current process state
2. Load the next process state
3. Resume execution

---

## **Why Context Switching Happens**

### **1. Time-Sharing (Multitasking)**

CPU gives each process a time slice (quantum):

```
Process A runs → Time quantum expires → Context switch → Process B runs
```

### **2. I/O Operations**

Process blocks waiting for I/O:

```
Process A runs → Requests disk read → Blocks → Context switch → Process B runs
```

### **3. Interrupts**

Hardware or software interrupt:

```
Process A runs → Timer interrupt → Context switch → Kernel code runs
```

### **4. Priority Changes**

Higher priority process becomes ready:

```
Process A runs → Higher priority Process B arrives → Context switch → Process B runs
```

### **5. System Calls**

Process makes system call requiring kernel mode:

```
User mode (Process A) → System call → Context switch → Kernel mode → Return → Process A
```

---

## **The Context Switching Process**

### **Step-by-Step Breakdown**

```
┌─────────────────────────────────────────────────┐
│            CONTEXT SWITCH STEPS                 │
└─────────────────────────────────────────────────┘

1. SAVE CURRENT PROCESS STATE
   ├─ Save CPU registers (PC, SP, etc.)
   ├─ Save process state (running → ready/waiting)
   ├─ Save memory management info
   └─ Update Process Control Block (PCB)

2. SELECT NEXT PROCESS
   ├─ Run scheduler algorithm
   ├─ Choose next process from ready queue
   └─ Mark as running

3. LOAD NEXT PROCESS STATE
   ├─ Load CPU registers from PCB
   ├─ Load memory management info
   ├─ Switch page tables (if different address space)
   └─ Update hardware state

4. RESUME EXECUTION
   └─ Jump to saved Program Counter (PC)
```

---

## **What Gets Saved/Restored**

### **Process Control Block (PCB)**

Each process has a PCB containing all state information:

```python
class ProcessControlBlock:
    def __init__(self, pid):
        # Process identification
        self.pid = pid
        self.parent_pid = None
        
        # Process state
        self.state = "NEW"  # NEW, READY, RUNNING, WAITING, TERMINATED
        
        # CPU registers (saved during context switch)
        self.program_counter = 0  # Next instruction address
        self.stack_pointer = 0     # Top of stack
        self.registers = [0] * 16  # General-purpose registers
        self.status_register = 0   # CPU flags
        
        # Memory management
        self.page_table_base = None
        self.memory_limits = {"base": 0, "limit": 0}
        
        # Scheduling information
        self.priority = 0
        self.cpu_time_used = 0
        self.time_quantum = 0
        
        # I/O status
        self.open_files = []
        self.pending_io = []
        
        # Accounting
        self.start_time = 0
        self.total_cpu_time = 0
```

### **Minimal Context (Threads)**

Threads within the same process share most resources, so less needs to be saved:

```python
class ThreadControlBlock:
    def __init__(self, tid):
        # Thread identification
        self.tid = tid
        
        # CPU registers (ONLY these need saving)
        self.program_counter = 0
        self.stack_pointer = 0
        self.registers = [0] * 16
        
        # Thread-specific data
        self.thread_local_storage = {}
        
        # State
        self.state = "READY"
        
        # Shared with other threads (NO need to save):
        # - Memory space
        # - Open files
        # - Global variables
        # - Heap
```

---

## **Context Switch Overhead**

### **Direct Costs**

**1. CPU Cycles for Switching**

- Save registers: ~50-100 cycles
- Update PCB: ~50 cycles
- Run scheduler: ~100-1000 cycles
- Load registers: ~50-100 cycles
- **Total: ~250-1250 cycles** per switch

**2. Cache Effects (The Hidden Cost)**

- **Much more significant than direct costs!**
- Old process data in cache is now useless
- New process data must be loaded
- **Cold cache** = slower memory access

```
Before switch (Process A):
┌─────────────┐
│ CPU Cache   │  Process A's data (hot)
├─────────────┤
│  var1: 42   │  ← Fast access (cache hit)
│  var2: 100  │  ← Fast access (cache hit)
│  var3: 55   │  ← Fast access (cache hit)
└─────────────┘

After switch (Process B):
┌─────────────┐
│ CPU Cache   │  Process A's data (now useless)
├─────────────┤
│  var1: 42   │  ← Cache miss! Must fetch from RAM
│  var2: 100  │  ← Cache miss! Must fetch from RAM
│  var3: 55   │  ← Cache miss! Must fetch from RAM
└─────────────┘

Cache miss penalty: ~100-200 cycles vs ~1 cycle for hit
Multiple cache misses: 1000s of cycles wasted!
```

**3. TLB Effects**

Translation Lookaside Buffer (TLB) caches virtual→physical address mappings:

```
Before switch:
┌──────────────┐
│     TLB      │  Process A's address mappings
├──────────────┤
│ 0x1000→0x5000│  ← Fast translation
│ 0x2000→0x6000│  ← Fast translation
└──────────────┘

After switch to Process B (different address space):
┌──────────────┐
│     TLB      │  Must be flushed!
├──────────────┤
│   (empty)    │  ← TLB miss! Slow page table walk
│   (empty)    │  ← TLB miss! Slow page table walk
└──────────────┘
```

**TLB flush for processes**: Required (different address spaces) **TLB flush for threads**: NOT required (same address space) ✓

### **Total Cost Breakdown**

```
Context Switch Cost = Direct Cost + Indirect Cost

Direct Cost:
  Register save/restore:    200 cycles
  Scheduler overhead:       500 cycles
  PCB updates:              100 cycles
  Total Direct:            ~800 cycles

Indirect Cost (Cache/TLB):
  Cache misses:          10,000 cycles
  TLB misses:            5,000 cycles
  Memory pipeline stalls: 2,000 cycles
  Total Indirect:       ~17,000 cycles

TOTAL: ~17,800 cycles (≈ 5-10 microseconds on modern CPU)
```

**Key insight**: Indirect costs (cache pollution) are **10-50× larger** than direct costs!

---

## **Process vs Thread Context Switch**

### **Process Context Switch**

**What must be done**:

1. Save CPU registers
2. Save process state
3. Switch memory address space (page tables)
4. Flush TLB
5. Flush cache (or tag with process ID)
6. Load new process state
7. Load new CPU registers

**Cost**: High (~5-10 microseconds)

```python
def process_context_switch(current_process, next_process):
    """Pseudo-code for process context switch"""
    
    # 1. Save current process state
    current_process.pcb.program_counter = cpu.pc
    current_process.pcb.stack_pointer = cpu.sp
    current_process.pcb.registers = cpu.registers.copy()
    current_process.state = "READY"
    
    # 2. Update scheduler data
    scheduler.update_statistics(current_process)
    
    # 3. Switch address space (EXPENSIVE!)
    mmu.switch_page_table(next_process.page_table)
    tlb.flush()  # ← Major performance hit!
    
    # 4. Load next process state
    cpu.pc = next_process.pcb.program_counter
    cpu.sp = next_process.pcb.stack_pointer
    cpu.registers = next_process.pcb.registers.copy()
    next_process.state = "RUNNING"
    
    # 5. Resume execution
    # (CPU continues from new PC)
```

### **Thread Context Switch**

**What must be done**:

1. Save CPU registers
2. Save thread state
3. Load new thread state
4. Load new CPU registers

**No need to**:

- Switch page tables (same address space) ✓
- Flush TLB ✓
- Flush cache ✓

**Cost**: Low (~1-2 microseconds)

```python
def thread_context_switch(current_thread, next_thread):
    """Pseudo-code for thread context switch"""
    
    # 1. Save current thread state
    current_thread.tcb.program_counter = cpu.pc
    current_thread.tcb.stack_pointer = cpu.sp
    current_thread.tcb.registers = cpu.registers.copy()
    current_thread.state = "READY"
    
    # 2. Load next thread state (SAME process)
    # NO page table switch needed! ✓
    # NO TLB flush needed! ✓
    
    cpu.pc = next_thread.tcb.program_counter
    cpu.sp = next_thread.tcb.stack_pointer
    cpu.registers = next_thread.tcb.registers.copy()
    next_thread.state = "RUNNING"
    
    # 3. Resume execution
    # (CPU continues from new PC)
```

### **Performance Comparison**

```python
import time

def measure_context_switch_overhead():
    """Measure actual context switch performance"""
    
    # Process context switch simulation
    print("Process Context Switch:")
    start = time.perf_counter()
    
    # Simulate expensive operations
    simulate_tlb_flush()        # ~1000 cycles
    simulate_cache_flush()      # ~5000 cycles
    simulate_page_table_switch() # ~500 cycles
    simulate_register_save()    # ~100 cycles
    
    process_time = time.perf_counter() - start
    
    # Thread context switch simulation
    print("\nThread Context Switch:")
    start = time.perf_counter()
    
    # Only register save/restore needed
    simulate_register_save()    # ~100 cycles
    
    thread_time = time.perf_counter() - start
    
    print(f"\nProcess switch: {process_time*1e6:.2f} µs")
    print(f"Thread switch: {thread_time*1e6:.2f} µs")
    print(f"Process is {process_time/thread_time:.1f}x slower")
```

**Typical results**:

```
Process switch: 8.5 µs
Thread switch: 1.2 µs
Process is 7.1x slower
```

---

## **Reducing Context Switch Overhead**

### **1. Increase Time Quantum**

Longer time slices = fewer context switches:

```python
# Bad: Frequent context switches
time_quantum = 1  # ms
# Every 1ms: switch!
# Overhead: High (lots of switching)

# Better: Reasonable quantum
time_quantum = 10  # ms
# Every 10ms: switch
# Overhead: Medium (balanced)

# Problem with too long:
time_quantum = 1000  # ms
# Every 1 second: switch
# Overhead: Low, but response time suffers!
```

**Rule of thumb**: 80% of CPU bursts should be shorter than quantum.

### **2. Use Threads Instead of Processes**

When sharing state is acceptable:

```python
# Expensive: Multiple processes
import multiprocessing

def worker(data):
    # Each worker is a separate process
    # High context switch cost
    process_data(data)

if __name__ == "__main__":
    with multiprocessing.Pool(4) as pool:
        pool.map(worker, data_items)

# Cheaper: Multiple threads
import threading

def worker(data):
    # Each worker is a thread
    # Low context switch cost
    process_data(data)

threads = [threading.Thread(target=worker, args=(d,)) for d in data_items]
for t in threads:
    t.start()
for t in threads:
    t.join()
```

### **3. CPU Affinity**

Keep process on same CPU to preserve cache:

```python
import os

def set_cpu_affinity(pid, cpu_cores):
    """Pin process to specific CPU cores"""
    # Linux-specific
    os.sched_setaffinity(pid, cpu_cores)

# Pin to CPU 0 and 1
set_cpu_affinity(os.getpid(), {0, 1})

# Benefits:
# - Process data stays in that CPU's cache
# - No cache cold-start after switch
# - Better cache hit rate
```

### **4. Minimize Process Count**

Don't create unnecessary processes/threads:

```python
# Bad: One process per request
def handle_request(request):
    import subprocess
    subprocess.run(["python", "handler.py", request])
    # New process for EVERY request!
    # Context switch overhead is massive!

# Good: Thread pool
from concurrent.futures import ThreadPoolExecutor

executor = ThreadPoolExecutor(max_workers=10)

def handle_request(request):
    executor.submit(process_request, request)
    # Reuse existing threads
    # Much lower overhead
```

### **5. Batch Operations**

Group operations to reduce switches:

```python
# Bad: Switch for every item
for item in items:
    process_item(item)
    # Potential context switch after each item

# Good: Batch processing
batch_size = 100
for i in range(0, len(items), batch_size):
    batch = items[i:i+batch_size]
    process_batch(batch)
    # Fewer potential context switches
```

### **6. Use Async I/O**

Avoid blocking (and context switches) during I/O:

```python
# Synchronous: Blocks and causes context switch
import requests

def fetch_url(url):
    response = requests.get(url)  # Blocks, context switch!
    return response.text

# Async: No blocking, no unnecessary context switch
import asyncio
import aiohttp

async def fetch_url(url):
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            return await response.text()  # Yields control, but same thread!
```

---

## **Measuring Context Switches**

### **Linux Tools**

**1. `vmstat` - System-wide context switches**

```bash
$ vmstat 1
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 2  0      0 482136 146804 1876532    0    0     0     4   89  156  1  0 99  0  0
 1  0      0 481888 146804 1876532    0    0     0     0  102  189  1  0 99  0  0
                                                             ^^^
                                                        Context switches per second
```

**2. `/proc/[pid]/status` - Per-process context switches**

```bash
$ cat /proc/1234/status | grep ctxt
voluntary_ctxt_switches:    1523
nonvoluntary_ctxt_switches: 245
```

- **Voluntary**: Process yielded CPU (I/O, sleep)
- **Involuntary**: Process was preempted (quantum expired)

**3. `perf` - Detailed profiling**

```bash
$ perf stat -e context-switches,cpu-migrations ./my_program

 Performance counter stats for './my_program':

           12,456      context-switches
              234      cpu-migrations

       2.502834567 seconds time elapsed
```

### **Python Monitoring**

```python
import os
import time

def get_context_switches(pid):
    """Get context switch count for a process"""
    try:
        with open(f"/proc/{pid}/status") as f:
            lines = f.readlines()
            for line in lines:
                if "voluntary_ctxt_switches" in line:
                    voluntary = int(line.split()[1])
                elif "nonvoluntary_ctxt_switches" in line:
                    nonvoluntary = int(line.split()[1])
        return voluntary, nonvoluntary
    except:
        return None, None

# Monitor current process
pid = os.getpid()

print("Monitoring context switches...")
prev_vol, prev_nonvol = get_context_switches(pid)

time.sleep(5)

curr_vol, curr_nonvol = get_context_switches(pid)

print(f"Voluntary: {curr_vol - prev_vol}")
print(f"Nonvoluntary: {curr_nonvol - prev_nonvol}")
print(f"Total: {(curr_vol - prev_vol) + (curr_nonvol - prev_nonvol)}")
```

---

## **Context Switch Scenarios**

### **Scenario 1: Time Quantum Expiration**

```
Process A (CPU-bound, long computation)

Timeline:
0ms      10ms     20ms     30ms
|───A───|───B───|───A───|───B───|
        ^       ^       ^
     Quantum  Quantum Quantum
     expired  expired expired
     
Each switch: ~5µs overhead
In 30ms: 3 switches = 15µs wasted (0.05% overhead - acceptable)
```

### **Scenario 2: I/O Operation**

```
Process A (I/O-bound, frequent I/O)

Timeline:
0ms    2ms  3ms   5ms  6ms   8ms
|─A─|wait|─A─|wait|─A─|wait|
    ^       ^       ^
    I/O     I/O     I/O
    request request request
    
Each I/O: context switch to another process
3 switches: 15µs overhead
But I/O takes milliseconds, so switching is good (CPU does useful work)
```

### **Scenario 3: Interrupt**

```
Process A running

Timeline:
|───── A ─────|
              ^
           Hardware interrupt (e.g., network packet arrived)
           
Switch to: Interrupt handler (kernel)
Handle: Process packet
Switch back: To A (or scheduler chooses different process)

2 context switches: ~10µs
```

### **Scenario 4: System Call**

```
Process A in user mode

|─── A (user) ───|
                 ^
              System call (e.g., read file)
              
Mode switch: User → Kernel (lighter than process switch)
             (no TLB flush, same address space)
Execute: Kernel code
Return: Back to A (user mode)

Cost: ~1-2µs (much cheaper than full context switch)
```

---

## **Best Practices**

### **1. Design for Minimal Context Switches**

```python
# Bad: Creates thread for every task
def process_items_bad(items):
    threads = []
    for item in items:
        t = threading.Thread(target=process_one, args=(item,))
        t.start()
        threads.append(t)
    for t in threads:
        t.join()
    # Many threads = many context switches

# Good: Use thread pool
from concurrent.futures import ThreadPoolExecutor

def process_items_good(items):
    with ThreadPoolExecutor(max_workers=4) as executor:
        executor.map(process_one, items)
    # Fixed number of threads = predictable context switches
```

### **2. Batch Work**

```python
# Bad: One item at a time
def process_queue():
    while True:
        item = queue.get()  # Might block → context switch
        process(item)

# Good: Batch processing
def process_queue_batched():
    while True:
        batch = []
        for _ in range(100):
            if not queue.empty():
                batch.append(queue.get_nowait())
        if batch:
            process_batch(batch)  # Process many at once
        else:
            time.sleep(0.01)  # Only sleep when truly empty
```

### **3. Use Async for I/O-Bound**

```python
# Synchronous: Blocks thread → context switch
def fetch_urls_sync(urls):
    results = []
    for url in urls:
        response = requests.get(url)  # Blocks!
        results.append(response.text)
    return results

# Async: Cooperative multitasking → fewer context switches
async def fetch_urls_async(urls):
    async with aiohttp.ClientSession() as session:
        tasks = [fetch_one(session, url) for url in urls]
        return await asyncio.gather(*tasks)
    # Single thread, but handles many requests
    # No OS-level context switches between requests
```

### **4. Monitor and Tune**

```python
import psutil
import os

def monitor_context_switches():
    """Monitor process context switch rate"""
    process = psutil.Process(os.getpid())
    
    # Get initial values
    ctx1 = process.num_ctx_switches()
    time.sleep(1)
    ctx2 = process.num_ctx_switches()
    
    voluntary = ctx2.voluntary - ctx1.voluntary
    involuntary = ctx2.involuntary - ctx1.involuntary
    
    print(f"Context switches per second:")
    print(f"  Voluntary: {voluntary}")
    print(f"  Involuntary: {involuntary}")
    
    # High involuntary = process being preempted often
    # Consider: fewer processes, higher priority, CPU affinity
```

---

## **Key Takeaways**

**Context Switch** = Save current state + Load next state

**Costs**:

- **Direct**: Register save/restore (~1µs)
- **Indirect**: Cache pollution, TLB flush (~5-10µs)
- **Total**: 5-10 microseconds per switch

**Process vs Thread**:

- Process: Expensive (TLB flush, cache flush)
- Thread: Cheap (shared address space)
- **Thread switch ~5-10× faster**

**Optimization**:

- Increase time quantum (balanced with response time)
- Use threads over processes when possible
- Use async I/O for I/O-bound tasks
- Set CPU affinity
- Batch operations
- Monitor with `vmstat`, `perf`, `/proc`

**When to worry**:

- Context switches > 10,000/sec per core
- High involuntary switches
- Real-time applications
- High-performance computing

**When not to worry**:

- Moderate context switch rates
- I/O-bound applications (switching is good!)
- Desktop applications

Context switching enables multitasking, but excessive switching wastes CPU cycles. The key is finding the right balance for your workload.

- Memory management
    
- Virtual memory
    
- Deadlocks & race conditions
    
- Blocking vs Non-blocking I/O  
---

## 8️⃣ Computer Networks Theory

- OSI vs TCP/IP
    
- TCP vs UDP
    
- HTTP vs HTTPS
    
- DNS
    
- TLS/SSL
    
- Latency vs Throughput
    
- Connection pooling