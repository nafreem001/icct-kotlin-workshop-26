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

## How to Iterate

There is **no hot reloading** -- after editing code, you need to restart the server to see your changes.

1. **Edit** your code in VS Code
2. **Stop** the running server with `Ctrl + C` in the terminal
3. **Restart** the server with `./gradlew run` (or `gradlew.bat run` on Windows)
4. **Refresh** the dashboard in your browser and click **"Re-check After Server Restart"** to see updated progress

Alternatively, you can verify your work by running tests (no server restart needed):

```bash
./gradlew test --tests "com.workshop.Level1MenuFilterTest"
```

Tests run against your code directly, so they always reflect your latest changes.

---

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
