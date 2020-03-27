import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Text area created to display data retrieved
        TextArea ta = new TextArea();

        Scene scene = new Scene(new ScrollPane(ta),510,200);

        // Create & display the scene on the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();

        new Thread(() ->{
            try{
                // A socket created to connect to the Server that's being hosted
                ServerSocket serverSocket = new ServerSocket(7025);

                Platform.runLater(()->ta.appendText
                        ("Server started on " + new Date() + "\n"));

                // Looks & accepts a connection
                Socket socket = serverSocket.accept();

                // Data input stream to retrieve input from the client
                DataInputStream inputFromClient = new DataInputStream(
                        socket.getInputStream());

                while(true){
                    //System.out.println("Testing Server !");
                    
                    // Get data from the client
                    char s = inputFromClient.readChar();

                    // Display the data to the server
                    Platform.runLater(()->{
                        ta.appendText(String.valueOf(s));
                    });
                }

            } catch (IOException e){
                e.printStackTrace();
            }

        }).start();

    }

}
