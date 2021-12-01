package model;

import bll.LocalFilesManager;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class LocalFilesModel {

    LocalFilesManager localFilesManager;

    public LocalFilesModel() {
        localFilesManager = new LocalFilesManager();

    }


    public Path getCurrentPath(){
        return localFilesManager.getCurrentPath();
    }

    public List<File> getAllLocalSongs(Path path){
        return localFilesManager.readAllFromDir(path);
    }

    public File addSong(Path path){

    }


}
