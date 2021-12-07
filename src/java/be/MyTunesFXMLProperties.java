package be;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MyTunesFXMLProperties
{

    @FXML
    public TextField txtFieldSearch;

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

    @FXML public TableView<SongModel> tblViewSongs = new TableView<SongModel>();

    @FXML public TableColumn<SongModel, String> tblClmnSongTitle = new TableColumn<SongModel, String>();

    @FXML public TableColumn<SongModel, String> tblClmnSongArtist = new TableColumn<SongModel, String>();

    @FXML public TableColumn<SongModel, String> tblClmnSongAlbum = new TableColumn<SongModel, String>();

    @FXML public TableColumn<SongModel, String> tblClmnSongGenre = new TableColumn<SongModel, String>();

    @FXML public TableColumn<SongModel, String> tblClmnSongTime = new TableColumn<SongModel, String>();

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

}