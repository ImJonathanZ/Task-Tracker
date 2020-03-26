/**
 * Main.java for Calendar Project
 * Last edited by Ian Reynolds
 * March 22nd, 2020, 4:39PM
 *
 * Description:
 * This will show a datepicker and a table. Upon picking a date, if there are any tasks on that date, the table will
 * display them. If not, the table is empty.
 *
 * TO DO:
 * -Save and load functionality by fileIO
 * -Implement multithreading and servers/sockets
 * -Make it look nicer
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {

    // IO Stream
    DataOutputStream toServer = null;

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
            try{
                Task newTask = new Task(newTitle.getText(), newDate.getValue().toString(), newTime.getText());
                taskRetriever.addTask(newTask);
                fillTable(tableView, taskRetriever, datePicker, chartTitle);

                // Send the data to the Server
                toServer.writeChars(String.valueOf(newTask));
                toServer.flush();

            }catch (IOException er){
                System.err.println(er);
            }

        });

        //When pressing the datepicker at the top, this will occur
        datePicker.setOnAction(e -> {
            fillTable(tableView, taskRetriever, datePicker, chartTitle);
        });

        //when pressing the delete button, this will occur
        remove.setOnAction(e -> {
            try{
                Task selected = tableView.getSelectionModel().getSelectedItem();
                taskRetriever.removeTask(selected);
                fillTable(tableView, taskRetriever, datePicker, chartTitle);

                //System.getProperty("line.separator");

                // Send data to the Server
                toServer.writeChars(String.valueOf(selected));
                toServer.flush();

            } catch (IOException er){
                System.err.println(er);
            }

        });


        try {
            // A socket created to connect to the Server that's being hosted
            Socket socket = new Socket("localhost", 7025);

            // A Output Stream to send data to the Server
            toServer = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

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
