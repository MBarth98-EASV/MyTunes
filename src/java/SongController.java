import be.PlaylistModel;
import be.SongModel;

import bll.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

    @FXML public ListView<PlaylistModel> lstViewAddSongToPlaylist = new ListView<>();
    @FXML public Button btnSelectPlaylist;


    String songPath = null;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        lstViewAddSongToPlaylist.setItems(DataManager.getPlaylists());
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
            DataManager.add(Path.of(songPath));
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    public void onEditSongDone(ActionEvent event)
    {
        String editTitle = txtFieldEditTitle.getText();
        String editArtist = txtFieldEditArtist.getText();
        String editAlbum = txtFieldEditAlbum.getText();
        String editGenre = txtFieldEditGenre.getText();

        if (editArtist != null && !editArtist.isEmpty()){
            DataManager.selectedSong().get().setArtists(editArtist); }
        if (editTitle != null && !editTitle.isEmpty()){
            DataManager.selectedSong().get().setTitle(editTitle); }
        if (editAlbum != null && !editAlbum.isEmpty()){
            DataManager.selectedSong().get().setAlbum(editAlbum); }
        if (editGenre != null && !editGenre.isEmpty()){
            DataManager.selectedSong().get().setGenre(editGenre); }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onEditPlaylist(ActionEvent event)
    {
        String editName = txtFieldPLEditName.getText();

        if (editName != null && !editName.isEmpty()){
            DataManager.selectedPlaylist().get().setName(editName);
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onAddPlaylist(ActionEvent event) {
        if (txtFieldPlaylistName.getText() != null && !txtFieldPlaylistName.getText().isEmpty())
        DataManager.addPlaylist(txtFieldPlaylistName.getText());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void onAddToPlaylist(ActionEvent event) {
        DataManager.songAddToPlaylist(lstViewAddSongToPlaylist.getSelectionModel().getSelectedItem(), DataManager.selectedSong().get());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
