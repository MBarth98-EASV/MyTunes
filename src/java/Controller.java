import CustomComponent.AutoCompleteTextField;
import be.MusicModel;
import be.MyTunesFXMLProperties;
import be.SongModel;
import bll.MusicManager;
import com.sun.source.tree.Tree;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
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
import java.net.URISyntaxException;
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



    /**
     *  isPlaying is now a property because we can attach an event handler when the value changes.
     */
    private final BooleanProperty isPlaying = new SimpleBooleanProperty();

    MusicManager songPlayer = new MusicManager();

    public Controller()
    {
        isPlaying.addListener((observable, oldValue, newValue) -> playPauseUpdateStyle(newValue));
        txtFieldSearch = new AutoCompleteTextField();


        tblViewSongs.getColumns().add(this.tblClmnSongTitle);
        tblViewSongs.getColumns().add(this.tblClmnSongArtist);
        tblViewSongs.getColumns().add(this.tblClmnSongGenre);
        tblViewSongs.getColumns().add(this.tblClmnSongAlbum);
        tblViewSongs.getColumns().add(this.tblClmnSongTime);

       //this.tblViewSongs.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
       //    try {
       //        songPlayer.setMedia(newValue.getSelectedItem().getLocation());
       //    } catch (URISyntaxException e) {
       //        e.printStackTrace();
       //    }
       //});
    }

    public void onSongSelectionChanged(ObservableValue obs, Number old, Number _new)
    {
        if (!old.equals(_new))
        {
            try
            {
                this.songPlayer.setMedia(this.songPlayer.data.get(_new.intValue()).getLocation());
                System.out.println(this.songPlayer.data.get(_new.intValue()).getLocation());
            }
            catch (Exception ex)
            {
                //
            }
        }
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
        this.tblClmnSongTitle.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        this.tblClmnSongArtist.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artists"));
        this.tblClmnSongGenre.setCellValueFactory(new PropertyValueFactory<SongModel, String>("genre"));
        this.tblClmnSongAlbum.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));
        this.tblClmnSongTime.setCellValueFactory(new PropertyValueFactory<SongModel, String>("duration"));

        songPlayer.data.addAll();
        tblViewSongs.setItems(songPlayer.data);

        this.tblViewSongs.getFocusModel().focusedIndexProperty().addListener(this::onSongSelectionChanged);

        initializeSearchEntries(songPlayer.data);

        sliderVolume.setMin(0);
        sliderVolume.setMax(1);
        sliderVolume.setValue(1);

        sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
            songPlayer.setVolume(sliderVolume.getValue());
        });
    }


    @FXML private void onPlayTrack(ActionEvent actionEvent) throws URISyntaxException
    {
        if (songPlayer.isPlaying.getValue() == false) {
           if (!(tblViewSongs.getSelectionModel().getSelectedItem() == null))
           {
               songPlayer.setMedia(tblViewSongs.getSelectionModel().getSelectedItem().getLocation());
               songPlayer.isPlaying.setValue(!songPlayer.isPlaying.getValue());
               songPlayer.playTrack();
           } else {
               tblViewSongs.getSelectionModel().select(0);
               songPlayer.setMedia(tblViewSongs.getSelectionModel().getSelectedItem().getLocation());
               songPlayer.playTrack();
           }
            songPlayer.playTrack();
        }
        else
        {
            songPlayer.pauseTrack();
        }
    }

    @FXML private void onNextTrack(ActionEvent actionEvent) throws URISyntaxException
    {
        songPlayer.stopTrack();

        tblViewSongs.getSelectionModel().selectNext();
        songPlayer.setMedia(tblViewSongs.getSelectionModel().getSelectedItem().getLocation());
        if(songPlayer.isPlaying.getValue() == true) {
            songPlayer.playTrack();
        }
    }

    @FXML private void onPreviousTrack(ActionEvent actionEvent) throws URISyntaxException
    {
        if (songPlayer.getDuration() > 5)
        {
            songPlayer.stopTrack();
            songPlayer.playTrack();

        } else {
            songPlayer.stopTrack();

            tblViewSongs.getSelectionModel().selectPrevious();
            songPlayer.setMedia(tblViewSongs.getSelectionModel().getSelectedItem().getLocation());
            songPlayer.setMedia(tblViewSongs.getSelectionModel().getSelectedItem().getLocation());
            if(songPlayer.isPlaying.getValue() == true) {
                songPlayer.playTrack();
            }
        }

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
    private void onPlaylistUp(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void onPlaylistDown(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void setVolume(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void onSearch(ActionEvent event)
    {
        txtFieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    SearchModel s = new SearchModel();
                    MusicModel m = s.getObjectFromText(songPlayer.data, txtFieldSearch.getText());
                    //if (m){

                    //}
                }
            }
        });

        }


    @FXML
    private void onPlaylistNew(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void onPlaylistEdit(ActionEvent event)
    {
        throw new NotImplementedException();
    }

    @FXML
    private void onPlaylistDelete(ActionEvent event)
    {
        throw new NotImplementedException();
    }


    private void initializeSearchEntries(List<SongModel> inputList){
        for (int i = 0; i < inputList.size(); i++){
            txtFieldSearch.getEntries().add((inputList.get(i)).toString());

        }
    }

    public void onShuffleToggled(ActionEvent actionEvent) {
    }
}
