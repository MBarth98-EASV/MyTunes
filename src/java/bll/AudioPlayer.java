package bll;

import be.IAudio;
import be.SongModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URI;


public class AudioPlayer implements IAudio
{
    MediaPlayer musicPlayer = null;

    BooleanProperty isPlaying = new SimpleBooleanProperty();

    public AudioPlayer()
    {
        isPlaying.setValue(false);
    }

    @Override
    public void load(SongModel song)
    {
        this.load(song.getLocation());
    }

    @Override
    public void load(String path)
    {
        if (musicPlayer != null)
        {
            musicPlayer.stop();
        }

        // path needs to be url encoded, hence the %20, and characters can't be escaped which is why backslashes are replaced with forward-slashes
        this.musicPlayer = new MediaPlayer(new Media("file:/" + path.replace("\\", "/").replace(" ", "%20")));
    }

    @Override
    public void setVolume(double val)
    {
        this.musicPlayer.setVolume(val);
    }

    @Override
    public Duration getDuration()
    {
        return musicPlayer.getTotalDuration();
    }

    @Override
    public Duration getTime()
    {
        return musicPlayer.getCurrentTime();
    }

    @Override
    public void play()
    {
        if (musicPlayer != null && !isPlaying.get())
        {
            musicPlayer.play();
            isPlaying.setValue(true);
        }
    }

    @Override
    public void pause()
    {
        isPlaying.setValue(false);
        musicPlayer.pause();
    }

    @Override
    public void reset()
    {
        this.pause();
        isPlaying.setValue(false);
        this.musicPlayer.seek(Duration.ZERO);
        this.play();
    }

    @Override
    public void skipTo(int ms)
    {
        this.pause();
        this.musicPlayer.seek(Duration.millis(ms));
        this.play();
    }

}
