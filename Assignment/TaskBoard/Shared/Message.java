package shared;

import java.io.Serializable;

public class Message implements Serializable {
    private String messageBody;
    private String user;
    private String command;

    public Message(String messageBody, String user, String command) {
        this.messageBody = messageBody;
        this.user = user;
        this.command = command;
    }
    public String getMessageBody(){
        return this.messageBody;
    }
    public String getUser(){
        return this.user;
    }

    public String getCommand() { return command; }

    @Override
    public String toString(){
        return this.user + ": " + this.messageBody;
    }
}