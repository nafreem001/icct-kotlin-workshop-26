plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

group = "com.workshop"
version = "1.0.0"

application {
    mainClass.set("com.workshop.ApplicationKt")
}

tasks.named<JavaExec>("run") {
    jvmArgs("-Dio.ktor.development=true")
}

repositories {
    mavenCentral()
}

val ktorVersion = "2.3.7"

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.22")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.22")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
