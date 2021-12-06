package be;

import javafx.beans.property.*;

public class SongModel
{
    private IntegerProperty id = null;
    private StringProperty title = null;
    private StringProperty artists = null;
    private StringProperty genre = null;
    private StringProperty tag = null;
    private StringProperty album = null;
    private IntegerProperty duration = null;
    private StringProperty location = null;

    public SongModel()
    {
        tag = new SimpleStringProperty();
        id = new SimpleIntegerProperty();
        title = new SimpleStringProperty();
        artists = new SimpleStringProperty();
        genre = new SimpleStringProperty();
        album = new SimpleStringProperty();
        location = new SimpleStringProperty();
        duration = new SimpleIntegerProperty();
    }

    public SongModel(int id, String name, String artists, int duration, String tag, String location)
    {
        this();

        this.setId(id);
        this.setTag(tag);
        this.setTitle(name);
        this.setArtists(artists);
        this.setDuration(duration);
        this.setLocation(location);
    }

    public SongModel(int id, String name, String artists, String genre, String album, int duration, String tag, String location)
    {
        this(id, name, artists, duration, tag, location);

        this.setGenre(genre);
        this.setAlbum(album);
    }

    public int getId()
    {
        return id.get();
    }

    public void setId(int id)
    {
        this.id.set(id);
    }

    public String getTitle()
    {
        return title.get();
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public String getArtists()
    {
        return artists.get();
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

    public String getTag()
    {
        return tag.get();
    }

    public void setTag(String tag)
    {
        this.tag.set(tag);
    }

    public String getLocation()
    {
        return location.get();
    }

    public void setLocation(String location)
    {
        this.location.set(location);
    }

    public String getGenre()
    {
        return genre.get();
    }

    public void setGenre(String genre)
    {
        this.genre.set(genre);
    }

    public String getAlbum()
    {
        return album.get();
    }

    public void setAlbum(String album)
    {
        this.album.set(album);
    }
}
