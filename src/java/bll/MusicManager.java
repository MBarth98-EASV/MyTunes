package bll;

import be.MusicModel;
import be.SongModel;
import dal.db.EASVDatabase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;

public class MusicManager {

        public final ArrayList<MusicModel> dataArray = new ArrayList();
        public final ObservableList<SongModel> data = FXCollections.observableArrayList();
        public final BooleanProperty isPlaying = new SimpleBooleanProperty();
        public DoubleProperty duration = new SimpleDoubleProperty(0);
        public DoubleProperty currentDuration = new SimpleDoubleProperty(0);

        int minutes = 0;
        int seconds = 0;
        int currentMinutes;
        int currentSeconds;

        MediaPlayer musicPlayer = null;

        public MusicManager()
        {
            data.addAll(new EASVDatabase().getAllSongs());
        }

    /**
     *  Makes a new MediaPlayer with a new media using the path from a song object.
     *  Waits for musicPlayer MediaPlayer to be ready before updating variables for total duration of the song.
     * @param song
     */
    public void setMedia(SongModel song)
        {
            this.musicPlayer = new MediaPlayer(new Media(Paths.get(song.getLocation()).toUri().toString()));
            musicPlayer.setOnReady(new Runnable() {
                @Override
                public void run()
                {
                   duration.setValue(musicPlayer.getTotalDuration().toSeconds());
                   minutes = (int) (musicPlayer.getTotalDuration().toSeconds() / 60) % 60;
                   seconds = (int) musicPlayer.getTotalDuration().toSeconds() % 60;

                   currentDuration.setValue(musicPlayer.getCurrentTime().toSeconds());
                   currentMinutes = (int) (musicPlayer.getCurrentTime().toSeconds() / 60 ) % 60;
                   currentSeconds = (int) musicPlayer.getCurrentTime().toSeconds() % 60;
                }
            });
        }

        public void playTrack()
        {
            musicPlayer.play();
            isPlaying.setValue(true);
        }

        public void stopTrack()
        {
            musicPlayer.stop();
            isPlaying.setValue(false);
        }

        public void pauseTrack()
        {
           musicPlayer.pause();
           isPlaying.setValue(false);
        }

          public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }

        public DoubleProperty getCurrentDurationProperty()
       {
        return currentDuration;
       }

        public DoubleProperty getDurationProperty() {
            return duration;
        }

        public double getDuration() {
        return musicPlayer.getTotalDuration().toSeconds();
        }

        public String getFormattedCurrentDuration() {
         return currentMinutes + ":" + currentSeconds;
        }

        public String getFormattedDuration() {
            return minutes + ":" + seconds;
        }
}
