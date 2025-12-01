# 🧠 PHASE 1 — THINK LIKE A COMPUTER (Foundations)

## 1️⃣ Computer Science Foundations

## What is computation?
Computation is the process of performing calculations or executing a sequence of operations to transform input data into output results according to a set of rules or instructions.

In computer science specifically, computation encompasses several key ideas:

**Core concept**: At its heart, computation involves taking some input, applying a well-defined procedure (an algorithm), and producing an output. This could be as simple as adding two numbers or as complex as rendering a 3D video game scene.

**Theoretical foundation**: Computer scientists study computation formally through models like Turing machines, which help define what problems can be solved algorithmically and what the fundamental limits of computation are. This leads to important questions about computability (what can be computed) and computational complexity (how efficiently it can be computed).

**Practical implementation**: In practice, computation happens when processors execute instructions. Your computer performs billions of computations per second—fetching instructions from memory, processing data through logic circuits, and storing results.

**Universal nature**: One of the profound insights of computer science is that computation is universal. Any sufficiently powerful computing device can simulate any other, which is why the same algorithm can run on your phone, laptop, or a supercomputer.

The field studies not just how to perform computations, but also how to design algorithms efficiently, prove their correctness, understand their resource requirements (time and space), and determine which problems are feasible to solve computationally.

## What is an algorithm?
An algorithm is a precise, step-by-step set of instructions for solving a problem or accomplishing a task. Think of it as a recipe for computation—it specifies exactly what operations to perform and in what order to transform input into the desired output.

**Key characteristics of algorithms**:

_Well-defined steps_: Each instruction must be clear and unambiguous. There's no room for interpretation—anyone (or any computer) following the algorithm should perform exactly the same operations.

_Finiteness_: An algorithm must eventually terminate. It can't run forever—it needs to produce a result in a finite number of steps.

_Input and output_: Algorithms take zero or more inputs and produce at least one output. For example, a sorting algorithm takes an unsorted list as input and produces a sorted list as output.

_Effectiveness_: Each step must be basic enough to be carried out, in principle, by a person using pencil and paper. This ensures the algorithm is actually executable.

**Simple example**: Here's an algorithm to find the largest number in a list:

1. Start with the first number and call it "largest"
2. For each remaining number in the list:
    - If the current number is greater than "largest", update "largest" to be this number
3. Return "largest" as the answer

**Why algorithms matter**: Computer science is fundamentally about designing algorithms to solve problems efficiently. The same problem might have many different algorithmic solutions, and they can vary dramatically in speed and resource usage. For instance, there are dozens of sorting algorithms, and choosing the right one depends on your specific needs.

Algorithms are independent of programming languages—the same algorithm can be implemented in Python, Java, C++, or any other language. The algorithm is the logical solution; code is just one way to express it.

## Time vs Space complexity
Time and space complexity are ways to measure how efficient an algorithm is. They help us understand how an algorithm's resource requirements grow as the input size increases.

**Time Complexity**: How long does the algorithm take to run?

Time complexity measures the number of operations an algorithm performs relative to the input size. We don't count exact milliseconds (since that depends on the computer), but rather how the number of steps grows.

For example, imagine you have a list of n numbers:

- If you need to look at each number once, that's linear time—doubling the list size doubles the work
- If you need to compare every number with every other number (like in bubble sort), that's quadratic time—doubling the list size quadruples the work
- If you can eliminate half the remaining possibilities at each step (like in binary search), that's logarithmic time—doubling the list size only adds one more step

**Space Complexity**: How much memory does the algorithm need?

Space complexity measures the amount of memory an algorithm uses relative to the input size. This includes memory for variables, data structures, and sometimes the input itself.

For example:

- An algorithm that only uses a few variables regardless of input size uses constant space
- An algorithm that creates a copy of the entire input list uses linear space
- A recursive algorithm might use space proportional to how deep the recursion goes

**Big O Notation**: We express complexity using Big O notation, which describes the worst-case growth rate. Common complexities from best to worst:

- O(1) — Constant: Same time/space regardless of input (accessing an array element)
- O(log n) — Logarithmic: Grows slowly (binary search)
- O(n) — Linear: Grows proportionally (finding max in unsorted list)
- O(n log n) — Linearithmic: Efficient sorting algorithms (merge sort)
- O(n²) — Quadratic: Nested loops over data (bubble sort)
- O(2ⁿ) — Exponential: Doubles with each addition (some recursive algorithms)

**The tradeoff**: Often there's a tradeoff between time and space. You might make an algorithm faster by storing pre-computed results (using more memory), or save memory by recomputing values as needed (taking more time). Choosing the right balance depends on your constraints—is memory or speed your bottleneck?    

## Big-O, Big-Theta, Big-Omega
These three notations describe different aspects of how an algorithm's performance relates to input size. They're like different ways of setting boundaries on an algorithm's behavior.

**Big-O (O) — Upper Bound (Worst Case)**
Big-O describes the maximum amount of time or space an algorithm could possibly take. It's an upper limit—the algorithm won't perform worse than this.

When we say an algorithm is O(n²), we mean that as the input size grows, the number of operations will grow no faster than some constant times n². The algorithm might actually be faster in practice, but we're guaranteed it won't be slower.

Think of it as a speed limit: "This algorithm will never take longer than n² operations (times some constant)."

**Big-Omega (Ω) — Lower Bound (Best Case)**
Big-Omega describes the minimum amount of time or space an algorithm needs. It's a lower limit—the algorithm won't perform better than this.

When we say an algorithm is Ω(n), we mean that as input grows, it must perform at least some constant times n operations. It might take longer, but it can't be faster than this.

Think of it as a minimum requirement: "This algorithm will take at least n operations, no matter what."

**Big-Theta (Θ) — Tight Bound (Average Case)**
Big-Theta describes when the upper and lower bounds match—the algorithm's performance grows at exactly this rate, sandwiched between matching upper and lower bounds.

When we say an algorithm is Θ(n log n), we mean it performs proportionally to n log n operations—not just sometimes, but always. The algorithm is both O(n log n) and Ω(n log n).

Think of it as an exact characterization: "This algorithm always takes roughly n log n operations."

**Practical example**: Consider finding a specific number in an unsorted list:
- **Big-O**: O(n) — In the worst case, the number is at the end or not present, so you check all n elements
- **Big-Omega**: Ω(1) — In the best case, the number is the very first element, taking constant time
- **Big-Theta**: For this algorithm, there's no tight bound because best and worst cases differ significantly

**Another example**: Merge sort (a sorting algorithm):
- **Big-O**: O(n log n) — worst case
- **Big-Omega**: Ω(n log n) — best case
- **Big-Theta**: Θ(n log n) — merge sort always performs the same divide-and-conquer steps regardless of input, so it has a tight bound

**Why three notations?** In everyday conversation, computer scientists often use Big-O loosely to mean "approximately grows like this," but technically Big-Theta is more precise for that. Big-O is most common because we typically care about worst-case guarantees. Big-Omega is less commonly discussed but important for proving lower bounds—showing that no algorithm can solve a problem faster than a certain threshold.

## Trade-offs in system design
    
## Deterministic vs Non-deterministic logic
    
## Recursion vs Iteration

---

## 2️⃣ Programming Paradigms (Language-Independent Thinking)

- Imperative vs Declarative
    
- Object-Oriented Programming (OOP)
    
- Functional Programming
    
- Procedural Programming
    
- Composition vs Inheritance
    
- Immutability vs Mutability
    
- Side effects & state
    

---