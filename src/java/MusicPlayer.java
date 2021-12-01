import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MusicPlayer {

        File filepath = new File("D:\\Music\\Avantasia - Ghostlights (2016)\\01. Mystery Of A Blood Red Rose.mp3");

        Media media = new Media(filepath.toURI().toString());
        MediaPlayer musicPlayer = new MediaPlayer(media);

        public void playTrack()
        {
            musicPlayer.play();
        }

        public void pauseTrack()
        {
            musicPlayer.pause();
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
}
