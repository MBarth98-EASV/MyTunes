import CustomComponent.AutoCompleteTextField;
import CustomComponent.ComboBoxEnum;
import be.ISearchable;
import be.MyTunesFXMLProperties;
import be.PlaylistModel;
import be.SongModel;
import bll.AudioManager;
import dal.Utility;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import dal.db.EASVDatabase;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import jdk.jshell.execution.Util;
import model.LocalFilesModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.SearchModel;
import org.apache.commons.lang.NotImplementedException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller extends MyTunesFXMLProperties implements Initializable
{
/**Assuming that the newly added item has an index of N,
 Selecting it:

 listView.getSelectionModel().select(N);
 Focusing on it:

 listView.getFocusModel().focus(N);
 Scrolling to it:

 listView.scrollTo(N);
 *
 * To Do: Get the selected item from search and do that. Use StringToMap.
 */

    AudioManager audioManager = new AudioManager();

    SearchModel searchModel;

    public Controller()
    {
        audioManager.getIsPlayingProperty().addListener((observable, oldValue, newValue) -> playPauseUpdateStyle(newValue));
        txtFieldSearch = new AutoCompleteTextField();
        searchModel = new SearchModel();
        
        tblViewSongs.getColumns().add(this.tblClmnSongTitle);
        tblViewSongs.getColumns().add(this.tblClmnSongArtist);
        tblViewSongs.getColumns().add(this.tblClmnSongGenre);
        tblViewSongs.getColumns().add(this.tblClmnSongAlbum);
        tblViewSongs.getColumns().add(this.tblClmnSongTime);

        tblViewPlaylist.getColumns().add(this.tblClmnPlaylistName);
        tblViewPlaylist.getColumns().add(this.tblClmnPlaylistSongCount);
        tblViewPlaylist.getColumns().add(this.tblClmnPlaylistDuration);

        audioManager.handleEndOfMusic.set(() -> onNextTrack(null));
    }


    public void onSongSelectionChanged()
    {
        try
        {
            this.audioManager.setSong(tblViewSongs.getSelectionModel().getSelectedItem());
        }
        catch (Exception ex)
        {
            // todo: error handling
        }
    }


    private void playPauseUpdateStyle(boolean state)
    {
        String playStyle = "-fx-background-image: url(/images/pause.png); -fx-background-position: 8;";
        String pauseStyle = "-fx-background-image: url(/images/play.png); -fx-background-position: 9;";

        btnPlayPause.setStyle(state ? playStyle : pauseStyle);
    }

    private void playlistInitialize()
    {
        this.tblClmnPlaylistName.setCellValueFactory(data -> data.getValue().getNameProperty());
        this.tblClmnPlaylistSongCount.setCellValueFactory(data -> data.getValue().getCountProperty().asString());
        this.tblClmnPlaylistDuration.setCellValueFactory(data -> data.getValue().getTotalDurationProperty().asString());

        this.tblViewPlaylist.setItems(audioManager.getPlaylists());

        this.audioManager.addPlaylist(new PlaylistModel(0, new EASVDatabase().getAllSongs(), 0, true, "Default"));
    }

    private void songsInitialize()
    {
        this.tblClmnSongTitle.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        this.tblClmnSongArtist.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artists"));
        this.tblClmnSongGenre.setCellValueFactory(new PropertyValueFactory<SongModel, String>("genre"));
        this.tblClmnSongAlbum.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));
        this.tblClmnSongTime.setCellValueFactory(new PropertyValueFactory<SongModel, String>("duration"));

        Utility.bind(tblViewSongs, audioManager.getAvailableSongs());

        tblViewSongs.getFocusModel().focusedCellProperty().addListener(o -> onSongSelectionChanged());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        playlistInitialize();
        songsInitialize();
        playlistContextMenu();
        songContextMenu();

        initializeMMSearchEntries();

        setComboBox();

        sliderVolume.setMin(0.0);
        sliderVolume.setMax(1.0);
        sliderVolume.setValue(1.0);

        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            audioManager.setVolume(sliderVolume.getValue());
        });

        this.sliderSong.setMax(1.0);
        this.sliderSong.setMin(0.0);
        sliderSong.valueProperty().bind(this.audioManager.getCompletionRatio());

        this.lblSongName.textProperty().bind(this.audioManager.getCurrentSong().asString());

        this.lblSongCurrentTime.textProperty().bind(Bindings.createStringBinding(this::formatCurrentTime, this.audioManager.getCurrentTime()));
        this.lblSongTotalTime.textProperty().bind(Bindings.createStringBinding(this::formatTotalTime, this.audioManager.getTotalTime()));
    }


    @FXML private void onPlayPauseTrack(ActionEvent actionEvent)
    {
        if (audioManager.getIsPlayingProperty().get())
        {
            audioManager.pause();
        }
        else
        {
            audioManager.play();
        }
    }

    @FXML private void onNextTrack(ActionEvent actionEvent)
    {
        tblViewSongs.getSelectionModel().selectNext();
    }

    @FXML private void onPreviousTrack(ActionEvent actionEvent)
    {
        tblViewSongs.getSelectionModel().selectPrevious();
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
        throw new NotImplementedException();
    }

    @FXML private void onMoveSongDown(ActionEvent actionEvent)
    {
        System.out.println("track is moved downwards");
        throw new NotImplementedException();
    }

    @FXML
    private void onSongNew(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/NewSong.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Song");
            stage.setMaxHeight(193);
            stage.setMinHeight(193);
            stage.setMaxWidth(320);
            stage.setMinWidth(320);
            stage.setScene(new Scene(root, 320, 193));
            stage.show();
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(new EASVDatabase().getAllSongs())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tblViewSongs.refresh();
    }

    @FXML
    private void onSongEdit(ActionEvent event)
    {
        try {

            ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][] {
                            { "selectedSong", tblViewSongs.getSelectionModel().getSelectedItem() }
                    };
                }
            };

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/EditSong.fxml")), resources);

            Stage stage = new Stage();

            stage.setTitle("Edit Song");
            stage.setMaxHeight(305);
            stage.setMinHeight(305);
            stage.setMaxWidth(320);
            stage.setMinWidth(320);
            stage.setScene(new Scene(root, 320, 305));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSongDelete(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void onPlaylistUp(ActionEvent event)
    {
        this.audioManager.setVolume(sliderVolume.getValue());
    }

    @FXML
    private void onPlaylistDown(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    /**
     * Sets the searchbutton enterkey action depending on the selected mode in
     * the combobox.
     * If Search is selected, the searchbox dropdown will contain suggestions for every playlist and song.
     * If a filter is selected, the table of songs will change to only include songs with the given parameters.
     * @param event
     */
    @FXML
    private void onSearch(ActionEvent event)
    {
        txtFieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent key)
            {
                if (key.getCode().equals(KeyCode.ENTER))
                {
                    int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

                    switch (ComboBoxEnum.values()[selectedItem])
                    {
                        case ARTIST -> {
                            searchModel.filterArtist(tblViewSongs, txtFieldSearch.getText());
                        }
                        case ALBUM -> {
                            searchModel.filterAlbum(tblViewSongs, txtFieldSearch.getText());
                        }
                        case GENRE -> {
                            searchModel.filterGenre(tblViewSongs, txtFieldSearch.getText());
                        }
                        case ARTISTTITLE -> {
                            searchModel.filterArtistAndTitle(tblViewSongs, txtFieldSearch.getText());
                        }
                        default -> {
                            Utility.bind(tblViewSongs, audioManager.getAvailableSongs());
                        }
                    }
                }
            }
        });
    }


    @FXML
    private void onPlaylistNew(ActionEvent event)
    { try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/NewPlaylist.fxml")));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 236, 193));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPlaylistEdit(ActionEvent event)
    { try {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/EditPlaylist.fxml")));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 236, 193));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }

}

    @FXML
    private void onPlaylistDelete(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    /**
     * Sets the searchbar's search entries for autocompletion to MusicModel, a superclass
     * of be.SongModel and be.PlaylistModel. Allows to search for both songs and playlist.
     * Default value for the searchbar. Used in the actual search function.
     */
    private void initializeMMSearchEntries()
    {
        txtFieldSearch.getEntries().clear();
        for (int i = 0; i < tblViewSongs.getItems().size(); i++){
            txtFieldSearch.getEntries().add((tblViewSongs.getItems().get(i)).toString());

        }
    }

    /**
     * Sets the searchbar's search entires for autocompletion to the input list of Strings.
     * Used for filter autocompletion - it's filled with a list of every unique value of the chosen parameter
     * in the database.
     * @param inputList
     */
    private <T extends ISearchable> void initializeGenericSearchEntries(List<T> inputList){
        txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
            txtFieldSearch.getEntries().add((inputList.get(i).toSearchable()));
        }
    }

    private void initializeStringSearchEntries(List<String> inputList){
        txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
            txtFieldSearch.getEntries().add((inputList.get(i)));
        }
    }



    public void onSongRemoveFromPlaylist(ActionEvent actionEvent) {
    }

    public void onSongAddToPlayList(ActionEvent actionEvent)
    {
        SongModel addSong = tblViewSongs.getSelectionModel().getSelectedItem();
        // todo: ?
    }


    private void setComboBox()
    {
        cmboBoxFilter.setItems(FXCollections.observableArrayList("Search", "Artist | Filter", "Album | Filter", "Genre | Filter", "Artist/Title | Filter"));
        cmboBoxFilter.getSelectionModel().select(0);
    }

    /**
     * Clears the textfield and sets the filter combobox to search (it's default value).
     * Effectively removes any filter the user has made.
     * @param event
     */
    public void onClearSearchFilter(ActionEvent event) {
        cmboBoxFilter.getSelectionModel().select(0);
        Utility.bind(this.tblViewSongs, audioManager.getAvailableSongs());
        txtFieldSearch.clear();
    }

    /**
     * Sets the searchbar's prompt text, and it's search entries for the autocomplete feature.
     */
    public void onComboBoxSelect(ActionEvent event)
    {
        int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

        switch (ComboBoxEnum.values()[selectedItem])
        {
            case ARTIST -> {
                initializeStringSearchEntries(searchModel.allAvailableArtist());
                txtFieldSearch.setPromptText("Enter artist to filter");
            }
            case ALBUM -> {
                initializeStringSearchEntries(searchModel.allAvailableAlbums());
                txtFieldSearch.setPromptText("Enter album to filter");
            }
            case GENRE -> {
                initializeStringSearchEntries(searchModel.allAvailableGenre());
                txtFieldSearch.setPromptText("Enter genre to filter");
            }
            case ARTISTTITLE -> {
                initializeStringSearchEntries(searchModel.allAvailableTitleArtist());
                txtFieldSearch.setPromptText("Enter artist or title to filter");
            }
            default -> {
                initializeGenericSearchEntries(searchModel.allAvailable());
                txtFieldSearch.setPromptText("Press enter to search");
            }
        }
    }

    private String formatDuration(Duration duration)
    {
        if (duration == null) {
            return "00:00";
        } else {
            return String.format("%02d:%02d", (int) duration.toMinutes(), (int) (duration.toSeconds() % 60));
        }
    }

    private String formatCurrentTime() {
        return formatDuration(this.audioManager.getCurrentTime().get());
    }
    private String formatTotalTime() {
        return formatDuration(this.audioManager.getTotalTime().get());
    }


    @FXML
    public void songContextMenu(){
        ContextMenu contextMenuSongs = new ContextMenu();
        tblViewSongs.setContextMenu(contextMenuSongs);
        contextMenuSongs.setStyle("-fx-background-color: #404040; ");

        MenuItem addSong = new MenuItem("Add to playlist");
        addSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem newSong = new MenuItem("New");
        newSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem editSong = new MenuItem("Edit");
        editSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem deleteSong = new MenuItem("Delete");
        deleteSong.setStyle("-fx-text-fill: #d5d4d4;");

        addSong.setOnAction(event -> onSongAddToPlayList(event));
        newSong.setOnAction(event -> onSongNew(event));
        editSong.setOnAction(event -> onSongEdit(event));
        deleteSong.setOnAction(event -> onSongDelete(event));

        contextMenuSongs.getItems().add(addSong);
        contextMenuSongs.getItems().add(newSong);
        contextMenuSongs.getItems().add(editSong);
        contextMenuSongs.getItems().add(deleteSong);
    }

    @FXML
    public void playlistContextMenu(){
        ContextMenu contextMenuPlaylist = new ContextMenu();
        contextMenuPlaylist.setStyle("-fx-background-color: #404040;");
        tblViewPlaylist.setContextMenu(contextMenuPlaylist);

        MenuItem newSong = new MenuItem("Remove from playlist");
        newSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem removeSong = new MenuItem("New");
        newSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem editSong = new MenuItem("Edit");
        editSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem deleteSong = new MenuItem("Delete");
        deleteSong.setStyle("-fx-text-fill: #d5d4d4;");

        removeSong.setOnAction(event -> onSongRemoveFromPlaylist(event));
        newSong.setOnAction(event -> onPlaylistNew(event));
        editSong.setOnAction(event -> onPlaylistEdit(event));
        deleteSong.setOnAction(event -> onPlaylistDelete(event));

        contextMenuPlaylist.getItems().add(removeSong);
        contextMenuPlaylist.getItems().add(newSong);
        contextMenuPlaylist.getItems().add(editSong);
        contextMenuPlaylist.getItems().add(deleteSong);
    }

}
