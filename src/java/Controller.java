import CustomComponent.AutoCompleteTextField;
import CustomComponent.ComboBoxEnum;
import be.MyTunesFXMLProperties;
import be.PlaylistModel;
import be.SongModel;
import bll.AudioManager;
import bll.DataManager;
import dal.Utility;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.event.EventHandler;
import dal.db.EASVDatabase;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import javafx.collections.FXCollections;
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

import javax.xml.crypto.Data;
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

    private void onPlaylistSelectionChanged()
    {
        try
        {
            DataManager.selectedPlaylist().setValue(tblViewPlaylist.getSelectionModel().selectedItemProperty().getValue());
            Utility.bind(tblViewSongs, new SimpleListProperty<>(DataManager.selectedPlaylist().get().getSongs()));

            if (tblViewSongs.getItems().size() > 0)
            {
                tblViewSongs.getSelectionModel().select(0);
            }
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

        //this.tblViewPlaylist.setItems(audioManager.getPlaylists());
        DataManager.fetchplaylists();
        //DataManager.playlists.add(new PlaylistModel(, DataManager.getAllSongs(), 0, true, "Default"));
        Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<>(DataManager.getPlaylists()));

        tblViewPlaylist.getFocusModel().focusedCellProperty().addListener(o -> onPlaylistSelectionChanged());
    }



    private void songsInitialize()
    {
        this.tblClmnSongTitle.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        this.tblClmnSongArtist.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artists"));
        this.tblClmnSongGenre.setCellValueFactory(new PropertyValueFactory<SongModel, String>("genre"));
        this.tblClmnSongAlbum.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));
        this.tblClmnSongTime.setCellValueFactory(new PropertyValueFactory<SongModel, String>("duration"));

        Utility.bind(tblViewSongs, new SimpleListProperty<>(DataManager.selectedPlaylist().get().getSongs()));

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

        tblViewPlaylist.getSelectionModel().select(0);
        tblViewSongs.getSelectionModel().select(0);

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
        /*try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/Settings.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 320, 157));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

         */ //TODO
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
        try
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/NewSong.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Song");
            stage.resizableProperty().setValue(false);
            stage.setScene(new Scene(root, 320, 193));
            stage.show();

            stage.getScene().getWindow().setOnHiding((o) -> Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs())));
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSongEdit(ActionEvent event)
    {
        try {
            ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][] {
                            { "selectedSong", tblViewSongs.getSelectionModel().getSelectedItem()}, {"selectedPlaylist", tblViewPlaylist.getSelectionModel().getSelectedItem()}
                    };
                }
            };

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/EditSong.fxml")), resources);

            Stage stage = new Stage();

            stage.setTitle("Edit Song");
            stage.setMaxHeight(335);
            stage.setMinHeight(335);
            stage.setMaxWidth(335);
            stage.setMinWidth(335);
            stage.setScene(new Scene(root, 335, 335));
            stage.show();
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs())));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSongDelete(ActionEvent event)
    {
        DataManager.removeSong(tblViewSongs.getSelectionModel().getSelectedItem());
        Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs()));
        Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists()));

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
                            Utility.bind(tblViewSongs, DataManager.selectedPlaylist().get().getSongs());
                            searchModel.Search(tblViewSongs, txtFieldSearch);
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
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPlaylistEdit(ActionEvent event) {
        if (tblViewPlaylist.getSelectionModel().getSelectedItem() != null) {
            try {
                ResourceBundle resources = new ListResourceBundle() {
                    @Override
                    protected Object[][] getContents() {
                        return new Object[][]{
                                {"selectedPlaylist", tblViewPlaylist.getSelectionModel().getSelectedItem()}, {"selectedSong", tblViewSongs.getSelectionModel().getSelectedItem()}
                        };
                    }
                };

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/EditPlaylist.fxml")), resources);
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 236, 193));
                stage.show();
                stage.getScene().getWindow().setOnHiding((o) -> Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists())));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}

    @FXML
    private void onPlaylistDelete(ActionEvent event)
    {
        DataManager.removePlaylist(tblViewPlaylist.getSelectionModel().getSelectedItem());
        tblViewPlaylist.getItems().remove(tblViewPlaylist.getSelectionModel().getSelectedItem());

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
    private <T> void initializeGenericSearchEntries(List<T> inputList){
        txtFieldSearch.getEntries().clear();

        for (int i = 0; i < inputList.size(); i++)
        {
            txtFieldSearch.getEntries().add((inputList.get(i).toString()));
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
        DataManager.removeSong(tblViewSongs.getSelectionModel().getSelectedItem());
        Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists()));
        Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs()));
    }

    public void onSongAddToPlayList(ActionEvent actionEvent)
    {
        try {
            ResourceBundle resources = new ListResourceBundle() {
                @Override
                protected Object[][] getContents() {
                    return new Object[][] {
                            { "selectedSong", tblViewSongs.getSelectionModel().getSelectedItem()}, {"selectedPlaylist", tblViewPlaylist.getSelectionModel().getSelectedItem()}
                    };
                }
            };

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/AddToPlaylist.fxml")), resources);

            Stage stage = new Stage();

            stage.setTitle("Add Song To Playlist");
            stage.setMaxHeight(363);
            stage.setMinHeight(363);
            stage.setMaxWidth(444);
            stage.setMinWidth(444);
            stage.setScene(new Scene(root, 444, 363));
            stage.show();
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bind(tblViewSongs, new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs())));
            stage.getScene().getWindow().setOnHiding((o) -> Utility.bindPlaylist(tblViewPlaylist, new SimpleListProperty<PlaylistModel>(DataManager.getPlaylists())));


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
        Utility.bind(this.tblViewSongs, DataManager.selectedPlaylist().get().getSongs());
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
        MenuItem removeSong = new MenuItem("Remove from playlist");
        removeSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem newSong = new MenuItem("New");
        newSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem editSong = new MenuItem("Edit");
        editSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem deleteSong = new MenuItem("Delete");
        deleteSong.setStyle("-fx-text-fill: #d5d4d4;");

        addSong.setOnAction(event -> onSongAddToPlayList(event));
        removeSong.setOnAction(event -> onSongRemoveFromPlaylist(event));
        newSong.setOnAction(event -> onSongNew(event));
        editSong.setOnAction(event -> onSongEdit(event));
        deleteSong.setOnAction(event -> onSongDelete(event));

        contextMenuSongs.getItems().add(addSong);
        contextMenuSongs.getItems().add(removeSong);
        contextMenuSongs.getItems().add(newSong);
        contextMenuSongs.getItems().add(editSong);
        contextMenuSongs.getItems().add(deleteSong);
    }

    @FXML
    public void playlistContextMenu(){
        ContextMenu contextMenuPlaylist = new ContextMenu();
        contextMenuPlaylist.setStyle("-fx-background-color: #404040;");
        tblViewPlaylist.setContextMenu(contextMenuPlaylist);

        MenuItem newSong = new MenuItem("New");
        newSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem editSong = new MenuItem("Edit");
        editSong.setStyle("-fx-text-fill: #d5d4d4;");
        MenuItem deleteSong = new MenuItem("Delete");
        deleteSong.setStyle("-fx-text-fill: #d5d4d4;");

        newSong.setOnAction(event -> onPlaylistNew(event));
        editSong.setOnAction(event -> onPlaylistEdit(event));
        deleteSong.setOnAction(event -> onPlaylistDelete(event));

        contextMenuPlaylist.getItems().add(newSong);
        contextMenuPlaylist.getItems().add(editSong);
        contextMenuPlaylist.getItems().add(deleteSong);
    }

}
