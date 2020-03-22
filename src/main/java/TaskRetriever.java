import java.time.LocalDate;
import java.util.ArrayList;
/**
 * IMPORTANT!!! AS of right now this does not utilize fileIO. Instead it uses hardcoded tasks as well as deleting and adding ones in program
 * There is no save or load functionality! Please ADD THIS
*/
public class TaskRetriever {
    //Hardcoded base tasks for example
    Task task1 = new Task("CSCI2020U project", "2020-03-23", "3:30pm");
    Task task2 = new Task("CSCI2160U assn", "2020-03-23", "11:59pm");
    Task task3 = new Task("CSCI2072U assn4", "2020-03-23", "11:59pm");
    Task task4 = new Task("CSCI2040U labs 6-10", "2020-04-03", "11:59pm");
    //A list of all tasks (this can be substituted out once fileIO is implemented
    ArrayList<Task> tasks = new ArrayList<>();

    //base constructor to add base tasks
    public TaskRetriever()
    {
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
    }

    //Returns all tasks on the entered date from the datepicker at the top of the screen
    //Needs FileIO implementation
    //Maybe have headers in the text file? a certain date will have the header yyyy-mm-dd
    //and then everything under that until the next header will get added
    //if a date has no header when adding stuff, make one
    //if a date has no header when loading stuff, it's empty
    public ArrayList<Task> getTasksOnDate(LocalDate date)
    {
        ArrayList<Task> list = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).getDueDate().equals(date.toString()))
            {
                list.add(tasks.get(i));
            }
        }
        return list;
    }

    //Adding and removing tasks from the total array list
    //SHOULD BE SUBSTITUTED WITH FILEIO /SERVERS/SOCKETS
    public void addTask(Task t)
    {
        tasks.add(t);
    }

    public void removeTask(Task t)
    {
        tasks.remove(t);
    }
}
