import CustomComponent.AutoCompleteTextField;
import be.MusicModel;
import be.MyTunesFXMLProperties;
import be.PlaylistModel;
import be.SongModel;
import com.google.gson.Gson;
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



    /**
     *  isPlaying is now a property because we can attach an event handler when the value changes.
     */
    private final BooleanProperty isPlaying = new SimpleBooleanProperty();

    final ArrayList<MusicModel> dataArray = new ArrayList();
    final ObservableList<SongModel> data = FXCollections.observableArrayList();
    final ObservableList<TreeItem<PlaylistModel>> playdata = FXCollections.observableArrayList();


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
    {
        data.add(new SongModel(1, "1st song", "Phillip", "soft pop", "rainbow and unicorns", 190, "local", "Magic location 2"));
        data.add(new SongModel(2, "2st song", "Rasmus", 50, "local", "Magic location 2"));
        data.add(new SongModel(3, "3st song", "Mads", null, null, 345, "local", "Magic location"));
        data.add(new SongModel(4, "1st", "Youtube", "Garbage", "Google", 190, "local", "The Cloud"));
        data.add(new SongModel(5, "Some song", "Rasmus", 50, "local", "Magic location 2"));
        data.add(new SongModel(6, "Yeezy what's good?", "Kanye", null, null, 345, "local", "Magic location"));
        data.add(new SongModel(7, "I Love Kanye", "Kanye West", "Hip-Hop", "The Life of Pablo", 60, "local", "Magic location 2"));
        data.add(new SongModel(8, "Kill Ed Sheeran", "Rasmus", 50, "local", "Magic location 2"));
        data.add(new SongModel(9, "Java is shit", "Mads", null, null, 345, "local", "Magic location"));
        data.add(new SongModel(10, "DNA.", "Kendrick", "Hip-Hop", "DAMN.", 190, "local", "Magic location 2"));
        data.add(new SongModel(11, "Superman", "Soulja Boy", 50, "local", "Magic location 2"));
        data.add(new SongModel(12, "Another Song", "Mads", null, null, 345, "local", "Magic location"));

        this.tblClmnSongTitle.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        this.tblClmnSongArtist.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artists"));
        this.tblClmnSongGenre.setCellValueFactory(new PropertyValueFactory<SongModel, String>("genre"));
        this.tblClmnSongAlbum.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));
        this.tblClmnSongTime.setCellValueFactory(new PropertyValueFactory<SongModel, String>("duration"));

        playdata.add(new TreeItem<PlaylistModel>(new PlaylistModel(12, new ArrayList<>(), 1, false, "Playlist 3")));
        playdata.add(new TreeItem<PlaylistModel>(new PlaylistModel(12, new ArrayList<>(), 1, false, "Playlist 2")));
        playdata.add(new TreeItem<PlaylistModel>(new PlaylistModel(12, new ArrayList<>(), 1, false, "Playlist 1")));
        //this.tvColumnPlaylist.setCellValueFactory(new PropertyValueFactory<PlaylistModel, List<SongModel>>("songs"));
        treeView = new TreeTableView<PlaylistModel>();
        TreeItem item = new TreeItem<PlaylistModel>(new PlaylistModel());
        item.getChildren().addAll(playdata);
        treeView.setRoot(item);


        data.addAll();
        tblViewSongs.setItems(data);
        //data.addAll(new EASVDatabase().getAllSongs());
        dataArray.addAll(data.stream().toList());

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
        throw new NotImplementedException();
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

                    String selectedItem = cmboBoxFilter.getSelectionModel().getSelectedItem().toString();
                    switch (selectedItem){
                        case "Artist | Filter": {
                            searchModel.filterEqualsArtist();
                            break;
                        }
                        case "Album | Filter": {
                            searchModel.filterEqualsAlbum();
                            break;
                        }
                        case "Genre | Filter": {
                            searchModel.filterEqualsGenre();
                            break;
                        }
                        case "Artist/Title | Filter": {
                            searchModel.filterEqualsArtistTitle();
                            break;
                        }
                        case "Search": {
                            searchModel.filterEqualsSearch(dataArray,tblViewSongs,treeView,txtFieldSearch);;
                            break;
                        }
                        default: {
                            searchModel.filterEqualsSearch(dataArray,tblViewSongs,treeView,txtFieldSearch);
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


    private void initializeMMSearchEntries(List<MusicModel> inputList){
        for (int i = 0; i < inputList.size(); i++){
            txtFieldSearch.getEntries().add((inputList.get(i)).toString());

        }
    }

    private void initializeStringSearchEntires(List<String> inputList){
        inputList.add("test");
        for (int i = 0; i < inputList.size(); i++){
            txtFieldSearch.getEntries().add((inputList.get(i)));

        }
    }

    public void onShuffleToggled(ActionEvent event) {
    }

    public void onSongRemoveFromPlaylist(ActionEvent actionEvent) {
    }

    public void onSongAddToPlayList(ActionEvent actionEvent) {
    }

    /**
     * Temporary filler for combobox Filter
     */
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


    public void onClearSearchFilter(ActionEvent event) {
        cmboBoxFilter.getSelectionModel().select(0);
        txtFieldSearch.clear();
    }


    public void onComboBoxSelect(ActionEvent event) {
        int selectedItem = cmboBoxFilter.getSelectionModel().getSelectedIndex();
        //TODO: Switch to ENUM
        switch (selectedItem){
            case 1: {
                txtFieldSearch.setPromptText("Enter artist to filter");
                initializeStringSearchEntires(searchModel.allAvailableArtist());
                System.out.println("artist");
                break;
            }
            case 2: {
                initializeStringSearchEntires(searchModel.allAvailableAlbums());
                txtFieldSearch.setPromptText("Enter album to filter");
                System.out.println("album");
                break;
            }
            case 3: {
                initializeStringSearchEntires(searchModel.allAvailableGenre());
                txtFieldSearch.setPromptText("Enter genre to filter");
                System.out.println("genre");
                break;
            }
            case 4: {
                initializeStringSearchEntires(searchModel.allAvailableTitleArtist());
                txtFieldSearch.setPromptText("Enter artist or title to filter");
                System.out.println("artititle");
                break;
            }
            default: {
                initializeMMSearchEntries(dataArray);
                txtFieldSearch.setPromptText("Press enter to search");
                System.out.println("search");
                break;
            }
        }
    }
}
