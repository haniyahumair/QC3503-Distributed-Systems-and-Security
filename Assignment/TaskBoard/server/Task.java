package server;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {
    private int id;
    private String description;
    private String creator;
    private String listName;
    private List<String> labels;
    private String priority;

    public Task(int id, String description, String creator) {
        this.id = id;
        this.description = description;
        this.creator = creator;
        this.priority = "none";      // default value
        this.listName = "none";      // default value
        this.labels = new ArrayList<>();  // empty list
    }

    //"get" methods
    public int getId() {
        return this.id;
    }

    public String getCreator(){
        return this.creator;
    }

    public String getDescription(){
        return this.description;
    }

    public String getListName(){
        return this.listName;
    }

    public List<String> getLabels(){
        return this.labels;
    }

    public String getPriority(){
        return this.priority;
    }

    //"set" methods
    public void setListName(String listCategory){
        this.listName = listCategory;
    }

    public void setPriority(String newPriority){
        this.priority = newPriority;
    }

    public void addLabel(String newLabel){
        this.labels.add(newLabel);
    }

    //formatting
    public String toString(){
        return "#" + this.id + " | " + this.description + " | " + this.creator + " | " + this.priority + " | " + this.listName + " | " + this.labels;
    }
}