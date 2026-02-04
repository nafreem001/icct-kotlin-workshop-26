# Exercises - Food Delivery API Workshop

Welcome to the **Kotlin Food Delivery API Workshop**! Over the next 2 hours, you will build and fix a food delivery backend using **Kotlin** and **Ktor**. The workshop is structured into **4 progressive levels** -- starting from reading and fixing existing code, moving through guided implementation, then tackling open-ended challenges, and finally creating your own feature from scratch. Each exercise has automated tests so you can verify your work as you go. Take your time, ask questions, and have fun!

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

### How to Iterate (Hot Reload)

The server supports **hot reload**. You need two terminals:

**Terminal 1** -- Run the server:
```bash
./gradlew run
```

**Terminal 2** -- Continuous build (auto-recompiles on save):
```bash
./gradlew -t classes
```

After you save a file, Terminal 2 recompiles and the server automatically picks up the changes. Refresh your browser to see the result.

**Tip:** You can also run tests directly -- they always use your latest saved code, no server needed.

---

## Level 1: Bug Hunt (Kotlin Basics)

**Goal:** Read existing code, find and fix bugs. You will learn about **data classes**, **null safety**, and **collection operations**.

In this level, the code is already written -- but it has bugs. Your job is to read the code carefully, understand what it _should_ do, find the bugs, and fix them. Read the tests to understand what the expected behavior is.

---

### Exercise 1A: Menu Filtering Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/MenuService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1MenuFilterTest"` |
| **Endpoint** | `GET /api/restaurants/{id}/menu?maxPrice=200&dietary=vegetarian` |

The `filterMenu()` function should return menu items from a restaurant, optionally filtered by a maximum price and/or a dietary tag (e.g., `vegetarian`, `halal`).

It does not work correctly. Read the function, read the tests, and fix it.

---

### Exercise 1B: Order Total Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1OrderTotalTest"` |
| **Endpoint** | `GET /api/orders/{id}/total` |

The `calculateTotal()` function should calculate the total cost of an order. Each order has line items with a unit price and quantity. The total is calculated as `unitPrice × quantity` for each item, summed together. If the subtotal exceeds ₱500, a 10% discount is applied to the entire order. Read the function and the tests to find the bugs.

---

### Exercise 1C: Restaurant Hours Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/MenuService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1RestaurantHoursTest"` |
| **Endpoint** | `GET /api/restaurants/{id}/open?time=14:30` |

The `isOpenAt()` function checks whether a restaurant is open at a given time. Restaurants have operating hours stored as open/close times in `HH:mm` format. Some restaurants do not have hours listed, and some are late-night establishments that stay open past midnight.

The function does not handle all these cases correctly. Read the tests to understand the expected behavior, then fix the bugs.

---

### Exercise 1D: Order Summary Bug

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level1OrderSummaryTest"` |
| **Endpoint** | `GET /api/orders/{id}/summary` |

The `getOrderSummary()` function returns a formatted summary of an order, including each item's name, quantity, unit price, subtotal, and the grand total. All monetary values should be formatted to two decimal places.

The function has bugs. Read the tests to understand the expected output format, then fix them.

---

## Level 2: Guided Implementation (Kotlin Features)

**Goal:** Implement new features using **sealed classes**, **`when` expressions**, **extension functions**, **null safety**, and **coroutines**. The function signatures are provided -- you fill in the logic.

---

### Exercise 2A: Order Status Transitions

| | |
|---|---|
| **Files** | `src/main/kotlin/com/workshop/models/OrderStatus.kt` and `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2StatusTransitionTest"` |
| **Endpoint** | `PUT /api/orders/{id}/status` with JSON body `{ "status": "Preparing" }` |

An order goes through a lifecycle: **Placed → Preparing → OutForDelivery → Delivered**. An order can also be **Cancelled** from any active state (Placed, Preparing, or OutForDelivery), but not after it has been Delivered. No other transitions are allowed -- you cannot go backwards.

**Your task:** Look at the `OrderStatus` sealed class and its subclasses. Then:

1. Implement `canTransitionTo()` in `OrderStatus.kt` -- use a `when` expression to return `true` only for valid transitions
2. Implement `updateOrderStatus()` in `OrderService.kt` -- parse the status string (e.g., `"Preparing"`) into an `OrderStatus`, call `canTransitionTo()` to validate, and update the order in `SampleData.orders`

---

### Exercise 2B: Delivery Fee Calculator (with Extension Functions)

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/DeliveryService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2DeliveryFeeTest"` |
| **Endpoint** | `GET /api/orders/{id}/delivery-fee` |

Implement `calculateDeliveryFee()` using **Kotlin extension functions**. The file defines three extension functions that you need to implement first:

- `Order.findRestaurant()` -- look up the restaurant matching `this.restaurantId` in `SampleData.restaurants`
- `Order.findCustomer()` -- look up the customer matching `this.customerId` in `SampleData.customers`
- `Double.roundTo(decimals)` -- round a Double to N decimal places using `10.0.pow()` and `Math.round()`

Then use these extensions in `calculateDeliveryFee()` to:
1. Look up the order, customer, and restaurant (return errors if any are missing or the customer has no address)
2. Get restaurant coordinates from `SampleData.restaurantCoordinates`
3. Calculate the distance using the provided `distanceInKm()` helper
4. Apply the fee tier based on distance:

| Distance | Fee | Tier Label |
|----------|-----|------------|
| 0--2 km | Free (₱0) | Free (0-2 km) |
| 2--5 km | ₱49 | Near (2-5 km) |
| 5--10 km | ₱99 | Medium (5-10 km) |
| 10+ km | ₱149 | Far (10+ km) |

5. Return a `DeliveryFeeResult` with the distance rounded to 2 decimal places

---

### Exercise 2C: Customer Loyalty Tiers

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/LoyaltyService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2LoyaltyTest"` |
| **Endpoint** | `GET /api/customers/{id}/loyalty` |

Implement a loyalty program that assigns customers to tiers based on their order history. Look at `models/LoyaltyTier.kt` for the sealed class hierarchy (Bronze, Silver, Gold, Platinum).

A customer's tier is determined by their order count **or** total spend (whichever qualifies them for the higher tier):

| Tier | Order Count | OR Total Spend | Discount |
|------|-------------|----------------|----------|
| Platinum | 25+ orders | ₱10,000+ | 15% |
| Gold | 10+ orders | ₱5,000+ | 10% |
| Silver | 3+ orders | ₱1,500+ | 5% |
| Bronze | default | default | 0% |

Check from highest tier down -- if a customer qualifies for Gold by spend but Bronze by order count, they get Gold.

**Your task:**

1. Implement `calculateTier()` -- get the customer's orders from `SampleData.orders`, calculate order count and total spend (`unitPrice × quantity` for each item), then use a `when` expression to return the appropriate tier
2. Implement `getLoyaltyInfo()` -- return a `LoyaltyInfo` object with the customer's name, tier, order count, total spend, and discount percentage from the tier

---

### Exercise 2D: Order Preparation Simulation (Coroutines)

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/PrepService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2PrepSimulationTest"` |
| **Endpoints** | `POST /api/orders/{id}/prepare` and `GET /api/orders/{id}/prep-status` |

Simulate kitchen preparation using Kotlin coroutines. When an order is started, each item should be prepared **concurrently** (not one at a time).

**Your task:**

1. Implement `startPreparation()` -- launch a coroutine for each line item using `launch` inside a `CoroutineScope`. Each item takes a simulated delay proportional to its quantity. Track progress in a `ConcurrentHashMap`.
2. Implement `getPrepStatus()` -- return the current progress (total items, completed items, whether still in progress).

The key concept: if you have 6 items and each takes 500ms, concurrent preparation should complete in ~500ms total (not 3000ms sequentially).

---

## Level 3: Open-Ended (Collections Mastery)

**Goal:** Implement features using Kotlin's **collection operations**. There is no single right answer -- use whichever approach achieves the correct result. The test files define the expected output.

---

### Exercise 3A: Restaurant Search & Ranking

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/SearchService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level3SearchTest"` |
| **Endpoint** | `GET /api/search?cuisine=Filipino&sortBy=rating&minRating=4.0` |

Implement `searchRestaurants()`. The function takes optional filter and sort parameters. Read the function signature and the tests to understand the requirements.

---

### Exercise 3B: Order Analytics Dashboard

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/AnalyticsService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level3AnalyticsTest"` |
| **Endpoint** | `GET /api/analytics/summary` |

Implement `getAnalyticsSummary()`. Aggregate order data into a summary. The `AnalyticsSummary` data class at the bottom of the file defines the shape of the response. The tests define the expected values.

---

### Exercise 3C: Order Recommendations

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/RecommendationService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level3RecommendationsTest"` |
| **Endpoint** | `GET /api/customers/{id}/recommendations` |

Implement `getRecommendations()`. Given a customer, analyze their past orders and recommend menu items they have not tried yet. Use collection operations like `flatMap`, `groupBy`, `sortedByDescending`, `distinctBy`, and `filter` to find relevant suggestions.

The function should:
- Not recommend items the customer has already ordered
- Prioritize items popular with customers who have similar tastes
- Fall back to untried items from restaurants the customer has visited
- Respect the `limit` parameter

---

## Level 4: Create Your Own

Congratulations on making it this far! In this final level, there are no predefined tests or templates. **Design and implement your own feature** for the food delivery API.

### Ideas

- **Promo Code / Discount System** -- Apply promo codes like `WELCOME20` for 20% off, validate expiration and usage limits
- **Customer Favorites / Reorder** -- Let customers save favorite items and quickly reorder past meals
- **Delivery Time Estimation** -- Estimate delivery time based on distance, restaurant prep time, and current order volume
- **Rating & Review System** -- Let customers rate and review restaurants and menu items after delivery

### How to Add a New Feature

1. **Create a service** in `src/main/kotlin/com/workshop/services/` with your business logic
2. **Add a route** in `src/main/kotlin/com/workshop/routes/` to expose your feature as an API endpoint
3. **Register the route** in `Application.kt` under the `/api` route block
4. _(Optional)_ **Write tests** in `src/test/kotlin/com/workshop/` to verify your logic

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
val label = when {
    score >= 90 -> "A"
    score >= 80 -> "B"
    else -> "C"
}
```

### Extension Functions

```kotlin
// Add a method to an existing class
fun String.initials(): String =
    this.split(" ").map { it.first().uppercase() }.joinToString("")

"Juan dela Cruz".initials()  // "JDC"

// Extension on Double
fun Double.roundTo(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return Math.round(this * factor) / factor
}

3.14159.roundTo(2)  // 3.14
```

### Collection Operations

```kotlin
val items = listOf("Burger", "Pizza", "Sushi", "Burger", "Ramen")

// filter -- keep matching items
items.filter { it.length > 4 }

// map -- transform each item
items.map { it.uppercase() }

// flatMap -- transform and flatten
orders.flatMap { it.items }

// groupBy -- group into a map
items.groupBy { it }                    // {Burger=[Burger, Burger], Pizza=[Pizza], ...}

// sortedBy / sortedByDescending
items.sortedBy { it.length }

// sumOf -- sum a numeric property
order.items.sumOf { it.price * it.quantity }

// take -- first N items
items.take(3)

// mapValues -- transform map values
grouped.mapValues { (_, v) -> v.size }

// associate -- create a map
items.associate { it to it.length }
```

### Coroutines

```kotlin
import kotlinx.coroutines.*

// Launch concurrent tasks
val scope = CoroutineScope(Dispatchers.Default)
scope.launch {
    // This runs concurrently
    delay(500)  // Non-blocking sleep
    println("Done!")
}

// Launch multiple tasks and wait for all
val jobs = items.map { item ->
    scope.launch {
        delay(500)
        processItem(item)
    }
}
jobs.forEach { it.join() }  // Wait for all to complete
```

### String Templates

```kotlin
val name = "Kotlin"
println("Hello, $name!")                       // Hello, Kotlin!
println("Length: ${name.length}")              // Length: 6
```

---

**Good luck and happy coding!** If you get stuck, ask for help -- that is what workshops are for.
