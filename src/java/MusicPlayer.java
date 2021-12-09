import dal.db.EASVDatabase;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer {


        /**
         * Variables, woo.
         */
        private Timer timer;
        private TimerTask timertask;

        private int songNumber;

        private boolean isRunning;

        public MusicPlayer()
        {

        }

        EASVDatabase dbaccess = new EASVDatabase();

        private File filepath = new File("src/resources/music/file_example_MP3_5MG.mp3");//Controller.class.getResource(dbaccess.getFilePath(6, "Songs")).getFile());

        Media media = new Media(filepath.toURI().toString());
        MediaPlayer musicPlayer = new MediaPlayer(media);


        /**
        * Plays the current track.
        */
        public void playTrack()
        {
            beginTimer();
            musicPlayer.play();
        }


        public void playSelectedTrack(String song, String table)
        {
            musicPlayer.stop();
            cancelTimer();

            File filepath = new File(Controller.class.getResource(dbaccess.getFilePath(song, table)).getFile());

            Media media = new Media(filepath.toURI().toString());
            musicPlayer = new MediaPlayer(media);

            beginTimer();
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
           cancelTimer();
           musicPlayer.seek(Duration.ZERO);
        }


        /**
         * Plays the next track in the list.
         */
        public void nextTrack()
        {
            cancelTimer();
            musicPlayer.stop();

            File filepath = new File(Controller.class.getResource("music/file_example_MP3_5MG.mp3").getFile());

            Media media = new Media(filepath.toURI().toString());
            musicPlayer = new MediaPlayer(media);

            playTrack();
            beginTimer();
        }

        /**
         * Plays the previous track in the list.
         */
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

                File filepath = new File(Controller.class.getResource("music/the-introvert-michael-kobrin-10959.mp3").getFile());

                Media media = new Media(filepath.toURI().toString());
                musicPlayer = new MediaPlayer(media);

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
        public double getTrackDuration()
        {
            return musicPlayer.getTotalDuration().toSeconds();
        }

        /**
         * Returns the name of the current track.
         */
        public void getTrackName()
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
