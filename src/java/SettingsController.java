import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML public TextField txtFieldDirectory;

    @FXML public Button btnSelectDirectory;


    public SettingsController()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onSelectDirectory(ActionEvent event){

    }

}
