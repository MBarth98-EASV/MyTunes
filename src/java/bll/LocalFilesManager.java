package bll;


import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocalFilesManager {


    /**
     *
     * @param path The given Path to read files from.
     * @return Every file in the path's directory and subdirectories.
     */

    Path currentPath = null;

    public List<File> readAllFromDir(Path path){
        currentPath = path;
        ArrayList<File> returnList = new ArrayList<>();

        File filePath = path.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths){


            if(checkForMp3OrWav(f.getName())) {
                returnList.add(f);
            }
            else if (f.isDirectory()){
                readAllFromDir(Path.of(f.getPath()));
            }

        }

        for (File f : returnList){

            System.out.println(f.getName());
        }

        return returnList;
    }



    public Boolean checkForMp3OrWav(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        if(extension.equals("mp3") || extension.equals("wav")){
            return true;
        }
        else return false;
    }

    public Path getCurrentPath(){
        return currentPath;
    }

}
