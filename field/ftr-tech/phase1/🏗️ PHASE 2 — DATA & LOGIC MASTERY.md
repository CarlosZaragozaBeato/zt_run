## 3️⃣ Data Structures Theory
Data Structures Theory studies how to organize, store, and manage data efficiently in a computer. The way you structure data dramatically affects how fast you can access, search, insert, or delete information.

Think of data structures as different ways to organize books: you could stack them in a pile, arrange them on a shelf by genre, organize them in a card catalog, or create a complex library system with multiple indexes. Each approach has trade-offs in terms of how quickly you can find a book, add a new one, or reorganize.

## Linear vs Non-linear structures
This is a fundamental classification based on how data elements are arranged and accessed.

ss
## **Linear Data Structures**
In linear structures, elements are arranged sequentially—one after another in a single level. Each element has exactly one predecessor (except the first) and exactly one successor (except the last). You traverse them in a specific order.
Think of it like a line of people waiting: each person knows who's in front and who's behind.

**Key Characteristic**: Elements form a sequence. There's a natural "next" element.

### **Common Linear Structures**
**1. Arrays**
A collection of elements stored in contiguous memory locations, accessed by index.
```
array = [10, 20, 30, 40, 50]
         0   1   2   3   4   (indices)
```

- **Access**: O(1) — Direct access by index: `array[2]` gets 30 instantly
- **Search**: O(n) — Must check each element in unsorted array
- **Insert/Delete**: O(n) — May need to shift elements
Use when: You need fast access by position, size is known, frequent random access.


**2. Linked Lists**
Elements (nodes) connected by pointers/references. Each node contains data and a reference to the next node.
```
[10|•]→[20|•]→[30|•]→[40|•]→[50|null]
```

- **Access**: O(n) — Must traverse from head to reach element
- **Search**: O(n) — Must check each node sequentially
- **Insert/Delete**: O(1) — Just change pointers (if you're at the position)
Use when: Frequent insertions/deletions, size changes dynamically, don't need random access.

**3. Stacks**
LIFO (Last In, First Out) structure. Like a stack of plates—you can only add or remove from the top.
```
Operations:
Push(5) → [5]
Push(3) → [5, 3]
Push(7) → [5, 3, 7]
Pop()   → returns 7, stack becomes [5, 3]
```

- **Push (insert)**: O(1)
- **Pop (remove)**: O(1)
- **Peek (view top)**: O(1)
Use when: Function calls (call stack), undo/redo operations, expression evaluation, backtracking algorithms.

**4. Queues**
FIFO (First In, First Out) structure. Like a line at a store—first person in line is served first.
```
Operations:
Enqueue(5) → [5]
Enqueue(3) → [5, 3]
Enqueue(7) → [5, 3, 7]
Dequeue()  → returns 5, queue becomes [3, 7]
```

- **Enqueue (insert at rear)**: O(1)
- **Dequeue (remove from front)**: O(1)
Use when: Task scheduling, breadth-first search, handling requests (print queue, CPU scheduling).

**Why Linear Structures Matter**:
- Simple to implement and understand
- Natural for sequential data processing
- Memory-efficient for sequential access
- Foundation for more complex structures
---

## **Non-linear Data Structures**
In non-linear structures, elements are not arranged sequentially. One element can connect to multiple other elements, creating hierarchical or networked relationships. There's no single "next" element.

Think of it like a family tree or a map of cities connected by roads—the relationships are more complex than a simple sequence.

**Key Characteristic**: Elements are organized hierarchically or as a network. One element can relate to many others.

### **Common Non-linear Structures**
**1. Trees**
A hierarchical structure with a root node and child nodes. Each node can have multiple children, but only one parent (except the root, which has no parent).

```
         50
       /    \
      30     70
     /  \   /  \
   20  40 60  80
```

Types of trees:
**Binary Tree**: Each node has at most two children (left and right).
**Binary Search Tree (BST)**: Binary tree where left child < parent < right child. Enables efficient searching.
- **Search**: O(log n) average, O(n) worst case (if unbalanced)
- **Insert/Delete**: O(log n) average
**Balanced Trees** (AVL, Red-Black): Self-balancing BSTs that guarantee O(log n) operations.
**Heaps**: Complete binary trees where parent is always greater (max heap) or smaller (min heap) than children. Used for priority queues.

Use trees when: Representing hierarchies (file systems, organizational charts), efficient searching and sorting, implementing databases and file systems.

**2. Graphs**
A collection of nodes (vertices) connected by edges. The most general non-linear structure—can represent any relationship.
```
    A --- B
    |     |
    |     |
    C --- D --- E
```

Graph types:
**Directed**: Edges have direction (A → B means you can go from A to B, but not necessarily B to A). Example: web pages with links, dependency graphs.

**Undirected**: Edges have no direction (A—B means you can go both ways). Example: social networks (friendship is mutual), road networks.

**Weighted**: Edges have values representing cost, distance, or strength. Example: city distances, network latency.

**Cyclic vs Acyclic**: Cyclic contains loops; acyclic (DAG - Directed Acyclic Graph) has no loops.
- **Search**: O(V + E) for traversal algorithms like DFS, BFS (V=vertices, E=edges)
- **Shortest path**: O(E log V) with Dijkstra's algorithm

Use graphs when: Representing networks (social, computer, transportation), finding paths and routes, modeling relationships and dependencies, recommendation systems.

**3. Hash Tables**
While often considered separately, hash tables are non-linear because they don't store data sequentially—they use a hash function to compute an index for storing/retrieving data.
```
Hash function maps keys to array indices:
"apple"  → hash → index 3 → stores value at array[3]
"banana" → hash → index 7 → stores value at array[7]
```
- **Access/Insert/Delete**: O(1) average case (with good hash function)
- **Search**: O(1) average case

Use when: Need extremely fast lookups, implementing dictionaries, caching, database indexing.

## **Comparing Linear vs Non-linear**

**Memory Layout**:
- Linear: Elements stored sequentially or linked in a chain
- Non-linear: Complex relationships, elements can connect to many others

**Traversal**:
- Linear: Single path, straightforward iteration
- Non-linear: Multiple paths, requires special traversal algorithms (DFS, BFS)

**Complexity**:
- Linear: Simpler to implement and understand
- Non-linear: More complex but can represent richer relationships

**Usage**:
- Linear: Sequential processing, simple relationships, temporal ordering
- Non-linear: Hierarchies, networks, complex relationships, optimization problems

**Memory Efficiency**:
- Linear: Can be more memory-efficient for sequential data
- Non-linear: May require extra space for pointers/references but enable faster operations
---

## **Real-World Examples**
**Linear Structures in Action**:
_Browser history_: Stack—back button pops the last page 
_Printer queue_: Queue—documents print in order received 
_Music playlist_: Array or linked list—sequential song playback 
_Undo functionality_: Stack—most recent action undone first

**Non-linear Structures in Action**:
_File system_: Tree—folders contain files and subfolders 
_Social network_: Graph—people connected by friendships 
_GPS navigation_: Graph—cities as nodes, roads as edges with distances 
_Company hierarchy_: Tree—CEO at top, managers below, employees at leaves 
_Website structure_: Tree or graph—pages linked to each other 
_DNS system_: Tree—hierarchical domain name structure

## **Choosing the Right Structure**
The choice depends on your needs:

**Need fast access by position?** → Array
**Frequent insertions/deletions?** → Linked List
**Need to process in specific order?** → Stack (LIFO) or Queue (FIFO)
**Representing hierarchy?** → Tree
**Modeling relationships/networks?** → Graph
**Need lightning-fast lookups?** → Hash Table
**Need sorted data with fast operations?** → Binary Search Tree or Balanced Tree

## **Hybrid Structures**
Many real-world applications combine structures:

_Priority Queue_: Uses a heap (non-linear) but presents a queue-like (linear) interface
_Graph with adjacency list_: Graph (non-linear) where each node stores a list (linear) of neighbors
_Hash table with chaining_: Hash table (non-linear) where collisions are handled with linked lists (linear)

Understanding both linear and non-linear structures gives you the tools to efficiently solve virtually any data organization problem. The key is recognizing the patterns in your problem and matching them to the appropriate structure.

## Static vs Dynamic
**Static vs Dynamic** refers to how data structures handle memory allocation and size changes. This distinction fundamentally affects how flexible, efficient, and usable a data structure is in different scenarios.

## **Static Data Structures**
Static data structures have a **fixed size** determined at compile time or creation time. Once allocated, their size cannot change during program execution.

**Key Characteristics**:
_Fixed size_: You must specify the size when creating the structure, and it remains constant.
_Contiguous memory_: Typically allocated in a single block of memory.
_Compile-time or early allocation_: Memory is reserved when the program compiles or when the structure is created.
_No resizing_: Cannot grow or shrink after creation.

### **Classic Example: Arrays**
```
// Static array - size fixed at 5
int numbers[5] = {10, 20, 30, 40, 50};

// This is the size forever - cannot add a 6th element
// numbers[5] = 60;  // ERROR or undefined behavior
```

The array has exactly 5 slots. No more, no less.

**Memory Representation**:
```
Memory addresses:
[10][20][30][40][50]
1000 1004 1008 1012 1016

Each element in contiguous memory
Size cannot change
```

### **Advantages of Static Structures**
**Predictable memory usage**: You know exactly how much memory is needed upfront. Critical for embedded systems or memory-constrained environments.

**Fast access**: Direct memory access using simple arithmetic. `array[3]` is just `base_address + (3 × element_size)`.
**No overhead**: No extra memory for pointers, metadata, or memory management structures.
**Cache-friendly**: Contiguous memory layout works well with CPU caches, leading to better performance.
**Simple implementation**: Straightforward to implement and understand.
**No fragmentation**: Since memory is allocated once, no memory fragmentation issues.

### **Disadvantages of Static Structures**
**Inflexible size**: Must know maximum size in advance. If you allocate too much, you waste memory; too little, and you run out of space.
**Cannot grow**: If you need more space, you must create a new, larger structure and copy everything over.
**Memory waste**: If you allocate for worst-case scenario but typically use less, memory is wasted.
**Fixed at creation**: Once created, you're stuck with that size for the structure's lifetime.

### **When to Use Static Structures**
- Size is known and constant (fixed configuration data, lookup tables)
- Memory is constrained and predictability is crucial (embedded systems, real-time systems)
- Maximum performance is needed (tight loops, high-frequency operations)
- Simplicity is valued over flexibility

**Real-world examples**:
- Fixed-size buffers in networking protocols
- Pixel data in images (image dimensions are known)
- Game board representations (chess has 64 squares)
- Configuration arrays with predefined options
---

## **Dynamic Data Structures**
Dynamic data structures can **grow or shrink** during program execution. Their size is not fixed and can adapt to the amount of data you need to store.

**Key Characteristics**:
_Variable size_: Can expand or contract as needed during runtime.
_Runtime allocation_: Memory is allocated as needed during program execution.
_Flexible_: Adapts to actual data requirements.
_May use non-contiguous memory_: Elements might be scattered across memory, connected by pointers/references.

### **Classic Examples**
**1. Dynamic Arrays (ArrayList, Vector)**
Arrays that can resize themselves automatically when needed.
```
// Dynamic array - starts small, grows as needed
dynamicArray = []
dynamicArray.add(10)    // size: 1
dynamicArray.add(20)    // size: 2
dynamicArray.add(30)    // size: 3
// ... can keep adding elements
dynamicArray.add(1000)  // size: 100
```

Under the hood, when the array fills up:
1. Allocate a new, larger array (typically 2× current size)
2. Copy all elements to the new array
3. Delete the old array
4. Continue using the new array

**2. Linked Lists**
Nodes allocated individually, connected by pointers. Can grow indefinitely.
```
// Start empty
list = empty

// Add nodes dynamically
list.add(10)  // [10] → null
list.add(20)  // [10] → [20] → null
list.add(30)  // [10] → [20] → [30] → null

// Each node allocated separately in memory
// Not contiguous - scattered across memory
```

**Memory Representation**:
```
Node 1 at address 1000: [10|ptr→2500]
Node 2 at address 2500: [20|ptr→5000]
Node 3 at address 5000: [30|null]

Nodes can be anywhere in memory
Connected by pointers
```

**3. Trees**
Nodes added dynamically as the tree grows.
```
tree = BinarySearchTree()
tree.insert(50)
tree.insert(30)
tree.insert(70)
// Tree grows as needed
```

**4. Hash Tables**
Can resize when load factor exceeds threshold.
```
hashTable = HashTable()
// Starts small
hashTable.put("key1", "value1")
hashTable.put("key2", "value2")
// ... as it fills up, automatically resizes to larger capacity
```

### **Advantages of Dynamic Structures**
**Flexibility**: Grows and shrinks based on actual needs. No need to predict maximum size.
**Efficient memory use**: Only allocates what's needed at any given time. No wasted space for unused capacity.
**No arbitrary limits**: Can handle varying amounts of data without predetermined constraints.
**Adaptable**: Perfect for situations where data size changes unpredictably.
**Easy insertions/deletions**: Particularly in linked structures—just adjust pointers without shifting elements.

### **Disadvantages of Dynamic Structures**
**Memory overhead**: Extra memory needed for pointers/references and metadata. A linked list node needs space for data _plus_ pointer(s).
**Slower access**: Non-contiguous memory means following pointers, which is slower than direct indexing. Cache misses are more common.
**Fragmentation**: Allocating and deallocating memory can lead to memory fragmentation.
**Allocation overhead**: Runtime memory allocation/deallocation takes time.
**Complexity**: More complex to implement and manage than static structures.
**Unpredictable performance**: Operations like resizing can occasionally be expensive (though amortized cost is usually good).

### **When to Use Dynamic Structures**
- Size is unknown or varies significantly
- Need to grow/shrink frequently
- Memory efficiency is important (don't want to over-allocate)
- Flexibility is more important than absolute maximum performance
- Building general-purpose data structures (libraries, frameworks)

**Real-world examples**:
- Shopping cart items (varies per user)
- Social media feeds (grow as users post)
- Text editors (document size changes as you type)
- Server request queues (fluctuates with traffic)
- Database records (data grows over time)
---

## **Detailed Comparison**

### **Memory Allocation**
**Static**:
```
// All memory allocated at once
int array[1000];
// 1000 × sizeof(int) bytes reserved immediately
// Whether you use 10 elements or 1000, memory is allocated
```

**Dynamic**:
```
// Memory allocated as needed
list = LinkedList()
// Minimal memory initially
list.add(10)  // Allocate memory for one node
list.add(20)  // Allocate memory for another node
// Only pays for what you use
```

### **Access Patterns**
**Static (Array)**:
```
// Direct access - O(1)
value = array[500]  
// Simple calculation: base_address + (500 × element_size)
// Single memory access
```

**Dynamic (Linked List)**:
```
// Sequential access - O(n)
value = list.get(500)
// Must traverse: start → node1 → node2 → ... → node500
// 500 memory accesses following pointers
```

### **Resizing**
**Static**:
```
// Cannot resize - must create new structure
oldArray = [1, 2, 3, 4, 5]  // size: 5
// Need size 10 now
newArray = new Array[10]
// Manually copy all elements
for i = 0 to 4:
    newArray[i] = oldArray[i]
// Replace reference
array = newArray
```

**Dynamic**:
```
// Automatically handles growth
dynamicArray = []
for i = 1 to 1000:
    dynamicArray.add(i)  // Handles resizing internally
// Occasionally resizes but user doesn't see it
```

### **Memory Efficiency Example**
**Scenario**: Storing 100 integers, but maximum might be 1000.

**Static approach**:
```
int array[1000];  // Allocate for worst case
// Actually use 100 elements
// Waste: 900 × sizeof(int) = 3600 bytes wasted
// Total memory: 4000 bytes
```

**Dynamic approach**:
```
dynamicArray = DynamicArray()
// Add 100 integers
// Allocates only what's needed (maybe 128 with growth strategy)
// Waste: minimal
// Total memory: ~128 × sizeof(int) + overhead = ~600 bytes
```
---

## **Hybrid Approaches**
Many real-world implementations combine both concepts:

### **Dynamic Arrays with Static Backing**
```
Dynamic array internally uses static array that grows when needed:

Initial:    [10][20][__][__]  capacity: 4, size: 2
Add 30:     [10][20][30][__]  capacity: 4, size: 3
Add 40:     [10][20][30][40]  capacity: 4, size: 4
Add 50:     [10][20][30][40][50][__][__][__]  
            capacity: 8, size: 5 (resized!)
```

Combines:
- Static array's fast access (O(1))
- Dynamic growth capability
- Amortized O(1) insertion

### **Static Array of Dynamic Structures**
```
// Fixed number of buckets, but each bucket is dynamic
HashTable with 10 buckets (static):
[0] → [key1|val1] → [key2|val2] → null
[1] → null
[2] → [key3|val3] → null
...
[9] → [key4|val4] → [key5|val5] → [key6|val6] → null

// Static array of buckets
// Dynamic linked lists for collision handling
```
---

## **Performance Implications**

### **Time Complexity**
**Static Array**:
- Access: O(1)
- Search: O(n) unsorted, O(log n) sorted
- Insert at end: O(1) if space available, impossible if full
- Insert in middle: O(n) - must shift elements
- Delete: O(n) - must shift elements

**Dynamic Array**:
- Access: O(1)
- Search: O(n) unsorted, O(log n) sorted
- Insert at end: O(1) amortized (occasional O(n) for resize)
- Insert in middle: O(n) - must shift elements
- Delete: O(n) - must shift elements

**Linked List (Dynamic)**:
- Access: O(n)
- Search: O(n)
- Insert at position: O(1) if at position, O(n) to find position
- Delete at position: O(1) if at position, O(n) to find position

### **Space Complexity**
**Static**: O(n) where n is the allocated size (not necessarily the used size)
**Dynamic**: O(n) where n is the actual number of elements, plus overhead for pointers/metadata

## **Choosing Between Static and Dynamic**

**Choose Static when**:
- Size is known and constant
- Maximum performance is critical
- Memory is severely constrained and predictability matters
- Working in embedded/real-time systems
- Implementing low-level system code

**Choose Dynamic when**:
- Size is unknown or varies significantly
- Flexibility is important
- Building general-purpose applications
- Memory efficiency matters (don't want to waste space)
- Convenience and ease of use are priorities

**In practice**: Most modern high-level programming uses dynamic structures by default (Python lists, JavaScript arrays, Java ArrayLists) because convenience and flexibility usually outweigh the performance costs. Low-level and performance-critical code still uses static structures strategically.

## **Language Examples**
**C (Static)**:
```c
int staticArray[100];  // Fixed size, stack or global
int *dynamicArray = malloc(100 * sizeof(int));  // Dynamic allocation, but size still fixed
```

**C++ (Both)**:
```cpp
int staticArray[100];              // Static
std::vector<int> dynamicArray;    // Dynamic (resizes automatically)
```

**Java (Primarily Dynamic)**:
```java
int[] staticArray = new int[100];  // Fixed size but heap-allocated
ArrayList<Integer> dynamic = new ArrayList<>();  // Truly dynamic
```

**Python (Dynamic)**:
```python
list = []  # Dynamic list, grows as needed
list.append(10)
list.append(20)
```

Understanding static vs dynamic is crucial for making informed decisions about which data structure to use, how to optimize memory usage, and when performance trade-offs are acceptable.

## Memory layout concepts
    
- Access patterns (random vs sequential)
    
- When to use:
    
    - Array vs List
        
    - Stack vs Queue
        
    - HashMap vs Tree
        

## 4️⃣ Algorithms Theory

- Searching & Sorting principles
    
- Divide & Conquer
    
- Greedy algorithms
    
- Dynamic Programming (concept only)
    
- Graph traversal logic (BFS/DFS)
    
- Optimization problems
    
