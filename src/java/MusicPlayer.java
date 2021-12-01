import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MusicPlayer
{
    File filepath = new File(Controller.class.getResource("music/file_example_MP3_5MG.mp3").getFile());

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
