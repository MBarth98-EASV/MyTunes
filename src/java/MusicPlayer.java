import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer {

        private File filepath = new File(Controller.class.getResource("music/file_example_MP3_5MG.mp3").getFile());
        private Timer timer;
        private TimerTask timertask;

        private boolean isRunning;

        Media media = new Media(filepath.toURI().toString());
        MediaPlayer musicPlayer = new MediaPlayer(media);

        /**
        * Plays the currently selected track.
        */
        public void playTrack()
        {
            musicPlayer.play();
        }

        /**
        * Pauses the current track.
        */
        public void pauseTrack()
        {
           musicPlayer.pause();
        }

        /**
        * Resets the current playing track back to its beginning.
        */
        public void resetTrack()
        {
           musicPlayer.seek(Duration.seconds(0));
        }

        public void stopTrack()
        {
          
        }

        public void nextTrack()
        {

        }

        public void previousTrack()
        {

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
                    double current = musicPlayer.getCurrentTime().toSeconds();
                    double end = musicPlayer.getTotalDuration().toSeconds();

                    if(current / end == 1)
                    {
                        cancelTimer();
                    }
                }

            };
        }

        /**
         * Cancels the running timer.
         */
        public void cancelTimer()
        {
            isRunning = false;
            timer.cancel();
        }
}
