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


# Polymorphism

Polymorphism is one of the four fundamental pillars of Object-Oriented Programming. The term comes from Greek: "poly" (many) + "morph" (form) = "many forms." It allows objects of different types to be treated through a common interface while each behaves differently.

Think of it as: **"Same interface, different behaviors"**

---

## **Core Concept**

Polymorphism means the same operation can behave differently on different classes. You can write code that works with a general type, and it automatically works correctly with any specific subtype.

**Real-world analogy**: A universal remote control

- **Interface**: Same buttons (power, volume, channel)
- **Polymorphic behavior**: Power button works differently for TV (turn on screen), stereo (start amplifier), lights (flip switch)
- **Same action, different results** depending on the device

---

## **Types of Polymorphism**

### **1. Compile-time Polymorphism (Static)**

- Method overloading
- Operator overloading
- Resolved at compile time

### **2. Runtime Polymorphism (Dynamic)**

- Method overriding
- Resolved at runtime based on actual object type
- **Most important for OOP**

---

## **Method Overriding (Runtime Polymorphism)**

Subclasses provide their own implementation of methods defined in the parent class.

### **Basic Example**

```python
class Animal:
    def __init__(self, name):
        self.name = name
    
    def make_sound(self):
        return "Some generic sound"
    
    def introduce(self):
        return f"I am {self.name}"

class Dog(Animal):
    def make_sound(self):
        """Override parent method"""
        return "Woof! Woof!"

class Cat(Animal):
    def make_sound(self):
        """Override parent method"""
        return "Meow!"

class Cow(Animal):
    def make_sound(self):
        """Override parent method"""
        return "Moo!"

# Polymorphism in action
def animal_sound(animal: Animal):
    """
    Takes ANY animal - polymorphism!
    Calls make_sound(), but behavior depends on actual type
    """
    print(f"{animal.name} says: {animal.make_sound()}")

# Create different animals
dog = Dog("Rex")
cat = Cat("Whiskers")
cow = Cow("Bessie")

# Same function, different behaviors
animal_sound(dog)  # Rex says: Woof! Woof!
animal_sound(cat)  # Whiskers says: Meow!
animal_sound(cow)  # Bessie says: Moo!

# Can store in a collection and iterate
animals = [Dog("Buddy"), Cat("Mittens"), Cow("Daisy"), Dog("Max")]

for animal in animals:
    animal_sound(animal)
# Each behaves correctly according to its actual type!
```

**Key insight**: The function `animal_sound()` doesn't need to know the specific type. It works with the `Animal` interface, but each object behaves according to its actual class.

---

## **The Power of Polymorphism**

### **Example 1: Payment Processing**

```python
from abc import ABC, abstractmethod

class PaymentMethod(ABC):
    @abstractmethod
    def process_payment(self, amount: float) -> bool:
        pass
    
    @abstractmethod
    def get_transaction_fee(self, amount: float) -> float:
        pass

class CreditCard(PaymentMethod):
    def __init__(self, card_number, cvv):
        self.card_number = card_number
        self.cvv = cvv
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing ${amount} via Credit Card ending in {self.card_number[-4:]}")
        # Credit card processing logic
        return True
    
    def get_transaction_fee(self, amount: float) -> float:
        return amount * 0.029 + 0.30  # 2.9% + $0.30

class PayPal(PaymentMethod):
    def __init__(self, email):
        self.email = email
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing ${amount} via PayPal account {self.email}")
        # PayPal API logic
        return True
    
    def get_transaction_fee(self, amount: float) -> float:
        return amount * 0.034 + 0.30  # 3.4% + $0.30

class BankTransfer(PaymentMethod):
    def __init__(self, account_number, routing_number):
        self.account_number = account_number
        self.routing_number = routing_number
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing ${amount} via Bank Transfer")
        # ACH/Wire transfer logic
        return True
    
    def get_transaction_fee(self, amount: float) -> float:
        return 1.00  # Flat $1 fee

class Cryptocurrency(PaymentMethod):
    def __init__(self, wallet_address):
        self.wallet_address = wallet_address
    
    def process_payment(self, amount: float) -> bool:
        print(f"Processing ${amount} via Cryptocurrency to {self.wallet_address[:10]}...")
        # Blockchain transaction
        return True
    
    def get_transaction_fee(self, amount: float) -> float:
        return amount * 0.01  # 1% network fee

# Checkout system uses polymorphism
class CheckoutSystem:
    def process_order(self, payment_method: PaymentMethod, amount: float):
        """
        Works with ANY payment method!
        Polymorphism allows adding new payment types without changing this code
        """
        fee = payment_method.get_transaction_fee(amount)
        total = amount + fee
        
        print(f"Order amount: ${amount:.2f}")
        print(f"Transaction fee: ${fee:.2f}")
        print(f"Total: ${total:.2f}")
        
        if payment_method.process_payment(total):
            print("✓ Payment successful!\n")
            return True
        else:
            print("✗ Payment failed\n")
            return False

# Usage - same checkout system, different payment methods
checkout = CheckoutSystem()

# Customer 1: Credit card
checkout.process_order(CreditCard("4532-1234-5678-9010", "123"), 100.00)

# Customer 2: PayPal
checkout.process_order(PayPal("user@example.com"), 100.00)

# Customer 3: Bank transfer
checkout.process_order(BankTransfer("123456789", "987654321"), 100.00)

# Customer 4: Cryptocurrency
checkout.process_order(Cryptocurrency("0x742d35Cc6..."), 100.00)

# Adding a new payment method? Just create a new class!
# No need to modify CheckoutSystem
```

**Output:**

```
Order amount: $100.00
Transaction fee: $3.20
Total: $103.20
Processing $103.20 via Credit Card ending in 9010
✓ Payment successful!

Order amount: $100.00
Transaction fee: $3.70
Total: $103.70
Processing $103.70 via PayPal account user@example.com
✓ Payment successful!

Order amount: $100.00
Transaction fee: $1.00
Total: $101.00
Processing $101.00 via Bank Transfer
✓ Payment successful!

Order amount: $100.00
Transaction fee: $1.00
Total: $101.00
Processing $101.00 via Cryptocurrency to 0x742d35Cc6...
✓ Payment successful!
```

**Benefits**:

- Add new payment methods without changing checkout code
- Each payment type handles its own complexity
- Same interface for all payment types
- Easy to test (mock payment methods)

---

### **Example 2: Graphics System**

```python
from abc import ABC, abstractmethod
from typing import List

class Shape(ABC):
    """Abstract base class for all shapes"""
    
    def __init__(self, color: str):
        self.color = color
    
    @abstractmethod
    def area(self) -> float:
        pass
    
    @abstractmethod
    def perimeter(self) -> float:
        pass
    
    @abstractmethod
    def draw(self) -> str:
        pass

class Circle(Shape):
    def __init__(self, color: str, radius: float):
        super().__init__(color)
        self.radius = radius
    
    def area(self) -> float:
        return 3.14159 * self.radius ** 2
    
    def perimeter(self) -> float:
        return 2 * 3.14159 * self.radius
    
    def draw(self) -> str:
        return f"Drawing a {self.color} circle with radius {self.radius}"

class Rectangle(Shape):
    def __init__(self, color: str, width: float, height: float):
        super().__init__(color)
        self.width = width
        self.height = height
    
    def area(self) -> float:
        return self.width * self.height
    
    def perimeter(self) -> float:
        return 2 * (self.width + self.height)
    
    def draw(self) -> str:
        return f"Drawing a {self.color} rectangle {self.width}x{self.height}"

class Triangle(Shape):
    def __init__(self, color: str, base: float, height: float):
        super().__init__(color)
        self.base = base
        self.height = height
    
    def area(self) -> float:
        return 0.5 * self.base * self.height
    
    def perimeter(self) -> float:
        # Simplified: assuming equilateral
        return 3 * self.base
    
    def draw(self) -> str:
        return f"Drawing a {self.color} triangle with base {self.base}"

class Canvas:
    """Graphics canvas that works with any shape"""
    
    def __init__(self):
        self.shapes: List[Shape] = []
    
    def add_shape(self, shape: Shape):
        """Add any shape - polymorphism!"""
        self.shapes.append(shape)
    
    def render(self):
        """Render all shapes"""
        print("=== Rendering Canvas ===")
        for shape in self.shapes:
            print(shape.draw())
        print()
    
    def calculate_total_area(self) -> float:
        """Calculate total area of all shapes"""
        return sum(shape.area() for shape in self.shapes)
    
    def get_statistics(self):
        """Display statistics about all shapes"""
        print("=== Canvas Statistics ===")
        print(f"Total shapes: {len(self.shapes)}")
        print(f"Total area: {self.calculate_total_area():.2f}")
        
        for i, shape in enumerate(self.shapes, 1):
            print(f"\nShape {i}: {type(shape).__name__}")
            print(f"  Color: {shape.color}")
            print(f"  Area: {shape.area():.2f}")
            print(f"  Perimeter: {shape.perimeter():.2f}")

# Usage - polymorphism in action
canvas = Canvas()

# Add different shapes
canvas.add_shape(Circle("red", 5))
canvas.add_shape(Rectangle("blue", 10, 20))
canvas.add_shape(Triangle("green", 8, 6))
canvas.add_shape(Circle("yellow", 3))
canvas.add_shape(Rectangle("purple", 15, 15))

# Render all shapes - each draws differently
canvas.render()

# Calculate statistics - works for any shape
canvas.get_statistics()

# Can iterate and treat uniformly
print("\n=== All Shapes ===")
for shape in canvas.shapes:
    print(f"{type(shape).__name__}: area = {shape.area():.2f}")
```

**Output:**

```
=== Rendering Canvas ===
Drawing a red circle with radius 5
Drawing a blue rectangle 10x20
Drawing a green triangle with base 8
Drawing a yellow circle with radius 3
Drawing a purple rectangle 15x15

=== Canvas Statistics ===
Total shapes: 5
Total area: 531.42

Shape 1: Circle
  Color: red
  Area: 78.54
  Perimeter: 31.42

Shape 2: Rectangle
  Color: blue
  Area: 200.00
  Perimeter: 60.00

Shape 3: Triangle
  Color: green
  Area: 24.00
  Perimeter: 24.00

Shape 4: Circle
  Color: yellow
  Area: 28.27
  Perimeter: 18.85

Shape 5: Rectangle
  Color: purple
  Area: 225.00
  Perimeter: 60.00

=== All Shapes ===
Circle: area = 78.54
Rectangle: area = 200.00
Triangle: area = 24.00
Circle: area = 28.27
Rectangle: area = 225.00
```

---

### **Example 3: File Handling System**

```python
from abc import ABC, abstractmethod

class FileHandler(ABC):
    """Abstract interface for file handlers"""
    
    @abstractmethod
    def read(self, filepath: str) -> str:
        pass
    
    @abstractmethod
    def write(self, filepath: str, content: str) -> bool:
        pass
    
    @abstractmethod
    def get_format(self) -> str:
        pass

class TextFileHandler(FileHandler):
    def read(self, filepath: str) -> str:
        with open(filepath, 'r') as f:
            return f.read()
    
    def write(self, filepath: str, content: str) -> bool:
        with open(filepath, 'w') as f:
            f.write(content)
        return True
    
    def get_format(self) -> str:
        return "Plain Text"

class JSONFileHandler(FileHandler):
    def read(self, filepath: str) -> str:
        import json
        with open(filepath, 'r') as f:
            data = json.load(f)
        return str(data)
    
    def write(self, filepath: str, content: str) -> bool:
        import json
        with open(filepath, 'w') as f:
            json.dump(eval(content), f, indent=2)
        return True
    
    def get_format(self) -> str:
        return "JSON"

class XMLFileHandler(FileHandler):
    def read(self, filepath: str) -> str:
        # XML parsing logic
        return "<xml>Parsed XML content</xml>"
    
    def write(self, filepath: str, content: str) -> bool:
        # XML writing logic
        return True
    
    def get_format(self) -> str:
        return "XML"

class CSVFileHandler(FileHandler):
    def read(self, filepath: str) -> str:
        import csv
        with open(filepath, 'r') as f:
            reader = csv.reader(f)
            return str(list(reader))
    
    def write(self, filepath: str, content: str) -> bool:
        import csv
        with open(filepath, 'w', newline='') as f:
            writer = csv.writer(f)
            writer.writerows(eval(content))
        return True
    
    def get_format(self) -> str:
        return "CSV"

class FileProcessor:
    """Processes files using polymorphism"""
    
    def __init__(self, handler: FileHandler):
        self.handler = handler
    
    def process_file(self, input_path: str, output_path: str):
        """
        Read and write files - works with ANY handler!
        Behavior changes based on actual handler type
        """
        print(f"Processing file using {self.handler.get_format()} handler")
        
        # Read using handler's specific method
        content = self.handler.read(input_path)
        print(f"Read content: {content[:50]}...")
        
        # Transform content (example)
        transformed = content.upper()
        
        # Write using handler's specific method
        success = self.handler.write(output_path, transformed)
        
        if success:
            print(f"✓ File processed and saved to {output_path}\n")
        else:
            print(f"✗ Failed to process file\n")
    
    def switch_handler(self, new_handler: FileHandler):
        """Dynamically change handler - polymorphism!"""
        self.handler = new_handler

# Usage
# Same processor, different handlers
processor = FileProcessor(TextFileHandler())
processor.process_file("data.txt", "output.txt")

# Switch to JSON handler
processor.switch_handler(JSONFileHandler())
processor.process_file("data.json", "output.json")

# Switch to CSV handler
processor.switch_handler(CSVFileHandler())
processor.process_file("data.csv", "output.csv")

# Factory pattern with polymorphism
class FileHandlerFactory:
    @staticmethod
    def get_handler(file_extension: str) -> FileHandler:
        """Return appropriate handler based on file type"""
        handlers = {
            '.txt': TextFileHandler(),
            '.json': JSONFileHandler(),
            '.xml': XMLFileHandler(),
            '.csv': CSVFileHandler()
        }
        return handlers.get(file_extension, TextFileHandler())

# Automatic handler selection
def process_any_file(filepath: str):
    """Process any file type automatically"""
    import os
    _, ext = os.path.splitext(filepath)
    handler = FileHandlerFactory.get_handler(ext)
    processor = FileProcessor(handler)
    processor.process_file(filepath, f"output{ext}")

process_any_file("document.txt")
process_any_file("config.json")
process_any_file("data.csv")
```

---

## **Duck Typing (Python's Polymorphism)**

Python uses "duck typing": "If it walks like a duck and quacks like a duck, it's a duck." Objects don't need to inherit from a common base class—they just need to implement the expected interface.

```python
# No common base class needed in Python!

class Dog:
    def speak(self):
        return "Woof!"

class Cat:
    def speak(self):
        return "Meow!"

class Robot:
    def speak(self):
        return "Beep boop!"

class Person:
    def speak(self):
        return "Hello!"

# Function works with anything that has a speak() method
def make_it_speak(thing):
    """
    Polymorphism without inheritance!
    Works with any object that has speak() method
    """
    print(thing.speak())

# All work, even though they don't share a base class
make_it_speak(Dog())      # Woof!
make_it_speak(Cat())      # Meow!
make_it_speak(Robot())    # Beep boop!
make_it_speak(Person())   # Hello!

# This is duck typing polymorphism
# "If it has a speak() method, I can call it"
```

---

## **Method Overloading (Compile-time Polymorphism)**

Same method name, different parameters. Python doesn't support traditional method overloading, but we can simulate it:

```python
class Calculator:
    def add(self, *args):
        """
        Polymorphic add - works with any number of arguments
        Simulates method overloading
        """
        if len(args) == 2:
            return args[0] + args[1]
        elif len(args) == 3:
            return args[0] + args[1] + args[2]
        else:
            return sum(args)
    
    def process(self, data):
        """
        Polymorphic behavior based on type
        """
        if isinstance(data, int):
            return data * 2
        elif isinstance(data, str):
            return data.upper()
        elif isinstance(data, list):
            return [x * 2 for x in data]
        else:
            return None

calc = Calculator()
print(calc.add(5, 3))              # 8 (two arguments)
print(calc.add(1, 2, 3))           # 6 (three arguments)
print(calc.add(1, 2, 3, 4, 5))     # 15 (multiple arguments)

print(calc.process(10))            # 20 (integer)
print(calc.process("hello"))       # HELLO (string)
print(calc.process([1, 2, 3]))     # [2, 4, 6] (list)
```

---

## **Operator Overloading**

Define how operators work with custom objects:

```python
class Vector:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __add__(self, other):
        """Overload + operator"""
        return Vector(self.x + other.x, self.y + other.y)
    
    def __sub__(self, other):
        """Overload - operator"""
        return Vector(self.x - other.x, self.y - other.y)
    
    def __mul__(self, scalar):
        """Overload * operator"""
        return Vector(self.x * scalar, self.y * scalar)
    
    def __str__(self):
        """Overload str() function"""
        return f"Vector({self.x}, {self.y})"
    
    def __eq__(self, other):
        """Overload == operator"""
        return self.x == other.x and self.y == other.y

v1 = Vector(2, 3)
v2 = Vector(4, 5)

# Polymorphic operators
v3 = v1 + v2        # Uses __add__
print(v3)           # Vector(6, 8)

v4 = v2 - v1        # Uses __sub__
print(v4)           # Vector(2, 2)

v5 = v1 * 3         # Uses __mul__
print(v5)           # Vector(6, 9)

print(v1 == v2)     # Uses __eq__: False
```

---

## **Polymorphism with Collections**

```python
from abc import ABC, abstractmethod

class Employee(ABC):
    def __init__(self, name, employee_id):
        self.name = name
        self.employee_id = employee_id
    
    @abstractmethod
    def calculate_salary(self) -> float:
        pass
    
    @abstractmethod
    def get_role(self) -> str:
        pass

class FullTimeEmployee(Employee):
    def __init__(self, name, employee_id, annual_salary):
        super().__init__(name, employee_id)
        self.annual_salary = annual_salary
    
    def calculate_salary(self) -> float:
        return self.annual_salary / 12
    
    def get_role(self) -> str:
        return "Full-Time Employee"

class PartTimeEmployee(Employee):
    def __init__(self, name, employee_id, hourly_rate, hours_worked):
        super().__init__(name, employee_id)
        self.hourly_rate = hourly_rate
        self.hours_worked = hours_worked
    
    def calculate_salary(self) -> float:
        return self.hourly_rate * self.hours_worked
    
    def get_role(self) -> str:
        return "Part-Time Employee"

class Contractor(Employee):
    def __init__(self, name, employee_id, contract_amount):
        super().__init__(name, employee_id)
        self.contract_amount = contract_amount
    
    def calculate_salary(self) -> float:
        return self.contract_amount
    
    def get_role(self) -> str:
        return "Contractor"

class Intern(Employee):
    def __init__(self, name, employee_id, stipend):
        super().__init__(name, employee_id)
        self.stipend = stipend
    
    def calculate_salary(self) -> float:
        return self.stipend
    
    def get_role(self) -> str:
        return "Intern"

class PayrollSystem:
    """Process payroll for all employee types polymorphically"""
    
    def __init__(self):
        self.employees = []
    
    def add_employee(self, employee: Employee):
        self.employees.append(employee)
    
    def process_payroll(self):
        """
        Process payroll for ALL employee types
        Each calculates salary differently - polymorphism!
        """
        print("=== Monthly Payroll ===\n")
        total_payroll = 0
        
        for employee in self.employees:
            salary = employee.calculate_salary()
            total_payroll += salary
            
            print(f"{employee.name} ({employee.get_role()})")
            print(f"  Employee ID: {employee.employee_id}")
            print(f"  Monthly Payment: ${salary:,.2f}\n")
        
        print(f"Total Monthly Payroll: ${total_payroll:,.2f}")

# Usage
payroll = PayrollSystem()

# Add different types of employees
payroll.add_employee(FullTimeEmployee("Alice Johnson", "FT001", 90000))
payroll.add_employee(FullTimeEmployee("Bob Smith", "FT002", 75000))
payroll.add_employee(PartTimeEmployee("Charlie Brown", "PT001", 25, 80))
payroll.add_employee(PartTimeEmployee("Diana Prince", "PT002", 30, 60))
payroll.add_employee(Contractor("Eve Wilson", "CT001", 8000))
payroll.add_employee(Intern("Frank Miller", "IN001", 1500))

# Process payroll - polymorphism handles all types
payroll.process_payroll()
```

**Output:**

```
=== Monthly Payroll ===

Alice Johnson (Full-Time Employee)
  Employee ID: FT001
  Monthly Payment: $7,500.00

Bob Smith (Full-Time Employee)
  Employee ID: FT002
  Monthly Payment: $6,250.00

Charlie Brown (Part-Time Employee)
  Employee ID: PT001
  Monthly Payment: $2,000.00

Diana Prince (Part-Time Employee)
  Employee ID: PT002
  Monthly Payment: $1,800.00

Eve Wilson (Contractor)
  Employee ID: CT001
  Monthly Payment: $8,000.00

Frank Miller (Intern)
  Employee ID: IN001
  Monthly Payment: $1,500.00

Total Monthly Payroll: $27,050.00
```

---

## **Benefits of Polymorphism**

1. **Code Reusability**: Write general code that works with many types
2. **Flexibility**: Easy to add new types without changing existing code
3. **Maintainability**: Changes localized to specific classes
4. **Extensibility**: System grows naturally by adding new implementations
5. **Testability**: Can mock/stub implementations for testing
6. **Clean Code**: Eliminates complex if/else chains based on type

---

## **Polymorphism vs Type Checking (Anti-pattern)**

### **BAD: Type checking (avoid this)**

```python
def process_payment_bad(payment_type, amount):
    """
    Anti-pattern: checking types manually
    Hard to extend, violates Open/Closed Principle
    """
    if payment_type == "credit_card":
        print("Processing credit card payment")
        fee = amount * 0.029
    elif payment_type == "paypal":
        print("Processing PayPal payment")
        fee = amount * 0.034
    elif payment_type == "bank_transfer":
        print("Processing bank transfer")
        fee = 1.00
    elif payment_type == "crypto":
        print("Processing cryptocurrency")
        fee = amount * 0.01
    else:
        print("Unknown payment type")
        fee = 0
    
    return amount + fee

# Adding new payment type requires modifying this function!
```

### **GOOD: Polymorphism**

```python
def process_payment_good(payment_method: PaymentMethod, amount: float):
    """
    Polymorphic approach: works with any PaymentMethod
    Easy to extend - just add new classes
    """
    fee = payment_method.get_transaction_fee(amount)
    return payment_method.process_payment(amount + fee)

# Adding new payment type: just create new class, no modification needed!
```

---

## **Key Takeaways**

**Polymorphism is**:

1. **"One interface, many implementations"**
2. **Writing code that works with abstractions**, not concrete types
3. **Letting objects decide their own behavior** based on their type
4. **The foundation of extensible systems**

**Three key aspects**:

1. **Inheritance**: Subclasses inherit from parent
2. **Method Overriding**: Subclasses provide specific implementations
3. **Dynamic Binding**: Correct method chosen at runtime

**Remember**: Polymorphism allows you to write code once that works with types that don't even exist yet. When you need a new payment method, shape, or employee type, you just add it—the existing code continues to work.

**The mantra**: "Program to an interface, not an implementation."

# Inheritance

Inheritance is one of the four fundamental pillars of Object-Oriented Programming. It allows a class to inherit properties and behaviors from another class, creating a hierarchical relationship between classes. Think of it as a "parent-child" or "is-a" relationship.

**Core concept**: "A Dog **is a** Animal" or "A Car **is a** Vehicle"

---

## **Core Concept**

Inheritance enables:

1. **Code Reuse**: Child classes inherit parent functionality
2. **Hierarchical Classification**: Model real-world relationships
3. **Extension**: Child classes can add new features
4. **Specialization**: Child classes can override/customize behavior
5. **Polymorphism**: Treat children as parents (covered in previous section)

**Real-world analogy**: Biological classification

- **Superclass (Parent)**: Animal
    - Has: body, can eat, can move
- **Subclass (Child)**: Dog
    - Inherits: body, can eat, can move
    - Adds: can bark, has breed
    - Specializes: moves by walking/running

---

## **Basic Inheritance Syntax**

### **Python Example**

```python
# Parent class (Base class, Superclass)
class Animal:
    def __init__(self, name, age):
        self.name = name
        self.age = age
        self.is_alive = True
    
    def eat(self):
        return f"{self.name} is eating"
    
    def sleep(self):
        return f"{self.name} is sleeping"
    
    def make_sound(self):
        return "Some generic sound"

# Child class (Derived class, Subclass)
class Dog(Animal):  # Inherits from Animal
    def __init__(self, name, age, breed):
        super().__init__(name, age)  # Call parent constructor
        self.breed = breed  # Add new attribute
    
    def make_sound(self):  # Override parent method
        return "Woof! Woof!"
    
    def fetch(self):  # Add new method
        return f"{self.name} is fetching the ball"

# Usage
dog = Dog("Rex", 5, "Golden Retriever")

# Inherited methods
print(dog.eat())      # Rex is eating (from Animal)
print(dog.sleep())    # Rex is sleeping (from Animal)

# Overridden method
print(dog.make_sound())  # Woof! Woof! (from Dog, not Animal)

# New method
print(dog.fetch())    # Rex is fetching the ball (unique to Dog)

# Inherited attributes
print(dog.name)       # Rex (from Animal)
print(dog.age)        # 5 (from Animal)
print(dog.is_alive)   # True (from Animal)

# New attributes
print(dog.breed)      # Golden Retriever (unique to Dog)
```

---

## **The Inheritance Hierarchy**

### **Single Inheritance**

One child inherits from one parent.

```python
class Vehicle:
    def __init__(self, make, model, year):
        self.make = make
        self.model = model
        self.year = year
    
    def start(self):
        return f"{self.make} {self.model} is starting"
    
    def stop(self):
        return f"{self.make} {self.model} is stopping"

class Car(Vehicle):  # Car inherits from Vehicle
    def __init__(self, make, model, year, num_doors):
        super().__init__(make, model, year)
        self.num_doors = num_doors
    
    def honk(self):
        return "Beep beep!"

class Motorcycle(Vehicle):  # Motorcycle also inherits from Vehicle
    def __init__(self, make, model, year, engine_cc):
        super().__init__(make, model, year)
        self.engine_cc = engine_cc
    
    def wheelie(self):
        return "Performing a wheelie!"

# Hierarchy:
#     Vehicle
#     /     \
#   Car   Motorcycle

car = Car("Toyota", "Camry", 2023, 4)
bike = Motorcycle("Harley", "Sportster", 2023, 1200)

print(car.start())    # Inherited from Vehicle
print(car.honk())     # Unique to Car

print(bike.start())   # Inherited from Vehicle
print(bike.wheelie()) # Unique to Motorcycle
```

---

### **Multi-level Inheritance**

A child inherits from a parent, which inherits from another parent (grandparent).

```python
class LivingThing:
    def __init__(self, name):
        self.name = name
    
    def breathe(self):
        return f"{self.name} is breathing"

class Animal(LivingThing):  # Animal inherits from LivingThing
    def __init__(self, name, species):
        super().__init__(name)
        self.species = species
    
    def move(self):
        return f"{self.name} is moving"

class Dog(Animal):  # Dog inherits from Animal (which inherits from LivingThing)
    def __init__(self, name, breed):
        super().__init__(name, "Canine")
        self.breed = breed
    
    def bark(self):
        return f"{self.name} says Woof!"

# Hierarchy (3 levels):
#   LivingThing
#       ↓
#     Animal
#       ↓
#      Dog

dog = Dog("Buddy", "Labrador")

# Methods from all levels of hierarchy
print(dog.breathe())  # From LivingThing (grandparent)
print(dog.move())     # From Animal (parent)
print(dog.bark())     # From Dog (itself)

print(dog.name)       # From LivingThing
print(dog.species)    # From Animal
print(dog.breed)      # From Dog
```

---

### **Multiple Inheritance**

A child inherits from multiple parents. (Python supports this; Java/C# don't directly)

```python
class Flyer:
    def fly(self):
        return "Flying through the air"
    
    def land(self):
        return "Landing safely"

class Swimmer:
    def swim(self):
        return "Swimming in water"
    
    def dive(self):
        return "Diving underwater"

class Duck(Flyer, Swimmer):  # Inherits from BOTH Flyer and Swimmer
    def __init__(self, name):
        self.name = name
    
    def quack(self):
        return f"{self.name} says Quack!"

# Hierarchy:
#   Flyer    Swimmer
#      \      /
#       Duck

duck = Duck("Donald")

# Methods from both parents
print(duck.fly())    # From Flyer
print(duck.swim())   # From Swimmer
print(duck.quack())  # From Duck itself
```

**Warning**: Multiple inheritance can be complex and lead to the "Diamond Problem" (see later).

---

### **Hierarchical Inheritance**

Multiple children inherit from one parent.

```python
class Employee:
    def __init__(self, name, employee_id, salary):
        self.name = name
        self.employee_id = employee_id
        self.salary = salary
    
    def work(self):
        return f"{self.name} is working"
    
    def get_salary(self):
        return self.salary

class Manager(Employee):
    def __init__(self, name, employee_id, salary, department):
        super().__init__(name, employee_id, salary)
        self.department = department
    
    def manage_team(self):
        return f"{self.name} is managing the {self.department} department"

class Developer(Employee):
    def __init__(self, name, employee_id, salary, programming_language):
        super().__init__(name, employee_id, salary)
        self.programming_language = programming_language
    
    def write_code(self):
        return f"{self.name} is writing {self.programming_language} code"

class Designer(Employee):
    def __init__(self, name, employee_id, salary, design_tool):
        super().__init__(name, employee_id, salary)
        self.design_tool = design_tool
    
    def create_design(self):
        return f"{self.name} is designing with {self.design_tool}"

# Hierarchy:
#           Employee
#          /    |    \
#    Manager Developer Designer

manager = Manager("Alice", "M001", 90000, "Engineering")
developer = Developer("Bob", "D001", 75000, "Python")
designer = Designer("Carol", "DS001", 70000, "Figma")

# All inherit from Employee
print(manager.work())        # Inherited
print(developer.work())      # Inherited
print(designer.work())       # Inherited

# Each has unique methods
print(manager.manage_team())
print(developer.write_code())
print(designer.create_design())
```

---

## **Method Resolution Order (MRO)**

When multiple inheritance is used, Python follows a specific order to find methods. This is the **Method Resolution Order**.

```python
class A:
    def method(self):
        return "Method from A"

class B(A):
    def method(self):
        return "Method from B"

class C(A):
    def method(self):
        return "Method from C"

class D(B, C):  # Multiple inheritance
    pass

# Hierarchy:
#     A
#    / \
#   B   C
#    \ /
#     D

obj = D()
print(obj.method())  # Which method is called?

# Check MRO
print(D.__mro__)
# Output: (<class 'D'>, <class 'B'>, <class 'C'>, <class 'A'>, <class 'object'>)

# Searches in order: D → B → C → A → object
# Finds method in B first, so prints "Method from B"
```

Python uses **C3 Linearization** algorithm to determine MRO, ensuring:

- Children are checked before parents
- Parent order is preserved
- No class is checked before its parents

---

## **The `super()` Function**

`super()` allows you to call methods from the parent class.

### **Basic Usage**

```python
class Parent:
    def __init__(self, name):
        self.name = name
        print(f"Parent constructor: {name}")
    
    def greet(self):
        return f"Hello from {self.name}"

class Child(Parent):
    def __init__(self, name, age):
        super().__init__(name)  # Call parent constructor
        self.age = age
        print(f"Child constructor: {age}")
    
    def greet(self):
        parent_greeting = super().greet()  # Call parent method
        return f"{parent_greeting}, and I'm {self.age} years old"

child = Child("Alice", 10)
print(child.greet())

# Output:
# Parent constructor: Alice
# Child constructor: 10
# Hello from Alice, and I'm 10 years old
```

### **Cooperative Multiple Inheritance**

`super()` is crucial for multiple inheritance:

```python
class A:
    def __init__(self):
        print("A.__init__")
        super().__init__()

class B:
    def __init__(self):
        print("B.__init__")
        super().__init__()

class C(A, B):
    def __init__(self):
        print("C.__init__")
        super().__init__()

c = C()

# Output:
# C.__init__
# A.__init__
# B.__init__

# super() follows MRO: C → A → B → object
```

---

## **Real-World Example: Banking System**

```python
from datetime import datetime

class Account:
    """Base class for all account types"""
    
    def __init__(self, account_number, owner, balance=0):
        self.account_number = account_number
        self.owner = owner
        self.balance = balance
        self.transactions = []
        self.created_at = datetime.now()
    
    def deposit(self, amount):
        if amount > 0:
            self.balance += amount
            self._record_transaction("Deposit", amount)
            return True
        return False
    
    def withdraw(self, amount):
        if amount > 0 and amount <= self.balance:
            self.balance -= amount
            self._record_transaction("Withdrawal", amount)
            return True
        return False
    
    def get_balance(self):
        return self.balance
    
    def _record_transaction(self, transaction_type, amount):
        self.transactions.append({
            'type': transaction_type,
            'amount': amount,
            'timestamp': datetime.now(),
            'balance': self.balance
        })
    
    def get_statement(self):
        statement = f"\n=== Account Statement ===\n"
        statement += f"Account: {self.account_number}\n"
        statement += f"Owner: {self.owner}\n"
        statement += f"Current Balance: ${self.balance:.2f}\n"
        statement += f"\nRecent Transactions:\n"
        for trans in self.transactions[-5:]:
            statement += f"  {trans['type']}: ${trans['amount']:.2f} "
            statement += f"(Balance: ${trans['balance']:.2f})\n"
        return statement

class SavingsAccount(Account):
    """Savings account with interest"""
    
    def __init__(self, account_number, owner, balance=0, interest_rate=0.02):
        super().__init__(account_number, owner, balance)
        self.interest_rate = interest_rate
        self.account_type = "Savings"
    
    def apply_interest(self):
        """Apply monthly interest"""
        interest = self.balance * self.interest_rate
        self.balance += interest
        self._record_transaction("Interest", interest)
        return interest
    
    def withdraw(self, amount):
        """Override: Savings has withdrawal limit"""
        max_withdrawal = 5000
        if amount > max_withdrawal:
            print(f"Withdrawal limit is ${max_withdrawal}")
            return False
        return super().withdraw(amount)

class CheckingAccount(Account):
    """Checking account with overdraft protection"""
    
    def __init__(self, account_number, owner, balance=0, overdraft_limit=500):
        super().__init__(account_number, owner, balance)
        self.overdraft_limit = overdraft_limit
        self.account_type = "Checking"
    
    def withdraw(self, amount):
        """Override: Allow overdraft up to limit"""
        if amount > 0 and (self.balance - amount) >= -self.overdraft_limit:
            self.balance -= amount
            self._record_transaction("Withdrawal", amount)
            if self.balance < 0:
                print(f"Warning: Account overdrawn by ${abs(self.balance):.2f}")
            return True
        print("Insufficient funds (including overdraft)")
        return False
    
    def get_available_balance(self):
        """Total available including overdraft"""
        return self.balance + self.overdraft_limit

class StudentAccount(SavingsAccount):
    """Student account - special savings account with no fees"""
    
    def __init__(self, account_number, owner, balance=0, university=""):
        super().__init__(account_number, owner, balance, interest_rate=0.01)
        self.university = university
        self.account_type = "Student"
        self.monthly_fee = 0  # No fees for students
    
    def verify_student_status(self):
        """Verify student is still enrolled"""
        return f"Student at {self.university}"
    
    def withdraw(self, amount):
        """Override: Student accounts have lower withdrawal limit"""
        max_withdrawal = 1000
        if amount > max_withdrawal:
            print(f"Student withdrawal limit is ${max_withdrawal}")
            return False
        return Account.withdraw(self, amount)  # Skip SavingsAccount limit

class PremiumAccount(CheckingAccount):
    """Premium account with extra benefits"""
    
    def __init__(self, account_number, owner, balance=0):
        super().__init__(account_number, owner, balance, overdraft_limit=2000)
        self.account_type = "Premium"
        self.reward_points = 0
    
    def withdraw(self, amount):
        """Override: Earn reward points on withdrawals"""
        success = super().withdraw(amount)
        if success:
            # Earn 1 point per $10 withdrawn
            points = int(amount / 10)
            self.reward_points += points
            print(f"Earned {points} reward points!")
        return success
    
    def deposit(self, amount):
        """Override: Earn reward points on deposits"""
        success = super().deposit(amount)
        if success:
            points = int(amount / 10)
            self.reward_points += points
            print(f"Earned {points} reward points!")
        return success
    
    def redeem_points(self, points):
        """Redeem points for cash"""
        if points <= self.reward_points:
            cash_value = points * 0.01  # 1 point = $0.01
            self.reward_points -= points
            self.deposit(cash_value)
            return cash_value
        return 0

# Usage - Demonstrating inheritance hierarchy

print("=== Creating Accounts ===\n")

# Basic savings account
savings = SavingsAccount("SAV001", "Alice Johnson", 5000)
print(f"Created {savings.account_type} account for {savings.owner}")

# Checking account
checking = CheckingAccount("CHK001", "Bob Smith", 2000)
print(f"Created {checking.account_type} account for {checking.owner}")

# Student account
student = StudentAccount("STU001", "Charlie Brown", 500, "MIT")
print(f"Created {student.account_type} account for {student.owner}")

# Premium account
premium = PremiumAccount("PRM001", "Diana Prince", 10000)
print(f"Created {premium.account_type} account for {premium.owner}")

print("\n=== Testing Inherited Methods ===\n")

# All accounts can deposit and withdraw (inherited from Account)
savings.deposit(1000)
checking.deposit(500)
student.deposit(200)
premium.deposit(3000)

print("\n=== Testing Specialized Behavior ===\n")

# Savings: Apply interest
interest = savings.apply_interest()
print(f"Applied ${interest:.2f} interest to savings account")

# Checking: Use overdraft
print(f"\nChecking balance: ${checking.get_balance():.2f}")
checking.withdraw(2400)  # More than balance, uses overdraft
print(f"After overdraft: ${checking.get_balance():.2f}")

# Student: Lower withdrawal limit
print(f"\nStudent attempting to withdraw $5000...")
student.withdraw(5000)  # Will fail - student limit is $1000
print(f"Student attempting to withdraw $500...")
student.withdraw(500)   # Will succeed

# Premium: Reward points
print(f"\nPremium account transactions with rewards:")
premium.withdraw(1000)
print(f"Total reward points: {premium.reward_points}")

print("\n=== Account Statements ===")
print(savings.get_statement())
print(checking.get_statement())
print(student.get_statement())
print(premium.get_statement())

# Polymorphism: Treat all as Account
print("\n=== Processing All Accounts ===\n")
accounts = [savings, checking, student, premium]

for account in accounts:
    print(f"{account.account_type} - {account.owner}: ${account.get_balance():.2f}")
```

**Output (partial):**

```
=== Creating Accounts ===

Created Savings account for Alice Johnson
Created Checking account for Bob Smith
Created Student account for Charlie Brown
Created Premium account for Diana Prince

=== Testing Inherited Methods ===

=== Testing Specialized Behavior ===

Applied $120.00 interest to savings account

Checking balance: $2500.00
Warning: Account overdrawn by $100.00
After overdraft: $-100.00

Student attempting to withdraw $5000...
Student withdrawal limit is $1000
Student attempting to withdraw $500...

Premium account transactions with rewards:
Earned 100 reward points!
Total reward points: 400

=== Account Statements ===

=== Account Statement ===
Account: SAV001
Owner: Alice Johnson
Current Balance: $6120.00

Recent Transactions:
  Deposit: $1000.00 (Balance: $6000.00)
  Interest: $120.00 (Balance: $6120.00)

...
```

**Inheritance hierarchy:**

```
        Account (base)
        /      |      \
    Savings Checking  ...
       |              |
    Student        Premium
```

---

## **Protected and Private Members**

### **Access Levels**

```python
class Parent:
    def __init__(self):
        self.public = "Everyone can access"
        self._protected = "Convention: internal use, but accessible"
        self.__private = "Name mangled, hard to access outside class"
    
    def public_method(self):
        return "Public method"
    
    def _protected_method(self):
        return "Protected method (by convention)"
    
    def __private_method(self):
        return "Private method"
    
    def access_private(self):
        # Can access private members within class
        return self.__private_method()

class Child(Parent):
    def __init__(self):
        super().__init__()
    
    def test_access(self):
        print(self.public)          # OK
        print(self._protected)       # OK (convention says OK for subclasses)
        # print(self.__private)      # Error! Name mangled
        print(self._Parent__private) # OK (but ugly - name mangling bypass)

parent = Parent()
print(parent.public)           # OK
print(parent._protected)       # OK (but convention says "don't")
# print(parent.__private)      # Error! Name mangled

child = Child()
child.test_access()
```

---

## **Method Overriding**

Child classes can replace parent methods:

```python
class Shape:
    def __init__(self, color):
        self.color = color
    
    def area(self):
        """Default implementation"""
        return 0
    
    def describe(self):
        return f"A {self.color} shape"

class Rectangle(Shape):
    def __init__(self, color, width, height):
        super().__init__(color)
        self.width = width
        self.height = height
    
    def area(self):
        """Override: specific calculation for rectangle"""
        return self.width * self.height
    
    def describe(self):
        """Override and extend"""
        parent_desc = super().describe()
        return f"{parent_desc} - specifically a rectangle"

class Circle(Shape):
    def __init__(self, color, radius):
        super().__init__(color)
        self.radius = radius
    
    def area(self):
        """Override: specific calculation for circle"""
        return 3.14159 * self.radius ** 2

rect = Rectangle("blue", 10, 5)
circle = Circle("red", 7)

print(rect.describe())   # Uses overridden method
print(f"Area: {rect.area()}")

print(circle.describe()) # Uses parent method (not overridden)
print(f"Area: {circle.area()}")
```

---

## **Abstract Base Classes (Enforcing Inheritance Contract)**

```python
from abc import ABC, abstractmethod

class Vehicle(ABC):
    """Abstract base class - cannot be instantiated"""
    
    def __init__(self, make, model):
        self.make = make
        self.model = model
    
    @abstractmethod
    def start_engine(self):
        """Every vehicle MUST implement this"""
        pass
    
    @abstractmethod
    def stop_engine(self):
        """Every vehicle MUST implement this"""
        pass
    
    def honk(self):
        """Concrete method - can be inherited as-is"""
        return "Beep!"

# Cannot do this:
# vehicle = Vehicle("Generic", "Vehicle")  # Error!

class Car(Vehicle):
    def start_engine(self):
        """Must implement abstract method"""
        return f"{self.make} {self.model}: Engine started with key"
    
    def stop_engine(self):
        """Must implement abstract method"""
        return f"{self.make} {self.model}: Engine stopped"

class ElectricCar(Vehicle):
    def start_engine(self):
        """Must implement abstract method"""
        return f"{self.make} {self.model}: Electric motor activated"
    
    def stop_engine(self):
        """Must implement abstract method"""
        return f"{self.make} {self.model}: Electric motor deactivated"

car = Car("Toyota", "Camry")
ev = ElectricCar("Tesla", "Model 3")

print(car.start_engine())
print(ev.start_engine())
print(car.honk())  # Inherited concrete method
```

---

## **Benefits of Inheritance**

1. **Code Reuse**: Don't repeat common functionality
2. **Extensibility**: Easy to add new types
3. **Maintainability**: Changes to parent affect all children
4. **Polymorphism**: Treat children as parents
5. **Natural Modeling**: Represents real-world hierarchies

---

## **When to Use Inheritance**

### **Good Use Cases**

**1. True "is-a" relationships:**

```python
# Dog IS-A Animal ✓
class Animal:
    pass

class Dog(Animal):
    pass
```

**2. Specialization:**

```python
# CheckingAccount is a specialized Account ✓
class Account:
    pass

class CheckingAccount(Account):
    pass
```

**3. Shared behavior:**

```python
# All shapes can calculate area ✓
class Shape:
    def area(self):
        pass

class Circle(Shape):
    pass
```

---

## **When NOT to Use Inheritance**

### **1. "Has-a" relationships (Use Composition)**

```python
# BAD: Car IS-A Engine? No, Car HAS-A Engine!
class Engine:
    def start(self):
        return "Engine started"

class Car(Engine):  # WRONG!
    pass

# GOOD: Composition
class Car:
    def __init__(self):
        self.engine = Engine()  # Car HAS-A Engine
    
    def start(self):
        return self.engine.start()
```

### **2. Utility functions (Use functions or modules)**

```python
# BAD: Inheriting for utility methods
class MathUtils:
    def add(self, a, b):
        return a + b

class Calculator(MathUtils):  # Unnecessary inheritance
    pass

# GOOD: Just use functions or composition
def add(a, b):
    return a + b
```

### **3. Deep hierarchies (Keep it shallow)**

```python
# BAD: Too deep
class LivingThing:
    pass

class Animal(LivingThing):
    pass

class Mammal(Animal):
    pass

class Primate(Mammal):
    pass

class Human(Primate):
    pass

# Often BETTER: Flatter with composition
class Human:
    def __init__(self):
        self.biology = BiologySystem()
        self.movement = MovementSystem()
```

---

## **Composition vs Inheritance**

### **The Classic Dilemma**

```python
# Inheritance approach
class Bird:
    def fly(self):
        return "Flying"

class Duck(Bird):
    pass

class Penguin(Bird):
    def fly(self):  # Problem: Penguins can't fly!
        raise NotImplementedError("Penguins can't fly!")

# Composition approach (better)
class FlyingAbility:
    def fly(self):
        return "Flying"

class SwimmingAbility:
    def swim(self):
        return "Swimming"

class Duck:
    def __init__(self):
        self.flying = FlyingAbility()
        self.swimming = SwimmingAbility()

class Penguin:
    def __init__(self):
        self.swimming = SwimmingAbility()
        # No flying ability - problem solved!
```

**Favor composition over inheritance** when:

- Relationship is "has-a" not "is-a"
- You need flexibility to change behavior at runtime
- Multiple inheritance would be needed
- Inheritance hierarchy becomes complex

---

## **Common Pitfalls**

### **1. The Fragile Base Class Problem**

```python
# Parent class
class Base:
    def method_a(self):
        return self.method_b()
    
    def method_b(self):
        return "Base B"

# Child overrides method_b
class Child(Base):
    def method_b(self):
        return "Child B"

child = Child()
print(child.method_a())  # "Child B" - might be unexpected!
# Parent method_a calls overridden method_b
```

**Lesson**: Changes to parent can break children, and vice versa.

### **2. The Diamond Problem**

```python
class A:
    def method(self):
        return "A"

class B(A):
    def method(self):
        return "B"

class C(A):
    def method(self):
        return "C"

class D(B, C):  # Multiple inheritance
    pass

# Diamond:
#     A
#    / \
#   B   C
#    \ /
#     D

d = D()
print(d.method())  # Which one? B or C?
# Python uses MRO: D → B → C → A
# Answer: "B"
```

**Lesson**: Multiple inheritance requires careful MRO understanding.

### **3. Yo-Yo Problem**

```python
# Deep hierarchy - hard to understand flow
class A:
    def method(self):
        super().method()
        print("A")

class B(A):
    def method(self):
        super().method()
        print("B")

class C(B):
    def method(self):
        super().method()
        print("C")

class D(C):
    def method(self):
        super().method()
        print("D")

# Control flow bounces up and down the hierarchy
```

**Lesson**: Keep hierarchies shallow (2-3 levels max).

---

## **Best Practices**

1. **Use inheritance for "is-a" relationships**
2. **Keep hierarchies shallow** (2-3 levels)
3. **Favor composition over inheritance** when in doubt
4. **Make abstract base classes** for contracts
5. **Use `super()`** for proper method chaining
6. **Don't override methods unnecessarily**
7. **Document inheritance relationships**
8. **Avoid multiple inheritance** unless necessary

---

## **Key Takeaways**

**Inheritance allows**:

- Code reuse through parent-child relationships
- Specialization of behavior
- Polymorphic behavior
- Natural modeling of hierarchies

**Remember the principle**:

- **"Is-a"** → Inheritance
- **"Has-a"** → Composition
- **"Can-do"** → Interface/Protocol

**When you add inheritance, ask**:

- Is this a true "is-a" relationship?
- Could composition work better?
- Am I creating deep hierarchies?
- Will this be easy to maintain?

Inheritance is powerful but should be used judiciously. Modern programming often favors composition and interfaces over deep inheritance hierarchies.

---

# SOLID Principles

**SOLID** is an acronym for **five design principles** that make software:

- Easier to maintain
    
- Easier to extend
    
- Less fragile
    
- Easier to test
    

### ✅ S — Single Responsibility Principle (SRP)

> **A class should have only ONE reason to change.**

❌ Bad:

```java
class Report {
    generateReport();
    saveToFile();
}
```

This class both **creates** and **saves** the report → two responsibilities.

✅ Good:

```java
class ReportGenerator { generateReport(); }
class ReportSaver { saveToFile(); }
```

---

### ✅ O — Open/Closed Principle (OCP)

> **Software entities should be open for extension, but closed for modification.**

You should be able to **add new behavior without changing existing code**.

❌ Bad: Editing a class every time you add a new feature.  
✅ Good: Use **interfaces, inheritance, or composition**.

---

### ✅ L — Liskov Substitution Principle (LSP)

> **A child class must be usable anywhere its parent class is used without breaking the program.**

If `Bird` has `fly()`, then `Penguin extends Bird` is a **violation**, because penguins can’t fly.

✅ If subclass breaks expectations → LSP is violated.

---

### ✅ I — Interface Segregation Principle (ISP)

> **Don’t force a class to implement methods it doesn’t use.**

❌ Bad:

```java
interface Worker {
    work();
    eat();
}
```

A robot worker doesn’t eat → forced to implement `eat()`.

✅ Good:

```java
interface Workable { work(); }
interface Eatable { eat(); }
```

---

### ✅ D — Dependency Inversion Principle (DIP)

> **High-level modules should NOT depend on low-level modules. Both should depend on abstractions.**

This is so important that you asked about it separately—we’ll go deeper in section 3.

---

# Coupling vs Cohesion

These describe **how parts of your system are related**.

---

## 🔗 Coupling (Between Classes)

> **How much classes depend on each other**

### Types:

- **Tight coupling** → Very dependent (BAD)
    
- **Loose coupling** → Independent (GOOD)
    

❌ Tight coupling:

```java
class Car {
    Engine engine = new DieselEngine(); // directly dependent
}
```

You CANNOT easily switch to ElectricEngine.

✅ Loose coupling:

```java
class Car {
    Engine engine; // interface
}
```

### ✅ Goal:

> **LOW coupling** → easier to change, test, and scale.

---

## 🎯 Cohesion (Inside a Class)

> **How focused a class is on one responsibility**

- **High cohesion** → Class does ONE job very well ✅
    
- **Low cohesion** → Class does MANY unrelated things ❌
    

❌ Low cohesion:

```java
class UserManager {
    login();
    sendEmail();
    generateReport();
}
```

✅ High cohesion:

```java
class AuthService { login(); }
class EmailService { sendEmail(); }
class ReportService { generateReport(); }
```

---

## ✅ SUMMARY TABLE

|Concept|Means|Desired|
|---|---|---|
|Coupling|How much classes depend on each other|**LOW**|
|Cohesion|How focused a class is|**HIGH**|

---

# Dependency Inversion Principle (DIP) — Deep Explanation

This is the **most powerful and most misunderstood** SOLID rule.

---

## ❌ Without Dependency Inversion (Bad Design)

```java
class Keyboard {}
class Computer {
    private Keyboard keyboard = new Keyboard();
}
```

### Problems:

- Computer is **tightly coupled** to Keyboard
    
- You **cannot change** the keyboard type easily
    
- Hard to **test**
    
- Hard to **extend**
    

---

## ✅ With Dependency Inversion (Good Design)

```java
interface InputDevice {}

class Keyboard implements InputDevice {}

class Computer {
    private InputDevice device;

    Computer(InputDevice device) {
        this.device = device;
    }
}
```

### Benefits:

- You can plug in:
    
    - Keyboard
        
    - Mouse
        
    - TouchScreen
        
- No class rewriting
    
- Easy testing with mock devices
    
- Loose coupling
    

---

## 📌 The Two Rules of DIP

1. **High-level modules should not depend on low-level modules**
    
2. **Both should depend on abstractions**
    

### Meaning:

> Business logic should NOT care about **how** things are implemented.

---

# 🔥 How These 3 Concepts Connect

|Concept|Purpose|
|---|---|
|SOLID|Overall design rules|
|Coupling|Measures dependency between parts|
|Cohesion|Measures focus inside a class|
|Dependency Inversion|Technique to achieve **low coupling**|

✅ If you:

- Apply **DIP**
    
- You get **low coupling**
    
- Which helps obey **SOLID**
    
- And improves **testability & scalability**
    

---

# ✅ Final One-Line Definitions

- **SOLID** → 5 principles for clean, maintainable OOP design
    
- **Coupling** → How dependent classes are (keep it LOW)
    
- **Cohesion** → How focused a class is (keep it HIGH)
    
- **Dependency Inversion** → Depend on interfaces, not concrete classes
    

---
# 6️⃣ Design Patterns (Theory Only)
Excellent—this is a **core theory block for software architecture interviews and real-world design**. I’ll explain each point **conceptually, clearly, and practically**, with zero code as requested.

---

# Why Design Patterns Exist

### 🔹 What is a Design Pattern?

A **design pattern** is a **reusable, proven solution to a common software design problem**.

They are:

- NOT finished code
    
- NOT frameworks
    
- They are **templates for thinking**
    

---

### 🔹 Why They Exist

### ✅ 1. To Avoid Reinventing the Wheel

Many problems repeat across systems:

- Object creation
    
- Communication between objects
    
- State management  
    Patterns capture **best-known solutions** to these.
    

---

### ✅ 2. To Improve Communication Between Developers

Saying:

> “Let’s use a Factory here”

Immediately communicates:

- How objects will be created
    
- What flexibility is expected
    

Patterns become a **shared language**.

---

### ✅ 3. To Reduce Bugs & Improve Maintainability

They are:

- Battle-tested
    
- Known to scale
    
- Known to avoid common design mistakes
    

---

### ✅ 4. To Enforce SOLID & Clean Architecture

Most patterns naturally enforce:

- Low coupling
    
- High cohesion
    
- Dependency inversion
    

---

# Creational vs Structural vs Behavioral Patterns

Design patterns are grouped by **what kind of problem they solve**.

---

## 🏗️ 1. Creational Patterns — _Object Creation_

> **How objects are created**

They help:

- Hide creation logic
    
- Control instantiation
    
- Improve flexibility
    

### Examples:

- Singleton
    
- Factory
    
- Abstract Factory
    
- Builder
    
- Prototype
    

### Key Question They Answer:

> _“How should this object be created?”_

---

## 🧱 2. Structural Patterns — _Object Composition_

> **How classes and objects are put together**

They help:

- Build large systems from small parts
    
- Wrap or adapt objects
    
- Add features without modifying code
    

### Examples:

- Adapter
    
- Decorator
    
- Composite
    
- Facade
    
- Proxy
    

### Key Question They Answer:

> _“How are objects connected?”_

---

## 🧠 3. Behavioral Patterns — _Object Interaction_

> **How objects communicate and share responsibility**

They help:

- Define workflows
    
- Control communication
    
- Reduce tight coupling
    

### Examples:

- Observer
    
- Strategy
    
- Command
    
- State
    
- Mediator
    
- Chain of Responsibility
    

### Key Question They Answer:

> _“How do objects talk and cooperate?”_

---

## ✅ Simple Memory Trick

|Category|Focus|Question|
|---|---|---|
|Creational|Creating objects|How is it made?|
|Structural|Connecting objects|How is it built?|
|Behavioral|Communication|How does it behave?|

---

# Problem → Context → Solution → Trade-offs

This is the **correct way to think about patterns**.

---

## 🔹 1. Problem

What is the **repeating design issue**?

Example:

- Too many `if-else` conditions
    
- Tight coupling
    
- Hard to extend behavior
    
- One class doing everything
    

---

## 🔹 2. Context

Under **what conditions does this problem occur?**

Example:

- Large codebase
    
- Rapid feature changes
    
- Multiple developers
    
- Frequent new requirements
    

---

## 🔹 3. Solution

The **pattern structure itself**:

- Classes involved
    
- Their responsibilities
    
- How they interact
    

---

## 🔹 4. Trade-offs (VERY IMPORTANT)

Every pattern **solves one problem but introduces another cost**.

### Example Trade-offs:

- More classes
    
- More abstraction
    
- Slight performance overhead
    
- Increased complexity
    

---

### ✅ Golden Rule:

> **Patterns are not “free”. They trade simplicity for flexibility.**

---

# Overengineering Dangers

This is when developers:

> **Use design patterns where they are NOT needed**

---

## ❌ What Overengineering Looks Like:

- Factory for only ONE object
    
- 10 interfaces for 2 classes
    
- Strategy pattern with only one strategy
    
- Abstract factories for a tiny project
    

---

## ❌ Why It’s Dangerous:

|Problem|Consequence|
|---|---|
|Too many layers|Hard to understand|
|Too many abstractions|Hard to debug|
|Unnecessary flexibility|Slower development|
|Complex structure|Junior devs get lost|

---

## ✅ When NOT to Use a Pattern

- When requirements are stable
    
- When system is small
    
- When change is unlikely
    
- When complexity > benefit
    

---

### ✅ Best Practice:

> **Start simple → Add patterns only when pain appears**

Not:

> **Add patterns “just in case”**

---

# Anti-Patterns

An **anti-pattern** is:

> A **commonly used solution that actually causes more harm than good**

Opposite of a design pattern.

---

## 🔴 Common Anti-Patterns

---

### ❌ 1. God Object

One class:

- Does everything
    
- Knows everything
    
- Controls everything
    

✅ Violates:

- SRP
    
- Cohesion
    
- Maintainability
    

---

### ❌ 2. Spaghetti Code

- No structure
    
- No layers
    
- No clear flow
    
- Random dependencies
    

✅ Result:

- Impossible to debug
    
- Impossible to scale
    

---

### ❌ 3. Lava Flow

- Dead code everywhere
    
- Nobody knows what can be removed
    
- Legacy hacks piled up
    

---

### ❌ 4. Golden Hammer

> Using the **same pattern for every problem**

Example:

- “Everything must be a microservice”
    
- “Everything must use Factory”
    
- “Everything must be event-driven”
    

---

### ❌ 5. Copy-Paste Programming

- Duplicate logic everywhere
    
- Bug fixed in one place but not others
    

---

## ✅ Relationship to Patterns

|Design Patterns|Anti-Patterns|
|---|---|
|Documented best practices|Documented bad practices|
|Encourage flexibility|Create rigidity|
|Encourage clean design|Create technical debt|

---

# ✅ Final High-Level Summary

- **Design Patterns exist** to solve recurring design problems using proven structures
    
- They are grouped into:
    
    - **Creational** → Object creation
        
    - **Structural** → Object composition
        
    - **Behavioral** → Object interaction
        
- Every pattern follows:
    
    > **Problem → Context → Solution → Trade-offs**
    
- **Overengineering** happens when patterns are used without real need
    
- **Anti-patterns** are harmful design habits that look like solutions but cause long-term damage
    

