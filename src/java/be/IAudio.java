package be;

public interface IAudio
{
    void play();
    void pause();
    void reset();
    void skipTo(int ms);

    void load(SongModel song);

    int getDuration(); // ms
    int getTime(); // ms offset
}
