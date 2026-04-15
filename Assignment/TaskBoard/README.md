# **TASKBOARD PROGRAMING ASSIGNMENT**
A multi-threaded client-server application for managing tasks with labeling and subscription capabilities.
## Installation & Setup

### Prerequisites
* **Java Development Kit (JDK) 11 or higher**: [Download here](https://www.oracle.com/java/technologies/downloads/)
* **Terminal/Command Prompt**: (Windows PowerShell, CMD, or macOS/Linux Terminal)

### Setup Instructions
1. **Clone or Download**: Ensure the `TaskBoard` project folder is on your local machine.
2. **Open Terminal**: Navigate to the root directory of the project:
   ```bash
   cd path/--/TaskBoard
   
## Running The Code
1. **Compile files**: From the root directory, compile all packages simultaneously to ensure dependencies are resolved:
    ```bash
   javac */*.java
2. **Start the server**: Run the server first so it can listen for incoming client connections.
    ```bash
   java server.StartTaskBoardServer
3. **Start client(s)**: Open a new terminal window(s) for every user you wish to connect.
    ```bash
   java client.TaskBoardClient
   
## Source Code Structure
Mentioned below is the directory layout and the purpose of each file
### Root directory
- `README.md`: for project documentation and set-up guide
### `/server` File structure
- `StartTaskBoardServer.java`: Initializes the TaskBoard and starts the `ServerSocket` to accept incoming clients.
- `TaskBoard.java`: The brains of the Taskboard's features. Manages the `HashMap` of tasks and the `HashMap` for subscriptions. Uses `synchronized` methods to ensure thread safety across multiple users.
- `Tasks.java`: Data model that represents and individual task. This is where the `taskId`, `description`, `lists`, `priority`, and `labels` are initialized and stored.
- `TaskBoardServer.java`: Handles the low-level networking logic, including accepting new socket connections, `Thread's` initialization, and passing them to the `ClientHandler`.
- `ClientHandler.java`: A dedicated thread for each connected user. It listens for specific `commands` (e.g., `CREATE`, `SUBSCRIBE`) and communicates between the user and the `TaskBoard`.
- `ConnectionPool.java`: A management class that keeps track of all active ClientHandler threads. It is where we broadcast messages to specific users or everyone connected to the socket.
### `/client` File structure
- `TaskBoardClient.java`: This is where the client connection logic lies. It captures user input from the terminal, wraps it into `Message` objects, and sends them to the server.
- `StartClient.java`: Entry point for the client. Contains `main()` which launches `TaskBoardClient`.
### `/Shared` File structure
- `Message.java`: The communication protocol object. It is a serializable class that allows the client and server to exchange structured data (command, body, and user_name). It's placed in the shared folder as the communication is shared between both the clients and the server.

## Features & Commands
Once the client is connected to the `ServerSocket`, you can use the following commands:
- `REGISTER [Username]` — Registers your name with the server session
- `CREATE [Task Description] #label` — Adds a new task. If a #label is included, the server updates the label registry.
- `CREATE_LIST [ListName]` - Create a new list to store and organize your tasks.
- `LIST` - View all the tasks created. 
- `ADD <taskId> <listName>` - You can assign specific tasks to a list and store them, to make your tasks more organized.
- `MOVE <taskId> <listName>]` - You can move as specific task to another list/ a new list.
- `DELETE [ListName]` - Delete a specific list from the list directory.
- `LABEL <taskId> <labelName>]` - Lets you add a label to a specific task.
- `LABELS` - Displays the full lists of labels.
- `SUBSCRIBE #label` — Adds your username to a specific label.
- `VIEW_SUBS` - Displays the full list of subscriptions and who's subscribed to what.
- `UNSUBSCRIBE #label` - Removes your username from the label.
- `VIEW <listName>` - Retrieves and displays the full details of a specific list.
- `VIEW <listName> <taskId> ` — shows full details of a specific task
- `PRIORITY <taskId>` - Lets you set priorities to specific tasks (HIGH, MEDIUM, LOW).

Lastly, typing `exit` safely closes the connection and notifies other users of your departure