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
            beginTimer();
            musicPlayer.play();
        }

        public void pauseTrack()
        {
           musicPlayer.pause();
        }

        public void resetTrack()
        {
           cancelTimer();
           musicPlayer.seek(Duration.ZERO);
        }

        public void nextTrack()
        {
            cancelTimer();
            musicPlayer.stop();

            playTrack();
            beginTimer();
        }

        public void previousTrack()
        {
            if (musicPlayer.getCurrentTime().toSeconds() > 5)
            {
                resetTrack();
            }
            else
            {
                cancelTimer();
                musicPlayer.stop();

                beginTimer();
                musicPlayer.play();
            }
        }

        /**
         * A timer to track how far the song is.
         */
        public void beginTimer()
        {
            timer = new Timer();

            timertask = new TimerTask()
            {

                public void run()
                {
                    isRunning = true;
                    double timeleft = musicPlayer.getCurrentTime().toSeconds() - musicPlayer.getTotalDuration().toSeconds();

                    if(timeleft == 0)
                    {
                        cancelTimer();
                    }
                }

            };
        }

        public void cancelTimer()
        {
            isRunning = false;
            timer.cancel();
        }

        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }
}
