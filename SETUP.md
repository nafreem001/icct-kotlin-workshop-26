# Workshop Setup Guide

Complete these steps **before** the workshop so we can jump straight into coding.

## Prerequisites

### 1. Java 17+ (JDK)

Download from [https://adoptium.net/](https://adoptium.net/) and select **Temurin 21 LTS**.

Verify your installation:

```bash
java -version
```

You should see version 17 or higher in the output.

### 2. VS Code

Download from [https://code.visualstudio.com/](https://code.visualstudio.com/) and install with default settings.

### 3. Git

Download from [https://git-scm.com/](https://git-scm.com/) and install with default settings.

## VS Code Extensions

When you open the project in VS Code, you will be prompted to install recommended extensions. Accept the prompt to install:

- **Kotlin Language** (`fwcd.kotlin`) -- syntax highlighting, code completion, and diagnostics

## Step-by-Step Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/user/icct-kotlin-workshop-26.git
   cd icct-kotlin-workshop-26
   ```

2. **Open the project in VS Code**
   ```bash
   code .
   ```

3. **Install recommended extensions** when VS Code prompts you.

4. **Open the integrated terminal** with `Ctrl + `` ` (backtick).

5. **Run the application**
   - Mac/Linux: `./gradlew run`
   - Windows: `gradlew.bat run`

6. **Open the dashboard** at [http://localhost:8080/](http://localhost:8080/) in your browser.

7. **Confirm it works.** You should see the dashboard with mostly red indicators. That is expected -- your job during the workshop is to turn them green.

## Running Tests

Run tests for a specific level to check your progress:

```bash
./gradlew test --tests "com.workshop.Level1MenuFilterTest"
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `java: command not found` | Make sure Java is installed and `JAVA_HOME` is set. Restart your terminal after installing. |
| `Port 8080 already in use` | Close whatever is using that port, or change the port in `Application.kt`. |
| Gradle download is slow | The first run downloads dependencies. Be patient -- subsequent runs are much faster. |
| Tests are failing | That is expected! The exercises start with failing tests. Your job is to make them pass. |
