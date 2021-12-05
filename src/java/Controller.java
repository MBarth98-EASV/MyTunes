import dal.db.EASVDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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


    MusicPlayer songPlayer = new MusicPlayer();

    boolean isPlaying = false;

    public Controller()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    @FXML private void onPlayTrack(ActionEvent actionEvent) throws SQLException {
        EASVDatabase dbtest = new EASVDatabase();

        String testname = dbtest.getSongNameFromID(1, "Songs");
        int testid = dbtest.getSongIDFromName("test", "Songs");

        String artisttest = dbtest.getArtists(1, "Songs");
        String anotherartiststest = dbtest.getArtists("test", "Songs");

        System.out.println(testid);
        System.out.println(testname);

        System.out.println(artisttest);
        System.out.println(anotherartiststest);

        if(isPlaying)
        {
            songPlayer.pauseTrack();
            isPlaying = false;
            switchPlayPause();
            dbtest.removeSong("Songs" ,"The Great Cheese");
        }
        else
        {
            songPlayer.playTrack();
            isPlaying = true;
            switchPlayPause();
        }
    }

    @FXML private void onNextTrack(ActionEvent actionEvent)
    {
        songPlayer.nextTrack();
    }

    @FXML private void onPreviousTrack(ActionEvent actionEvent)
    {
        songPlayer.previousTrack();
    }

    @FXML
    private void onSettings(ActionEvent actionEvent)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/Settings.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 320, 157));
            stage.show();
        }
        catch (IOException e)
        {
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
    private void onSongNew(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/NewSong.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Song");
            stage.setScene(new Scene(root, 320, 190));
            stage.show();
        }
        catch (IOException e)
        {
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
    private void switchPlayPause()
    {
        if (isPlaying)
        {
            btnPlayPause.setStyle("-fx-background-image: url(/images/pause.png);-fx-background-position: 8");
        }
        else
        {
            btnPlayPause.setStyle("-fx-background-image: url(/images/play.png);-fx-background-position: 9");
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

    public void onShuffleToggled(ActionEvent actionEvent)
    {
    }
}
