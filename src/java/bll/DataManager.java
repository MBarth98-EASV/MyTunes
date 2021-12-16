package bll;

import be.PlaylistModel;
import be.SongModel;
import dal.LocalFilesDAO;
import dal.db.EASVDatabase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class DataManager {
    private static final ObservableList<PlaylistModel> playlists = FXCollections.emptyObservableList();;

    private static final ObjectProperty<PlaylistModel> selectedPlaylist = new SimpleObjectProperty<>();;
    private static final ObjectProperty<SongModel> selectedSong = new SimpleObjectProperty<>();;

    private static EASVDatabase database = new EASVDatabase();;
    private static LocalFilesDAO localfiles = new LocalFilesDAO();



    public static ObjectProperty<SongModel> selectedSong()
    {
        return selectedSong;
    }

    public static ObjectProperty<PlaylistModel> selectedPlaylist()
    {
        return selectedPlaylist;
    }

    public static void addSong(SongModel song)
    {
        database.addSong(song);
        selectedPlaylist.get().getSongs().add(song);
    }

    public static ObservableList<SongModel> getAllSongs()
    {
        return database.getAllSongs();
    }

    public static ObservableList<PlaylistModel> getPlaylists(){
        return database.getAllPlaylists();
    }

    public static ListProperty<SongModel> filterEqualsParameter(String filterType, String filterType2, String filterParameter)
    {
        return database.filterEqualsParameter(filterType, filterType2, filterParameter);
    }

    public static List<String> getUniqueValuesIn(String columnName)
    {
        return database.getUniqueValuesIn(columnName);
    }

    public static void addSong(Path path) {
        SongModel song = localfiles.loadSongModels(Collections.singletonList(path)).get(0);
        addSong(song);
    }

    public static void editSong(SongModel currentlySelected) {
        database.updateSong(currentlySelected);
    }

    public static void removeSong(SongModel song)
    {
        database.removeSong(song.getTitle());
        playlists.get(0).getSongs().remove(song);
    }

    public static void addPlaylist(String name) {
        database.addPlaylist(name);
    }

    public static void editPlaylist(PlaylistModel currentlySelected) {
        database.updatePlaylist(currentlySelected);
    }

    public static void removePlaylist(PlaylistModel selectedItem) {
        database.removePlaylist(selectedItem);
    }

    public static void songRemoveFromPlaylist(PlaylistModel selectedPlaylist, SongModel selectedSong) {
        database.removeSongFromPlaylist(selectedPlaylist,selectedSong);
    }

    public static void songAddToPlaylist(PlaylistModel selectedPlaylist, SongModel selectedSong) {
        database.addSongToPlaylist(selectedPlaylist, selectedSong);
    }
}
