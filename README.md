# 🤖 Jarvis Lite

<h3 align="center">Your Simple Desktop Automation Assistant</h3>

<p align="center">
  A Java-based desktop assistant that makes everyday computer tasks easier through simple text commands.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/GUI-Java%20Swing-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Platform-Windows-0078D6?style=for-the-badge&logo=windows" />
  <img src="https://img.shields.io/badge/Build-No%20Maven-success?style=for-the-badge" />
</p>

---

## 📌 About The Project

**Jarvis Lite** is a simple text-based desktop automation assistant developed using **Java and Java Swing**.

It allows users to interact with their computer through a modern graphical chat interface. Users can type commands to open desktop applications, launch websites, perform mathematical calculations, search the internet, and retrieve current date, time, and weather information.

This project was developed as a **2nd Semester BS Software Engineering (BSSE) OOP project**, focusing on practical Java programming and Object-Oriented Programming concepts.

---

## ✨ Features

<table>
  <tr>
    <td width="50%">
      <h3>🖥️ Desktop Automation</h3>
      <ul>
        <li>Open Notepad</li>
        <li>Open Calculator</li>
        <li>Open Paint</li>
        <li>Open File Explorer</li>
        <li>Launch supported websites</li>
      </ul>
    </td>
    <td width="50%">
      <h3>🧮 Smart Calculator</h3>
      <ul>
        <li>Addition and subtraction</li>
        <li>Multiplication and division</li>
        <li>Decimal and negative numbers</li>
        <li>Multiple operations</li>
        <li>Operator precedence and brackets</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
      <h3>🌐 Internet Search</h3>
      <ul>
        <li>Google Search integration</li>
        <li>Search general questions</li>
        <li>Automatic search for unknown commands</li>
      </ul>
    </td>
    <td>
      <h3>📅 Date & Time</h3>
      <ul>
        <li>Current time</li>
        <li>Current date</li>
        <li>Current day of the week</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
      <h3>🌤️ Weather Information</h3>
      <ul>
        <li>Live weather updates</li>
        <li>City-based weather search</li>
        <li>Temperature in Celsius</li>
        <li>Weather descriptions</li>
      </ul>
    </td>
    <td>
      <h3>💬 Modern Chat Interface</h3>
      <ul>
        <li>Dark-themed GUI</li>
        <li>Message bubbles</li>
        <li>Text-based interaction</li>
        <li>Enter key support</li>
        <li>Automatic scrolling</li>
      </ul>
    </td>
  </tr>
</table>

---

## 🛠️ Technologies Used

| Technology | Purpose |
|:---|:---|
| ☕ Java | Core programming language |
| 🎨 Java Swing | Graphical user interface |
| 🖥️ Java AWT | Desktop integration |
| ⚙️ ProcessBuilder | Launching Windows applications |
| 🌐 Desktop API | Opening websites |
| 🔗 Java HTTP Client | Weather API communication |
| 📆 Java Time API | Date and time handling |
| 🌤️ OpenWeatherMap API | Live weather information |

---

## 📂 Project Structure

```text
Jarvis-Lite/
│
├── 📄 ChatFrame.java
├── 📄 BotEngin.java
├── 📄 CommandProcessor.java
├── 📄 AppLauncher.java
├── 📄 Calculator.java
├── 📄 SearchHandler.java
├── 📄 DateTimeHandler.java
├── 📄 MessageLog.java
│
├── 📄 README.md
└── 📄 .gitignore
```

### 📦 Class Responsibilities

| Class | Description |
|:---|:---|
| `ChatFrame` | Creates and manages the Java Swing GUI. |
| `BotEngin` | Main engine that processes user commands. |
| `CommandProcessor` | Connects different feature classes. |
| `AppLauncher` | Opens desktop applications and websites. |
| `Calculator` | Evaluates mathematical expressions. |
| `SearchHandler` | Performs Google searches. |
| `DateTimeHandler` | Provides date, time, and day information. |
| `MessageLog` | Maintains messages during the current session. |

---

## 💻 Supported Commands

Try typing these commands directly into the Jarvis Lite chat interface.

| Command | Action |
|:---|:---|
| `hello` | Displays a greeting |
| `who are you` | Introduces Jarvis Lite |
| `open notepad` | Opens Notepad |
| `open calculator` | Opens Windows Calculator |
| `open paint` | Opens Paint |
| `open file explorer` | Opens File Explorer |
| `open youtube` | Opens YouTube |
| `open google` | Opens Google |
| `open github` | Opens GitHub |
| `open chatgpt` | Opens ChatGPT |
| `5 + 3` | Calculates addition |
| `2 + 2 * 3` | Calculates with operator precedence |
| `(2 + 3) * 4` | Calculates bracket expressions |
| `search for Java tutorials` | Searches Google |
| `what is Java` | Automatically searches Google |
| `time` | Displays current time |
| `date` | Displays current date |
| `day` | Displays current day |
| `weather in Karachi` | Retrieves current weather |
| `bye` | Displays a goodbye message |

---

## 🏗️ System Architecture

Jarvis Lite follows a modular architecture in which each class is responsible for a specific task.

```text
              👤 USER
                 |
                 v
          +--------------+
          |  ChatFrame   |
          +--------------+
                 |
                 v
          +--------------+
          |   BotEngin   |
          +--------------+
                 |
                 v
       +-------------------+
       | CommandProcessor  |
       +-------------------+
          |      |      |
          v      v      v
       App     Calc   Search
      Launcher ulator Handler
                 |
                 v
           DateTimeHandler
```

### ⚙️ How It Works

1. The user enters a command through the chat interface.
2. `ChatFrame` receives the input.
3. `BotEngin` identifies the type of command.
4. `CommandProcessor` forwards it to the appropriate class.
5. The selected feature performs the requested operation.
6. The result is displayed in the chat interface.

---

## 🧠 Object-Oriented Programming

The project demonstrates important OOP principles and Java programming practices.

| Concept | Implementation |
|:---|:---|
| Encapsulation | Private fields and controlled access through class methods. |
| Abstraction | Hiding internal implementation behind simple methods. |
| Inheritance | Java API inheritance and framework-based class extension. |
| Polymorphism | Method overriding and event-handling mechanisms where applicable. |
| Composition | CommandProcessor manages objects of other feature classes. |
| Modularity | Separate classes for separate responsibilities. |

---

## 🚀 Installation & Setup

### 1. Requirements

Before running the project, make sure you have:

- Windows 10 or Windows 11
- Java Development Kit (JDK)
- Visual Studio Code or another Java-compatible editor
- Internet connection
- OpenWeatherMap API key for weather functionality

**Note:** Maven is not required.

### 2. Clone the Repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

Alternatively, download the repository as a ZIP file and extract it.

### 3. Open the Project

Open the extracted `Jarvis-Lite` folder in Visual Studio Code.

### 4. Configure Weather API

Create your own API key through:

[OpenWeatherMap](https://openweathermap.org/api)

Set the API key using Windows Command Prompt:

```cmd
setx OPENWEATHER_API_KEY "YOUR_API_KEY"
```

Restart Visual Studio Code after setting the environment variable.

The application retrieves the key using:

```java
System.getenv("OPENWEATHER_API_KEY");
```

### 5. Compile the Project

Open the terminal in your project directory:

```bash
javac *.java
```

### 6. Run Jarvis Lite

```bash
java ChatFrame
```

🎉 **Jarvis Lite is now ready to use!**

---

## 🔐 Security

- Never upload API keys to GitHub.
- Store API credentials in environment variables.
- Use only predefined and trusted application commands.
- Keep private credentials outside the source code.

---

## 🔮 Future Improvements

Possible future enhancements include:

- Support for additional desktop applications.
- Improved command recognition.
- More advanced mathematical operations.
- Additional GUI customization.
- Better#   J a r v i s _ L i t e  
 