package be;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class SongModel
{
    private StringProperty tag = null;
    private IntegerProperty id = null;
    private StringProperty name = null;
    private StringProperty location = null;
    private IntegerProperty duration = null;
    private ListProperty<String> artists = null;

    public SongModel()
    {
        tag = new SimpleStringProperty();
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        artists = new SimpleListProperty<>();
        location = new SimpleStringProperty();
        duration = new SimpleIntegerProperty();
    }

    public SongModel(int id, String name, ObservableList<String> artists, int duration, String tag, String location)
    {
        this();

        this.setId(id);
        this.setTag(tag);
        this.setName(name);
        this.setArtists(artists);
        this.setDuration(duration);
        this.setLocation(location);
    }

    public int getId()
    {
        return id.get();
    }

    public void setId(int id)
    {
        this.id.set(id);
    }

    public IntegerProperty idProperty()
    {
        return id;
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public StringProperty nameProperty()
    {
        return name;
    }

    public ObservableList<String> getArtists()
    {
        return artists.get();
    }

    public void setArtists(ObservableList<String> artists)
    {
        this.artists.set(artists);
    }

    public ListProperty<String> artistsProperty()
    {
        return artists;
    }

    public int getDuration()
    {
        return duration.get();
    }

    public void setDuration(int duration)
    {
        this.duration.set(duration);
    }

    public IntegerProperty durationProperty()
    {
        return duration;
    }

    public String getTag()
    {
        return tag.get();
    }

    public void setTag(String tag)
    {
        this.tag.set(tag);
    }

    public StringProperty tagProperty()
    {
        return tag;
    }

    public String getLocation()
    {
        return location.get();
    }

    public void setLocation(String location)
    {
        this.location.set(location);
    }

    public StringProperty locationProperty()
    {
        return location;
    }
}
