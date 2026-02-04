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

### How to Iterate (Auto-Reload)

The project supports **auto-reload** so you do NOT need to restart the server after every change.

Open **two terminals** in VS Code (`Ctrl + `` ` then click the **+** button):

| Terminal | Command | Purpose |
|---|---|---|
| Terminal 1 | `./gradlew run` | Runs the server |
| Terminal 2 | `./gradlew -t build` | Watches files and recompiles on save |

Then just **edit → save → refresh the browser**. The server auto-reloads with your changes.

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

The `calculateTotal()` function should calculate the total cost of an order. Read the doc comment on the function to understand the business rules, then compare what the code _actually_ does versus what it _should_ do.

---

## Level 2: Guided Implementation (Kotlin Features)

**Goal:** Implement new features using **sealed classes**, **`when` expressions**, and **null safety**. The function signatures are provided -- you fill in the logic.

---

### Exercise 2A: Order Status Transitions

| | |
|---|---|
| **Files** | `src/main/kotlin/com/workshop/models/OrderStatus.kt` and `src/main/kotlin/com/workshop/services/OrderService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2StatusTransitionTest"` |
| **Endpoint** | `PUT /api/orders/{id}/status` with JSON body `{ "status": "Preparing" }` |

An order goes through a lifecycle: it gets placed, prepared, sent out for delivery, and delivered. It can also be cancelled. But not every transition makes sense -- you should not be able to go from `Delivered` back to `Placed`, for example.

**Your task:** Look at the `OrderStatus` sealed class and its subclasses. Then:

1. Implement `canTransitionTo()` in `OrderStatus.kt` -- decide which transitions are valid
2. Implement `updateOrderStatus()` in `OrderService.kt` -- parse the status string, validate the transition, and update the order

Read the test file to understand exactly which transitions should be allowed and which should be rejected.

---

### Exercise 2B: Delivery Fee Calculator

| | |
|---|---|
| **File** | `src/main/kotlin/com/workshop/services/DeliveryService.kt` |
| **Test** | `./gradlew test --tests "com.workshop.Level2DeliveryFeeTest"` |
| **Endpoint** | `GET /api/orders/{id}/delivery-fee` |

Implement `calculateDeliveryFee()`. Given an order, figure out how far the restaurant is from the customer and charge accordingly.

Look at:
- The `DeliveryFeeResult` data class at the bottom of the file to understand what you need to return
- The `distanceInKm()` helper function already provided
- `SampleData.kt` for restaurant coordinates and customer data
- The test file for the exact fee tiers and expected behavior

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

### String Templates

```kotlin
val name = "Kotlin"
println("Hello, $name!")                       // Hello, Kotlin!
println("Length: ${name.length}")              // Length: 6
```

---

**Good luck and happy coding!** If you get stuck, ask for help -- that is what workshops are for.
