package dal.local;

import org.apache.commons.io.FilenameUtils;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalFilesDAO {


    /**
     *
     * @param path The given Path to read files from.
     * @return Every file in the path's directory and subdirectories.
     */

    Path currentPath = null;

    public List<File> readAllFromDir(Path path){
        File filePath = path.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths){
            if (f.isDirectory()){
                readAllFromDir(Path.of(f.getPath()));
            }
        }
/*
        for (File f : allLocalFilePaths){

            System.out.println(f.getName());
        }
*/
        ArrayList<File> returnList = new ArrayList<>();
        for (File f : allLocalFilePaths){
            if(checkForMp3OrWav(f.getName())){
                returnList.add(f);
            }
        }


        return returnList;
    }

    public List<File> allLocalMusic(Path path) {
        ArrayList<File> filterList = new ArrayList<>(readAllFromDir(path));

        ArrayList<File> returnList = new ArrayList<>();
        for (File f : filterList){
            if(checkForMp3OrWav(f.getName())){
                System.out.println(f);
            }
        }

        for (File f : returnList){
            System.out.println(f);
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

    public static void main(String[] args) {
        LocalFilesDAO l = new LocalFilesDAO();
        String filepath = "D:\\Music\\Rap";
        //l.readAllFromDir(Path.of(filepath));
        ArrayList<File> p = new ArrayList<>(l.readAllFromDir(Path.of(filepath)));
        for (File f : p){
            System.out.println(f.getName());
        }
        //l.allLocalMusic(Path.of(filepath));
    }
}
