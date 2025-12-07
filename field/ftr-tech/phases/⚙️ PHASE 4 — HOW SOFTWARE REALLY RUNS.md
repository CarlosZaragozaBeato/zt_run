# 7️⃣ Operating Systems Theory

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

# ✅ 1. Memory management
Perfect—**Memory Management** is one of the **most important and most asked topics in Operating Systems theory**. I’ll explain it **clearly, step-by-step, with concepts only (no code)**.

---

**Memory Management** is the OS responsibility of:

> **Tracking, allocating, deallocating, protecting, and optimizing the use of RAM among running processes.**

In simple words:

- Who gets **how much memory**
    
- **When** they get it
    
- **Where** it is placed
    
- **When it is removed**
    

---

# ✅ 2. Goals of Memory Management

The OS manages memory to achieve:

|Goal|Meaning|
|---|---|
|✅ Efficiency|Use RAM fully, no waste|
|✅ Protection|One process must not access another’s memory|
|✅ Isolation|Each process behaves as if it has its own memory|
|✅ Fast Access|Programs should run quickly|
|✅ Fairness|All processes get memory fairly|
|✅ Scalability|Support many programs at once|

---

# ✅ 3. Logical vs Physical Address

### 🔹 Logical Address (Virtual Address)

- Generated by the **CPU**
    
- What a program **thinks** is its memory
    

### 🔹 Physical Address

- Actual location in **RAM**
    

### ✅ Address Translation

The OS + hardware (MMU) translate:

```
Logical Address → Physical Address
```

This gives:

- Security ✅
    
- Process isolation ✅
    
- Virtual Memory ✅
    

---

# ✅ 4. Contiguous vs Non-Contiguous Memory Allocation

---

## 1️⃣ Contiguous Memory Allocation

Each process gets **one continuous block** of memory.

### ✅ Advantages:

- Simple
    
- Fast access
    

### ❌ Problems:

- **External Fragmentation**
    
- Difficult to allocate large memory if scattered blocks exist
    

---

## 2️⃣ Non-Contiguous Memory Allocation

Process memory is **split into pieces and placed anywhere**.

✅ Solves fragmentation  
✅ Used in modern OS  
✅ Enables **Paging & Segmentation**

---

# ✅ 5. Fragmentation (VERY IMPORTANT)

---

## 🔴 External Fragmentation

> Free memory exists, but in small separated blocks.

- Happens in **contiguous allocation**
    
- Memory is wasted due to gaps
    

---

## 🔴 Internal Fragmentation

> Memory is allocated but not fully used.

Example:

- You request 18 KB
    
- OS gives 20 KB
    
- 2 KB is wasted inside the block
    

---

|Type|Where Waste Occurs|
|---|---|
|Internal|_Inside_ allocated block|
|External|_Between_ allocated blocks|

---

# ✅ 6. Paging (Core Concept)

Paging is a **non-contiguous memory allocation technique**.

---

### 🔹 How Paging Works

|Concept|Meaning|
|---|---|
|Page|Fixed-size block of process memory|
|Frame|Fixed-size block of physical memory|
|Page Table|Maps pages → frames|

```
Process → Pages → Mapped to → Frames in RAM
```

---

### ✅ Advantages of Paging

- No **external fragmentation**
    
- Efficient memory use
    
- Enables **virtual memory**
    
- Easy swapping
    

---

### ❌ Disadvantages

- Page table overhead
    
- **Internal fragmentation still exists**
    
- Needs hardware support (MMU, TLB)
    

---

# ✅ 7. Segmentation

Segmentation divides memory based on **logical units**, not fixed size.

Examples:

- Code segment
    
- Stack segment
    
- Heap segment
    

---

|Paging|Segmentation|
|---|---|
|Fixed size|Variable size|
|No external fragmentation|Has external fragmentation|
|Internal fragmentation exists|No internal fragmentation|
|Hardware-friendly|Programmer-friendly|

---

# ✅ 8. Virtual Memory (MOST IMPORTANT)

Virtual Memory allows:

> **A program to run even if it is larger than physical RAM.**

It creates the illusion:

> “Each process has unlimited memory.”

---

### 🔹 How It Works

- Only **needed pages** are loaded into RAM
    
- Other pages stay in **secondary storage (disk)**
    
- When a page is needed → OS loads it (**Page Fault**)
    

---

### ✅ Benefits

- Run large programs ✅
    
- More programs at once ✅
    
- Efficient RAM usage ✅
    
- Process isolation ✅
    

---

### ❌ Cost

- Slower than pure RAM
    
- Page faults are expensive operations
    

---

# ✅ 9. Page Fault

Occurs when:

> A process tries to access a page **not currently in RAM**

### OS Actions:

1. Pause process
    
2. Load page from disk
    
3. Update page table
    
4. Resume process
    

---

# ✅ 10. Page Replacement Algorithms

When RAM is full, OS must decide:

> **Which page to remove?**

---

### Common Algorithms:

|Algorithm|Idea|
|---|---|
|FIFO|Remove the oldest page|
|LRU|Remove least recently used|
|Optimal|Remove page used farthest in future (theoretical best)|
|Clock|Efficient version of LRU|

---

### ⚠️ Belady’s Anomaly

> Increasing memory can **increase page faults** (FIFO problem)

---

# ✅ 11. Thrashing

> The system spends **more time swapping pages than executing processes**.

### Causes:

- Too many processes
    
- Too little memory
    
- High page fault rate
    

### Result:

- CPU idle
    
- Disk overloaded
    
- System becomes extremely slow
    

---

# ✅ 12. Swapping

> Entire process is moved between **RAM ↔ Disk**

|Swapping|Paging|
|---|---|
|Whole process|Only pages|
|Slower|Faster|
|Old systems|Modern OS uses paging|

---

# ✅ 13. TLB (Translation Lookaside Buffer)

TLB is a **special fast cache** that stores:

> Recent logical → physical address mappings

### Purpose:

- Avoid slow page table access
    
- Speed up memory access
    

---

# ✅ 14. Memory Protection

OS ensures:

- A process:
    
    - Cannot access kernel memory
        
    - Cannot access other process memory
        

Used via:

- Page tables
    
- Access bits (Read / Write / Execute)
    

---

# ✅ Final High-Level Summary

- **Memory management** ensures efficient, secure, and fast RAM usage
    
- Key techniques:
    
    - Paging ✅
        
    - Segmentation ✅
        
    - Virtual Memory ✅
        
- Major problems:
    
    - Fragmentation
        
    - Page Faults
        
    - Thrashing
        
- Core structures:
    
    - Page Table
        
    - Frames
        
    - TLB
        
- OS balances:
    
    > **Speed vs Space vs Safety**
    

---

# ✅ One-Line Interview Definitions

- **Paging** → Dividing memory into fixed-size pages and frames
    
- **Virtual Memory** → Using disk as an extension of RAM
    
- **Thrashing** → Excessive paging causing system slowdown
    
- **Internal Fragmentation** → Wasted space inside allocated memory
    
- **External Fragmentation** → Wasted gaps between memory blocks
    
- **TLB** → Fast cache for page table entries

# ✅ Virtual memory
**Virtual Memory** is a memory management technique that allows:

> **A process to run even if it is larger than the available physical RAM.**

It creates the **illusion that each process has a large, continuous memory space**, while actually using:

- ✅ RAM (fast)
    
- ✅ Disk / SSD (slow but large)
---
# ✅ Why Virtual Memory Is Needed

Without virtual memory:
- Program size ≤ Physical RAM
- Number of running programs is limited
- RAM gets exhausted quickly
    

With virtual memory:
- ✅ Large programs can run
- ✅ Multiple programs can run together
- ✅ Better RAM utilization
- ✅ Process isolation and protection
    

---

# ✅ Key Idea (Core Principle)

> **Only the required part of a program is loaded into RAM at any time.**  
> The rest stays on disk and is fetched **on demand**.

This is called:  
➡️ **Demand Paging**

---
# ✅ How Virtual Memory Works (Step-by-Step)

Let’s say:

- RAM = 8 GB
- Program needs = 20 GB
    

Steps:

1. Program is divided into **pages**
2. Only some pages are loaded into **RAM (frames)**
3. Remaining pages stay on **disk**
4. CPU generates a **virtual (logical) address**
5. MMU checks the **page table**
6. If page is:
    - ✅ In RAM → Access it
    - ❌ Not in RAM → **Page Fault**

---

# ✅ Page Fault (Critical Concept)

A **page fault** occurs when:

> The process tries to access a page **not currently in RAM**.

### What OS Does:

1. Pauses the process
2. Finds the required page on disk
3. Loads it into a free frame in RAM  
    (or replaces another page)
4. Updates the page table
5. Resumes the process

⚠️ Page fault is **slow** because disk is much slower than RAM.

---

# ✅ Hardware & Data Structures Used

|Component|Role|
|---|---|
|**MMU** (Memory Management Unit)|Converts virtual → physical address|
|**Page Table**|Maps pages to frames|
|**TLB** (Translation Lookaside Buffer)|Fast cache for page table entries|
|**Disk**|Stores unused pages|
|**RAM**|Stores active pages|

---

# ✅ Virtual Address vs Physical Address

|Virtual Address|Physical Address|
|---|---|
|Generated by CPU|Actual RAM location|
|Process sees this|Hardware uses this|
|Large range|Limited by RAM|

---

# ✅ Demand Paging

> Pages are loaded **only when they are needed**, not in advance.

✅ Saves memory  
✅ Faster startup  
❌ More page faults possible initially

---

# ✅ Page Replacement Algorithm (When RAM Is Full)

When RAM is full and a new page must be loaded:

> OS must decide which page to remove.

Common algorithms:

|Algorithm|Idea|
|---|---|
|**FIFO**|Remove oldest page|
|**LRU**|Remove least recently used page|
|**Optimal**|Remove page used farthest in future (theoretical best)|
|**Clock**|Practical LRU approximation|

---

# ✅ Thrashing (Failure State of Virtual Memory)

> Thrashing occurs when the system spends **most of its time swapping pages instead of executing programs.**
### Causes:
- Too many processes
- Too little RAM
- High page fault rate
### Symptoms:
- Very slow system
- High disk usage
- CPU mostly idle
    

---

# ✅ Local vs Global Replacement

|Type|Meaning|
|---|---|
|**Local Replacement**|A process replaces its own pages only|
|**Global Replacement**|A process can replace any process’s page|

---

# ✅ Working Set Concept

> The **working set** is the set of pages a process is actively using at a given time.

If working set fits in RAM → ✅ Smooth execution  
If not → ❌ Thrashing

---

# ✅ Advantages of Virtual Memory

✅ Programs can be larger than RAM  
✅ Better memory utilization  
✅ More programs run simultaneously  
✅ Process isolation & protection  
✅ Less I/O at startup  
✅ Efficient multitasking

---

# ✅ Disadvantages of Virtual Memory

❌ Page faults cause delay  
❌ Disk access is slow  
❌ Complex page table & hardware  
❌ Thrashing risk  
❌ Extra overhead in translation

---

# ✅ Virtual Memory vs Physical Memory

|Virtual Memory|Physical Memory|
|---|---|
|Logical view|Actual RAM|
|Large|Limited|
|Uses disk|Direct RAM only|
|Slower|Faster|
|Illusion of infinity|Hardware constrained|

---

# ✅ Effective Access Time (EAT) – Interview Favorite

Used to measure the **average memory access time**:

```
EAT = (1 - p) × MemoryAccess
    + p × PageFaultTime
```

Where:

- `p` = probability of page fault
    

⚠️ Even **small p** makes EAT very large because disk access is very slow.

---

# ✅ Relationship with Paging

|Paging|Virtual Memory|
|---|---|
|Divides memory into pages|Uses paging + disk|
|Works within RAM|Extends memory using disk|
|Always in RAM|Pages move RAM ↔ Disk|

➡️ **Virtual memory is built on top of paging**

---

# ✅ One-Line Interview Definitions

- **Virtual Memory** → Disk-based extension of RAM that allows large programs to run
- **Page Fault** → Access to a page not in RAM
- **Demand Paging** → Load pages only when needed
- **Thrashing** → Excessive paging causing performance collapse
- **Working Set** → Actively used pages of a process
    

---

# ✅ Final Summary in One Paragraph

> Virtual Memory is an OS technique that uses disk as an extension of RAM to give each process the illusion of large memory. It works using paging, where only required pages are kept in RAM and the rest stay on disk. When a required page is absent, a page fault occurs and the OS loads the page from disk. While virtual memory improves memory utilization and multitasking, excessive page faults lead to thrashing, drastically reducing system performance.

---
# Deadlocks & race conditions
# 1️⃣ Deadlocks

### ✅ Definition:

A **deadlock** occurs when:

> Two or more processes are **waiting indefinitely for resources held by each other**, so none can proceed.

### Example (Simple Analogy):
- Two people, A and B, want to pick up **fork and knife** to eat.
- A has the fork, waiting for knife.
- B has the knife, waiting for fork.
- Neither can eat → deadlock.

---

### 🔹 Necessary Conditions (Coffman’s Conditions)

All **four conditions must occur simultaneously**:
1. **Mutual Exclusion**: Resource cannot be shared.
2. **Hold and Wait**: A process holding a resource is waiting for another.
3. **No Preemption**: Resource cannot be forcibly taken away.
4. **Circular Wait**: Chain of processes waiting for each other forms a loop.
    

---

### 🔹 Deadlock Handling Strategies

| Strategy       | How it Works                                                             |
| -------------- | ------------------------------------------------------------------------ |
| **Prevention** | Break at least one of the four conditions (e.g., disallow circular wait) |
| **Avoidance**  | Use algorithms like **Banker’s Algorithm** to check safe states          |
| **Detection**  | Allow deadlock, but detect cycles in resource allocation graph           |
| **Recovery**   | Kill process or rollback resources to break deadlock                     |

---

### 🔹 Resource Allocation Graph
- Nodes: **Processes (P)** and **Resources (R)**
- Edge P → R: Process requests resource
- Edge R → P: Resource allocated to process
- **Cycle in graph → deadlock**

---

### 🔹 Example of Deadlock in OS

|Process|Resource Held|Resource Waiting|
|---|---|---|
|P1|R1|R2|
|P2|R2|R1|

Cycle → Deadlock occurs.

---

# 2️⃣ Race Conditions

### ✅ Definition:

A **race condition** occurs when:
> **Two or more processes access shared data concurrently, and the final outcome depends on the timing of execution.**

- It is a **timing problem**, not about deadlock.
- Causes **unpredictable behavior and bugs**.
    

---

### 🔹 Example (Bank Account)
- Account balance = 100
- Process A: Withdraw 70
- Process B: Withdraw 50
    

If both read **100 simultaneously**, both think money is enough.
- Both withdraw → balance = -20 → incorrect!

✅ Correct behavior requires **synchronization**.

### 🔹 Causes of Race Conditions
1. **Concurrent Access**: Multiple threads/processes access same memory.
2. **Lack of Synchronization**: No locking mechanism used.
3. **Shared Resources**: Variables, files, hardware devices.
---

### 🔹 Solutions

|Method|How it Works|
|---|---|
|**Mutex / Locks**|Only one process can access critical section at a time|
|**Semaphores**|Signal mechanism to allow controlled access|
|**Monitors**|High-level abstraction to protect shared resources|
|**Atomic Operations**|Make critical operations indivisible|

# 3️⃣ Deadlock vs Race Condition

|Aspect|Deadlock|Race Condition|
|---|---|---|
|Definition|Processes wait forever|Concurrent processes lead to inconsistent results|
|Problem Type|Waiting / blocking|Timing / ordering|
|Outcome|System halt (for affected processes)|Incorrect computation or data|
|Solution|Prevention, avoidance, detection, recovery|Synchronization (mutex, semaphore)|

---

# 4️⃣ Quick Interview Analogies
- **Deadlock** → Traffic jam at intersection, nobody moves.
- **Race Condition** → Two people writing the same file at the same time → corruption.
---

# 5️⃣ Key Takeaways
1. **Deadlocks**: Waiting problem, all 4 conditions required.
2. **Race conditions**: Timing problem, shared resource must be synchronized.
3. **Prevention vs Solution**: Deadlock can be prevented/detected; race conditions are fixed with **locks and atomic operations**.
4. Both are **common in multi-threaded/multi-process systems**, but are conceptually different.
---
# Blocking vs Non-blocking I/O  
# 1️⃣ What Is I/O?

**I/O (Input/Output)** refers to operations where a program communicates with **external devices** like:
- Disk (read/write files)
- Network (send/receive data)
- Keyboard/Mouse
I/O is **slow compared to CPU speed**, so how a program waits for I/O is important.
---

# 2️⃣ Blocking I/O

### ✅ Definition:

> In **blocking I/O**, the process **waits (is blocked) until the I/O operation completes**.
- CPU cannot do anything else while waiting.
- The process is **inactive** until I/O finishes.
---

### 🔹 Example Analogy
- Ordering food at a restaurant:
    - You **wait at the counter until your food is ready** → nothing else happens.
- Your CPU “waits” while disk/network responds.
---

### 🔹 Characteristics:
- Simple to implement ✅
- Predictable behavior ✅
- Wastes CPU during wait ❌
---

### 🔹 Example (Pseudo-code)

```
data = readFile("file.txt")  // CPU waits until read completes
processData(data)
```

- CPU cannot move to next instruction until `readFile` finishes.
---

# 3️⃣ Non-blocking I/O

### ✅ Definition:

> In **non-blocking I/O**, the process **does not wait**.  
> It can continue executing while I/O operation completes in the background.

- I/O either completes immediately or returns **“try again later”**.
- Often used with **polling, callbacks, or async/await**.
---

### 🔹 Example Analogy
- Ordering food at a restaurant:
    - You **order food and go sit at a table**.
    - Waiter calls you when food is ready → meanwhile, you do other tasks.
---

### 🔹 Characteristics:
- Efficient CPU usage ✅
- More complex implementation ❌
- Requires **callbacks, event loops, or interrupts**

---

### 🔹 Example (Pseudo-code)

```
request = readFileNonBlocking("file.txt") // returns immediately
doOtherWork()
data = request.getResultWhenReady()
```

- CPU can do **other work while waiting**.
---

# 4️⃣ Comparison Table

|Aspect|Blocking I/O|Non-blocking I/O|
|---|---|---|
|CPU Usage|Wastes CPU while waiting|CPU can do other tasks|
|Complexity|Simple|More complex (callbacks, polling)|
|Latency|Potentially higher|Can be lower for multiple tasks|
|Use Case|Simple apps, sequential tasks|High-performance servers, async tasks|

---

# 5️⃣ OS Perspective
- **Blocking**:
    - Process goes into **WAITING state**
    - Kernel suspends execution until I/O completes
    
- **Non-blocking**:
    - Process remains **READY/RUNNING**
    - Kernel returns immediately
    - Process must **check status** or receive notification
---

# 6️⃣ Real-World Examples

|Type|Examples|
|---|---|
|Blocking I/O|Reading a local file, simple console input|
|Non-blocking I/O|Node.js async file/network calls, Linux `O_NONBLOCK` sockets, GUI event loops|

---

# 7️⃣ Key Takeaways

1. **Blocking I/O** → CPU waits; simple; sequential; wastes time.
2. **Non-blocking I/O** → CPU continues; efficient; asynchronous; complex.
3. Choice depends on:
    - Task nature (single-threaded vs multi-threaded)
    - Performance requirements
    - Complexity you’re willing to handle
---

## 8️⃣ Computer Networks Theory

# OSI vs TCP/IP
# 1️⃣ Why These Models Exist

Both **OSI** and **TCP/IP** are **networking reference models** that describe:

- How data moves through a network
- What functions each layer performs
- How software, hardware, and protocols communicate
    

They help engineers:

- Understand network operations
- Standardize communication
- Build interoperable systems
    

---

# 2️⃣ OSI Model (7 layers)

OSI = **Open Systems Interconnection Model**  
A theoretical, detailed model with **7 layers**.

![Image](https://cdn2.hubspot.net/hubfs/2954816/The%207%20Layers%20of%20OSI.png?utm_source=chatgpt.com)

![Image](https://www.imperva.com/learn/wp-content/uploads/sites/13/2020/02/OSI-7-layers.jpg.webp?utm_source=chatgpt.com)

### OSI Layers (Top → Bottom)

|#|Layer|Purpose|
|---|---|---|
|7|Application|User-level protocols (HTTP, FTP)|
|6|Presentation|Data formatting, encryption|
|5|Session|Connection management|
|4|Transport|End-to-end communication (TCP/UDP)|
|3|Network|Routing (IP)|
|2|Data Link|MAC addressing, frame control|
|1|Physical|Bits, cables, signals|

---

# 3️⃣ TCP/IP Model (4 layers)

TCP/IP = **Internet Protocol Suite**  
A practical, real-world model.

![Image](https://afteracademy.com/images/what-is-the-tcp-ip-model-and-how-it-works-tcp-ip-model-four-layers-54cb912bbb0e51ff.jpg?utm_source=chatgpt.com)

![Image](https://cheapsslsecurity.com/blog/wp-content/uploads/2022/06/tcp-ip-model-4-layers.png?utm_source=chatgpt.com)

### TCP/IP Layers (Top → Bottom)

|#|Layer|Purpose|
|---|---|---|
|4|Application|HTTP, DNS, FTP, SMTP|
|3|Transport|TCP, UDP|
|2|Internet|IP, ICMP|
|1|Network Access|Ethernet, Wi-Fi, physical part|

---

# 4️⃣ OSI vs TCP/IP Mapping

> TCP/IP **compresses** OSI's 7 layers into **4 layers**.

|OSI Layer|TCP/IP Equivalent|
|---|---|
|7 Application||
|6 Presentation|**Application Layer**|
|5 Session||
|4 Transport|**Transport Layer**|
|3 Network|**Internet Layer**|
|2 Data Link||
|1 Physical|**Network Access Layer**|

---

# 5️⃣ Key Differences (Exam + Interview Ready)

---

## **1. Number of layers**

- OSI → **7 layers**
    
- TCP/IP → **4 layers**
    

---

## **2. Purpose**

- OSI → Theoretical + conceptual
    
- TCP/IP → Practical + used in real networking
    

---

## **3. Control of Internet**

- OSI → Developed by ISO
    
- TCP/IP → Developed by DoD → backbone of the Internet
    

---

## **4. Layer Behavior**

- OSI → Strict separation; more layers
    
- TCP/IP → Layers can overlap in functionality
    

---

## **5. Protocol dependency**

- OSI → Protocol-independent
    
- TCP/IP → Has actual protocols (TCP, IP, HTTP, etc.)
    

---

## **6. Reliability**

- OSI → Transport layer provides reliability
    
- TCP/IP → Transport layer (TCP) provides reliability, but Internet layer (IP) is unreliable
    

---

## **7. Used today**

- OSI → Learning & design
    
- TCP/IP → Implementation & communication
    

---

# 6️⃣ Why OSI Is Still Taught?

Even though we use TCP/IP in real networks, OSI is still very important:

- Clearer, finer-grained conceptual model
    
- Helps understand what happens at each layer
    
- Useful for troubleshooting and network design
    

---

# 7️⃣ Protocols at Each Layer (High Value)

## **OSI**

- **7 Application:** HTTP, FTP, SMTP
    
- **6 Presentation:** SSL/TLS, JPEG, MP3
    
- **5 Session:** NetBIOS, RPC
    
- **4 Transport:** TCP, UDP
    
- **3 Network:** IP, ICMP, ARP
    
- **2 Data Link:** Ethernet, PPP
    
- **1 Physical:** Cables, Wi-Fi radio, bits
    

## **TCP/IP**

- **Application:** HTTP, DNS, SMTP, FTP
    
- **Transport:** TCP, UDP
    
- **Internet:** IP, ICMP, ARP
    
- **Network Access:** Ethernet, Wi-Fi
    

---

# 8️⃣ Typical Exam Question

### ❓ Why doesn't TCP/IP have Presentation and Session layers?

**Answer**:  
Because these functions (encryption, formatting, session management) are either:

- Handled inside **Application Layer protocols** (HTTP, TLS)
    
- Not needed as separate layers in Internet architecture
    

---

# 9️⃣ Summary (One-Paragraph)

The **OSI model** is a 7-layer theoretical framework for understanding network communication, while the **TCP/IP model** is a 4-layer practical implementation used on the Internet. TCP/IP combines OSI’s Application–Presentation–Session layers into one, and Data Link–Physical layers into one. OSI is primarily conceptual for teaching and troubleshooting, while TCP/IP defines actual working protocols (TCP, IP, HTTP) that make real networks function.

---
# TCP vs UDP
# 1️⃣ What Are TCP and UDP?

Both **TCP** and **UDP** are **Transport Layer protocols** (Layer 4):

They are responsible for:

- End-to-end communication
    
- Process-to-process data delivery
    
- Port numbers
    
- Flow control and reliability (mainly TCP)
    

---

# 2️⃣ Core Difference in One Line

> **TCP is reliable and connection-oriented.  
> UDP is fast and connectionless.**

---

# 3️⃣ How Data Flows (Conceptually)

![Image](https://afteracademy.com/images/what-is-a-tcp-3-way-handshake-process-three-way-handshaking-establishing-connection-6a724e77ba96e241.jpg?utm_source=chatgpt.com)

![Image](https://www.cloudflare.com/img/learning/ddos/glossary/user-datagram-protocol-udp/tcp-vs-udp.svg?utm_source=chatgpt.com)

- **TCP** → Establishes a connection first (3-way handshake)
    
- **UDP** → Sends data directly without connection setup
    

---

# 4️⃣ TCP (Transmission Control Protocol)

### 🔹 Definition:

> TCP provides **reliable, ordered, and error-checked delivery** of data.

---

### 🔹 Key Features of TCP

|Feature|Meaning|
|---|---|
|✅ Connection-oriented|Connection is established before data transfer|
|✅ Reliable|Lost data is retransmitted|
|✅ Ordered|Data arrives in correct sequence|
|✅ Error checking|Detects corrupted packets|
|✅ Flow control|Prevents sender from overwhelming receiver|
|✅ Congestion control|Prevents network overload|

---

### 🔹 TCP 3-Way Handshake

1. **SYN** → Client requests connection
    
2. **SYN-ACK** → Server acknowledges
    
3. **ACK** → Connection established
    

Only after this → Data transfer begins.

---

### 🔹 TCP Use Cases

- Web browsing (HTTP/HTTPS)
    
- Email (SMTP, POP3, IMAP)
    
- File transfer (FTP)
    
- SSH
    
- Online banking
    

✅ Used when **accuracy is more important than speed**

---

# 5️⃣ UDP (User Datagram Protocol)

### 🔹 Definition:

> UDP provides **fast but unreliable, connectionless data delivery**.

---

### 🔹 Key Features of UDP

|Feature|Meaning|
|---|---|
|✅ Connectionless|No setup before sending|
|✅ Fast|No handshake, no acknowledgments|
|❌ Not reliable|Packet loss possible|
|❌ No ordering|Packets may arrive out of order|
|❌ No congestion control|Network can get overloaded|

---

### 🔹 UDP Use Cases

- Video streaming (YouTube, Netflix)
    
- Online gaming
    
- Voice calls (VoIP)
    
- Live broadcasts
    
- DNS queries
    

✅ Used when **speed is more important than perfect accuracy**

---

# 6️⃣ Full TCP vs UDP Comparison Table

|Feature|TCP|UDP|
|---|---|---|
|Connection type|Connection-oriented|Connectionless|
|Reliability|✅ Reliable|❌ Unreliable|
|Packet ordering|✅ Maintained|❌ Not guaranteed|
|Speed|Slower|Faster|
|Error control|✅ Yes|❌ No|
|Congestion control|✅ Yes|❌ No|
|Flow control|✅ Yes|❌ No|
|Header size|20–60 bytes|8 bytes|
|Overhead|High|Very low|
|Data loss tolerant|❌ No|✅ Yes|

---

# 7️⃣ Real-Life Analogy

### TCP:

> Sending a registered parcel.  
> You get:

- Tracking
    
- Confirmation
    
- Re-delivery on failure
    

### UDP:

> Shouting information in a crowd.

- Fast
    
- Some people may miss parts
    
- No confirmation
    

---

# 8️⃣ When Should You Use Which?

|Requirement|Choose|
|---|---|
|Accurate data|✅ TCP|
|File transfer|✅ TCP|
|Web pages|✅ TCP|
|Live video/audio|✅ UDP|
|Online games|✅ UDP|
|Fast DNS lookup|✅ UDP|

---

# 9️⃣ Important Interview Traps

### ❓ Is UDP always unreliable?

✅ At **transport level**, yes.  
❗ But reliability can be added at the **application level** (e.g., QUIC).

---

### ❓ Is TCP always slow?

❌ No.  
It is **slower than UDP due to safety checks**, but still extremely fast for reliable data.

---

# 🔟 Exam-Ready One-Line Definitions

- **TCP** → Reliable, ordered, connection-oriented transport protocol
    
- **UDP** → Fast, connectionless, unreliable transport protocol
    
- **Connection-oriented** → Setup required before data transfer
    
- **Connectionless** → Data sent without setup
    

---

# Final Summary (Short & Powerful)

> TCP is a reliable, connection-oriented protocol that guarantees correct and ordered delivery of data, making it ideal for web, email, and file transfer. UDP is a fast, connectionless protocol that does not guarantee delivery or order, making it ideal for real-time applications like video streaming and online gaming where speed matters more than accuracy.

# HTTP vs HTTPS
# 1️⃣ What Is HTTP?

**HTTP (HyperText Transfer Protocol)** is the foundation of data communication on the web.

> It defines how a **client (browser)** and a **server** exchange requests and responses.

### Key Points:

- ❌ **Not secure**
    
- ❌ Data is sent in **plain text**
    
- ✅ Fast and lightweight
    
- ✅ Uses **TCP**
    
- ✅ Default port: **80**
    

---

# 2️⃣ What Is HTTPS?

**HTTPS (HyperText Transfer Protocol Secure)** is the **secure version of HTTP**.

> It uses **HTTP + SSL/TLS encryption** to protect data.

### Key Points:

- ✅ **Secure & encrypted**
    
- ✅ Protects from hackers
    
- ✅ Ensures data integrity
    
- ✅ Provides authentication (real website verification)
    
- ✅ Default port: **443**
    

---

# 3️⃣ Core Difference in One Line

> **HTTP is not secure.  
> HTTPS is HTTP with encryption and identity verification.**

---

# 4️⃣ How HTTPS Works (High-Level)

![Image](https://cf-assets.www.cloudflare.com/slt3lc6tev37/5aYOr5erfyNBq20X5djTco/3c859532c91f25d961b2884bf521c1eb/tls-ssl-handshake.png?utm_source=chatgpt.com)

![Image](https://www.researchgate.net/profile/Wazen-Shbair/publication/298065605/figure/fig1/AS:357056767905792@1462140375566/TLS-handshake-protocol.png)

When you visit an HTTPS site:

1. Browser requests secure connection
    
2. Server sends **SSL certificate**
    
3. Browser verifies the certificate
    
4. Encryption keys are exchanged
    
5. Secure communication starts
    

✅ After this, **all data is encrypted**

---

# 5️⃣ What Is SSL/TLS?

- **SSL (Secure Sockets Layer)** → Old encryption standard
    
- **TLS (Transport Layer Security)** → Modern replacement of SSL
    

Both provide:

- 🔐 Encryption
    
- ✅ Authentication
    
- ✅ Data integrity
    

---

# 6️⃣ HTTP vs HTTPS Comparison Table

|Feature|HTTP|HTTPS|
|---|---|---|
|Security|❌ Not secure|✅ Secure|
|Encryption|❌ None|✅ Encrypted with SSL/TLS|
|Data Visibility|Anyone can read|Encrypted, unreadable|
|Protection from hackers|❌ No|✅ Yes|
|Port|80|443|
|SSL Certificate|❌ Not required|✅ Required|
|SEO Ranking|❌ Lower|✅ Better|
|Performance|Slightly faster|Slightly slower (encryption overhead)|
|Trust in browser|❌ “Not secure”|✅ Lock symbol 🔒|

---

# 7️⃣ What Does HTTPS Protect Against?

✅ **Man-in-the-Middle (MITM) Attacks**  
✅ **Data theft (passwords, credit cards)**  
✅ **Website spoofing**  
✅ **Data tampering**

---

# 8️⃣ What HTTP Is Vulnerable To

- Packet sniffing
    
- Session hijacking
    
- Data modification
    
- Password theft
    
- Fake websites
    

---

# 9️⃣ Real-World Use Cases

|Use Case|Protocol|
|---|---|
|Bank websites|✅ HTTPS|
|Online shopping|✅ HTTPS|
|Login pages|✅ HTTPS|
|Old static websites|⚠️ HTTP (rare now)|
|APIs in production|✅ HTTPS|

🔴 Today, **almost everything uses HTTPS by default**.

---

# 🔟 Are HTTP and HTTPS Different Protocols?

✅ Yes.

- HTTP → Application layer protocol
    
- HTTPS → HTTP running over:
    
    ```
    TCP → SSL/TLS → HTTP
    ```
    

---

# 1️⃣1️⃣ Does HTTPS Use TCP or UDP?

✅ HTTPS uses **TCP**, just like HTTP.  
(Except newer protocols like **HTTP/3 use QUIC over UDP**)

---

# 1️⃣2️⃣ Interview-Ready One-Line Definitions

- **HTTP** → A stateless, plain-text web communication protocol
    
- **HTTPS** → A secure version of HTTP using SSL/TLS encryption
    
- **SSL/TLS** → Cryptographic protocols for secure data transmission
    
- **Port 80** → HTTP
    
- **Port 443** → HTTPS
    

---

# Final Summary (One Powerful Paragraph)

> HTTP is a basic web communication protocol that transfers data in plain text, making it insecure and vulnerable to attacks. HTTPS is the secure version of HTTP that uses SSL/TLS encryption to protect data, authenticate websites, and ensure data integrity. HTTPS is essential for all modern websites, especially those involving logins, payments, and private user data.

---

# DNS

# 1️⃣ What Is DNS?

**DNS (Domain Name System)** is a system that:

> **Translates human-readable domain names into machine-readable IP addresses.**

### Example:

```
www.google.com  →  142.250.190.14
```

Humans remember **names** ✅  
Computers communicate using **IP addresses** ✅  
DNS connects the two.

---

# 2️⃣ Why DNS Is Needed

Without DNS:

- You would have to remember IP addresses like:
    
    ```
    142.250.190.14 instead of google.com
    ```
    

With DNS:

- You just type:
    
    ```
    www.google.com
    ```
    

✅ Easy  
✅ Scalable  
✅ Global

---

# 3️⃣ Where DNS Works in Networking

DNS works at the:

- **Application Layer**
    
- Uses:
    
    - ✅ **UDP (port 53)** for normal queries
        
    - ✅ **TCP (port 53)** for large responses & zone transfers
        

---

# 4️⃣ DNS Components (Who’s Involved?)

|Component|Role|
|---|---|
|**Client (Browser/User)**|Requests IP for a domain|
|**Recursive Resolver**|Finds the answer on your behalf|
|**Root Server**|Knows where TLD servers are|
|**TLD Server**|Knows where domain servers are (.com, .org)|
|**Authoritative Server**|Final source of truth for the domain|
|**Cache**|Stores recent DNS results for speed|

---

# 5️⃣ How DNS Works (Step-by-Step Resolution)

Let’s say you type:

```
www.example.com
```

![Image](https://www.tcpipguide.com/free/diagrams/dnsresolution.png?utm_source=chatgpt.com)

![Image](https://www.indusface.com/wp-content/uploads/2024/10/DNS-lookup-process-.png?utm_source=chatgpt.com)

### Step-by-Step Flow:

1. **Browser Cache Check**
    
    - If IP already known → ✅ Use it
        
2. **OS Cache Check**
    
    - If not found → ask resolver
        
3. **Recursive Resolver (ISP DNS)**
    
    - If not cached → continues lookup
        
4. **Root DNS Server**
    
    - Says: “Ask the .com server”
        
5. **TLD (.com) Server**
    
    - Says: “Ask example.com’s server”
        
6. **Authoritative Name Server**
    
    - Gives actual IP address
        
7. **Resolver Caches the Result**
    
8. **Browser Connects to the IP**
    

✅ All this happens in **milliseconds**

---

# 6️⃣ Important DNS Record Types

DNS stores information using **records**.

|Record|Purpose|Example|
|---|---|---|
|**A**|Maps domain → IPv4|google.com → 142.x.x.x|
|**AAAA**|Domain → IPv6|google.com → IPv6|
|**CNAME**|Alias to another name|www → google.com|
|**MX**|Mail server|gmail mail routing|
|**NS**|Name servers|DNS authority|
|**TXT**|Verification, security|SPF, DKIM|

---

# 7️⃣ Caching in DNS (Why It’s Fast)

DNS uses **caching at multiple levels**:

- Browser cache
    
- OS cache
    
- Router cache
    
- ISP DNS cache
    

Each record has:

> **TTL (Time To Live)** → How long it stays cached

✅ Reduces network traffic  
✅ Speeds up browsing  
✅ Improves reliability

---

# 8️⃣ Is DNS Secure?

❌ **Basic DNS is NOT encrypted**

- DNS queries are sent in **plain text**
    

So it is vulnerable to:

- DNS spoofing
    
- Cache poisoning
    
- Man-in-the-middle attacks
    

---

# 9️⃣ Secure DNS Solutions

|Technology|Purpose|
|---|---|
|**DNSSEC**|Prevents fake DNS responses|
|**DoH (DNS over HTTPS)**|Encrypts DNS queries|
|**DoT (DNS over TLS)**|Encrypts DNS traffic|

✅ Modern browsers now use **DoH by default**

---

# 🔟 DNS vs IP (Quick Comparison)

|DNS|IP Address|
|---|---|
|Human-friendly|Machine-friendly|
|Example: google.com|Example: 142.250.190.14|
|Changes rarely by user|Can change anytime|
|Needs resolver|Used directly for routing|

---

# 1️⃣1️⃣ Common Exam / Interview Questions

### ❓ Is DNS UDP or TCP?

✅ Mainly **UDP (faster)**  
✅ Uses **TCP for large replies & zone transfers**

---

### ❓ What happens if DNS server is down?

- Websites **won’t load by name**
    
- But known IPs may still work
    

---

### ❓ Is DNS centralized?

❌ No. It is **distributed & hierarchical**

---

# 📌 1️⃣2️⃣ One-Line Definitions (Interview Gold)

- **DNS** → System that translates domain names into IP addresses
    
- **Recursive Resolver** → Finds IP on behalf of the user
    
- **Authoritative Server** → Final source for a domain’s IP
    
- **A Record** → Domain → IPv4 mapping
    
- **CNAME** → Alias domain mapping
    
- **TTL** → Cache expiration time
    

---

# Final Summary (Powerful & Short)

> DNS is a distributed, hierarchical naming system that converts human-friendly domain names like google.com into IP addresses used by computers. It works using recursive resolution through root, TLD, and authoritative name servers, and heavily relies on caching for speed. While basic DNS is unencrypted and vulnerable to attacks, modern technologies like DNSSEC and DNS over HTTPS provide secure name resolution.

---

# TLS/SSL
# 1️⃣ What Is SSL / TLS?

### ✅ Definition:

**SSL (Secure Sockets Layer)** and **TLS (Transport Layer Security)** are **cryptographic security protocols** that provide:

> ✅ **Encryption**  
> ✅ **Authentication**  
> ✅ **Data integrity**

for data sent over a network.

---

### ⚠️ Important:

- **SSL is obsolete (deprecated)**
    
- **TLS is the modern, secure version**
    
- People still say _“SSL”_, but they **actually mean TLS**
    

✅ Today, all secure internet communication uses **TLS**

---

# 2️⃣ Why TLS/SSL Is Needed

Without TLS:

- Data travels in **plain text**
    
- Hackers can:
    
    - Read passwords
        
    - Steal credit card numbers
        
    - Modify data
        
    - Impersonate websites
        

With TLS:

- Data is **encrypted**
    
- Website is **authenticated**
    
- Data **cannot be tampered with**
    

---

# 3️⃣ What Does TLS/SSL Protect? (Three Pillars)

|Security Goal|Meaning|
|---|---|
|🔐 **Confidentiality**|No one can read the data|
|✅ **Integrity**|Data cannot be altered|
|🧾 **Authentication**|Confirms the real identity of the server|

---

# 4️⃣ Where TLS/SSL Works in the Network Stack

TLS sits **between Application and Transport layers**:

```
Application (HTTP, FTP, SMTP)
↓
TLS / SSL  ← Security Layer
↓
Transport (TCP)
↓
IP → Network
```

Example:

- **HTTPS = HTTP + TLS + TCP**
    
- **FTPS = FTP + TLS**
    
- **SMTPS = SMTP + TLS**
    

---

# 5️⃣ How TLS Works (High-Level Handshake)

This is the **most important conceptual flow**.

![Image](https://cf-assets.www.cloudflare.com/slt3lc6tev37/5aYOr5erfyNBq20X5djTco/3c859532c91f25d961b2884bf521c1eb/tls-ssl-handshake.png?utm_source=chatgpt.com)

![Image](https://www.thesslstore.com/blog/wp-content/uploads/2017/01/SSL_Handshake_10-Steps-1.png?utm_source=chatgpt.com)

### 🔁 TLS Handshake (Simplified Steps):

1. **Client Hello**
    
    - Browser sends supported encryption methods
        
2. **Server Hello**
    
    - Server selects encryption method
        
    - Sends **digital certificate**
        
3. **Certificate Verification**
    
    - Browser checks:
        
        - Trusted CA
            
        - Valid date
            
        - Domain matches
            
4. **Key Exchange**
    
    - Client creates a **session key**
        
    - Encrypts it using server’s **public key**
        
5. **Secure Session Begins**
    
    - Both sides now use the **same symmetric session key**
        
    - All further data is encrypted
        

✅ After handshake → **Fast symmetric encryption is used**

---

# 6️⃣ Symmetric vs Asymmetric Encryption in TLS

TLS uses **both**:

|Type|Used For|Speed|
|---|---|---|
|**Asymmetric** (Public/Private Key)|Secure key exchange & authentication|Slow|
|**Symmetric** (Session Key)|Actual data encryption|Fast|

✅ **Best of both worlds:**

- Asymmetric → Secure setup
    
- Symmetric → Fast data transfer
    

---

# 7️⃣ Digital Certificates (Very Important)

A **digital certificate** proves that:

> “This server is who it claims to be.”

Issued by:

- **Certificate Authorities (CA)** like:
    
    - DigiCert
        
    - Let’s Encrypt
        
    - GlobalSign
        

---

### Certificate Contains:

- Domain name
    
- Public key
    
- Issuer (CA)
    
- Expiry date
    
- Digital signature
    

---

### If Certificate Is:

- ✅ Valid → Browser shows 🔒 lock
    
- ❌ Invalid → Browser shows **“Not Secure” warning**
    

---

# 8️⃣ TLS Versions

|Version|Status|
|---|---|
|SSL 2.0|❌ Insecure|
|SSL 3.0|❌ Insecure|
|TLS 1.0|❌ Weak|
|TLS 1.1|❌ Weak|
|TLS 1.2|✅ Widely used|
|TLS 1.3|✅ Latest, fastest, most secure|

✅ Today: **TLS 1.2 & TLS 1.3 only**

---

# 9️⃣ Performance Impact of TLS

|Aspect|Effect|
|---|---|
|Handshake|Has some overhead|
|Data transfer|Very fast after setup|
|CPU usage|Slightly higher|
|Security|Massively improved ✅|

⚠️ **TLS 1.3 is faster than older versions**

---

# 🔟 TLS vs SSL (Quick Comparison)

|Feature|SSL|TLS|
|---|---|---|
|Full form|Secure Sockets Layer|Transport Layer Security|
|Current use|❌ Not used|✅ Used everywhere|
|Security|Weak|Strong|
|Performance|Slower|Faster|
|Status|Deprecated|Active standard|

---

# 1️⃣1️⃣ Common Attacks Prevented by TLS

✅ Man-in-the-Middle (MITM)  
✅ Packet sniffing  
✅ Data tampering  
✅ Credential theft  
✅ Website impersonation

---

# 1️⃣2️⃣ One-Line Interview Definitions

- **TLS** → A cryptographic protocol that secures network communication
    
- **SSL** → Older, insecure version of TLS
    
- **Digital Certificate** → Proof of server identity
    
- **Public Key** → Used to encrypt session key
    
- **Session Key** → Symmetric key for fast encryption
    

---

# Final Summary (Strong & Short)

> TLS (formerly SSL) is a cryptographic security protocol that provides encryption, authentication, and data integrity for internet communication. It works by using public-key encryption to securely exchange a symmetric session key during the handshake, and then uses that session key for fast, secure data transfer. TLS is the foundation of HTTPS and protects users from data theft, tampering, and impersonation attacks.

---

# Latency vs Throughput
# 1️⃣ What Is Latency?

### ✅ Definition:

> **Latency is the time delay between sending a request and receiving the first response.**

It answers:

> 🕒 **“How long does one operation take?”**

---

### ✅ Measured In:

- Milliseconds (ms)
    
- Microseconds (μs)
    

---

### ✅ Examples:

- Time between clicking a link and the page starting to load
    
- Time between sending a packet and getting the first reply (ping)
    

---

### ✅ Real-Life Analogy:

- You place a food order →  
    ⏱️ Time until the **first dish arrives** = **Latency**
    

---

### ✅ Causes of High Latency:

- Long physical distance
    
- Network congestion
    
- Slow servers
    
- DNS lookup delay
    
- TLS handshake delay
    
- Disk access delays
    

---

# 2️⃣ What Is Throughput?

### ✅ Definition:

> **Throughput is the amount of data processed or transferred per unit time.**

It answers:

> 📦 **“How much data can be handled per second?”**

---

### ✅ Measured In:

- Mbps / Gbps (networks)
    
- Requests per second (RPS)
    
- Transactions per second (TPS)
    
- Data per second (MB/s)
    

---

### ✅ Examples:

- Download speed = throughput
    
- Number of users a server can handle per second
    

---

### ✅ Real-Life Analogy:

- You place continuous food orders →  
    🍽️ **How many dishes per minute are served** = **Throughput**
    

---

# 3️⃣ Core Difference in One Line

> ✅ **Latency = Delay per operation**  
> ✅ **Throughput = Work done per unit time**

---

# 4️⃣ Latency vs Throughput Comparison Table

|Feature|Latency|Throughput|
|---|---|---|
|Meaning|Time delay|Data volume per time|
|Focus|Speed of single task|Capacity of system|
|Unit|ms, μs|Mbps, RPS|
|Affects|Responsiveness|Bandwidth & capacity|
|User feels|“System is slow”|“System handles few users”|

---

# 5️⃣ Important Relationship Between Them

They are **not the same**, and **improving one does not always improve the other**.

### ✅ Cases:

- ✅ **Low latency, low throughput**
    
    - Fast response but handles few users
        
- ✅ **High throughput, high latency**
    
    - Handles many users but slow response
        
- ✅ **Low latency, high throughput** ✅ (Ideal system)
    
- ❌ **High latency, low throughput** (Worst system)
    

---

# 6️⃣ Network Example

|Scenario|Latency|Throughput|
|---|---|---|
|Video call|Must be low|Moderate|
|File download|Can be high|Must be high|
|Online gaming|Must be very low|Medium|
|Web server|Low|High|

---

# 7️⃣ Formula Connection (Advanced Insight)

A simplified performance relationship:

```
Throughput ≈ (Data in flight) / Latency
```

This means:

- If **latency increases**, you need **more parallel data** to maintain throughput.
    
- This is why **CDNs, TCP windows, and pipelining exist**.
    

---

# 8️⃣ Latency vs Bandwidth (Common Confusion)

- **Bandwidth** = Maximum possible throughput
    
- **Throughput** = Actual achieved data rate
    
- **Latency** = Delay before data starts arriving
    

You can have:

- ✅ High bandwidth + high latency (satellite internet)
    
- ✅ Low bandwidth + low latency (slow but responsive)
    

---

# 9️⃣ How to Reduce Latency

- Use **CDNs**
    
- Faster DNS resolution
    
- Persistent connections (HTTP/2)
    
- Caching
    
- Faster hardware (SSD, RAM)
    
- Reduce network hops
    

---

# 🔟 How to Increase Throughput

- Increase bandwidth
    
- Parallel processing
    
- Load balancing
    
- Compression
    
- Better protocols (HTTP/2, HTTP/3, QUIC)
    
- Better CPU & I/O performance
    

---

# 1️⃣1️⃣ Interview-Ready One-Line Definitions

- **Latency** → Time taken for a single request to get a response
    
- **Throughput** → Amount of data processed per second
    
- **High latency** → Slow response
    
- **Low throughput** → Low capacity
    

---

# Final Summary (Powerful & Short)

> Latency measures how long a single operation takes to complete, while throughput measures how much data a system can process in a given time. Latency affects responsiveness, and throughput affects capacity. A system can have low latency but low throughput, or high throughput but high latency, depending on how it is designed and optimized.
---

# Connection pooling

# 1️⃣ What Is Connection Pooling?

### ✅ Definition:

> **Connection pooling is a technique where a fixed number of reusable connections are created in advance and shared among multiple clients instead of creating a new connection every time.**

Instead of this:

```
Request → Create Connection → Use → Close
```

We do this:

```
Request → Borrow Connection → Use → Return to Pool
```

---

# 2️⃣ Why Connection Pooling Is Needed

Creating a new connection (especially to a **database**) is:

- ❌ Slow
    
- ❌ CPU expensive
    
- ❌ Memory intensive
    
- ❌ Network expensive
    

So instead of repeatedly creating & destroying connections, we:

✅ Create once  
✅ Reuse many times

This improves:

- Performance
    
- Scalability
    
- Resource usage
    

---

# 3️⃣ How Connection Pooling Works (Step-by-Step)

1. **At startup**, the system creates:
    
    - 10, 20, or 50 ready-to-use connections
        
2. These are stored in a **pool**
    
3. When a client makes a request:
    
    - A free connection is **borrowed**
        
4. After use:
    
    - The connection is **returned to the pool**
        
5. If the pool is full and all connections are busy:
    
    - New requests **wait**
        
    - Or fail after **timeout**
        

✅ Connections are **reused**, not destroyed each time.

---

# 4️⃣ Where Connection Pooling Is Used

Most commonly used with:

|Area|Example|
|---|---|
|Databases|MySQL, PostgreSQL, MongoDB|
|Web servers|API → DB calls|
|Thread pools|Worker thread reuse|
|Network sockets|HTTP keep-alive|

---

# 5️⃣ Real-World Analogy

🏦 **Bank Counters Analogy**

- Suppose a bank has **5 counters** (connections)
    
- Customers (requests) come in
    
- Each customer uses a counter briefly
    
- Then leaves and the counter becomes free again
    

✅ No need to rebuild a counter for every customer  
✅ Faster service  
✅ Controlled load

---

# 6️⃣ Benefits of Connection Pooling

|Benefit|Why It Matters|
|---|---|
|✅ Faster performance|No repeated connection setup|
|✅ Lower latency|Immediate access to ready connections|
|✅ Resource efficiency|Fewer TCP & DB handshakes|
|✅ Scalability|Supports many concurrent users|
|✅ Load control|Limits how many connections hit DB|

---

# 7️⃣ Drawbacks & Risks

|Problem|Explanation|
|---|---|
|❌ Pool exhaustion|All connections busy → requests wait|
|❌ Misconfigured size|Too small → slow / Too large → DB crash|
|❌ Connection leaks|Connection not returned → pool dries up|
|❌ Stale connections|Long-idle connections may break|

✅ These are **configuration & usage issues**, not design flaws.

---

# 8️⃣ Key Pool Configuration Parameters (Very Important)

|Parameter|Meaning|
|---|---|
|**Max Pool Size**|Maximum number of connections|
|**Min Pool Size**|Minimum always kept alive|
|**Idle Timeout**|When to close unused connections|
|**Max Lifetime**|Max age of a connection|
|**Connection Timeout**|How long to wait for a free connection|

✅ Proper tuning = stable high-performance system

---

# 9️⃣ Connection Pooling vs No Pooling

|Without Pooling|With Pooling|
|---|---|
|Create connection every time|Reuse connections|
|Slow|Fast|
|High CPU & network cost|Low cost|
|Poor for high traffic|Excellent for high traffic|
|Unstable under load|Stable under load|

---

# 🔟 Common Interview Traps

### ❓ Is connection pooling only for databases?

❌ No.  
It’s also used for:

- Network sockets
    
- Threads
    
- HTTP keep-alive
    

---

### ❓ What happens if all pool connections are busy?

- Requests:
    
    - Wait in a queue ✅
        
    - Or fail after a timeout ❌
        

---

### ❓ Is a bigger pool always better?

❌ No.

- Too large → overloads DB
    
- Too small → causes slow requests
    

✅ Pool size must match:

- CPU
    
- DB limit
    
- Traffic volume
    

---

# 1️⃣1️⃣ Connection Pooling vs Thread Pool (Quick Compare)

|Feature|Connection Pool|Thread Pool|
|---|---|---|
|Manages|DB/Network connections|CPU threads|
|Purpose|I/O efficiency|CPU multitasking|
|Used in|Databases, APIs|Parallel task execution|

---

# 1️⃣2️⃣ One-Line Interview Definitions

- **Connection Pooling** → Reusing a fixed set of connections to reduce overhead
    
- **Pool Exhaustion** → When all connections are in use
    
- **Connection Leak** → Borrowed connection not returned
    
- **Max Pool Size** → Upper limit of open connections
    

---

# ✅ Final Summary (Strong & Short)

> Connection pooling is a resource management technique where a fixed number of reusable connections are maintained and shared among multiple clients instead of creating a new connection for every request. It significantly improves performance, reduces latency, and prevents system overload, but requires proper configuration to avoid pool exhaustion and connection leaks.
