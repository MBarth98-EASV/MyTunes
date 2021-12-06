package be;

public interface IAudioPlayer
{
    void play();
    void pause();
    void reset();
    void skipTo(int ms);
}
