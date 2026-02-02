# Exercises - Food Delivery API Workshop

Welcome to the **Kotlin Food Delivery API Workshop**! Over the next 4 hours, you will build and fix a food delivery backend using **Kotlin** and **Ktor**. The workshop is structured into **4 progressive levels** -- starting from reading and fixing existing code, moving through guided implementation, then tackling open-ended challenges, and finally creating your own feature from scratch. Each exercise has automated tests so you can verify your work as you go. Take your time, ask questions, and have fun!

---

## Getting Started

### Running Tests

Each exercise has its own test class. Run a specific test to check your work:

```bash
./gradlew test --tests "com.workshop.Level1MenuFilterTest"
```

### Running the Server

To start the API server and see the live dashboard:

```bash
./gradlew run
```

Then open [http://localhost:8080/](http://localhost:8080/) in your browser to see the dashboard.

---

## Level 1: Bug Hunt (Kotlin Basics)

**Goal:** Read existing code, find and fix bugs. You will learn about **data classes**, **null safety**, and **collection operations**.

In this level, the code is already written -- but it has bugs. Your job is to read the code carefully, understand what it _should_ do, find the bugs, and fix them.

---

### Exercise 1A: Menu Filtering Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/MenuService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1MenuFilterTest"` |
| **Endpoint** | `GET /api/restaurants/{id}/menu?maxPrice=200&dietary=vegetarian` |

**What it should do:** Return menu items from a restaurant, optionally filtered by a maximum price and/or a dietary tag (e.g., `vegetarian`, `halal`).

**What is broken:** There are **two bugs** in the filtering logic.

#### Bug 1: Price filter is inverted

The price filter currently returns items **above** the max price instead of items **at or below** the max price.

> **Hint:** Look for a comparison operator. The code uses `it.price > maxPrice` -- but it should keep items where the price is less than or equal to the max.

```kotlin
// BROKEN:
.filter { it.price > maxPrice }

// FIXED:
.filter { it.price <= maxPrice }
```

#### Bug 2: Dietary filter crashes on null tags

Some menu items do not have dietary tags (the field is `null`). The dietary filter does not handle this and will crash with a `NullPointerException`.

> **Hint:** Use the **safe call operator** (`?.`) to avoid calling `.contains()` on a null list. The `== true` pattern is a common Kotlin idiom for safely checking a nullable Boolean.

```kotlin
// BROKEN:
.filter { it.dietaryTags.contains(dietary) }

// FIXED:
.filter { it.dietaryTags?.contains(dietary) == true }
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level1MenuFilterTest"
```

---

### Exercise 1B: Order Total Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1OrderTotalTest"` |
| **Endpoint** | `GET /api/orders/{id}/total` |

**What it should do:** Calculate the total cost of an order by summing up `price x quantity` for each item, then applying a **10% discount** if the subtotal exceeds **₱500**.

**What is broken:** There are **two bugs** in the total calculation.

#### Bug 1: Not multiplying price by quantity

The code sums up unit prices without considering how many of each item were ordered.

> **Hint:** Use `it.price * it.quantity` instead of just `it.price`.

```kotlin
// BROKEN:
val subtotal = order.items.sumOf { it.price }

// FIXED:
val subtotal = order.items.sumOf { it.price * it.quantity }
```

#### Bug 2: Missing discount for large orders

Orders over ₱500 should receive a 10% discount, but the discount logic is missing.

> **Hint:** After computing the subtotal, check if it exceeds 500 and multiply by `0.9` if so.

```kotlin
// Add after computing subtotal:
val total = if (subtotal > 500.0) subtotal * 0.9 else subtotal
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level1OrderTotalTest"
```

---

## Level 2: Guided Implementation (Kotlin Features)

**Goal:** Implement new features using **sealed classes**, **`when` expressions**, **extension functions**, and the **Elvis operator**. The function signatures and structure are provided -- you fill in the logic.

---

### Exercise 2A: Order Status Transitions

| | |
|---|---|
| **Files** | `src/main/kotlin/com/workshop/models/OrderStatus.kt` and `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2StatusTransitionTest"` |
| **Endpoint** | `PUT /api/orders/{id}/status` with JSON body `{ "status": "Preparing" }` |

**What to implement:** An order can only move through certain status transitions. You need to enforce these rules.

#### Valid Status Transitions

| Current Status | Can Transition To |
|---|---|
| `Placed` | `Preparing`, `Cancelled` |
| `Preparing` | `OutForDelivery`, `Cancelled` |
| `OutForDelivery` | `Delivered`, `Cancelled` |
| `Delivered` | _(none -- final state)_ |
| `Cancelled` | _(none -- final state)_ |

#### Part 1: Implement `canTransitionTo()` in OrderStatus

Open `OrderStatus.kt` and implement the `canTransitionTo()` method on the sealed class. Use a `when` expression to check if the transition is valid.

```kotlin
fun canTransitionTo(next: OrderStatus): Boolean = when (this) {
    is Placed -> next is Preparing || next is Cancelled
    is Preparing -> next is OutForDelivery || next is Cancelled
    is OutForDelivery -> next is Delivered || next is Cancelled
    is Delivered -> false
    is Cancelled -> false
}
```

#### Part 2: Implement `updateOrderStatus()` in OrderService

Open `OrderService.kt` and implement the status update function. You need to:

1. **Parse** the status string into the sealed class using a `when` expression
2. **Validate** the transition using `canTransitionTo()`
3. **Update** the order if the transition is valid

```kotlin
// Parsing a string to the sealed class:
fun parseStatus(status: String): OrderStatus? = when (status.lowercase()) {
    "placed" -> OrderStatus.Placed
    "preparing" -> OrderStatus.Preparing
    "outfordelivery" -> OrderStatus.OutForDelivery
    "delivered" -> OrderStatus.Delivered
    "cancelled" -> OrderStatus.Cancelled
    else -> null
}
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level2StatusTransitionTest"
```

---

### Exercise 2B: Delivery Fee Calculator

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/DeliveryService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2DeliveryFeeTest"` |
| **Endpoint** | `GET /api/orders/{id}/delivery-fee` |

**What to implement:** Calculate the delivery fee based on the distance between the restaurant and the customer's delivery address.

#### Steps

1. **Find the order and customer** from the provided repositories
2. **Handle a missing address** -- if the customer has no saved delivery address, use a default address. Use the **Elvis operator** (`?:`)
3. **Calculate the distance** using the provided `calculateDistance()` helper function
4. **Apply the fee tiers** using a `when` expression

#### Fee Tiers

| Distance | Fee |
|---|---|
| 0 -- 2 km | Free (₱0) |
| 2 -- 5 km | ₱49 |
| 5 -- 10 km | ₱99 |
| 10+ km | ₱149 |

#### Hints

```kotlin
// Elvis operator for default address:
val address = customer.deliveryAddress ?: defaultAddress

// when expression with ranges:
val fee = when {
    distance <= 2.0 -> 0.0
    distance <= 5.0 -> 49.0
    distance <= 10.0 -> 99.0
    else -> 149.0
}
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level2DeliveryFeeTest"
```

---

## Level 3: Open-Ended (Collections Mastery)

**Goal:** Implement features using Kotlin's powerful **collection operations**. There is no single right answer here -- use whichever combination of collection functions achieves the correct result.

---

### Exercise 3A: Restaurant Search & Ranking

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/SearchService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level3SearchTest"` |
| **Endpoint** | `GET /api/search?q=burger&sortBy=rating` |

**What to implement:** Search restaurants by name or cuisine type, then sort results by the requested criteria (rating, delivery time, or name).

#### Useful Collection Functions

- `filter { }` -- keep items matching a condition
- `sortedBy { }` -- sort ascending by a property
- `sortedByDescending { }` -- sort descending by a property
- `lowercase()` -- convert a string to lowercase for case-insensitive matching

#### Example Chain

```kotlin
restaurants
    .filter { it.name.lowercase().contains(query.lowercase()) ||
              it.cuisine.lowercase().contains(query.lowercase()) }
    .let { results ->
        when (sortBy) {
            "rating" -> results.sortedByDescending { it.rating }
            "deliveryTime" -> results.sortedBy { it.estimatedDeliveryMinutes }
            else -> results.sortedBy { it.name }
        }
    }
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level3SearchTest"
```

---

### Exercise 3B: Order Analytics Dashboard

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/AnalyticsService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level3AnalyticsTest"` |
| **Endpoint** | `GET /api/analytics/summary` |

**What to implement:** Generate analytics from order data -- total revenue, popular items, busiest restaurants, etc.

#### Useful Collection Functions

| Function | What It Does | Example |
|---|---|---|
| `sumOf { }` | Sum a numeric property | `orders.sumOf { it.total }` |
| `groupBy { }` | Group items by a key | `orders.groupBy { it.restaurantId }` |
| `flatMap { }` | Flatten nested lists | `orders.flatMap { it.items }` |
| `mapValues { }` | Transform map values | `grouped.mapValues { it.value.size }` |
| `take(n)` | Take first N items | `sorted.take(5)` |
| `associate { }` | Create a map from items | `items.associate { it.name to it.count }` |

#### Example: Top 5 Most Ordered Items

```kotlin
val topItems = orders
    .flatMap { it.items }               // Flatten all order items into one list
    .groupBy { it.menuItemName }        // Group by item name
    .mapValues { it.value.sumOf { item -> item.quantity } }  // Sum quantities
    .entries
    .sortedByDescending { it.value }    // Sort by total quantity
    .take(5)                            // Take top 5
    .associate { it.key to it.value }   // Convert back to map
```

#### Example: Revenue Per Restaurant

```kotlin
val revenueByRestaurant = orders
    .groupBy { it.restaurantId }
    .mapValues { entry -> entry.value.sumOf { it.total } }
```

Run the test to verify:

```bash
./gradlew test --tests "com.workshop.Level3AnalyticsTest"
```

---

## Level 4: Create Your Own

Congratulations on making it this far! In this final level, there are no predefined tests or templates. **Design and implement your own feature** for the food delivery API.

### Ideas

- **Promo Code / Discount System** -- Apply promo codes like `WELCOME20` for 20% off, validate expiration and usage limits
- **Customer Favorites / Reorder** -- Let customers save favorite items and quickly reorder past meals
- **Restaurant Recommendations** -- Suggest restaurants based on a customer's order history and preferences
- **Delivery Time Estimation** -- Estimate delivery time based on distance, restaurant prep time, and current order volume
- **Rating & Review System** -- Let customers rate and review restaurants and menu items after delivery

### How to Add a New Feature

1. **Create a service** in `src/main/kotlin/com/workshop/services/` with your business logic
2. **Add a route** in `src/main/kotlin/com/workshop/routes/` to expose your feature as an API endpoint
3. **Register the route** in the main application routing configuration
4. _(Optional)_ **Write tests** in `src/test/kotlin/com/workshop/` to verify your logic

### Example Skeleton

```kotlin
// src/main/kotlin/com/workshop/services/PromoService.kt
class PromoService {
    private val promoCodes = mapOf(
        "WELCOME20" to 0.20,
        "SAVE10" to 0.10
    )

    fun applyPromo(code: String, subtotal: Double): Double {
        val discount = promoCodes[code.uppercase()] ?: return subtotal
        return subtotal * (1 - discount)
    }
}
```

```kotlin
// In your routes file:
get("/api/orders/{id}/apply-promo") {
    val orderId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
    val code = call.request.queryParameters["code"] ?: return@get call.respond(HttpStatusCode.BadRequest)
    val total = promoService.applyPromo(code, orderTotal)
    call.respond(mapOf("total" to total, "promoApplied" to code))
}
```

---

## Kotlin Cheat Sheet

### Data Classes

```kotlin
data class MenuItem(
    val name: String,
    val price: Double,
    val dietaryTags: List<String>? = null   // nullable with default
)

// Automatically provides: toString(), equals(), hashCode(), copy()
val updated = item.copy(price = 199.0)
```

### Null Safety

```kotlin
val name: String? = null       // Nullable type

name?.length                   // Safe call -- returns null if name is null
name ?: "Unknown"              // Elvis operator -- default if null
name!!.length                  // Non-null assertion -- throws if null (avoid when possible)

// Safe chain example:
customer.address?.street?.lowercase()
```

### When Expressions

```kotlin
// As a statement:
when (status) {
    is OrderStatus.Placed -> println("New order!")
    is OrderStatus.Delivered -> println("Done!")
    else -> println("In progress")
}

// As an expression (returns a value):
val label = when (status) {
    is OrderStatus.Placed -> "New"
    is OrderStatus.Preparing -> "Cooking"
    is OrderStatus.OutForDelivery -> "On the way"
    is OrderStatus.Delivered -> "Done"
    is OrderStatus.Cancelled -> "Cancelled"
}

// With conditions (no argument):
val fee = when {
    distance <= 2.0 -> 0.0
    distance <= 5.0 -> 49.0
    else -> 99.0
}
```

### Collection Operations

```kotlin
val items = listOf("Burger", "Pizza", "Sushi", "Burger", "Ramen")

// filter -- keep matching items
items.filter { it.length > 4 }               // [Burger, Pizza, Sushi, Burger, Ramen]

// map -- transform each item
items.map { it.uppercase() }                  // [BURGER, PIZZA, SUSHI, BURGER, RAMEN]

// flatMap -- transform and flatten
orders.flatMap { it.items }                   // All items from all orders in one list

// groupBy -- group into a map
items.groupBy { it }                          // {Burger=[Burger, Burger], Pizza=[Pizza], ...}

// sortedBy / sortedByDescending
items.sortedBy { it.length }                  // Sort by string length ascending

// sumOf -- sum a numeric property
order.items.sumOf { it.price * it.quantity }  // Total cost

// take -- first N items
items.take(3)                                 // [Burger, Pizza, Sushi]

// any / all / none -- boolean checks
items.any { it == "Pizza" }                   // true
items.all { it.length > 3 }                   // true

// associate -- create a map
items.associate { it to it.length }           // {Burger=6, Pizza=5, Sushi=5, Ramen=5}
```

### String Templates

```kotlin
val name = "Kotlin"
println("Hello, $name!")                       // Hello, Kotlin!
println("Length: ${name.length}")              // Length: 6
println("Order #${order.id} total: ₱${order.total}")
```

### Extension Functions

```kotlin
// Add a method to an existing class:
fun Double.toPeso(): String = "₱${"%.2f".format(this)}"

// Usage:
val display = 149.50.toPeso()   // "₱149.50"

fun List<MenuItem>.totalPrice(): Double = sumOf { it.price }
```

---

**Good luck and happy coding!** If you get stuck, ask for help -- that is what workshops are for.
