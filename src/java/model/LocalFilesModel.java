package model;


import be.SongModel;
import bll.LocalFilesManager;

import javafx.collections.ObservableList;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class LocalFilesModel {

    LocalFilesManager localFilesManager;

    public LocalFilesModel() 
    {
        localFilesManager = new LocalFilesManager();
    }

    public void addSong(Path path) {
        localFilesManager.addSong(path);
    }

    public void removeSong(Path path){
        localFilesManager.removeSong(path);
    }

    public void editSong(SongModel songModel){
        localFilesManager.editSong(songModel);
    }
}
