package be;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class PlaylistModel
{
    /**
     *  used to determine where the entity is displayed.
     */
    private final IntegerProperty orderID;

    /**
     *   songs assigned to this playlist.
     */
    private final ListProperty<SongModel> songs;

    /**
     *  currently selected entry in the playlist songs.
     */
    private final IntegerProperty selectedSongIndex;

    /**
     *  is this playlist the active one.
     */
    private final BooleanProperty isActive;

    /**
     *  human-readable identifier of this playlist.
     */
    private final StringProperty name;



    /**
     *  initialize the objects to valid references
     */
    public PlaylistModel()
    {
        this.orderID = new SimpleIntegerProperty();
        this.songs = new SimpleListProperty<>();
        this.selectedSongIndex = new SimpleIntegerProperty();
        this.isActive = new SimpleBooleanProperty();
        this.name = new SimpleStringProperty();
    }

    /**
     *  set all properties at construction time
     */
    public PlaylistModel(int id, List<SongModel> songs, int selectedSong, boolean isActive, String name)
    {
        this();

        this.setOrderID(id);
        this.setSongs(songs);
        this.setSelectedSongIndex(selectedSong);
        this.setIsActive(isActive);
        this.setName(name);
    }


    public int getOrderID()
    {
        return orderID.get();
    }

    public void setOrderID(int orderID)
    {
        this.orderID.set(orderID);
    }


    public ObservableList<SongModel> getSongs()
    {
        return songs.get();
    }

    public void setSongs(List<SongModel> songs)
    {
        this.songs.getValue().setAll(songs);
    }


    public int getSelectedSongIndex()
    {
        return selectedSongIndex.get();
    }

    public void setSelectedSongIndex(int selectedSongIndex)
    {
        this.selectedSongIndex.set(selectedSongIndex);
    }


    public boolean getIsActive()
    {
        return isActive.get();
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive.set(isActive);
    }


    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }
}
