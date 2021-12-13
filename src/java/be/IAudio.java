package be;

import be.SongModel;
import javafx.util.Duration;


public interface IAudio
{
    void play();
    void pause();
    void reset();
    void skipTo(int ms);

    void load(SongModel song);
    void load(String path);

    void setVolume(double val);

    Duration getDuration(); // ms
    Duration getTime(); // ms offset
}
