package bll;

import be.SongModel;
import dal.LocalFilesDAO;
import dal.db.EASVDatabase;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalFilesManager 
{

    LocalFilesDAO localFilesDAO;
    EASVDatabase db;

    public LocalFilesManager() 
    {
        localFilesDAO = new LocalFilesDAO();
        db = new EASVDatabase();
    }

    public void loadAllFromNewDir(Path path)
    {
        localFilesDAO.loadAllLocalSongs(path);
    }



    public void addSong(Path path){
        localFilesDAO.addSong(path);

    }

    public void removeSong(Path path)
    {
        
    }

    public void editSong(SongModel songModel)
    {
    }

}
