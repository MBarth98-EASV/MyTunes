package be;

import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

/**
 *   a model of a single song instance, described by the database and visualized by fxml data binding.
 */
public class SongModel extends MusicModel
{
    private static final String TYPE = "[SONG]";


    /**
     *  the title of the song
     */
    private StringProperty title = null;

    /**
     *  the csv or single line description of the artist
     */
    private StringProperty artists = null;

    /**
     *  the genre of the song
     */
    private StringProperty genre = null;

    /**
     *  internal tag (unused?) to determine the method at which we fetch the audio
     */
    private StringProperty tag = null;

    /**
     *  set if the song is a part of an album
     */
    private StringProperty album = null;

    /**
     *  if the tag is set to local:     Standard file path
     *  if the tag is set to youtube:   The entity id seen in the url of a youtube video.
     */
    private StringProperty location = null;

    /**
     *  internal id for faster sql queries (is the primary key of the song table)
     */
    private IntegerProperty id = null;

    /**
     *  the time in seconds the song takes to complete
     */
    private IntegerProperty duration = null;


    /**
     *  initialize the objects to valid references
     * @param id
     * @param title
     * @param artist
     * @param album
     * @param duration
     * @param genre
     * @param s
     */
    public SongModel(int id, String title, String artist, String album, int duration, String genre, String s)
    {
        tag = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        artists = new SimpleStringProperty();
        this.genre = new SimpleStringProperty();
        this.album = new SimpleStringProperty();
        location = new SimpleStringProperty();
        this.duration = new SimpleIntegerProperty();
    }

    /**
     *  set all properties except the genre and album
     */
    public SongModel(int id, String name, String artists, int duration, String tag, String location)
    {
        this.setId(id);
        this.setTag(tag);
        this.setTitle(name);
        this.setArtists(artists);
        this.setDuration(duration);
        this.setLocation(location);
    }

    private void setId(int id) {
        this.id.set(id);
    }

    /**
     *  set all properties at construction time
     */
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


    private void setId(int id)
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

    public int setDuration(int duration)
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
  
      
    @Override
    public String toString()
    {
        return "("+id+") " + artists + " - " + title + " - " + duration;
    }
    

    public Map<String, SongModel> StringToObject(){
        HashMap<String, SongModel> thisMap = new HashMap<>();
        thisMap.put(toString(),this);
        return thisMap;
    }
}
