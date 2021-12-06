package bll;

import be.SongModel;
import dal.LocalFilesDAO;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalFilesManager {

    LocalFilesDAO localFilesDAO;

    public LocalFilesManager() {
        localFilesDAO = new LocalFilesDAO();
    }


    public List<SongModel> readAllFromNewDir(Path path){
        localFilesDAO.saveDirectory(path);
        return localFilesDAO.loadAllLocalSongs();
    }




    public Path addSong(Path path){
        return localFilesDAO.addSong(path);
    }

    public void removeSong(Path path){
    }

    public void editSong(SongModel songModel){

    }

}
