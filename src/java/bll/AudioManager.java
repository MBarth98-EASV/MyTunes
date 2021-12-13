package bll;

import be.PlaylistModel;
import be.SongModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;


import java.util.List;

public class AudioManager
{
    private final ObservableList<PlaylistModel> playlists;

    private final ObjectProperty<PlaylistModel> selectedPlaylist = new SimpleObjectProperty<>();
    private final ObjectProperty<SongModel> selectedSong = new SimpleObjectProperty<>();

    private final AudioPlayer player;

    public ListProperty<SongModel> getAvailableSongs() {
        return this.selectedPlaylist.get().getSongs();
    }

    public ObjectProperty<SongModel> getCurrentSong()
    {
        return selectedSong;
    }

    public ObjectProperty<Runnable> handleEndOfMusic = new SimpleObjectProperty<>();

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
        this.player.musicPlayer.onEndOfMediaProperty().bind(handleEndOfMusic);
    }

    public void pause()
    {
        player.pause();
        this.player.musicPlayer.onEndOfMediaProperty().unbind();
    }

    public ObjectProperty<Duration> getCurrentTime()
    {
        return this.player.CurrentlyPlayedTime;
    }

    public ObjectProperty<Duration> getTotalTime()
    {
        return this.player.TotalClipTime;
    }

    public DoubleProperty getCompletionRatio()
    {
        return this.player.completionRatio;
    }


}
