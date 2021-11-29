import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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


    public Controller()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    @FXML public void onPlayTrack(ActionEvent actionEvent)
    {
        System.out.println("play/pause current song");
    }

    public void onNextTrack(ActionEvent actionEvent)
    {
        System.out.println("next song");
    }

    public void onPreviousTrack(ActionEvent actionEvent)
    {
        System.out.println("previous/reset song");
    }

    public void onRandomTrack(ActionEvent actionEvent)
    {
        System.out.println("get a random song");
    }

    public void onSettings(ActionEvent actionEvent)
    {
        System.out.println("open settings");
    }

    public void onMoveTrackUp(ActionEvent actionEvent)
    {
        System.out.println("track is moved upwards");
    }

    public void onMoveTrackDown(ActionEvent actionEvent)
    {
        System.out.println("track is moved downwards");
    }
}