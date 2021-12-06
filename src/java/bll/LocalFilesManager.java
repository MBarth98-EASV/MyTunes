package bll;

import dal.db.LocalFilesDAO;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalFilesManager {

    LocalFilesDAO localFilesDAO;

    public LocalFilesManager() 
    {
        localFilesDAO = new LocalFilesDAO();
    }

    public List<Path> readAllFromNewDir(Path path)
    {
        return null;
        //localFilesDAO.readAllFromDir(path);
    }

    public Path addSong(Path path)
    {
        return localFilesDAO.addSong(path);
    }

    public void removeSong(Path path){
    }

    public Path editSong(Path path)
    {
        return path;
    }

}
