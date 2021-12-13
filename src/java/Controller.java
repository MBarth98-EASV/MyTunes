import CustomComponent.AutoCompleteTextField;
import CustomComponent.ComboBoxEnum;
import be.MusicModel;
import be.MyTunesFXMLProperties;
import be.PlaylistModel;
import be.SongModel;
import com.google.gson.Gson;
import dal.db.EASVDatabase;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.LocalFilesModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.sql.SQLException;
import CustomComponent.ComboBoxEnum.*;

import static CustomComponent.ComboBoxEnum.*;

public class Controller extends MyTunesFXMLProperties implements Initializable
{
    /**
     *  isPlaying is now a property because we can attach an event handler when the value changes.
     */
    private final BooleanProperty isPlaying = new SimpleBooleanProperty();

    final ArrayList<MusicModel> dataArray = new ArrayList();
    final ObservableList<SongModel> data = FXCollections.observableArrayList();
    final ObservableList<PlaylistModel> playlistData = FXCollections.observableArrayList();


    SearchModel searchModel;
    MusicPlayer songPlayer = new MusicPlayer();

    public Controller()
    {
        isPlaying.addListener((observable, oldValue, newValue) -> playPauseUpdateStyle(newValue));
        txtFieldSearch = new AutoCompleteTextField();
        searchModel = new SearchModel();

        tblViewSongs.getColumns().add(this.tblClmnSongTitle);
        tblViewSongs.getColumns().add(this.tblClmnSongArtist);
        tblViewSongs.getColumns().add(this.tblClmnSongGenre);
        tblViewSongs.getColumns().add(this.tblClmnSongAlbum);
        tblViewSongs.getColumns().add(this.tblClmnSongTime);
    }

    private void playPauseUpdateStyle(boolean state)
    {
        String playStyle = "-fx-background-image: url(/images/pause.png); -fx-background-position: 8;";
        String pauseStyle = "-fx-background-image: url(/images/play.png); -fx-background-position: 9;";

        btnPlayPause.setStyle(state ? playStyle : pauseStyle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {   songPlayer = new MusicPlayer();

        this.tblClmnSongTitle.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        this.tblClmnSongArtist.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artists"));
        this.tblClmnSongGenre.setCellValueFactory(new PropertyValueFactory<SongModel, String>("genre"));
        this.tblClmnSongAlbum.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));
        this.tblClmnSongTime.setCellValueFactory(new PropertyValueFactory<SongModel, String>("duration"));

        this.tblClmnPlaylistName.setCellValueFactory(new PropertyValueFactory<PlaylistModel, String>("name"));
        this.tblClmnPlaylistSongCount.setCellValueFactory(new PropertyValueFactory<PlaylistModel, String>("songCount"));
        this.tblClmnPlaylistDuration.setCellValueFactory(new PropertyValueFactory<PlaylistModel, String>("duration"));




        data.addAll();
        tblViewSongs.setItems(data);
        data.addAll(new EASVDatabase().getAllSongs());
        playlistData.addAll(new EASVDatabase().getAllPlaylists());
        tblViewPlaylist.setItems(playlistData);
        dataArray.addAll(data.stream().toList());
        dataArray.addAll(playlistData.stream().toList());

        initializeMMSearchEntries(dataArray);
        setComboBox();

    }


    @FXML private void onPlayTrack(ActionEvent actionEvent)
    {
        isPlaying.setValue(!isPlaying.getValue());
    }

    @FXML private void onNextTrack(ActionEvent actionEvent)
    {
        System.out.println("next song");
    }

    @FXML private void onPreviousTrack(ActionEvent actionEvent)
    {
        System.out.println("previous/reset song");
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/NewSong.fxml")));
            Stage stage = new Stage();
            stage.setTitle("New Song");
            stage.setMaxHeight(193);
            stage.setMinHeight(193);
            stage.setMaxWidth(320);
            stage.setMinWidth(320);
            stage.setScene(new Scene(root, 320, 193));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSongEdit(ActionEvent event){
        LocalFilesModel.setCurrentlySelectedSong((SongModel) tblViewSongs.getSelectionModel().getSelectedItem());
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/EditSong.fxml")));
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
    private void setVolume(ActionEvent event)
    {
        songPlayer.setVolume(sliderVolume.getValue());
    }

    @FXML
    private void onPlaylistUp(ActionEvent event)
    {
        throw new NotImplementedException();
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
        txtFieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();

                    switch (ComboBoxEnum.values()[selectedItem]){
                        case ARTIST: {
                            searchModel.filterEqualsArtist(tblViewSongs, txtFieldSearch.getText());
                            break;
                        }
                        case ALBUM: {
                            searchModel.filterEqualsAlbum(tblViewSongs, txtFieldSearch.getText());
                            break;
                        }
                        case GENRE: {
                            searchModel.filterEqualsGenre(tblViewSongs, txtFieldSearch.getText());
                            break;
                        }
                        case ARTISTTITLE: {
                            searchModel.filterEqualsArtistTitle(tblViewSongs, txtFieldSearch.getText());
                            break;
                        }
                        default: {
                            searchModel.filterEqualsSearch(dataArray,tblViewSongs,tblViewPlaylist,txtFieldSearch);
                            break;
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
     * @param inputList
     */
    private void initializeMMSearchEntries(List<MusicModel> inputList){
        txtFieldSearch.getEntries().clear();
        for (int i = 0; i < inputList.size(); i++){
            txtFieldSearch.getEntries().add((inputList.get(i)).toString());

        }
    }

    /**
     * Sets the searchbar's search entires for autocompletion to the input list of Strings.
     * Used for filter autocompletion - it's filled with a list of every unique value of the chosen parameter
     * in the database.
     * @param inputList
     */
    private void initializeStringSearchEntries(List<String> inputList){
        txtFieldSearch.getEntries().clear();
        for (int i = 0; i < inputList.size(); i++){
            txtFieldSearch.getEntries().add((inputList.get(i)));

        }
    }

    public void onShuffleToggled(ActionEvent event) {
    }

    public void onSongRemoveFromPlaylist(ActionEvent actionEvent) {
    }

    public void onSongAddToPlayList(ActionEvent actionEvent) {
        SongModel addSong = tblViewSongs.getSelectionModel().getSelectedItem();
        //tblViewPlaylist.getSelectionModel().getSelectedItem().
    }


    private void setComboBox(){
        ObservableList<String> comboBoxList = FXCollections.observableArrayList();
        comboBoxList.add("Search");
        comboBoxList.add("Artist | Filter");
        comboBoxList.add("Album | Filter");
        comboBoxList.add("Genre | Filter");
        comboBoxList.add("Artist/Title | Filter");
        cmboBoxFilter.setItems(comboBoxList);
        cmboBoxFilter.getSelectionModel().select(comboBoxList.get(0));
    }

    /**
     * Clears the textfield and sets the filter combobox to search (it's default value).
     * Effectively removes any filter the user has made.
     * @param event
     */
    public void onClearSearchFilter(ActionEvent event) {
        cmboBoxFilter.getSelectionModel().select(0);
        txtFieldSearch.clear();
    }

    /**
     * Sets the searchbar's prompt text and it's search entries for the autocomplete feature.
     * @param event
     */
    public void onComboBoxSelect(ActionEvent event) {
        int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();
        switch (ComboBoxEnum.values()[selectedItem]){
            case ARTIST: {
                txtFieldSearch.setPromptText("Enter artist to filter");
                initializeStringSearchEntries(searchModel.allAvailableArtist());
                break;
            }
            case ALBUM: {
                initializeStringSearchEntries(searchModel.allAvailableAlbums());
                txtFieldSearch.setPromptText("Enter album to filter");
                break;
            }
            case GENRE: {
                initializeStringSearchEntries(searchModel.allAvailableGenre());
                txtFieldSearch.setPromptText("Enter genre to filter");
                break;
            }
            case ARTISTTITLE: {
                initializeStringSearchEntries(searchModel.allAvailableTitleArtist());
                txtFieldSearch.setPromptText("Enter artist or title to filter");
                break;
            }
            default: {
                initializeMMSearchEntries(dataArray); //Uses the original input data for the table.
                tblViewSongs.setItems(data);
                txtFieldSearch.setPromptText("Press enter to search");
                break;
            }
        }
    }

    public void onPlayPauseTrack(ActionEvent event) {
    }
}
