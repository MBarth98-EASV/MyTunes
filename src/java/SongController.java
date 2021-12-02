import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.LocalFilesModel;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML public TextField txtFieldAddSongPath;

    @FXML public Button btnSelectFile;

    @FXML public TextField txtFieldDirectory;

    @FXML public Button btnSelectDirectory;
    @FXML public Button btnApply;

    String songPath = null;
    Path localDirPath = null;
    LocalFilesModel localFilesModel;

    public SongController()
    {
        localFilesModel = new LocalFilesModel();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onSelectFile(ActionEvent event) {
        FileChooser fc = new FileChooser();

        File selectedFile =  fc.showOpenDialog(new Stage());
        txtFieldAddSongPath.setText(selectedFile.getAbsolutePath());


        songPath = txtFieldAddSongPath.getText();

    }

    public void onAddSong(ActionEvent event) {
        if(!txtFieldAddSongPath.getText().equals(null) && !txtFieldAddSongPath.getText().isEmpty()){
            localFilesModel.addSong(Path.of(songPath));
        }


        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onRemoveSong(ActionEvent event) {

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onEditSong(ActionEvent event) {

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Opens a DirectoryChooser, and sets the accompanying TextField's text as the directory.
     * Should the user choose to manipulate the chosen directory, it parses the text in the TextField
     * as a path, which is then used by other classes.
     */
    @FXML
    public void onSelectDirectory(ActionEvent event){
        DirectoryChooser dc = new DirectoryChooser();

        File selectedDirectory = dc.showDialog(new Stage());

        if (selectedDirectory == null)
            return;

        txtFieldDirectory.setText(selectedDirectory.getAbsolutePath());

        Path path = Path.of(txtFieldDirectory.getText());
    }

    @FXML
    public void onApplySettings(ActionEvent event){
        if(!txtFieldDirectory.getText().isEmpty() &&
                !txtFieldDirectory.getText().equals(null))
        {
            localFilesModel.getAllLocalSongs(Path.of(txtFieldDirectory.getText()));
            localDirPath = Path.of(txtFieldDirectory.getText());
            localFilesModel.setLocalDirPathInModel(localDirPath);
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}
