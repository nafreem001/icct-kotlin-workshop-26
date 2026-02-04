# Workshop Setup Guide

Follow these steps to get your environment ready. We will do this together at the start of the workshop.

## Prerequisites

These should already be installed on the lab machines. If you are on your own laptop, install them now.

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
- **Java Extension Pack** (`vscjava.vscode-java-pack`) -- debugging, testing, and project management
- **Kotlin Ktlint Formatter** (`rnoro.vscode-ktlint-formatter`) -- code formatting that preserves multi-line lambdas

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

Once everyone sees the dashboard, we are ready to start.

## How to Iterate (Hot Reload)

The server supports **hot reload** so you do not need to restart it after every change. Use two terminals:

**Terminal 1** -- Start the server (run once):
```bash
./gradlew run
```

**Terminal 2** -- Start the continuous build (run once):
```bash
./gradlew -t classes
```

Now when you save a file, Terminal 2 automatically recompiles and the running server picks up the changes. Just refresh your browser.

### Alternative: Run tests

You can also verify your work by running tests (no server needed):

```bash
./gradlew test --tests "com.workshop.Level1MenuFilterTest"
```

Tests always use your latest saved code.

## Debugging with VS Code

The server starts with a debug port enabled on **port 5005**. You can attach the VS Code debugger to set breakpoints and step through your code.

### How to Debug

1. **Start the server** in a terminal: `./gradlew run`
2. **Set breakpoints** by clicking to the left of a line number in any `.kt` file
3. **Attach the debugger**: Press `F5` or go to **Run > Start Debugging** and select **"Attach to Server"**
4. **Trigger the code** by making an API call (via the dashboard or browser)
5. VS Code will pause at your breakpoint and you can inspect variables, step through code, etc.

### Running and Debugging Tests

With the **Java Extension Pack** installed, you can run and debug individual tests directly from VS Code:

- Open any test file (e.g., `Level1MenuFilterTest.kt`)
- Click the green play button next to a test method to run it
- Click the debug icon next to a test method to debug it with breakpoints
- Use the **Testing** sidebar (flask icon) to see all tests and their status

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
| `Port 8080 already in use` | A previous server is still running. See "Killing a stuck server" below. |
| Gradle download is slow | The first run downloads dependencies. Be patient -- subsequent runs are much faster. |
| Tests are failing | That is expected! The exercises start with failing tests. Your job is to make them pass. |
| Hot reload not working | Make sure `./gradlew -t classes` is running in a second terminal. Check Terminal 2 for compilation errors. |
| Debugger won't attach | Make sure the server is running first (`./gradlew run`). The debug port is 5005. |
| `Address already in use` on port 5005 | A previous server process is still running. See "Killing a stuck server" below. |

### Killing a stuck server

If you close a terminal window without pressing `Ctrl + C` first, the server process may keep running in the background and hold onto ports 8080 and 5005. The next time you try `./gradlew run` you will see an "Address already in use" error.

**Windows (PowerShell):**
```powershell
# Find what is using the port
netstat -ano | findstr :5005

# Kill the process by its PID (the last number in the output)
Stop-Process -Id <PID> -Force
```

**Windows (Command Prompt):**
```cmd
netstat -ano | findstr :5005
taskkill /F /PID <PID>
```

**Mac / Linux:**
```bash
lsof -i :5005
kill -9 <PID>
```

After killing the process, `./gradlew run` should start normally. To avoid this, always stop the server with `Ctrl + C` in the terminal before closing it.
