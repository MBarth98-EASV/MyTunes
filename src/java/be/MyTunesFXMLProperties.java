package be;

import CustomComponent.AutoCompleteTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MyTunesFXMLProperties
{
    @FXML public AutoCompleteTextField txtFieldSearch;

    @FXML public Button btnSettings;

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

    @FXML public Slider sliderVolume = new Slider();

    @FXML public Button btnSongAddToPlaylist;

    @FXML public Button btnSongRemoveFromPlaylist;

    @FXML public ComboBox cmboBoxFilter;

    @FXML public Button btnClearSearchFilter;

    @FXML public TableView<PlaylistModel> tblViewPlaylist = new TableView<PlaylistModel>();

    @FXML public TableColumn<PlaylistModel, String> tblClmnPlaylistName = new TableColumn<PlaylistModel, String>();

    @FXML public TableColumn<PlaylistModel, String> tblClmnPlaylistSongCount = new TableColumn<PlaylistModel, String>();

    @FXML public TableColumn<PlaylistModel, String> tblClmnPlaylistDuration = new TableColumn<PlaylistModel, String>();

}
