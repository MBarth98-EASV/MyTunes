package dal.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import be.SongModel;


import com.mpatric.mp3agic.*;


public class LocalFilesDAO {

    private Path currentPath = Path.of("D:\\Music\\Rap");

    private static final Path songPath = Path.of("src/resources/data/externalsongs.txt");
    private static final Path dirPath = Path.of("src/resources/data/directory.txt");


    /**
     * @param path The given Path to read files from.
     * @return Every file in the path's directory and subdirectories.
     */

    public List<File> readAllFromNewDir(Path path) {
        saveDirectory(path);
        ArrayList<File> returnList = new ArrayList<>();

        File filePath = path.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForMp3OrWav(f.getName())) {
                returnList.add(f);
            } else if (f.isDirectory()) {
                readAllFromNewDir(Path.of(f.getPath()));
            }
        }
        return returnList;
    }

    public List<Path> readAllFromCurDirectory(Path currentPath) {
        ArrayList<Path> returnList = new ArrayList<>();

        File filePath = currentPath.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForMp3OrWav(f.getName())) {
                returnList.add(f.toPath());
            } else if (f.isDirectory()) {
                readAllFromNewDir(Path.of(f.getPath()));
            }
        }
        return returnList;
    }


    private void saveDirectory(Path path) {
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

    public Path loadDirectory(){
        Path returnPath = null;
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(dirPath)))) {
            String line;
            while ((line = br.readLine()) != null) {

                returnPath = Path.of(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnPath;
    }


    public Path addSong(Path path) {

        try (BufferedWriter bw = Files.newBufferedWriter(songPath, StandardOpenOption.SYNC,
                StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {

            if (checkForMp3OrWav(String.valueOf(path))) {
                bw.newLine();
                bw.write(String.valueOf(path));
            } else throw new Exception("The file you added is not currently supported");

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public List<Path> loadAllExternalSongs() {
        ArrayList<Path> returnList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(songPath)))) {
            String line;
            while ((line = br.readLine()) != null) {

                String songPath = line;
                returnList.add(Path.of(songPath));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public void removeSong(SongModel song) {

    }

    /**
     *
     * @param fileName String value of a Path object.
     * @return true if the file has a .mp3 or .wav file ex
     */
    private Boolean checkForMp3OrWav(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        if (extension.equals("mp3") || extension.equals("wav")) {
            return true;
        } else return false;
    }

    private List<SongModel> loadAllLocalSongs() {
        ArrayList<Path> loadList = new ArrayList<>();
        loadList.addAll(readAllFromCurDirectory(loadDirectory()));
        loadList.addAll(loadAllExternalSongs());

        ArrayList<SongModel> returnList = new ArrayList<>();
        for (Path p : loadList) {
            try {
                Mp3File mp3File = new Mp3File("p");
                mp3File.getId3v2Tag();



            } catch (Exception e) {
                e.printStackTrace();
            }

            }

        return returnList;
        }

        public static void main(String[] args) {
        LocalFilesDAO localFilesDAO = new LocalFilesDAO();
        localFilesDAO.loadAllLocalSongs();
    }
}

