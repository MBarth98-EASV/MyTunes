package bll;

import be.SongModel;
import dal.LocalFilesDAO;
import dal.db.EASVDatabase;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalFilesManager 
{

    public LocalFilesManager() 
    {
    }

    public void addSong(Path path){
        DataManager.addSong(path);

    }

    public void removeSong(Path path)
    {
        
    }

    public void editSong(SongModel songModel)
    {
    }

}
