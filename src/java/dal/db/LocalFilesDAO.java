package dal.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LocalFilesDAO {

    private Path currentPath = null;

    private static final Path songPath = Path.of("data/externalsongs.txt");
    private static final Path dirPath = Path.of("data/directory.txt");


    /**
     *
     * @param path The given Path to read files from.
     * @return Every file in the path's directory and subdirectories.
     */

    public List<File> readAllFromNewDir(Path path){
        saveDirectory(path);
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
                readAllFromNewDir(Path.of(f.getPath()));
            }

        }

        return returnList;
    }


    private void saveDirectory(Path path){
        try (BufferedWriter bw = Files.newBufferedWriter(dirPath, StandardOpenOption.SYNC,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {

            bw.newLine();
            bw.write(String.valueOf(path));


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path addSong(Path path){

        try (BufferedWriter bw = Files.newBufferedWriter(songPath, StandardOpenOption.SYNC,
                StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {

            bw.newLine();
            bw.write(String.valueOf(path));


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    private Boolean checkForMp3OrWav(String fileName){
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

}
