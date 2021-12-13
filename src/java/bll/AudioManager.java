package bll;

import be.PlaylistModel;
import be.SongModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

public class AudioManager
{
    private final ObservableList<PlaylistModel> playlists;

    private final ObjectProperty<PlaylistModel> selectedPlaylist = new SimpleObjectProperty<>();
    private final ObjectProperty<SongModel> selectedSong = new SimpleObjectProperty<>();

    private final AudioPlayer player;

    public ObservableList<SongModel> getAvailableSongs() {
        return this.selectedPlaylist.get().getSongs();
    }

    public AudioManager()
    {
        player = new AudioPlayer();
        playlists = FXCollections.observableArrayList();

        this.selectedSong.addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue)
            {
                this.player.load(newValue);
                this.selectedPlaylist.get().setSelectedSongIndex(selectedPlaylist.get().getSongs().indexOf(newValue));

                if (this.player.isPlaying.get())
                {
                    pause();
                    play();
                }
            }
        });
    }

    public BooleanProperty getIsPlayingProperty()
    {
        return player.isPlaying;
    }

    public ObservableList<PlaylistModel> getPlaylists()
    {
        return this.playlists;
    }

    public void populate(List<PlaylistModel> playlists)
    {
        this.playlists.setAll(playlists);

        if (!playlists.isEmpty())
        {
            this.selectedPlaylist.setValue(playlists.get(0));
        }
    }

    public void addPlaylist(PlaylistModel playlist)
    {
        this.playlists.add(playlist);
        this.selectedPlaylist.set(this.playlists.get(this.playlists.size() - 1));
        this.selectedSong.set(this.selectedPlaylist.get().getSongs().get(0));
    }

    public void setSong(SongModel song)
    {
        if (selectedPlaylist.get().getSongs().contains(song))
        {
            this.selectedSong.setValue(song);
        }


    }

    public void setVolume(double value)
    {
        this.player.setVolume(value);
    }

    public void play()
    {
        player.play();
    }

    public void pause()
    {
        player.pause();
    }

}
