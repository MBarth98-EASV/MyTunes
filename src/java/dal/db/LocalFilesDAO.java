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


    private static final Path songPath = Path.of("src/resources/data/externalsongs.txt");
    private static final Path dirPath = Path.of("src/resources/data/directory.txt");


    /**
     * Lists all files in the given path, and checks whether it's a directory
     * or a supported filetype (.wav or .mp3). If its a directory, the method is run again in the subdirectory.
     * If a supported filetype is found, it is added to the returnList and returned by the method.
     * @param path The given Path to read files from.
     * @return Every file in the path's directory and subdirectories.
     */

    public List<Path> readAllFromSubDir(Path path) {
        ArrayList<Path> returnList = new ArrayList<>();

        File filePath = path.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForMp3OrWav(f.getName())) {
                returnList.add(f.toPath());
            } else if (f.isDirectory()) {
                readAllFromSubDir(Path.of(f.getPath()));
            }
        }
        return returnList;
    }


    /**
     * Read all files in the current directory and directories.
     * If the file is of a supported filetype (.wav or .mp3), it is added to
     * the returnList. If it is a directory, readAllFromSubDir.
     *
     * @return every mp3 and wav file in a directory.
     */
    public List<Path> readAllFromCurDirectory() {
        ArrayList<Path> returnList = new ArrayList<>();

        File filePath = loadDirectory().toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForMp3OrWav(f.getName())) {
                returnList.add(f.toPath());
            } else if (f.isDirectory()) {
                returnList.addAll(readAllFromSubDir(Path.of(f.getPath())));
            }
        }
        return returnList;
    }

    /**
     * Saves the given directory in directory.txt
     * @param path The directory to be saved.
     */
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

    /**
     * Reads directory.txt as a string.
     * @return the currently stored directory as a Path object.
     */
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

    /**
     * Writes the given path to externalsongs.txt. Also checks whether or not the given
     * path is of a supported filetype (.mp3 or .wav). An exception is thrown if it is not.
     * @param path The path to the song added manually by the user.
     * @return The input path.
     */
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

    /**
     * Reads the entirety of externalsongs.txt and adds the indvidual lines to the returnList
     * as Path objects.
     * @return returnList of all manually added songs.
     */
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
     * Checks the characters of a filename after the "." indicating it's filetype.
     * @param fileName String value of a Path object.
     * @return true if the file has a .mp3 or .wav file extension.
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
        loadList.addAll(readAllFromCurDirectory());
        loadList.addAll(loadAllExternalSongs());

        String artist = null;
        String title = null;
        String album = null;
        String genre = null;
        int duration = 0;


        ArrayList<SongModel> returnList = new ArrayList<>();
        for (Path p : loadList) {
            try {
                Mp3File mp3file = new Mp3File(p);
                if (mp3file.hasId3v2Tag()) {
                    ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                    artist = id3v2Tag.getArtist();
                    title = id3v2Tag.getTitle();
                    album = id3v2Tag.getAlbum();
                    genre = id3v2Tag.getGenreDescription();
                    duration = id3v2Tag.getDataLength();

                } else if (mp3file.hasId3v1Tag()){
                    ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                    artist = id3v1Tag.getArtist();
                    title = id3v1Tag.getTitle();
                    album = id3v1Tag.getAlbum();
                    genre = id3v1Tag.getGenreDescription();
                    duration = 0;
                }

                System.out.println(artist + title +album+genre + duration);

            } catch (InvalidDataException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

