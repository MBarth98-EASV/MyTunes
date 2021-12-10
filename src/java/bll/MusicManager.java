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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MusicManager {

        public final ArrayList<MusicModel> dataArray = new ArrayList();
        public final ObservableList<SongModel> data = FXCollections.observableArrayList();
        public final BooleanProperty isPlaying = new SimpleBooleanProperty();

        MediaPlayer musicPlayer = null;

        public MusicManager()
        {
            data.addAll(new EASVDatabase().getAllSongs());
        }

        public void pathToAbsolute(String path)
        {
            URL res = getClass().getClassLoader().
            File file = Paths.get()
            System.out.println(FileSystems.getDefault().getPath(path).toAbsolutePath());
        }

        public void setMedia(String path) {
            this.musicPlayer = new MediaPlayer(new Media(FileSystems.getDefault().getPath(path).toAbsolutePath().toUri().toString()));
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

        public int getDuration()
        {
            return (int) musicPlayer.getCurrentTime().toSeconds();
        }

        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }
}
