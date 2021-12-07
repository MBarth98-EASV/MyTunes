import dal.db.EASVDatabase;
import be.SongModel;
import model.LocalFilesModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML public TextField txtFieldAddSongPath;
    @FXML public Button btnSelectFile;


    @FXML public TextField txtFieldEditTitle;
    @FXML public TextField txtFieldEditArtist;
    @FXML public TextField txtFieldEditAlbum;
    @FXML public TextField txtFieldEditGenre;

    @FXML public Button btnEditDone;

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


    EASVDatabase dbtest = new EASVDatabase();

    public void onAddSong(ActionEvent event) 
    {
        if(!txtFieldAddSongPath.getText().equals(null) && !txtFieldAddSongPath.getText().isEmpty())
        {
            localFilesModel.addSong(Path.of(songPath));
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
  
  
    public void onRemoveSong(ActionEvent event) {

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onEditSongDone(ActionEvent event) {
        String editTitle = txtFieldEditTitle.getText();
        String editArtist = txtFieldEditArtist.getText();
        String editAlbum = txtFieldEditAlbum.getText();
        String editGenre = txtFieldEditGenre.getText();
        SongModel currentlySelected = LocalFilesModel.getCurrentlySelectedSong();

        if (editArtist != null || !editArtist.isEmpty() || !editArtist.equals("null")){
        currentlySelected.setArtists(editArtist); }
        if (editTitle != null || !editTitle.isEmpty() || !editTitle.equals("null")){
        currentlySelected.setName(editTitle); }
        if (editAlbum != null || !editAlbum.isEmpty() || !editAlbum.equals("null")){
        currentlySelected.setAlbum(editAlbum); }
        if (editGenre != null || !editGenre.isEmpty() || !editGenre.equals("null")){
        currentlySelected.setTag(editGenre); }

        localFilesModel.editSong(currentlySelected);


        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
