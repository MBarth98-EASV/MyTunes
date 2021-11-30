import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.EventHandler;
import java.net.URL;
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

    @FXML public TableView tblViewSongs;

    @FXML public TableColumn tblClmnSongTitle;

    @FXML public TableColumn tblClmnSongArtist;

    @FXML public TableColumn tblClmnSongAlbum;

    @FXML public TableColumn tblClmnSongGenre;

    @FXML public TableColumn tblClmnSongTime;

    @FXML public Button btnSongUp;

    @FXML public Button btnSongDown;

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
    private static boolean isPlaying = true;

    public Controller()
    {
        //musicPlayer.loadMusic(filepath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    @FXML public void onPlayTrack(ActionEvent actionEvent)
    {
        isPlaying = !isPlaying;

        switchPlayPause();
    }

    @FXML public void onNextTrack(ActionEvent actionEvent)
    {
        System.out.println("next song");
    }

    @FXML public void onPreviousTrack(ActionEvent actionEvent)
    {
        System.out.println("previous/reset song");
    }

    @FXML public void onRandomTrack(ActionEvent actionEvent)
    {
        System.out.println("get a random song");
    }

    @FXML public void onSettings(ActionEvent actionEvent)
    {
        System.out.println("open settings");
    }

    @FXML public void onMoveTrackUp(ActionEvent actionEvent)
    {
        System.out.println("track is moved upwards");
    }

    @FXML private void onMoveTrackDown(ActionEvent actionEvent)
    {
        System.out.println("track is moved downwards");
    }

    @FXML
    public void switchPlayPause(){
        if (isPlaying == true){
            btnPlayPause.setStyle("-fx-background-image: url(/images/pause.png);"
                    + "-fx-background-position: 8");
        }
        else if (isPlaying == false){
            btnPlayPause.setStyle("-fx-background-image: url(/images/play.png);"
                    + "-fx-background-position: 9");
        }
    }
}