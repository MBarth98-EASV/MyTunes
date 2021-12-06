package model;

import be.SongModel;
import bll.LocalFilesManager;
import javafx.collections.ObservableList;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class LocalFilesModel {

    LocalFilesManager localFilesManager;
    ObservableList musicFilePaths;
    public static SongModel currentlySelectedSong;

    public LocalFilesModel() {
        localFilesManager = new LocalFilesManager();



    }



    public List<Path> readAllFromNewDir(Path path){
        return localFilesManager.readAllFromNewDir(path);
    }

    public Path addSong(Path path){

        return localFilesManager.addSong(path);
    }

    public static void setCurrentlySelectedSong(SongModel songModel){
        currentlySelectedSong = songModel;
    }

    public static SongModel getCurrentlySelectedSong(){
        return currentlySelectedSong;
    }

    public void removeSong(Path path){
        localFilesManager.removeSong(path);
    }

    public void editSong(SongModel songModel){
        localFilesManager.editSong(songModel);
    }



}
