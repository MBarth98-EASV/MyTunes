import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.File;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML public TextField txtFieldSearch;

    @FXML public Button btnSettings;

    @FXML public TreeTableView treeView;

    @FXML public TreeTableColumn tvColumnPlaylist;

    @FXML public TreeTableColumn tvColumnArtist;

    @FXML public TreeTableColumn tvColumnSongs;

    @FXML public TreeTableColumn tvColumnTime;

    @FXML public Button btnPlaylistUp;

    @FXML public Button btnPlaylistDown;

    @FXML public Button btnPlaylistNew;

    @FXML public Button btnPlaylistEdit;

    @FXML public Button btnPlaylistDelete;

    @FXML public TableView tblViewSongs;

    @FXML public TableColumn tblClmnSongTitle;

    @FXML public TableColumn tblClmnSongArtist;

    @FXML public TableColumn tblClmnSongAlbum;

    @FXML public TableColumn tblClmnSongGenre;

    @FXML public TableColumn tblClmnSongTime;

    @FXML public Button btnSongUp;

    @FXML public Button btnSongDown;

    @FXML public Button btnSongNew;

    @FXML public Button btnSongEdit;

    @FXML public Button btnSongDelete;

    @FXML public Button btnPlayPause;

    @FXML public Button btnNextSong;

    @FXML public Button btnPrevSong;

    @FXML public Label lblSongName;

    @FXML public Label lblSongCurrentTime;

    @FXML public Label lblSongTotalTime;

    @FXML public Slider sliderSong;

    @FXML public ToggleButton tglBtnShuffle;

    @FXML public Slider sliderVolume;


/*
    static musicPlayer player = musicPlayer.getInstance();
    static String filepath = "C:\\Users\\Kish\\Documents\\GitHub\\1st_semester_exam\\testMusic\\TestMusicFile.wav";
    static long clipTimePosition;
*/

    private static boolean isPlaying = false;

    public Controller()
    {
        //musicPlayer.loadMusic(filepath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this::systemAudioControl);

    }

    @FXML private void onPlayTrack(ActionEvent actionEvent)
    {
        isPlaying = !isPlaying;

        switchPlayPause();
    }

    @FXML private void onNextTrack(ActionEvent actionEvent)
    {
        System.out.println("next song");
    }

    @FXML private void onPreviousTrack(ActionEvent actionEvent)
    {
        System.out.println("previous/reset song");
    }

    @FXML private void onShuffleToggled(ActionEvent actionEvent)
    {
        System.out.println("get a random song");
    }

    @FXML
    private void onSettings(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/Settings.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 320, 157));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onMoveSongUp(ActionEvent actionEvent)
    {
        System.out.println("track is moved upwards");
    }

    @FXML private void onMoveSongDown(ActionEvent actionEvent)
    {
        System.out.println("track is moved downwards");
    }

    @FXML
    private void onSongNew(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/NewSong.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Song");
            stage.setScene(new Scene(root, 320, 190));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSongEdit(ActionEvent event){

    }

    @FXML
    private void onSongDelete(ActionEvent event){

    }


    @FXML
    private void switchPlayPause(){
        if (isPlaying == true){
            btnPlayPause.setStyle("-fx-background-image: url(/images/pause.png);"
                    + "-fx-background-position: 8");
        }
        else if (isPlaying == false){
            btnPlayPause.setStyle("-fx-background-image: url(/images/play.png);"
                    + "-fx-background-position: 9");
        }
    }

    private boolean systemAudioControl(KeyEvent event)
    {
        try
        {
            int key = event.getKeyCode();

            if (key == KeyEvent.VK_PAUSE || key == KeyEvent.VK_SPACE)
            {
                onPlayTrack(null);
            }

            event.consume();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    @FXML
    private void setVolume(ActionEvent event){

    }

    @FXML
    private void onPlaylistUp(ActionEvent event){

    }

    @FXML
    private void onPlaylistDown(ActionEvent event){

    }

    @FXML
    private void onSearch(ActionEvent event){

    }

    @FXML
    private void onPlaylistNew(ActionEvent event){

    }

    @FXML
    private void onPlaylistEdit(ActionEvent event){

    }

    @FXML
    private void onPlaylistDelete(ActionEvent event){

    }


}