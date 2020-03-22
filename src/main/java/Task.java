//This is a task class, holds the information needed to be added to the TableView
//
//IMPORTANT!!
//
//Do not change the structure of this class. The TableView depends on it
public class Task {
    private String title;
    private String dueDate;
    private String dueTime;

    public Task(String title, String due, String time)
    {
        this.title = title;
        dueDate = due;
        dueTime = time;
    }
    public String getTitle() {return title;}
    public String getDueDate() {return dueDate;}
    public String getDueTime() {return dueTime;}
    public String toString()
    {
        return "Title: " + title + " Due Date: " + dueDate + " Due Time: " + dueTime;
    }
}
