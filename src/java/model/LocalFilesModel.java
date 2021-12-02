package model;

import bll.LocalFilesManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LocalFilesModel {

    ObservableList<Path> musicFilePaths;
    LocalFilesManager localFilesManager;
    Path localDirPath = null;

    public LocalFilesModel() {
        localFilesManager = new LocalFilesManager();
        musicFilePaths = FXCollections.observableArrayList();

    }


    public void setLocalDirPathInModel(Path path){
        localDirPath = path;
    }

    public List<File> getAllLocalSongs(Path path){
        return localFilesManager.readAllFromDir(path);
    }

    public Path addSong(Path path){

        return localFilesManager.addSong(path);
    }

    public void removeSong(Path path){
        localFilesManager.removeSong(path);
    }

    public void editSong(Path path){
        localFilesManager.editSong(path);
    }



}
