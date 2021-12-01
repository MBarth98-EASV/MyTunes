import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javax.sound.sampled.Clip;
import java.util.Objects;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/MyTunesView.fxml")));
        primaryStage.setTitle("MyTunes");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }
}
