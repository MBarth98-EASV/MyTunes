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

    private final AudioPlayer player;

    public ListProperty<SongModel> getAvailableSongs() {
        return DataManager.selectedPlaylist().get().getSongs();
    }

    public ObjectProperty<SongModel> getCurrentSong()
    {
        return DataManager.selectedSong();
    }

    public ObjectProperty<Runnable> handleEndOfMusic = new SimpleObjectProperty<>();

    public AudioManager()
    {
        player = new AudioPlayer();

        DataManager.selectedSong().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue)
            {
                this.player.load(newValue);
                DataManager.selectedPlaylist().get().setSelectedSongIndex(DataManager.selectedPlaylist().get().getSongs().indexOf(newValue));

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





    public void setSong(SongModel song)
    {
        if (DataManager.selectedPlaylist().get().getSongs().contains(song))
        {
            DataManager.selectedSong().setValue(song);
        }
    }

    public void setVolume(double value)
    {
        this.player.setVolume(value);
    }

    public void play()
    {
        player.play();
        if (this.player.musicPlayer != null)
        {
            this.player.musicPlayer.onEndOfMediaProperty().bind(handleEndOfMusic);
        }

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
