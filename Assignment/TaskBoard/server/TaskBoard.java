package server;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class TaskBoard {
    private HashMap<Integer, Task> tasks;
    private ArrayList<String> lists;
    private ArrayList<String> labels;
    private HashMap<String, List<String>> subscriptions;
    private int nextId = 1;

    public TaskBoard() {
        this.tasks = new HashMap<>();
        this.lists = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.subscriptions = new HashMap<>();
        this.nextId = 1;
    }

    public synchronized Task createTask(String description, String creator) {
        if (description != null && !description.isEmpty()) {
            Task newTask = new Task(nextId, description, creator);
            tasks.put(nextId, newTask);
            nextId++;

            String[] words = description.split(" ");

            for (String word : words) {
                if (word.startsWith("#")) {
                    newTask.addLabel(word);

                    if (!labels.contains(word)) {
                        this.labels.add(word);
                    }
                }
            }

            return newTask;
        }

        return null;
    }

    public synchronized Task getTask(int tasksId){
        Task foundTask = tasks.get(tasksId);
        return foundTask;
    }

    public synchronized String viewTask(int taskId){
        Task t = getTask(taskId);
        StringBuilder viewAllTasks = new StringBuilder("[TaskBoard] Tasks:\n");

        if(t == null){
            return "[Taskboard] Error: Task ID: " + taskId + "doesn't exist.";
        }
        else{
            viewAllTasks.append("ID: ").append(taskId)
                    .append(" | User: ").append(t.getCreator())
                    .append(" | Desc: ").append(t.getDescription())
                    .append(" | ").append(t.getPriority())
                    .append(" | ").append(t.getLabels())
                    .append("\n");
        }
        return viewAllTasks.toString();
    }

    public synchronized String getAllTasks(){
        StringBuilder allTasksText = new StringBuilder("[TaskBoard] Current Tasks:\n");

        for (int id: tasks.keySet()){
            Task t = tasks.get(id);

            allTasksText.append("ID: ").append(id)
                    .append(" | User: ").append(t.getCreator())
                    .append(" | Desc: ").append(t.getDescription())
                    .append("\n");
        }
        return allTasksText.toString();
    }

    public synchronized String createList(String listName){
        if (lists.contains(listName)) {
            return "Error: List " + listName + " already exists.";
        }

        lists.add(listName);
        return "List " + listName + " created.";
    }

    public synchronized String addTaskToList(int taskId, String listName){
        Task task = getTask(taskId);
        if(task == null){
            return "[Taskboard] Error: Task ID: " + taskId + " doesn't exist.";
        }

        if (!lists.contains(listName)) {
            return "[TaskBoard] Error: List: " + listName + " doesn't exist.";
        }

        task.setListName(listName);
        return "[Taskboard] Task: " + taskId + " moved to list: "+ listName + ".";
    }

    public synchronized String moveTask(int taskId, String listName){
        Task task = getTask(taskId);
        if(task == null){
            return "[Taskboard] Error: Task ID: " + taskId + " doesn't exist.";
        }

        if (!lists.contains(listName)) {
            return "[TaskBoard] Error: List: " + listName + " doesn't exist.";
        }

        task.setListName(listName);
        return "[Taskboard] Task: " + taskId + " moved to new list: "+ listName + ".";
    }

    public synchronized String deleteList(String listName){
        if(lists.contains(listName)){
            lists.remove(listName);
            for(int id : tasks.keySet()){
                Task task = tasks.get(id);

                if (task.getListName().equals(listName)){
                    task.setListName("none");
                }
            }
            return "[TaskBoard] List: " + listName + " deleted.";

        }
        else{
            return "[TaskBoard] Error: List: " + listName + " doesn't exist.";
        }
    }

    public synchronized String viewList(String listName){
        if(!lists.contains(listName)){
            return "[TaskBoard] Error: List: " + listName + " doesn't exist.";
        }

        StringBuilder viewTasksInList = new StringBuilder("[TaskBoard] Tasks in: " + listName + "\n");

        for (int id : tasks.keySet()){
            Task t = tasks.get(id);

            if(t.getListName().equals(listName)){
                viewTasksInList.append("ID: ").append(id)
                        .append(" | ").append(t.getCreator())
                        .append(" | ").append(t.getDescription())
                        .append(" | ").append(t.getPriority())
                        .append(" | ").append(t.getLabels())
                        .append("\n");
            }
        }
        return viewTasksInList.toString();
    }

    public synchronized String getAllLists(){
        if (lists.isEmpty()) {
            return "[TaskBoard] No lists have been created yet.";
        }

        StringBuilder sb = new StringBuilder("[TaskBoard] Available Lists:\n");
        for (String listName : lists) {
            sb.append("- ").append(listName).append("\n");
        }
        return sb.toString();
    }

    public synchronized String addLabel(String labelName){
        if(!labelName.startsWith("#")){
            return "[TaskBoard] Error: Label: " + labelName + " must start with a '#'.";
        }

        if(labels.contains(labelName)){
            return "[TaskBoard] Error: Label: " + labelName + " already exists.";
        }

        labels.add(labelName);
        return "[TaskBoard] Successfully added Label: " + labelName + " to labels array.";
    }

    public synchronized String getAllLabels(){
        StringBuilder allLabelsText = new StringBuilder("[TaskBoard] Current Labels:\n");

        for (String label: labels){
            allLabelsText.append("- ").append(label).append("\n");
        }
        return allLabelsText.toString();
    }

    public synchronized String subscribe(String labelName, String userName) {
        if (!labels.contains(labelName)) {
            return "[TaskBoard] Error: Label: " + labelName + " doesn't exist.";
        }

        if (!subscriptions.containsKey(labelName)){
            subscriptions.put(labelName, new ArrayList<>());
        }

        if (subscriptions.get(labelName).contains(userName)){
            return "[TaskBoard] Your already subscribed!";
        }
        else{
            subscriptions.get(labelName).add(userName);
            return "[TaskBoard] Successfully added username: " + userName + " to label " + labelName + "." ;
        }
    }

    public synchronized String unsubscribe(String labelName, String userName) {
        if (!labels.contains(labelName)) {
            return "[TaskBoard] Error: Label: " + labelName + " doesn't exist.";
        }

        if (!subscriptions.get(labelName).contains(userName)) {
            return "[TaskBoard] Error: Username: " + userName + " doesn't exist.";
        }

        subscriptions.get(labelName).remove(userName);
        return "[TaskBoard] Successfully removed username: " + userName + " from label " + labelName +"." ;
    }

    public synchronized String getAllSubscriptions(String labelName, String userName) {
        if (!labels.contains(labelName)) {
            return "[TaskBoard] Error: Label: " + labelName + " doesn't exist.";
        }

        StringBuilder viewAllSubscriptions = new StringBuilder("[TaskBoard] Subscriptions list: \n");

        for(String user : subscriptions.get(labelName)){
            viewAllSubscriptions.append("#").append(labelName).append("->").append(user).append("\n");
        }
        return viewAllSubscriptions.toString();
    }

    public synchronized List<String> getSubscribers(String labelName){
        if (!labels.contains(labelName)) {
            return null;
        }
        return subscriptions.get(labelName);
    }

}