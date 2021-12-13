package model;

import CustomComponent.AutoCompleteTextField;
import be.SongModel;
import bll.SearchManager;
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
     * Goes through every MusicModel in the inputList and returns the one
     * matching the user's search query by comparing the MusicModel toString
     * with the search query. With autocompletion, there's a high likelihood
     * of a match.
     *
     * @param inputList A list of every song and playlist.
     * @param search    The string the user searched for.
     * @return The matching MusicModel instance.
     */
    private SongModel getObjectFromText(List<SongModel> inputList, String search)
    {
        if (search != null && !search.isEmpty())
        {
            for (SongModel m : inputList)
            {
                if (m.toString().equalsIgnoreCase(search)) {
                    return m;
                }
            }
            if (search.length() >= 3)
            {
                List<SongModel> backupSearch = inputList.stream().filter(m -> m.toString().toLowerCase().contains(search.toLowerCase())).sorted().collect(Collectors.toList());
                return backupSearch.get(0);
            }
        }
        return null;
    }

    /**
     * Gets matching MusicModel instance and determines its type by using polymorphism
     * with overridden methods in the MusicModels subclasses. The MusicModel "m" is then typecast
     * to its actual class and selected and scrolled to in its list.
     * @param textField The searchbar.
     */
    public void filterEqualsSearch(TableView<SongModel> table, AutoCompleteTextField textField)
    {
        SongModel m = getObjectFromText(table.getItems().stream().toList(), textField.getText());

        if (m == null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Your search did not match any songs or playlists.");
            errorAlert.showAndWait();
        }
        else
        {
            table.getSelectionModel().select(m);
            table.scrollTo(m);
        }
    }

    public void filterArtist(TableView<SongModel> songTable, String filterParameter)
    {
        songTable.getItems().setAll(searchManager.filter("artists", filterParameter));
    }

    public void filterAlbum(TableView<SongModel> songTable, String filterParameter)
    {
        songTable.getItems().setAll(searchManager.filter("album", filterParameter));
    }

    public void filterGenre(TableView<SongModel> songTable, String filterParameter)
    {
        songTable.getItems().setAll(searchManager.filter("genre", filterParameter));
    }

    public void filterArtistAndTitle(TableView<SongModel> songTable, String filterParameter)
    {
        songTable.getItems().setAll(searchManager.filter("artists", "title",filterParameter));
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
