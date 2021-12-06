package bll;

import be.SongModel;

import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystem;

public class LocalAudio implements IAudio, IAudioPlayer
{
    private File audioFile;


    @Override
    public void load(SongModel song)
    {
        this.load(song.getLocation());
    }

    @Override
    public void load(String path)
    {
        this.audioFile = new File(path);
    }

    @Override
    public int getDuration()
    {
        return 0;
    }

    @Override
    public int getTime()
    {
        return 0;
    }

    @Override
    public void play()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void reset()
    {

    }

    @Override
    public void skipTo(int ms)
    {

    }
}
