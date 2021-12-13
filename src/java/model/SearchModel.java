package model;

import CustomComponent.AutoCompleteTextField;
import be.MusicModel;
import be.PlaylistModel;
import be.SongModel;
import bll.SearchManager;
import com.sun.source.tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchModel {

    SearchManager searchManager;

    public SearchModel() {
        searchManager = new SearchManager();
    }

    /**
     * Goes through every MusicModel in the inputList and returns the one
     * matching the user's search query by comparing the MusicModel toString
     * with the search query. With autocompletion, there's a high likelihood
     * of a match.
     *
     * @param inputList A list of every song and playlist.
     * @param search    The string the user searched for.
     * @return The matching MusicModel instance.
     */
    private MusicModel getObjectFromText(List<MusicModel> inputList, String search) {
        if (!search.isEmpty() && !search.equals(null)) {
            for (MusicModel m : inputList) {
                if (m.toString().equalsIgnoreCase(search)) {
                    return m;
                }
            } if (search.length() >= 3) {
                List<MusicModel> backupSearch = inputList.stream().filter
                        (m -> m.toString().toLowerCase().contains(search.toLowerCase())).sorted().collect(Collectors.toList());
                return backupSearch.get(0);
            } }
        return null;
    }

    /**
     * Gets matching MusicModel instance and determines its type by using polymorphism
     * with overridden methods in the MusicModels subclasses. The MusicModel "m" is then typecast
     * to its actual class and selected and scrolled to in its list.
     * @param alldata A list of every song and playlist available as a MusicModel instance.
     * @param songTable The table of songs in the GUI.
     * @param playlistTable The table of Playlists.
     * @param textField The searchbar.
     */
    public void filterEqualsSearch(List<MusicModel> alldata, TableView songTable, TableView playlistTable, AutoCompleteTextField textField){
        MusicModel m = getObjectFromText(alldata, textField.getText());
        if (m == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any songs or playlists.");
            errorAlert.showAndWait();
            return;
        }
        if (m.getType().equals("[SONG]")){
            songTable.getSelectionModel().select((SongModel) m);
            songTable.scrollTo((SongModel) m);
        }
        if (m.getType().equals("[PLAYLIST]")){
            playlistTable.getSelectionModel().select((PlaylistModel) m);
            playlistTable.scrollTo((PlaylistModel) m);
        }
    }

    public void filterEqualsArtist(TableView songTable, String filterParameter){
        ObservableList<SongModel> data = FXCollections.observableArrayList(searchManager.filterEqualsArtist(filterParameter));
        songTable.setItems(data);
    }

    public void filterEqualsAlbum(TableView songTable, String filterParameter){
        ObservableList<SongModel> data = FXCollections.observableArrayList(searchManager.filterEqualsAlbum(filterParameter));
        songTable.setItems(data);
    }


    public void filterEqualsGenre(TableView songTable, String filterParameter){
        ObservableList<SongModel> data = FXCollections.observableArrayList(searchManager.filterEqualsGenre(filterParameter));
        songTable.setItems(data);
    }


    public void filterEqualsArtistTitle(TableView songTable, String filterParameter){
        ObservableList<SongModel> data = FXCollections.observableArrayList(searchManager.filterEqualsArtistTitle(filterParameter));
        songTable.setItems(data);
    }

    public List<String> allAvailableArtist(){
        return searchManager.allAvailableArtist();
    }

    public List<String> allAvailableAlbums(){
        return searchManager.allAvailableAlbums();
    }

    public List<String> allAvailableGenre(){
        return searchManager.allAvailableGenre();
    }

    public List<String> allAvailableTitleArtist(){
        return searchManager.allAvailableTitleArtist();
    }

    public List<SongModel> allAvailable(){
        return searchManager.filterEqualsSearch();
    }




}
