package com.workshop.data

import com.workshop.models.*

object SampleData {

    val restaurants = mutableListOf(
        Restaurant(
            id = "resto-1",
            name = "Mang Inasal",
            cuisine = "Filipino",
            rating = 4.5,
            address = "123 Rizal Ave, Quezon City",
            deliveryTimeMinutes = 25,
            minimumOrder = 150.0,
            menu = listOf(
                MenuItem("mi-1", "Chicken Inasal Paa", "Grilled chicken leg quarter with rice", 149.0, "Mains", listOf("halal")),
                MenuItem("mi-2", "Chicken Inasal Pecho", "Grilled chicken breast with rice", 159.0, "Mains", listOf("halal")),
                MenuItem("mi-3", "Pork BBQ", "Grilled pork skewers with rice", 99.0, "Mains"),
                MenuItem("mi-4", "Halo-Halo", "Classic Filipino shaved ice dessert", 89.0, "Desserts", listOf("vegetarian")),
                MenuItem("mi-5", "Palabok", "Rice noodles with shrimp sauce", 79.0, "Noodles"),
                MenuItem("mi-6", "Extra Rice", "Plain steamed rice", 25.0, "Sides", listOf("vegetarian", "vegan", "gluten-free"))
            )
        ),
        Restaurant(
            id = "resto-2",
            name = "Jollibee",
            cuisine = "Filipino",
            rating = 4.3,
            address = "456 EDSA, Makati City",
            deliveryTimeMinutes = 20,
            minimumOrder = 100.0,
            menu = listOf(
                MenuItem("jb-1", "Chickenjoy 1pc", "Crispy fried chicken with rice", 99.0, "Mains"),
                MenuItem("jb-2", "Chickenjoy Bucket (6pc)", "Six pieces of crispy fried chicken", 449.0, "Mains"),
                MenuItem("jb-3", "Jolly Spaghetti", "Sweet-style Filipino spaghetti", 75.0, "Pasta"),
                MenuItem("jb-4", "Yumburger", "Classic Filipino-style burger", 45.0, "Burgers"),
                MenuItem("jb-5", "Burger Steak", "Beef patties with mushroom gravy and rice", 80.0, "Mains"),
                MenuItem("jb-6", "Peach Mango Pie", "Crispy fried pie with peach mango filling", 39.0, "Desserts", listOf("vegetarian")),
                MenuItem("jb-7", "Palabok Fiesta", "Rice noodles with garlic, shrimp, and egg", 85.0, "Noodles")
            )
        ),
        Restaurant(
            id = "resto-3",
            name = "Mesa Filipino Moderne",
            cuisine = "Filipino",
            rating = 4.7,
            address = "789 Bonifacio High Street, Taguig",
            deliveryTimeMinutes = 35,
            minimumOrder = 300.0,
            menu = listOf(
                MenuItem("mesa-1", "Sinigang na Baboy", "Pork sinigang with vegetables", 395.0, "Soups", listOf("gluten-free")),
                MenuItem("mesa-2", "Crispy Kare-Kare", "Crispy pork with peanut sauce and veggies", 455.0, "Mains"),
                MenuItem("mesa-3", "Laing", "Taro leaves in coconut milk", 245.0, "Vegetable", listOf("vegetarian", "gluten-free")),
                MenuItem("mesa-4", "Adobo sa Puti", "White adobo chicken", 345.0, "Mains", listOf("gluten-free")),
                MenuItem("mesa-5", "Sizzling Tofu", "Crispy tofu with special sauce", 195.0, "Vegetable", listOf("vegetarian")),
                MenuItem("mesa-6", "Bibingka", "Traditional rice cake", 155.0, "Desserts", listOf("vegetarian", "gluten-free")),
                MenuItem("mesa-7", "Leche Flan", "Classic caramel custard", 175.0, "Desserts", listOf("vegetarian", "gluten-free"))
            )
        ),
        Restaurant(
            id = "resto-4",
            name = "Ramen Nagi",
            cuisine = "Japanese",
            rating = 4.6,
            address = "SM Aura, Taguig",
            deliveryTimeMinutes = 30,
            minimumOrder = 200.0,
            menu = listOf(
                MenuItem("rn-1", "Original King Butao", "Rich pork bone broth ramen", 390.0, "Ramen"),
                MenuItem("rn-2", "Red King", "Spicy miso pork ramen", 410.0, "Ramen"),
                MenuItem("rn-3", "Green King", "Basil and cheese pork ramen", 410.0, "Ramen"),
                MenuItem("rn-4", "Black King", "Squid ink pork ramen", 410.0, "Ramen"),
                MenuItem("rn-5", "Gyoza (5pcs)", "Pan-fried dumplings", 180.0, "Sides"),
                MenuItem("rn-6", "Vegetarian Ramen", "Mushroom-based broth with tofu", 350.0, "Ramen", listOf("vegetarian"))
            )
        ),
        Restaurant(
            id = "resto-5",
            name = "Locavore",
            cuisine = "Filipino",
            rating = 4.8,
            address = "10 Brixton St, Pasig City",
            deliveryTimeMinutes = 40,
            minimumOrder = 250.0,
            menu = listOf(
                MenuItem("loc-1", "Lechon Belly Bites", "Crispy lechon belly with liver sauce", 285.0, "Mains"),
                MenuItem("loc-2", "Pancit Canton", "Stir-fried egg noodles with vegetables", 195.0, "Noodles", listOf("vegetarian")),
                MenuItem("loc-3", "Tokwa't Baboy", "Tofu and pork with vinegar soy dip", 165.0, "Appetizers"),
                MenuItem("loc-4", "Ensaladang Talong", "Grilled eggplant salad with tomatoes", 145.0, "Salads", listOf("vegetarian", "vegan", "gluten-free")),
                MenuItem("loc-5", "Bistek Tagalog", "Filipino-style beef steak with onions", 325.0, "Mains", listOf("gluten-free")),
                MenuItem("loc-6", "Turon", "Fried banana spring rolls with caramel", 95.0, "Desserts", listOf("vegetarian", "vegan")),
                MenuItem("loc-7", "Mais con Yelo", "Sweet corn shaved ice", 85.0, "Desserts", listOf("vegetarian", "gluten-free"))
            )
        )
    )

    val customers = mutableListOf(
        Customer(
            id = "cust-1",
            name = "Juan dela Cruz",
            email = "juan@email.com",
            phone = "+63 917 123 4567",
            address = Address(
                street = "Unit 5, Sunshine Residences, Katipunan Ave",
                city = "Quezon City",
                latitude = 14.6407,
                longitude = 121.0766
            )
        ),
        Customer(
            id = "cust-2",
            name = "Maria Santos",
            email = "maria@email.com",
            phone = "+63 918 234 5678",
            address = Address(
                street = "Block 3, Lot 7, BF Homes",
                city = "Parañaque",
                latitude = 14.4445,
                longitude = 121.0244
            )
        ),
        Customer(
            id = "cust-3",
            name = "Pedro Reyes",
            email = "pedro@email.com",
            phone = "+63 919 345 6789",
            address = null // No address on file - used for null safety exercises
        )
    )

    // Restaurant coordinates for delivery distance calculation
    val restaurantCoordinates = mapOf(
        "resto-1" to Pair(14.6510, 121.0495), // Mang Inasal - QC
        "resto-2" to Pair(14.5547, 121.0244), // Jollibee - Makati
        "resto-3" to Pair(14.5507, 121.0535), // Mesa - BGC
        "resto-4" to Pair(14.5430, 121.0565), // Ramen Nagi - SM Aura
        "resto-5" to Pair(14.5764, 121.0612)  // Locavore - Pasig
    )

    val orders = mutableListOf(
        Order(
            id = "order-1",
            restaurantId = "resto-1",
            customerId = "cust-1",
            items = listOf(
                LineItem("mi-1", "Chicken Inasal Paa", 2, 149.0),
                LineItem("mi-4", "Halo-Halo", 1, 89.0),
                LineItem("mi-6", "Extra Rice", 2, 25.0)
            ),
            status = OrderStatus.Placed,
            specialInstructions = "Extra chicken oil please",
            createdAt = "2026-02-05T13:30:00"
        ),
        Order(
            id = "order-2",
            restaurantId = "resto-2",
            customerId = "cust-2",
            items = listOf(
                LineItem("jb-2", "Chickenjoy Bucket (6pc)", 1, 449.0),
                LineItem("jb-3", "Jolly Spaghetti", 2, 75.0),
                LineItem("jb-6", "Peach Mango Pie", 3, 39.0)
            ),
            status = OrderStatus.Preparing,
            createdAt = "2026-02-05T14:00:00"
        ),
        Order(
            id = "order-3",
            restaurantId = "resto-3",
            customerId = "cust-1",
            items = listOf(
                LineItem("mesa-1", "Sinigang na Baboy", 1, 395.0),
                LineItem("mesa-3", "Laing", 1, 245.0),
                LineItem("mesa-6", "Bibingka", 2, 155.0)
            ),
            status = OrderStatus.OutForDelivery,
            specialInstructions = null,
            createdAt = "2026-02-05T12:15:00"
        ),
        Order(
            id = "order-4",
            restaurantId = "resto-5",
            customerId = "cust-2",
            items = listOf(
                LineItem("loc-1", "Lechon Belly Bites", 1, 285.0),
                LineItem("loc-4", "Ensaladang Talong", 1, 145.0),
                LineItem("loc-6", "Turon", 2, 95.0)
            ),
            status = OrderStatus.Delivered,
            createdAt = "2026-02-05T11:00:00"
        ),
        Order(
            id = "order-5",
            restaurantId = "resto-4",
            customerId = "cust-3",
            items = listOf(
                LineItem("rn-1", "Original King Butao", 1, 390.0),
                LineItem("rn-5", "Gyoza (5pcs)", 1, 180.0)
            ),
            status = OrderStatus.Placed,
            specialInstructions = "Extra firm noodles",
            createdAt = "2026-02-05T15:45:00"
        ),
        Order(
            id = "order-6",
            restaurantId = "resto-1",
            customerId = "cust-2",
            items = listOf(
                LineItem("mi-3", "Pork BBQ", 3, 99.0),
                LineItem("mi-5", "Palabok", 1, 79.0),
                LineItem("mi-6", "Extra Rice", 3, 25.0)
            ),
            status = OrderStatus.Cancelled,
            createdAt = "2026-02-05T10:30:00"
        )
    )
}
