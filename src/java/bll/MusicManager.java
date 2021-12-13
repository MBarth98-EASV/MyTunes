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

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MusicManager {

        public final ArrayList<MusicModel> dataArray = new ArrayList();
        public final ObservableList<SongModel> data = FXCollections.observableArrayList();
        public final BooleanProperty isPlaying = new SimpleBooleanProperty();

        MediaPlayer musicPlayer = null;

        public MusicManager()
        {
            data.addAll(new EASVDatabase().getAllSongs());
        }

        public void setMedia(String path) throws URISyntaxException
        {
            this.musicPlayer = new MediaPlayer(new Media(getClass().getClassLoader().getResource(path).toURI().toString()));
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
            return (int) musicPlayer.getCurrentTime().toSeconds();
        }

        public void setVolume(Double volume)
        {
            musicPlayer.setVolume(volume);
        }

        private void setMaxDuration()
        {
            double maxDura = musicPlayer.getTotalDuration().toSeconds() / 60;
            String totalTime = maxDura + ":00";

        }
}
