import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * IMPORTANT!!! AS of right now this does not utilize fileIO. Instead it uses hardcoded tasks as well as deleting and adding ones in program
 * There is no save or load functionality! Please ADD THIS
*/
public class TaskRetriever {

    String currentFile = "src/main/resources/default.csv";
    ArrayList<Task> tasks = new ArrayList<>();

    //base constructor to add base tasks from a default file
    public TaskRetriever()
    {
        loadTasks(currentFile);
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
        saveTasks();
    }

    public void removeTask(Task t)
    {
        tasks.remove(t);
        saveTasks();
    }

    //Used to change file in further implementations
    public void changeFile(String name){
        currentFile = name;
        loadTasks(currentFile);
    }

    //Loads tasks from a designated path
    //No return
    public void loadTasks(String name){
        try {
            Scanner scanner = new Scanner(new File(name));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.isEmpty()) {
                    String word[] = line.split(",");
                    Task newTask = new Task(word[0], word[1], word[2]);
                    tasks.add(newTask);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void saveTasks(){
        Writer writer = null;
        try {
            File file = new File(currentFile);
            writer = new BufferedWriter(new FileWriter(file));
            for (Task currentTask : tasks) {

                String text = currentTask.getTitle() + "," + currentTask.getDueDate() + "," +
                        currentTask.getDueTime()+"\n";

                writer.write(text);
            }
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
