package bll;

import be.MusicModel;
import be.SongModel;
import dal.db.EASVDatabase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MusicManager {

        public final ArrayList<MusicModel> dataArray = new ArrayList();
        public final ObservableList<SongModel> data = FXCollections.observableArrayList();
        public final BooleanProperty isPlaying = new SimpleBooleanProperty();

        MediaPlayer musicPlayer = null;


    /**
         * Variables, woo.
         */
        private Timer timer;
        private TimerTask timertask;

        private int songNumber;

        private boolean isRunning;

        public MusicManager()
        {
            data.addAll(new EASVDatabase().getAllSongs());
        }

        public void setMedia(String path)
        {
            this.musicPlayer = new MediaPlayer(new Media(path));
        }

        private void setMedia(int index) throws IOException
        {
            setMedia(data.get(index).getLocation());
        }

        public void playTrack()
        {
            musicPlayer.play();
        }

        public void stopTrack()
        {
            musicPlayer.stop();
        }

        public void pauseTrack()
        {
           musicPlayer.pause();
        }

        public void resetTrack()
        {
           musicPlayer.seek(Duration.ZERO);
        }

        public void nextTrack()
        {
            musicPlayer.stop();

            playTrack();
        }

        public void previousTrack()
        {
            if (musicPlayer.getCurrentTime().toSeconds() > 5)
            {

            }
            else
            {
                musicPlayer.stop();
                musicPlayer.play();
            }
        }

        public int getDuration()
        {
            return (int) musicPlayer.getCurrentTime().toSeconds();
        }

        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }
}
