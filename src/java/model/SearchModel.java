package model;

import CustomComponent.AutoCompleteTextField;
import be.SongModel;
import bll.SearchManager;
import dal.Utility;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

public class SearchModel {

    SearchManager searchManager;

    public SearchModel() {
        searchManager = new SearchManager();
    }

    /**
     * Goes through every SongModel in the input list and compares the
     * search string to every SongModels toString method. If the search string doesn't match
     * exactly, it will return the closest song it can find, so long as the search
     * string is longer than 3 characters.
     *
     * @param inputList A list of every song and playlist.
     * @param search    The string the user searched for.
     * @return The matching songmodel instance.
     */
    private SongModel getObjectFromText(List<SongModel> inputList, String search)
    {
        if (search != null && !search.isEmpty())
        {
            for (SongModel song : inputList)
            {
                if (song.toString().equalsIgnoreCase(search)) {
                    return song;
                }
            }
            if (search.length() >= 3)
            {
                List<SongModel> backupSearch = inputList.stream().filter(m -> m.toString()
                        .toLowerCase().contains(search.toLowerCase())).sorted().collect(Collectors.toList());
                return backupSearch.get(0);
            }
        }
        return null;
    }

    /**
     * Gets matching songmodel and selects it in the table upon search.
     * @param textField The searchbar.
     */
    public void Search(TableView<SongModel> table, AutoCompleteTextField textField)
    {
        SongModel song = getObjectFromText(table.getItems().stream().toList(), textField.getText());

        if (song == null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any songs or playlists.");
            errorAlert.showAndWait();
        }
        else
        {
            table.getSelectionModel().select(song);
            table.scrollTo(song);
        }
    }

    public void filterArtist(TableView<SongModel> songTable, String filterParameter)
    {
        Utility.bind(songTable, searchManager.filter("artists", filterParameter));
        //songTable.getItems().setAll(searchManager.filter("artists", filterParameter));
    }

    public void filterAlbum(TableView<SongModel> songTable, String filterParameter)
    {
        Utility.bind(songTable, searchManager.filter("album", filterParameter));
        //songTable.getItems().setAll(searchManager.filter("album", filterParameter));
    }

    public void filterGenre(TableView<SongModel> songTable, String filterParameter)
    {
        Utility.bind(songTable, searchManager.filter("genre", filterParameter));

       //songTable.getItems().setAll(searchManager.filter("genre", filterParameter));
    }

    public void filterArtistAndTitle(TableView<SongModel> songTable, String filterParameter)
    {
        Utility.bind(songTable, searchManager.filter("artists", "title", filterParameter));

       //songTable.getItems().setAll(searchManager.filter("artists", "title",filterParameter));
    }

    public List<String> allAvailableArtist()
    {
        return searchManager.getAllByParameter("artists");
    }

    public List<String> allAvailableAlbums()
    {
        return searchManager.getAllByParameter("album");
    }

    public List<String> allAvailableGenre()
    {
        return searchManager.getAllByParameter("genre");
    }

    public List<String> allAvailableTitleArtist()
    {
        return searchManager.getAllByParameter("artists", "title");
    }

    public List<SongModel> allAvailable()
    {
        return searchManager.getAll();
    }
}
