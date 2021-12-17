package be;

public interface IAudio
{
    void play();
    void pause();

    void load(SongModel song);
    void load(String path);

    void setVolume(double val);
}
