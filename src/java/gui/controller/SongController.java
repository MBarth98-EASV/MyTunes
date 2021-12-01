package gui.controller;

import gui.model.LocalFilesModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class SongController implements Initializable {

    @FXML public TextField txtFieldSongPath;

    @FXML public Button btnSelectFile;

    String songPath = null;

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
        txtFieldSongPath.setText(selectedFile.getAbsolutePath());
        songPath = txtFieldSongPath.getText();

        localFilesModel.addSong(songPath);
    }

    public void onAddSong(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}
