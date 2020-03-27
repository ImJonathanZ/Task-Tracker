/**
 * Main.java for Calendar Project
*/
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Initialize Main Vbox
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setSpacing(10);

        //Initialize vbox cells
        DatePicker datePicker = new DatePicker();
        Label chartTitle = new Label("Events for date:");
        TableView<Task> tableView = new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);   //tableView columns are resizable

        //"bottom" hbox is for all of the controls for adding an event
        HBox bottom = new HBox();
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(15, 15, 15, 15));
        Button add = new Button("Add");
        TextField newTitle = new TextField("Title:");
        TextField newTime = new TextField("Due Time");
        DatePicker newDate = new DatePicker();
        bottom.getChildren().addAll(add, newTitle, newDate, newTime);

        //"delete" hbox is for all of the controls for deleting an event
        Button remove = new Button("Delete");
        Label instructions = new Label("Select an event and press Delete to remove it");
        HBox delete = new HBox();
        delete.getChildren().addAll(instructions, remove);
        delete.setSpacing(10);
        delete.setPadding(new Insets(15, 15, 15, 15));

        //Add everything to the main vbox
        vbox.getChildren().addAll(datePicker, chartTitle, tableView, bottom, delete);

        //create columns for TableView, data comes from Task.java
        TableColumn<Task, String> titles = new TableColumn<>("Title");
        titles.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, String> dueDates = new TableColumn<>("Due Date");
        dueDates.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableColumn<Task, String> dueTimes = new TableColumn<>("Due Time");
        dueTimes.setCellValueFactory(new PropertyValueFactory<>("dueTime"));

        tableView.getColumns().addAll(titles, dueDates, dueTimes);
        TaskRetriever taskRetriever = new TaskRetriever();

        //When pressing the add button, this will occur
        add.setOnAction(e -> {
            Task newTask = new Task(newTitle.getText(), newDate.getValue().toString(), newTime.getText());
            taskRetriever.addTask(newTask);
            fillTable(tableView, taskRetriever, datePicker, chartTitle);
        });

        //When pressing the datepicker at the top, this will occur
        datePicker.setOnAction(e -> {
            fillTable(tableView, taskRetriever, datePicker, chartTitle);
        });

        //when pressing the delete button, this will occur
        remove.setOnAction(e -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            taskRetriever.removeTask(selected);
            fillTable(tableView, taskRetriever, datePicker, chartTitle);
        });

        //Main scene/stage setup
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //add css file
        primaryStage.setTitle("Event Manager");
        primaryStage.show();

    }

    //Updates table with new values. Without this method, you can't add things in the date you're on
    public void fillTable(TableView tb, TaskRetriever tr, DatePicker dp, Label lb)
    {
        lb.setText("Events on date: " + dp.getValue().toString());
        tb.refresh();
        tb.getItems().clear();
        ArrayList<Task> tasks = tr.getTasksOnDate(dp.getValue());
        for (int i = 0; i < tasks.size(); i++)
        {
            tb.getItems().add(tasks.get(i));
        }
    }
}

//i was unable to get the program to run with multithreading so this is the commented version of multithreading-obaida
/* //Updates table with new values. Without this method, you can't add things in the date you're on
    @Override
    public void run() {


        public void fillTable (TableView tb, TaskRetriever tr, DatePicker dp, Label lb)
        {
            lb.setText("Events on date: " + dp.getValue().toString());
            tb.refresh();
            tb.getItems().clear();
            ArrayList<Task> tasks = tr.getTasksOnDate(dp.getValue());
            for (int i = 0; i < tasks.size(); i++) {
                tb.getItems().add(tasks.get(i));
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    };t.setUncaughtExceptionHandler(h);
t.start();

    };
}
*/
