package bll;

import be.SongModel;
import dal.db.EASVDatabase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;
import java.util.ArrayList;

public class MusicManager {

        public final ObservableList<SongModel> data = FXCollections.observableArrayList();
        public final BooleanProperty isPlaying = new SimpleBooleanProperty();

        MediaPlayer musicPlayer = null;

        public MusicManager()
        {
            data.addAll(new EASVDatabase().getAllSongs());
        }

        public void setMedia(SongModel song)
        {
            this.musicPlayer = new MediaPlayer(new Media(Paths.get(song.getLocation()).toUri().toString()));
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

        public int getDuration()
        {
            musicPlayer.getOnReady();
            return (int) musicPlayer.getCurrentTime().toSeconds();
        }

        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }

        public void setMaxDuration()
        {
            musicPlayer.getTotalDuration().toSeconds();
        }

        public void setPlayback(int seekTime)
        {
           // musicPlayer.seek(Duration);
        }
}
