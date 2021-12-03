package be;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class PlaylistModel
{
    /**
     *  used to determine where the entity is displayed.
     */
    private IntegerProperty orderID;

    /**
     *   songs assigned to this playlist.
     */
    private ListProperty<SongModel> songs;

    /**
     *  currently selected entry in the playlist songs.
     */
    private IntegerProperty selectedSongIndex;

    /**
     *  is this playlist the active one.
     */
    private BooleanProperty isActive;

    /**
     *  this human-readable identifier of this playlist.
     */
    private StringProperty name;


    public PlaylistModel(int id, ObservableList<SongModel> songs, int selectedSong, boolean isActive, String name)
    {
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

    public IntegerProperty orderIDProperty()
    {
        return orderID;
    }

    public void setOrderID(int orderID)
    {
        this.orderID.set(orderID);
    }

    public ObservableList<SongModel> getSongs()
    {
        return songs.get();
    }

    public ListProperty<SongModel> songsProperty()
    {
        return songs;
    }

    public void setSongs(ObservableList<SongModel> songs)
    {
        this.songs.set(songs);
    }

    public int getSelectedSongIndex()
    {
        return selectedSongIndex.get();
    }

    public IntegerProperty selectedSongIndexProperty()
    {
        return selectedSongIndex;
    }

    public void setSelectedSongIndex(int selectedSongIndex)
    {
        this.selectedSongIndex.set(selectedSongIndex);
    }

    public boolean isIsActive()
    {
        return isActive.get();
    }

    public BooleanProperty isActiveProperty()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive.set(isActive);
    }

    public String getName()
    {
        return name.get();
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }
}
