import be.PlaylistModel;
import be.SongModel;

import bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
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

    @FXML public TextField txtFieldPlaylistName;
    @FXML public Button btnAddPlaylist;

    @FXML public TextField txtFieldPLEditName;
    @FXML public Button btnEditPLName;

    String songPath = null;

    SongModel model;
    PlaylistModel playlistModel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if (resources != null)
        {
            model = (SongModel) resources.getObject("selectedSong");
            System.out.println(model.getTitle());

        }



    }

    public void onSelectFile(ActionEvent event) 
    {
        FileChooser fc = new FileChooser();

        File selectedFile =  fc.showOpenDialog(new Stage());
        txtFieldAddSongPath.setText(selectedFile.getAbsolutePath());


        songPath = txtFieldAddSongPath.getText();

    }


    public void onAddSong(ActionEvent event) {
        if(!txtFieldAddSongPath.getText().equals(null) && !txtFieldAddSongPath.getText().isEmpty()){
            DataManager.addSong(Path.of(songPath));
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    public void onEditSongDone(ActionEvent event)
    {
        String editTitle = txtFieldEditTitle.getText();
        String editArtist = txtFieldEditArtist.getText();
        String editAlbum = txtFieldEditAlbum.getText();
        String editGenre = txtFieldEditGenre.getText();
        SongModel currentlySelected = model;

        if (editArtist != null || !editArtist.isEmpty() || !editArtist.equals("null")){
        currentlySelected.setArtists(editArtist); }
        if (editTitle != null || !editTitle.isEmpty() || !editTitle.equals("null")){
        currentlySelected.setTitle(editTitle); }
        if (editAlbum != null || !editAlbum.isEmpty() || !editAlbum.equals("null")){
        currentlySelected.setAlbum(editAlbum); }
        if (editGenre != null || !editGenre.isEmpty() || !editGenre.equals("null")){
        currentlySelected.setTag(editGenre); }

        DataManager.editSong(currentlySelected);

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onEditPlaylist(ActionEvent event) {
        String editName = txtFieldPLEditName.getText();
        PlaylistModel currentlySelected = playlistModel;
        if (editName != null || !editName.isEmpty() || !editName.equals("null")){
            currentlySelected.setName(editName); }

        DataManager.editPlaylist(currentlySelected);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onAddPlaylist(ActionEvent event) {
        DataManager.addPlaylist(txtFieldPlaylistName.getText());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
