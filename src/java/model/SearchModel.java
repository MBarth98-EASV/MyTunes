package model;

import CustomComponent.AutoCompleteTextField;
import be.MusicModel;
import be.PlaylistModel;
import be.SongModel;
import bll.SearchManager;
import com.sun.source.tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.util.ArrayList;
import java.util.List;

public class SearchModel {

    SearchManager searchManager;
    public SearchModel() {
        searchManager = new SearchManager();
    }

    /** Goes through every MusicModel in the inputList and returns the one
     * matching the user's search query by comparing the MusicModel toString
     * with the search query. With autocompletion, there's a high likelihood
     * of a match.
     * @param inputList A list of every song and playlist.
     * @param search The string the user searched for.
     * @return The matching MusicModel instance.
     */
    private MusicModel getObjectFromText(List<MusicModel> inputList, String search) {
        for (MusicModel m : inputList) {
            if (m.toString().equals(search)) {
                return m;
            }
        }

        return null;
    }

    private TreeItem getTreeItem(ObservableList<TreeItem<PlaylistModel>> inputList, PlaylistModel inputPlaylist){
        for (TreeItem<PlaylistModel> node : inputList){
            if (node.getValue().getName().equals(inputPlaylist.getName())
                && node.getValue().getOrderID() == inputPlaylist.getOrderID()){
                return node;
            }
        }
        return null;
    }

    /**
     * Gets matching MusicModel instance and determines its type by using polymorphism
     * with overridden methods in the MusicModels subclasses. The MusicModel "m" is then typecast
     * to its actual class and selected and scrolled to in its list.
     * @param alldata A list of every song and playlist available as a MusicModel instance.
     * @param songTable The table of songs in the GUI.
     * @param treeTableView The table of Playlists.
     * @param textField The searchbar.
     */
    public void filterEqualsSearch(List<MusicModel> alldata, TableView songTable, TreeTableView treeTableView, AutoCompleteTextField textField){
        MusicModel m = getObjectFromText(alldata, textField.getText());
        if (m == null){
            
        }
        if (m.getType().equals("[SONG]")){
            songTable.getSelectionModel().select((SongModel) m);
            songTable.scrollTo((SongModel) m);
        }
        if (m.getType().equals("[PLAYLIST]")){
            TreeItem treeItem = getTreeItem(treeTableView.getRoot().getChildren(), (PlaylistModel) m);
            treeTableView.getSelectionModel().select(treeItem);
            int index = treeItem.getParent().getChildren().indexOf(treeItem);
            treeTableView.scrollTo(index);
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
