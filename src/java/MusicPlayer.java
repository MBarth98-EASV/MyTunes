import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.io.File;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer implements Initializable {


        /**
         * Variables, woo.
         */
        private File filepath = new File(Controller.class.getResource("music/honor-and-sword-main-11222.mp3").getFile());
        private Timer timer;
        private TimerTask timertask;

        private File directory;
        private File[] files;

        private int songNumber;

        private ArrayList<File> songs;

        private boolean isRunning;


        /** Loads all local files from a folder into a list.
         *  Or it should, if it wasn't because it keeps returning null, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.
         * @param location
         * @param resources
         */
        @Override
        public void initialize(URL location, ResourceBundle resources)
        {
            songs = new ArrayList<File>();

            File directoryPath = new File("music");

            File filesList[] = directoryPath.listFiles();

            if(files != null)
            {
                for(File file : filesList)
                {
                    songs.add(file);
                }
            }
        }

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
           musicPlayer.seek(Duration.ZERO);
        }


        /**
         * Stops the current track.
         */
        public void stopTrack()
        {
            musicPlayer.stop();
        }

        /**
         * Plays the next track in the list.
         */
        public void nextTrack()
        {

        }

        /**
         * Plays the previous track in the list.
         */
        public void previousTrack()
        {
                resetTrack();
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

        /**
         * Returns the total length of the current track.
         * @return
         */
        public double trackDuration()
        {
            return musicPlayer.getTotalDuration().toSeconds();
        }

        /**
         * Returns the name of the current track.
         */

        public void trackName()
        {

        }

        /**
         * Sets the volume of the music player.
         * @param volume
         */
        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }
}
