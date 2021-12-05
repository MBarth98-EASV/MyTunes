import dal.db.EASVDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.LocalFilesModel;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML public TextField txtFieldAddSongPath;

    @FXML
    public Button btnSelectFile;

    String songPath = null;
    LocalFilesModel localFilesModel;

    public SongController()
    {
        localFilesModel = new LocalFilesModel();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void onSelectFile(ActionEvent event)
    {
        FileChooser fc = new FileChooser();

        File selectedFile = fc.showOpenDialog(new Stage());
        txtFieldAddSongPath.setText(selectedFile.getAbsolutePath());

        songPath = txtFieldAddSongPath.getText();
    }

    /**
    public void onAddSong(ActionEvent event)
    {
        FileChooser fc = new FileChooser();

        ((Node) (event.getSource())).getScene().getWindow().hide();
        File selectedFile =  fc.showOpenDialog(new Stage());
        txtFieldAddSongPath.setText(selectedFile.getAbsolutePath());


        songPath = txtFieldAddSongPath.getText();

    }

     */

    EASVDatabase dbtest = new EASVDatabase();

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
}
