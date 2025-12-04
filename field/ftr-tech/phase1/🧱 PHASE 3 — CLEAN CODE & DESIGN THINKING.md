## 5️⃣ Object-Oriented Design & SOLID

# Encapsulation
Encapsulation is one of the four fundamental pillars of Object-Oriented Programming (OOP). It's about **bundling data and the methods that operate on that data together**, while **controlling access** to prevent external interference and misuse.

Think of it as building a capsule around your data—hence "encapsulation."

---

## **Core Concept**

Encapsulation means:

1. **Bundling**: Group related data (attributes) and behavior (methods) together in a single unit (class)
2. **Information Hiding**: Hide internal implementation details from the outside world
3. **Controlled Access**: Provide a public interface to interact with the object while keeping internals private

**Real-world analogy**: A car's accelerator pedal

- **What you see (public interface)**: A pedal you press to go faster
- **What you don't see (hidden internals)**: Fuel injection, throttle position sensors, engine control unit, complex algorithms
- **Benefit**: You can drive without understanding internal combustion engines

---

## **Why Encapsulation Matters**

### **1. Protection**

Prevent invalid states and maintain data integrity:

```python
# BAD: No encapsulation
class BankAccount:
    def __init__(self):
        self.balance = 0  # Public, anyone can modify

account = BankAccount()
account.balance = -5000  # Disaster! Negative balance allowed
account.balance = "not a number"  # Even worse!
```

```python
# GOOD: With encapsulation
class BankAccount:
    def __init__(self):
        self.__balance = 0  # Private
    
    def deposit(self, amount):
        if amount > 0:
            self.__balance += amount
        else:
            raise ValueError("Deposit amount must be positive")
    
    def withdraw(self, amount):
        if amount > 0 and amount <= self.__balance:
            self.__balance -= amount
        else:
            raise ValueError("Invalid withdrawal amount")
    
    def get_balance(self):
        return self.__balance

account = BankAccount()
account.deposit(1000)
account.withdraw(500)
# account.__balance = -5000  # Can't do this! Protected
```

Now the balance can only be modified through controlled methods that enforce business rules.

### **2. Flexibility to Change Implementation**

Hide implementation details so you can change them later without breaking external code:

```python
# Version 1: Store temperature in Celsius
class Thermostat:
    def __init__(self):
        self.__temperature_celsius = 20
    
    def set_temperature(self, celsius):
        self.__temperature_celsius = celsius
    
    def get_temperature(self):
        return self.__temperature_celsius

# External code uses it
thermostat = Thermostat()
thermostat.set_temperature(25)
print(thermostat.get_temperature())  # 25
```

```python
# Version 2: Later, you change internal storage to Kelvin
# External code still works unchanged!
class Thermostat:
    def __init__(self):
        self.__temperature_kelvin = 293.15  # 20°C in Kelvin
    
    def set_temperature(self, celsius):
        self.__temperature_kelvin = celsius + 273.15
    
    def get_temperature(self):
        return self.__temperature_kelvin - 273.15

# Same external code still works
thermostat = Thermostat()
thermostat.set_temperature(25)  # Still works!
print(thermostat.get_temperature())  # Still returns 25
```

The internal representation changed from Celsius to Kelvin, but external code doesn't break because it uses the public interface, not direct access to private data.

### **3. Reduced Complexity**

Users only need to understand the public interface, not internal complexity:

```python
class EmailService:
    def __init__(self):
        self.__smtp_server = None
        self.__connection = None
        self.__authenticated = False
    
    def send_email(self, to, subject, body):
        """Simple public interface"""
        self.__connect()
        self.__authenticate()
        self.__compose_message(to, subject, body)
        self.__send_via_smtp()
        self.__cleanup()
    
    # All complexity hidden in private methods
    def __connect(self):
        # Complex connection logic
        pass
    
    def __authenticate(self):
        # Authentication with tokens, OAuth, etc.
        pass
    
    def __compose_message(self, to, subject, body):
        # MIME formatting, encoding, headers
        pass
    
    def __send_via_smtp(self):
        # SMTP protocol details
        pass
    
    def __cleanup(self):
        # Close connections, cleanup resources
        pass

# User just calls one simple method
email_service = EmailService()
email_service.send_email("user@example.com", "Hello", "Test message")
# Don't need to know about SMTP, MIME, authentication, etc.
```

---

## **Access Modifiers**

Different languages provide different levels of access control:

### **Python**

Python uses naming conventions (not enforced by the language):

```python
class Example:
    def __init__(self):
        self.public_var = "Everyone can access"
        self._protected_var = "Convention: internal use"
        self.__private_var = "Name mangled, harder to access"
    
    def public_method(self):
        """Anyone can call this"""
        pass
    
    def _protected_method(self):
        """Convention: for internal/subclass use"""
        pass
    
    def __private_method(self):
        """Name mangled: _ClassName__method_name"""
        pass
```

**Note**: Python's philosophy is "we're all consenting adults"—encapsulation is more about convention than enforcement.

### **Java**

Java has strict access modifiers:

```java
public class Example {
    public int publicVar;        // Accessible everywhere
    protected int protectedVar;  // Accessible in package & subclasses
    private int privateVar;      // Accessible only within this class
    int defaultVar;              // Package-private (no modifier)
    
    public void publicMethod() { }
    protected void protectedMethod() { }
    private void privateMethod() { }
    void defaultMethod() { }
}
```

### **C++**

```cpp
class Example {
public:
    int publicVar;
    void publicMethod();

protected:
    int protectedVar;
    void protectedMethod();

private:
    int privateVar;
    void privateMethod();
};
```

---

## **Getters and Setters (Accessors and Mutators)**

Provide controlled access to private data:

### **Basic Example**

```python
class Person:
    def __init__(self, name, age):
        self.__name = name
        self.__age = age
    
    # Getter
    def get_name(self):
        return self.__name
    
    # Setter
    def set_name(self, name):
        if not name or not name.strip():
            raise ValueError("Name cannot be empty")
        self.__name = name
    
    # Getter
    def get_age(self):
        return self.__age
    
    # Setter with validation
    def set_age(self, age):
        if age < 0 or age > 150:
            raise ValueError("Invalid age")
        self.__age = age

person = Person("Alice", 30)
print(person.get_name())  # Alice
person.set_age(31)  # Valid
# person.set_age(-5)  # Raises ValueError
```

### **Python Properties (Pythonic Way)**

Python provides `@property` decorator for cleaner syntax:

```python
class Person:
    def __init__(self, name, age):
        self.__name = name
        self.__age = age
    
    @property
    def name(self):
        """Getter"""
        return self.__name
    
    @name.setter
    def name(self, value):
        """Setter with validation"""
        if not value or not value.strip():
            raise ValueError("Name cannot be empty")
        self.__name = value
    
    @property
    def age(self):
        return self.__age
    
    @age.setter
    def age(self, value):
        if value < 0 or age > 150:
            raise ValueError("Invalid age")
        self.__age = value

# Usage looks like direct attribute access but goes through methods
person = Person("Alice", 30)
print(person.name)  # Calls the getter
person.age = 31     # Calls the setter with validation
# person.age = -5   # Raises ValueError
```

### **When to Use Getters/Setters**

**Use them when you need**:

- **Validation**: Ensure data meets requirements
- **Computed values**: Calculate values on-the-fly
- **Side effects**: Log access, trigger events, update related data
- **Access control**: Read-only properties, write-once fields

```python
class Rectangle:
    def __init__(self, width, height):
        self.__width = width
        self.__height = height
    
    @property
    def width(self):
        return self.__width
    
    @width.setter
    def width(self, value):
        if value <= 0:
            raise ValueError("Width must be positive")
        self.__width = value
    
    @property
    def height(self):
        return self.__height
    
    @height.setter
    def height(self, value):
        if value <= 0:
            raise ValueError("Height must be positive")
        self.__height = value
    
    @property
    def area(self):
        """Computed property - no setter"""
        return self.__width * self.__height
    
    @property
    def perimeter(self):
        """Another computed property"""
        return 2 * (self.__width + self.__height)

rect = Rectangle(5, 10)
print(rect.area)       # 50 (computed)
print(rect.perimeter)  # 30 (computed)
rect.width = 7         # OK
# rect.area = 100      # Error: can't set computed property
```

**Don't use them when**:

- Simple data holders with no validation (use direct attributes or dataclasses)
- Over-engineering simple cases

```python
# OVER-ENGINEERED for simple data
class Point:
    def __init__(self, x, y):
        self.__x = x
        self.__y = y
    
    def get_x(self):
        return self.__x
    
    def set_x(self, x):
        self.__x = x
    # ... same for y

# BETTER: Just use a simple class or dataclass
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

# Or even better in Python 3.7+
from dataclasses import dataclass

@dataclass
class Point:
    x: float
    y: float
```

---

## **Real-World Examples**

### **Example 1: Shopping Cart**

```python
class ShoppingCart:
    def __init__(self):
        self.__items = []  # Private: protect from direct manipulation
        self.__discount = 0
    
    def add_item(self, item, price, quantity=1):
        """Public interface to add items"""
        if price < 0:
            raise ValueError("Price cannot be negative")
        if quantity <= 0:
            raise ValueError("Quantity must be positive")
        
        self.__items.append({
            'item': item,
            'price': price,
            'quantity': quantity
        })
    
    def remove_item(self, item):
        """Public interface to remove items"""
        self.__items = [i for i in self.__items if i['item'] != item]
    
    def apply_discount(self, percentage):
        """Controlled discount application"""
        if percentage < 0 or percentage > 100:
            raise ValueError("Discount must be between 0 and 100")
        self.__discount = percentage
    
    def get_total(self):
        """Computed property using private data"""
        subtotal = sum(item['price'] * item['quantity'] 
                      for item in self.__items)
        discount_amount = subtotal * (self.__discount / 100)
        return subtotal - discount_amount
    
    def get_item_count(self):
        """Public read-only access to item count"""
        return len(self.__items)
    
    def __calculate_tax(self, amount):
        """Private helper method"""
        return amount * 0.08  # 8% tax

cart = ShoppingCart()
cart.add_item("Book", 29.99, 2)
cart.add_item("Pen", 1.99, 5)
cart.apply_discount(10)

# Public interface is simple and safe
print(f"Total: ${cart.get_total():.2f}")
print(f"Items: {cart.get_item_count()}")

# Can't do this (protected):
# cart.__items.append({"item": "Free stuff", "price": 0})
# cart.__discount = 200  # Would cause invalid state
```

**Benefits**:

- Cart state can't be corrupted
- Business rules enforced (no negative prices, valid discounts)
- Implementation can change (maybe store items in database later)
- Clear interface for users

### **Example 2: Database Connection**

```python
class DatabaseConnection:
    def __init__(self, host, username, password):
        self.__host = host
        self.__username = username
        self.__password = password  # Sensitive data hidden
        self.__connection = None
        self.__is_connected = False
    
    def connect(self):
        """Public method to establish connection"""
        if self.__is_connected:
            return
        
        self.__connection = self.__establish_connection()
        self.__is_connected = True
        print("Connected to database")
    
    def disconnect(self):
        """Public method to close connection"""
        if self.__is_connected:
            self.__close_connection()
            self.__is_connected = False
            print("Disconnected from database")
    
    def execute_query(self, query):
        """Public interface for queries"""
        if not self.__is_connected:
            raise Exception("Not connected to database")
        
        if self.__is_dangerous_query(query):
            raise Exception("Dangerous query detected")
        
        return self.__execute_safe_query(query)
    
    # Private methods - implementation details
    def __establish_connection(self):
        # Complex connection logic with retry, timeouts, etc.
        print(f"Establishing connection to {self.__host}...")
        return "connection_object"
    
    def __close_connection(self):
        # Cleanup logic
        print("Closing connection...")
    
    def __is_dangerous_query(self, query):
        # SQL injection detection
        dangerous_keywords = ["DROP", "DELETE", "TRUNCATE"]
        return any(keyword in query.upper() for keyword in dangerous_keywords)
    
    def __execute_safe_query(self, query):
        # Actual query execution with prepared statements
        print(f"Executing: {query}")
        return "query_results"

# Usage
db = DatabaseConnection("localhost", "user", "secret_password")
db.connect()
results = db.execute_query("SELECT * FROM users")
db.disconnect()

# Can't access these (protected):
# print(db.__password)  # Would raise AttributeError
# db.__execute_safe_query("malicious query")  # Can't bypass safety checks
```

**Benefits**:

- Credentials are hidden
- Safety checks can't be bypassed
- Connection management is controlled
- Implementation details (retry logic, pooling) can change without affecting users

### **Example 3: Game Character**

```python
class Character:
    def __init__(self, name, max_health):
        self.__name = name
        self.__max_health = max_health
        self.__current_health = max_health
        self.__is_alive = True
        self.__experience = 0
        self.__level = 1
    
    @property
    def name(self):
        """Read-only property"""
        return self.__name
    
    @property
    def health(self):
        """Read-only access to health"""
        return self.__current_health
    
    @property
    def is_alive(self):
        return self.__is_alive
    
    @property
    def level(self):
        return self.__level
    
    def take_damage(self, damage):
        """Controlled way to reduce health"""
        if not self.__is_alive:
            return
        
        if damage < 0:
            raise ValueError("Damage cannot be negative")
        
        self.__current_health -= damage
        
        if self.__current_health <= 0:
            self.__current_health = 0
            self.__is_alive = False
            print(f"{self.__name} has been defeated!")
    
    def heal(self, amount):
        """Controlled way to restore health"""
        if not self.__is_alive:
            print("Cannot heal a defeated character")
            return
        
        if amount < 0:
            raise ValueError("Heal amount cannot be negative")
        
        self.__current_health = min(self.__current_health + amount, 
                                   self.__max_health)
    
    def gain_experience(self, exp):
        """Controlled experience gain with auto-leveling"""
        if exp < 0:
            raise ValueError("Experience cannot be negative")
        
        self.__experience += exp
        self.__check_level_up()
    
    def __check_level_up(self):
        """Private method - internal game logic"""
        exp_needed = self.__level * 100
        
        while self.__experience >= exp_needed:
            self.__level += 1
            self.__experience -= exp_needed
            self.__max_health += 10
            self.__current_health = self.__max_health
            print(f"{self.__name} leveled up to level {self.__level}!")
            exp_needed = self.__level * 100
    
    def get_status(self):
        """Public method to display character info"""
        status = "Alive" if self.__is_alive else "Defeated"
        return (f"{self.__name} (Level {self.__level})\n"
                f"Health: {self.__current_health}/{self.__max_health}\n"
                f"Status: {status}\n"
                f"Experience: {self.__experience}")

# Usage
hero = Character("Aragorn", 100)
print(hero.get_status())

hero.take_damage(30)
print(f"Health: {hero.health}")

hero.gain_experience(150)  # Automatic level up
print(hero.get_status())

# Can't do these (protected):
# hero.__current_health = 1000  # Can't cheat health
# hero.__level = 99  # Can't cheat level
# hero.__is_alive = True  # Can't resurrect bypassing game rules
```

**Benefits**:

- Game state integrity maintained
- Can't cheat by directly modifying stats
- Complex game logic (level-up, death) handled correctly
- Easy to modify rules (change exp formula, add new stats) without breaking code

---

## **Common Encapsulation Patterns**

### **1. Immutable Objects**

Once created, state cannot be changed:

```python
class ImmutablePoint:
    def __init__(self, x, y):
        self.__x = x
        self.__y = y
    
    @property
    def x(self):
        return self.__x
    
    @property
    def y(self):
        return self.__y
    
    # No setters - once created, cannot change
    
    def move(self, dx, dy):
        """Returns new point instead of modifying"""
        return ImmutablePoint(self.__x + dx, self.__y + dy)

point1 = ImmutablePoint(0, 0)
point2 = point1.move(5, 10)  # Creates new point
# point1 is unchanged
```

**Benefits**: Thread-safe, predictable, easier to reason about

### **2. Fluent Interface**

Methods return `self` for chaining:

```python
class QueryBuilder:
    def __init__(self):
        self.__table = None
        self.__columns = []
        self.__where_clauses = []
    
    def select(self, *columns):
        self.__columns = columns
        return self  # Return self for chaining
    
    def from_table(self, table):
        self.__table = table
        return self
    
    def where(self, condition):
        self.__where_clauses.append(condition)
        return self
    
    def build(self):
        query = f"SELECT {', '.join(self.__columns)} FROM {self.__table}"
        if self.__where_clauses:
            query += f" WHERE {' AND '.join(self.__where_clauses)}"
        return query

# Fluent interface usage
query = (QueryBuilder()
         .select("name", "email")
         .from_table("users")
         .where("age > 18")
         .where("active = true")
         .build())

print(query)
# SELECT name, email FROM users WHERE age > 18 AND active = true
```

### **3. Builder Pattern**

Encapsulate complex object construction:

```python
class Pizza:
    def __init__(self):
        self.__size = None
        self.__crust = None
        self.__toppings = []
    
    # Internal method only builder uses
    def _set_size(self, size):
        self.__size = size
    
    def _set_crust(self, crust):
        self.__crust = crust
    
    def _add_topping(self, topping):
        self.__toppings.append(topping)
    
    def __str__(self):
        return f"{self.__size} pizza with {self.__crust} crust and toppings: {', '.join(self.__toppings)}"

class PizzaBuilder:
    def __init__(self):
        self.__pizza = Pizza()
    
    def size(self, size):
        self.__pizza._set_size(size)
        return self
    
    def crust(self, crust):
        self.__pizza._set_crust(crust)
        return self
    
    def add_topping(self, topping):
        self.__pizza._add_topping(topping)
        return self
    
    def build(self):
        return self.__pizza

# Usage
pizza = (PizzaBuilder()
         .size("Large")
         .crust("Thin")
         .add_topping("Pepperoni")
         .add_topping("Mushrooms")
         .add_topping("Olives")
         .build())

print(pizza)
```

---

## **Encapsulation Best Practices**

### **1. Start with Private, Expose as Needed**

```python
# Default to private
class Example:
    def __init__(self):
        self.__data = []  # Private by default
    
    # Only expose what's necessary
    def add_item(self, item):
        self.__data.append(item)
    
    def get_count(self):
        return len(self.__data)
    
    # Don't expose: direct access to __data
```

### **2. Validate in Setters**

```python
class Temperature:
    def __init__(self, celsius):
        self.celsius = celsius  # Uses setter
    
    @property
    def celsius(self):
        return self.__celsius
    
    @celsius.setter
    def celsius(self, value):
        if value < -273.15:  # Absolute zero
            raise ValueError("Temperature below absolute zero")
        self.__celsius = value
```

### **3. Use Properties for Computed Values**

```python
class Circle:
    def __init__(self, radius):
        self.__radius = radius
    
    @property
    def radius(self):
        return self.__radius
    
    @radius.setter
    def radius(self, value):
        if value < 0:
            raise ValueError("Radius cannot be negative")
        self.__radius = value
    
    @property
    def diameter(self):
        return self.__radius * 2
    
    @property
    def area(self):
        return 3.14159 * self.__radius ** 2
    
    @property
    def circumference(self):
        return 2 * 3.14159 * self.__radius
```

### **4. Keep Internal Complexity Hidden**

```python
class RecommendationEngine:
    def __init__(self):
        self.__user_data = {}
        self.__item_data = {}
        self.__model = None
    
    def get_recommendations(self, user_id, count=10):
        """Simple public interface"""
        return self.__generate_recommendations(user_id, count)
    
    # Complex internal methods
    def __generate_recommendations(self, user_id, count):
        user_vector = self.__get_user_vector(user_id)
        candidates = self.__get_candidate_items(user_id)
        scores = self.__score_candidates(user_vector, candidates)
        return self.__rank_and_filter(scores, count)
    
    def __get_user_vector(self, user_id):
        # Complex ML model logic
        pass
    
    def __get_candidate_items(self, user_id):
        # Database queries, filtering
        pass
    
    def __score_candidates(self, user_vector, candidates):
        # Similarity calculations
        pass
    
    def __rank_and_filter(self, scores, count):
        # Sorting, diversity, business rules
        pass
```

### **5. Document Public Interface**

```python
class Cache:
    """
    Simple in-memory cache with TTL (time-to-live) support.
    
    Public Interface:
        set(key, value, ttl=None): Store a value
        get(key): Retrieve a value (None if expired/missing)
        delete(key): Remove a value
        clear(): Remove all values
    """
    
    def __init__(self):
        self.__data = {}
        self.__expiry = {}
    
    def set(self, key, value, ttl=None):
        """Store a value with optional time-to-live in seconds"""
        self.__data[key] = value
        if ttl:
            self.__expiry[key] = time.time() + ttl
    
    def get(self, key):
        """Retrieve value, returns None if not found or expired"""
        if self.__is_expired(key):
            self.delete(key)
            return None
        return self.__data.get(key)
    
    def delete(self, key):
        """Remove a key from cache"""
        self.__data.pop(key, None)
        self.__expiry.pop(key, None)
    
    def clear(self):
        """Remove all cached values"""
        self.__data.clear()
        self.__expiry.clear()
    
    def __is_expired(self, key):
        """Private helper to check expiration"""
        if key not in self.__expiry:
            return False
        return time.time() > self.__expiry[key]
```

---

## **Common Mistakes**

### **1. Over-encapsulation**

```python
# TOO MUCH: Simple data doesn't need heavy encapsulation
class Point:
    def __init__(self, x, y):
        self.__x = x
        self.__y = y
    
    def get_x(self):
        return self.__x
    
    def set_x(self, x):
        self.__x = x
    
    def get_y(self):
        return self.__y
    
    def set_y(self, y):
        self.__y = y

# BETTER: Just use public attributes for simple data
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
```

### **2. Exposing Internal Data Structures**

```python
# BAD: Returns reference to private list
class Team:
    def __init__(self):
        self.__members = []
    
    def get_members(self):
        return self.__members  # Oops! Can be modified externally

team = Team()
members = team.get_members()
members.append("Intruder")  # Broke encapsulation!

# GOOD: Return a copy
class Team:
    def __init__(self):
        self.__members = []
    
    def get_members(self):
        return list(self.__members)  # Return copy
    
    # Or even better: provide specific methods
    def add_member(self, member):
        self.__members.append(member)
    
    def remove_member(self, member):
        self.__members.remove(member)
    
    def get_member_count(self):
        return len(self.__members)
```

### **3. Inconsistent Access Patterns**

```python
# BAD: Mixed direct access and methods
class Person:
    def __init__(self, name, age):
        self.name = name  # Public
        self.__age = age  # Private
    
    def get_age(self):
        return self.__age

# GOOD: Consistent approach
class Person:
    def __init__(self, name, age):
        self.__name = name
        self.__age = age
    
    @property
    def name(self):
        return self.__name
    
    @property
    def age(self):
        return self.__age
```

---

## **Key Takeaways**

**Encapsulation is about**:

1. **Bundling** data and methods together
2. **Hiding** implementation details
3. **Controlling** access through a public interface
4. **Protecting** object state from corruption
5. **Flexibility** to change internals without breaking external code

**Benefits**:

- Data integrity and validation
- Easier maintenance and refactoring
- Reduced coupling between components
- Clearer interfaces
- Ability to add logging, caching, etc. transparently

**Remember**: Encapsulation isn't about making everything private—it's about thoughtfully designing what should be public (the contract) and what should be hidden (the implementation).
# Abstraction

Abstraction is one of the four fundamental pillars of Object-Oriented Programming. While encapsulation is about _hiding_ implementation details, abstraction is about _simplifying_ complexity by modeling only the essential features relevant to your problem domain.

Think of abstraction as creating a simplified model that captures what matters while ignoring what doesn't.

---

## **Core Concept**

Abstraction means:

1. **Focus on "what" not "how"**: Define what an object does, not how it does it
2. **Hide complexity**: Show only essential features, hide unnecessary details
3. **Create models**: Represent real-world concepts at an appropriate level of detail
4. **Establish contracts**: Define interfaces that specify behavior without implementation

**Real-world analogy**: A TV remote control

- **Abstraction**: Simple buttons like "Power," "Volume," "Channel"
- **Hidden complexity**: Signal encoding, infrared transmission, frequency modulation, microcontroller operations
- **You interact with**: High-level concepts (turn on, change channel)
- **You don't need to know**: Electronics, signal processing, wireless protocols

---

## **Abstraction vs Encapsulation**

While related, they serve different purposes:

|Aspect|Abstraction|Encapsulation|
|---|---|---|
|**Focus**|What an object does|How to protect data|
|**Purpose**|Simplify complexity|Hide implementation|
|**Achieved by**|Abstract classes, interfaces|Access modifiers, private members|
|**Question**|"What operations are available?"|"How is data protected?"|
|**Example**|"A vehicle can move"|"Speed is private, use setSpeed()"|

```python
# Abstraction: Define WHAT a shape can do
class Shape:  # Abstract concept
    def area(self):
        pass
    
    def perimeter(self):
        pass

# Encapsulation: Hide HOW it's calculated
class Circle(Shape):
    def __init__(self, radius):
        self.__radius = radius  # Encapsulation: private data
    
    def area(self):  # Abstraction: implements the interface
        return 3.14159 * self.__radius ** 2
    
    def perimeter(self):
        return 2 * 3.14159 * self.__radius
```

---

## **Levels of Abstraction**

Abstraction exists at multiple levels:

### **Level 1: Low-level Details**

```python
# Direct bit manipulation
register = 0b10101010
register |= (1 << 3)  # Set bit 3
```

### **Level 2: Basic Abstractions**

```python
# Hardware abstraction
file_descriptor = open("data.txt", "r")
content = os.read(file_descriptor, 1024)
```

### **Level 3: Higher-level Abstractions**

```python
# File system abstraction
with open("data.txt", "r") as file:
    content = file.read()
```

### **Level 4: Domain-level Abstractions**

```python
# Business logic abstraction
user_profile = UserRepository.find_by_email("user@example.com")
```

Each level builds on the one below, hiding more complexity.

---

## **Achieving Abstraction**

### **1. Abstract Classes**

Define a blueprint that subclasses must follow:

```python
from abc import ABC, abstractmethod

class Vehicle(ABC):
    """Abstract base class - cannot be instantiated"""
    
    def __init__(self, make, model):
        self.make = make
        self.model = model
    
    @abstractmethod
    def start(self):
        """Every vehicle must implement start"""
        pass
    
    @abstractmethod
    def stop(self):
        """Every vehicle must implement stop"""
        pass
    
    @abstractmethod
    def get_max_speed(self):
        """Every vehicle must report max speed"""
        pass
    
    # Concrete method (shared implementation)
    def describe(self):
        return f"{self.make} {self.model}"

# Cannot do this:
# vehicle = Vehicle("Generic", "Vehicle")  # Error: Can't instantiate abstract class

class Car(Vehicle):
    def __init__(self, make, model, doors):
        super().__init__(make, model)
        self.doors = doors
    
    def start(self):
        return "Turn key to start engine"
    
    def stop(self):
        return "Press brake pedal"
    
    def get_max_speed(self):
        return 180  # km/h

class Bicycle(Vehicle):
    def __init__(self, make, model, gears):
        super().__init__(make, model)
        self.gears = gears
    
    def start(self):
        return "Start pedaling"
    
    def stop(self):
        return "Squeeze brake levers"
    
    def get_max_speed(self):
        return 40  # km/h

# Usage - work with abstraction, not concrete types
def test_vehicle(vehicle: Vehicle):
    """Accepts ANY vehicle - abstraction in action"""
    print(vehicle.describe())
    print(vehicle.start())
    print(f"Max speed: {vehicle.get_max_speed()} km/h")

car = Car("Toyota", "Camry", 4)
bike = Bicycle("Trek", "FX3", 24)

test_vehicle(car)   # Works
test_vehicle(bike)  # Works too - same interface
```

**Benefits**:

- Forces consistent interface across implementations
- Prevents instantiation of incomplete classes
- Documents what subclasses must implement
- Enables polymorphism

### **2. Interfaces (Protocol in Python)**

Define a contract without any implementation:

```python
from typing import Protocol

class Drawable(Protocol):
    """Interface - defines contract only"""
    def draw(self) -> str:
        """Return string representation of drawing"""
        ...
    
    def get_color(self) -> str:
        """Return the color"""
        ...

# Multiple classes can implement the same interface
class Circle:
    def __init__(self, radius, color):
        self.radius = radius
        self.color = color
    
    def draw(self) -> str:
        return f"Drawing a circle with radius {self.radius}"
    
    def get_color(self) -> str:
        return self.color

class Rectangle:
    def __init__(self, width, height, color):
        self.width = width
        self.height = height
        self.color = color
    
    def draw(self) -> str:
        return f"Drawing a rectangle {self.width}x{self.height}"
    
    def get_color(self) -> str:
        return self.color

# Function works with any Drawable
def render(shape: Drawable):
    print(shape.draw())
    print(f"Color: {shape.get_color()}")

circle = Circle(5, "red")
rectangle = Rectangle(10, 20, "blue")

render(circle)     # Works
render(rectangle)  # Works - both implement Drawable interface
```

### **3. Abstraction Through Methods**

Hide complex operations behind simple method calls:

```python
class EmailService:
    """Abstract away email complexity"""
    
    def send_email(self, to: str, subject: str, body: str):
        """
        Simple interface - user doesn't need to know about:
        - SMTP protocol
        - Connection management
        - Authentication
        - Error handling
        - Retry logic
        """
        self._connect_to_server()
        self._authenticate()
        self._compose_message(to, subject, body)
        self._send_message()
        self._handle_response()
        self._disconnect()
    
    # All complexity hidden in private methods
    def _connect_to_server(self):
        # Complex connection logic with retry
        pass
    
    def _authenticate(self):
        # OAuth, tokens, encryption
        pass
    
    def _compose_message(self, to, subject, body):
        # MIME formatting, encoding, headers
        pass
    
    def _send_message(self):
        # SMTP protocol implementation
        pass
    
    def _handle_response(self):
        # Parse response, check for errors
        pass
    
    def _disconnect(self):
        # Clean shutdown
        pass

# Usage - incredibly simple despite complex implementation
email_service = EmailService()
email_service.send_email("user@example.com", "Hello", "Test message")
```

---

## **Real-World Examples**

### **Example 1: Payment Processing**

```python
from abc import ABC, abstractmethod

class PaymentProcessor(ABC):
    """Abstract payment interface"""
    
    @abstractmethod
    def process_payment(self, amount: float) -> bool:
        """Process a payment"""
        pass
    
    @abstractmethod
    def refund_payment(self, transaction_id: str, amount: float) -> bool:
        """Refund a payment"""
        pass
    
    @abstractmethod
    def get_transaction_status(self, transaction_id: str) -> str:
        """Check transaction status"""
        pass

class CreditCardProcessor(PaymentProcessor):
    """Concrete implementation for credit cards"""
    
    def process_payment(self, amount: float) -> bool:
        # Credit card specific logic
        print(f"Processing credit card payment: ${amount}")
        # Validate card, check funds, contact payment gateway
        return True
    
    def refund_payment(self, transaction_id: str, amount: float) -> bool:
        print(f"Refunding ${amount} to credit card")
        # Reverse transaction through payment gateway
        return True
    
    def get_transaction_status(self, transaction_id: str) -> str:
        return "Completed"

class PayPalProcessor(PaymentProcessor):
    """Concrete implementation for PayPal"""
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing PayPal payment: ${amount}")
        # PayPal API calls, OAuth, different workflow
        return True
    
    def refund_payment(self, transaction_id: str, amount: float) -> bool:
        print(f"Refunding ${amount} via PayPal")
        # PayPal refund API
        return True
    
    def get_transaction_status(self, transaction_id: str) -> str:
        return "Completed"

class CryptocurrencyProcessor(PaymentProcessor):
    """Concrete implementation for crypto"""
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing cryptocurrency payment: ${amount}")
        # Blockchain interaction, wallet management
        return True
    
    def refund_payment(self, transaction_id: str, amount: float) -> bool:
        print(f"Refunding ${amount} in cryptocurrency")
        # Blockchain transaction
        return True
    
    def get_transaction_status(self, transaction_id: str) -> str:
        return "Pending confirmation"

# E-commerce system works with abstraction
class ShoppingCart:
    def __init__(self, payment_processor: PaymentProcessor):
        self.payment_processor = payment_processor
        self.items = []
    
    def add_item(self, item, price):
        self.items.append((item, price))
    
    def checkout(self):
        total = sum(price for _, price in self.items)
        
        # Works with ANY payment processor - abstraction in action
        if self.payment_processor.process_payment(total):
            print("Payment successful!")
            return True
        else:
            print("Payment failed")
            return False

# Can swap payment methods easily
cart1 = ShoppingCart(CreditCardProcessor())
cart1.add_item("Book", 29.99)
cart1.checkout()  # Uses credit card

cart2 = ShoppingCart(PayPalProcessor())
cart2.add_item("Game", 59.99)
cart2.checkout()  # Uses PayPal

cart3 = ShoppingCart(CryptocurrencyProcessor())
cart3.add_item("Software", 99.99)
cart3.checkout()  # Uses crypto
```

**Benefits**:

- Easy to add new payment methods (just implement interface)
- Shopping cart doesn't care about payment details
- Can switch payment processors without changing cart code
- Each processor handles its own complexity

### **Example 2: Data Storage Abstraction**

```python
from abc import ABC, abstractmethod

class DataStore(ABC):
    """Abstract data storage interface"""
    
    @abstractmethod
    def save(self, key: str, value: any) -> bool:
        pass
    
    @abstractmethod
    def load(self, key: str) -> any:
        pass
    
    @abstractmethod
    def delete(self, key: str) -> bool:
        pass
    
    @abstractmethod
    def exists(self, key: str) -> bool:
        pass

class FileSystemStore(DataStore):
    """Store data in files"""
    
    def __init__(self, base_path):
        self.base_path = base_path
    
    def save(self, key: str, value: any) -> bool:
        import json
        file_path = f"{self.base_path}/{key}.json"
        with open(file_path, 'w') as f:
            json.dump(value, f)
        return True
    
    def load(self, key: str) -> any:
        import json
        file_path = f"{self.base_path}/{key}.json"
        with open(file_path, 'r') as f:
            return json.load(f)
    
    def delete(self, key: str) -> bool:
        import os
        file_path = f"{self.base_path}/{key}.json"
        os.remove(file_path)
        return True
    
    def exists(self, key: str) -> bool:
        import os
        file_path = f"{self.base_path}/{key}.json"
        return os.path.exists(file_path)

class DatabaseStore(DataStore):
    """Store data in database"""
    
    def __init__(self, connection_string):
        self.connection = self._connect(connection_string)
    
    def save(self, key: str, value: any) -> bool:
        # SQL INSERT or UPDATE
        query = "INSERT INTO data (key, value) VALUES (?, ?)"
        self.connection.execute(query, (key, str(value)))
        return True
    
    def load(self, key: str) -> any:
        # SQL SELECT
        query = "SELECT value FROM data WHERE key = ?"
        result = self.connection.execute(query, (key,))
        return result.fetchone()
    
    def delete(self, key: str) -> bool:
        # SQL DELETE
        query = "DELETE FROM data WHERE key = ?"
        self.connection.execute(query, (key,))
        return True
    
    def exists(self, key: str) -> bool:
        query = "SELECT COUNT(*) FROM data WHERE key = ?"
        result = self.connection.execute(query, (key,))
        return result.fetchone()[0] > 0
    
    def _connect(self, connection_string):
        # Database connection logic
        pass

class MemoryStore(DataStore):
    """Store data in memory (cache)"""
    
    def __init__(self):
        self.data = {}
    
    def save(self, key: str, value: any) -> bool:
        self.data[key] = value
        return True
    
    def load(self, key: str) -> any:
        return self.data.get(key)
    
    def delete(self, key: str) -> bool:
        if key in self.data:
            del self.data[key]
            return True
        return False
    
    def exists(self, key: str) -> bool:
        return key in self.data

class CloudStore(DataStore):
    """Store data in cloud (S3, Azure, etc.)"""
    
    def __init__(self, bucket_name):
        self.bucket = bucket_name
        self.client = self._init_cloud_client()
    
    def save(self, key: str, value: any) -> bool:
        # Upload to cloud storage
        self.client.put_object(Bucket=self.bucket, Key=key, Body=value)
        return True
    
    def load(self, key: str) -> any:
        # Download from cloud
        response = self.client.get_object(Bucket=self.bucket, Key=key)
        return response['Body'].read()
    
    def delete(self, key: str) -> bool:
        self.client.delete_object(Bucket=self.bucket, Key=key)
        return True
    
    def exists(self, key: str) -> bool:
        try:
            self.client.head_object(Bucket=self.bucket, Key=key)
            return True
        except:
            return False
    
    def _init_cloud_client(self):
        # Initialize cloud SDK
        pass

# Application works with abstraction
class UserManager:
    def __init__(self, data_store: DataStore):
        self.store = data_store  # Works with ANY storage
    
    def save_user(self, user_id, user_data):
        self.store.save(f"user_{user_id}", user_data)
    
    def get_user(self, user_id):
        return self.store.load(f"user_{user_id}")
    
    def delete_user(self, user_id):
        self.store.delete(f"user_{user_id}")

# Can easily switch storage backends
# Development: use memory
manager_dev = UserManager(MemoryStore())

# Testing: use filesystem
manager_test = UserManager(FileSystemStore("/tmp/test_data"))

# Production: use database
manager_prod = UserManager(DatabaseStore("postgresql://..."))

# Scale up: use cloud storage
manager_scale = UserManager(CloudStore("my-bucket"))

# Same code works with all storage types!
```

**Benefits**:

- Easy to switch storage backends (dev, test, prod)
- Can add new storage types without changing UserManager
- Application logic separated from storage details
- Can mock storage for testing

### **Example 3: Notification System**

```python
from abc import ABC, abstractmethod
from typing import List

class NotificationChannel(ABC):
    """Abstract notification interface"""
    
    @abstractmethod
    def send(self, recipient: str, message: str) -> bool:
        pass
    
    @abstractmethod
    def is_available(self) -> bool:
        pass

class EmailChannel(NotificationChannel):
    def send(self, recipient: str, message: str) -> bool:
        print(f"Sending email to {recipient}: {message}")
        # SMTP logic
        return True
    
    def is_available(self) -> bool:
        # Check SMTP server availability
        return True

class SMSChannel(NotificationChannel):
    def send(self, recipient: str, message: str) -> bool:
        print(f"Sending SMS to {recipient}: {message}")
        # SMS gateway API
        return True
    
    def is_available(self) -> bool:
        # Check SMS service status
        return True

class PushNotificationChannel(NotificationChannel):
    def send(self, recipient: str, message: str) -> bool:
        print(f"Sending push notification to {recipient}: {message}")
        # Firebase, APNs, etc.
        return True
    
    def is_available(self) -> bool:
        # Check push service status
        return True

class SlackChannel(NotificationChannel):
    def send(self, recipient: str, message: str) -> bool:
        print(f"Sending Slack message to {recipient}: {message}")
        # Slack API
        return True
    
    def is_available(self) -> bool:
        # Check Slack connection
        return True

class NotificationService:
    """High-level service that uses abstraction"""
    
    def __init__(self):
        self.channels: List[NotificationChannel] = []
    
    def add_channel(self, channel: NotificationChannel):
        self.channels.append(channel)
    
    def notify(self, recipient: str, message: str, priority: str = "normal"):
        """Send notification through all available channels"""
        
        if priority == "high":
            # High priority: try all channels
            for channel in self.channels:
                if channel.is_available():
                    channel.send(recipient, f"[URGENT] {message}")
        else:
            # Normal priority: use first available channel
            for channel in self.channels:
                if channel.is_available():
                    channel.send(recipient, message)
                    break
    
    def broadcast(self, recipients: List[str], message: str):
        """Send to multiple recipients"""
        for recipient in recipients:
            self.notify(recipient, message)

# Setup notification system
notifier = NotificationService()
notifier.add_channel(EmailChannel())
notifier.add_channel(SMSChannel())
notifier.add_channel(PushNotificationChannel())
notifier.add_channel(SlackChannel())

# Use simple interface - complexity hidden
notifier.notify("user@example.com", "Your order has shipped")
notifier.notify("+1234567890", "Security alert!", priority="high")
notifier.broadcast(
    ["admin1@example.com", "admin2@example.com"],
    "System maintenance scheduled"
)
```

**Benefits**:

- Easy to add new notification channels
- Can configure channels per environment
- Retry logic, fallback channels easy to implement
- Each channel handles its own protocol complexity

---

## **Abstraction Layers in Software**

Complex systems use multiple layers of abstraction:

```python
# Layer 1: Hardware Abstraction
class NetworkInterface:
    def send_packet(self, data: bytes):
        # Direct hardware interaction
        pass

# Layer 2: Protocol Abstraction
class TCPConnection:
    def __init__(self, interface: NetworkInterface):
        self.interface = interface
    
    def send_data(self, data: bytes):
        # Handle TCP protocol: packets, acknowledgments, retries
        self.interface.send_packet(data)

# Layer 3: Application Protocol Abstraction
class HTTPClient:
    def __init__(self, connection: TCPConnection):
        self.connection = connection
    
    def get(self, url: str) -> str:
        # Handle HTTP: headers, status codes, redirects
        request = f"GET {url} HTTP/1.1\r\n\r\n"
        self.connection.send_data(request.encode())

# Layer 4: Business Logic Abstraction
class APIClient:
    def __init__(self, http_client: HTTPClient):
        self.http = http_client
    
    def get_user(self, user_id: int):
        # High-level business operation
        response = self.http.get(f"/api/users/{user_id}")
        return json.loads(response)

# Usage: Highest level of abstraction
api = APIClient(HTTPClient(TCPConnection(NetworkInterface())))
user = api.get_user(123)  # Simple call, complex operation

# User doesn't know about:
# - Hardware interfaces
# - TCP protocol
# - HTTP protocol
# - JSON parsing
```

---

## **Abstraction Best Practices**

### **1. Define Clear Contracts**

```python
from abc import ABC, abstractmethod

class Repository(ABC):
    """
    Clear contract: what operations are supported,
    what parameters they take, what they return
    """
    
    @abstractmethod
    def find_by_id(self, id: int) -> dict:
        """Find entity by ID, returns dict or None"""
        pass
    
    @abstractmethod
    def find_all(self) -> List[dict]:
        """Return all entities"""
        pass
    
    @abstractmethod
    def save(self, entity: dict) -> bool:
        """Save entity, return success status"""
        pass
    
    @abstractmethod
    def delete(self, id: int) -> bool:
        """Delete entity, return success status"""
        pass
```

### **2. Appropriate Level of Abstraction**

```python
# TOO LOW-LEVEL (leaking implementation details)
class UserService:
    def execute_sql_query(self, sql: str):
        # Exposes database details
        pass

# TOO HIGH-LEVEL (not useful)
class UserService:
    def do_stuff(self, data):
        # Too vague
        pass

# JUST RIGHT (domain-appropriate)
class UserService:
    def create_user(self, username: str, email: str) -> User:
        pass
    
    def get_user_by_email(self, email: str) -> User:
        pass
    
    def update_user_profile(self, user_id: int, profile_data: dict) -> bool:
        pass
```

### **3. Avoid Leaky Abstractions**

```python
# BAD: Leaky abstraction - exposes implementation
class FileStorage:
    def save(self, data):
        file_handle = open("data.txt", "w")  # Exposes file handle
        return file_handle  # Caller has to manage file

# GOOD: Proper abstraction
class FileStorage:
    def save(self, key: str, data: str) -> bool:
        try:
            with open(f"{key}.txt", "w") as f:
                f.write(data)
            return True
        except:
            return False
    # Caller doesn't deal with file handles
```

### **4. Consistent Abstraction Level**

```python
# BAD: Mixed abstraction levels
class OrderProcessor:
    def process_order(self, order):
        # High-level operation
        self.validate_order(order)
        
        # Suddenly low-level database details
        cursor = self.db.execute("INSERT INTO orders...")
        
        # Back to high-level
        self.send_confirmation_email(order)

# GOOD: Consistent abstraction level
class OrderProcessor:
    def process_order(self, order):
        self.validate_order(order)
        self.save_order(order)  # Hide database details
        self.send_confirmation_email(order)
    
    def save_order(self, order):
        # Database details encapsulated here
        cursor = self.db.execute("INSERT INTO orders...")
```

### **5. Dependency Inversion**

Depend on abstractions, not concrete implementations:

```python
# BAD: Depends on concrete class
class UserController:
    def __init__(self):
        self.database = MySQLDatabase()  # Tightly coupled
    
    def get_user(self, id):
        return self.database.query(f"SELECT * FROM users WHERE id={id}")

# GOOD: Depends on abstraction
class UserController:
    def __init__(self, repository: UserRepository):  # Abstract dependency
        self.repository = repository
    
    def get_user(self, id):
        return self.repository.find_by_id(id)

# Can inject any implementation
controller1 = UserController(MySQLUserRepository())
controller2 = UserController(MongoUserRepository())
controller3 = UserController(InMemoryUserRepository())  # For testing
```

---

## **Benefits of Abstraction**

1. **Simplicity**: Complex systems become manageable
2. **Flexibility**: Easy to change implementations
3. **Reusability**: Abstract components work in many contexts
4. **Testability**: Can mock abstractions for testing
5. **Maintainability**: Changes localized to implementations
6. **Scalability**: Add new implementations without changing existing code
7. **Collaboration**: Teams can work on different implementations independently

---

## **Common Pitfalls**

### **1. Over-abstraction**

```python
# TOO MUCH: Unnecessary abstraction
class StringWrapper:
    def __init__(self, value: str):
        self.value = value
    
    def get_value(self) -> str:
        return self.value

# SIMPLE: Just use a string
name = "Alice"
```

### **2. Wrong Abstraction**

```python
# BAD: Shape abstraction that doesn't fit all shapes
class Shape(ABC):
    @abstractmethod
    def get_corners(self):
        pass  # Doesn't work for circles!

# BETTER: Abstract common operations
class Shape(ABC):
    @abstractmethod
    def area(self):
        pass
    
    @abstractmethod
    def perimeter(self):
        pass
```

### **3. Incomplete Abstraction**

```python
# BAD: Missing important operations
class DatabaseConnection(ABC):
    @abstractmethod
    def query(self, sql):
        pass
    # Missing: transactions, error handling, connection pooling

# BETTER: Complete interface
class DatabaseConnection(ABC):
    @abstractmethod
    def query(self, sql):
        pass
    
    @abstractmethod
    def begin_transaction(self):
        pass
    
    @abstractmethod
    def commit(self):
        pass
    
    @abstractmethod
    def rollback(self):
        pass
```

---

## **Key Takeaways**

**Abstraction is about**:

1. **Simplifying complexity** by hiding details
2. **Focusing on essential features** for your domain
3. **Creating contracts** through interfaces and abstract classes
4. **Enabling flexibility** to change implementations
5. **Building layers** where each level hides details below

**Remember**: Good abstraction makes complex systems feel simple. Bad abstraction adds confusion without benefit.

The goal is to find the right level—abstract enough to be flexible and simple, concrete enough to be useful and understandable.

Would you like to explore the other SOLID principles, see how abstraction relates to design patterns, or dive into more advanced abstraction techniques?


- Polymorphism
    
- Inheritance
    
- SOLID principles
    
- Coupling vs Cohesion
    
- Dependency Inversion (concept)

---
## 6️⃣ Design Patterns (Theory Only)

- Why patterns exist
    
- Creational vs Structural vs Behavioral
    
- Problem → Context → Solution → Trade-offs
    
- Overengineering dangers
    
- Anti-patterns
    

