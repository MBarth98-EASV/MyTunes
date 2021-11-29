import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    public RadioButton radioBtnYoutube;
    public RadioButton radioBtnServer;
    public RadioButton radioBtnLocal;
    public TextField txtFieldSearch;
    public Button btnSettings;

    public TableView tblViewPlaylist;
    public TableColumn tblClmnPlaylists;
    public TableColumn tblClmnPlaylistSongs;
    public TableColumn tblClmnPlaylistTime;
    public Button btnPlaylistUp;
    public Button btnPlaylistDown;
    public MenuButton mnuBtnPlaylistOptions;

    public TableView tblViewSongs;
    public TableColumn tblClmnSongTitle;
    public TableColumn tblClmnSongArtist;
    public TableColumn tblClmnSongAlbum;
    public TableColumn tblClmnSongGenre;
    public TableColumn tblClmnSongTime;
    public Button btnSongUp;
    public Button btnSongDown;
    public MenuButton mnuBtnSongOptions;

    public Button btnPlayPause;
    public Button btnNextSong;
    public Button btnPrevSong;
    public Label lblSongName;
    public Label lblSongCurrentTime;
    public Label lblSongTotalTime;
    public Slider sliderSong;
    public ToggleButton tglBtnShuffle;
    public Slider sliderVolume;


    public Controller(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

}