package be;

public interface IAudio
{
    void play();
    void pause();
    void reset();
    void skipTo(int ms);

    void load(SongModel song);
    void load(String path);

    int getDuration(); // ms
    int getTime(); // ms offset
}
