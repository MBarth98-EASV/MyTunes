package be;

import javafx.beans.property.*;

public class SongModel
{
    private StringProperty tag = null;
    private IntegerProperty id = null;
    private StringProperty name = null;
    private StringProperty location = null;
    private IntegerProperty duration = null;
    private SimpleStringProperty artists = null;

    public SongModel()
    {
        tag = new SimpleStringProperty();
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        artists = new SimpleStringProperty();
        location = new SimpleStringProperty();
        duration = new SimpleIntegerProperty();
    }

    public SongModel(int id, String name, String artists, int duration, String tag, String location)
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

    public String getArtists()
    {
        return artists.get();
    }


    public SimpleStringProperty artistsProperty()
    {
        return artists;
    }


    public void setArtists(String artists)
    {
        this.artists.set(artists);
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

    @Override
    public String toString(){
        return "("+id+") " + artists + " - " + name + " - " + duration;
    }
}
