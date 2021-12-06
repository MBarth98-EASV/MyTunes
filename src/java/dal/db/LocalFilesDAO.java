package dal.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import be.SongModel;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class LocalFilesDAO {


    private static final Path songPath = Path.of("src/resources/data/externalsongs.txt");
    private static final Path dirPath = Path.of("src/resources/data/directory.txt");


    /**
     * Lists all files in the given path, and checks whether it's a directory
     * or a supported filetype (.wav or .mp3). The file's path is then stored as a Path object in
     * the returnlist. If it's a directory, the method is run again in inside that directory.
     * The method loops until there are no more subdirectories.
     * @param path The given Path to read files from.
     * @return A list containing a list of every file mp3 file and wav file
     * in the path's directory and subdirectories.
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
            }
            else if (f.isDirectory()) {
                readAllFromSubDir(Path.of(f.getPath()));
            }
        }
        return returnList;
    }


    /**
     * Read all files in the current directory and directories.
     * If the file is of a supported filetype (.wav or .mp3), it is added to
     * the returnList. If it is a directory, readAllFromSubDir is run.
     * @return A HashMap containing a list of every mp3 file and a list of every wav file in a directory.
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
            }
            else if (f.isDirectory()) {
                returnList.addAll(readAllFromSubDir(Path.of(f.getPath())));
            }
        }
        return returnList;
    }

    /**
     * Saves the given directory in directory.txt
     * @param path The directory to be saved.
     */
    public void saveDirectory(Path path) {
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
    private Path loadDirectory(){
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
    private List<Path> loadAllExternalSongs() {
        ArrayList<Path> returnList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(songPath)))) {
            String line;
            while ((line = br.readLine()) != null) {

                String songPath = line;
                if (checkForMp3OrWav(songPath)) {
                    returnList.add(Path.of(songPath));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnList;
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

    /**
     *
     * @return
     * 
     * Throws "Could not invoke DirectBuffer method - illegal access".
     * Library fault - To do.
     */
    public List<SongModel> loadAllLocalSongs() {
        ArrayList<SongModel> returnList = new ArrayList<>();
        ArrayList<Path> loadList = new ArrayList<>();
        loadList.addAll(readAllFromCurDirectory());
        loadList.addAll(loadAllExternalSongs());


        for (Path p : loadList) {
            String artist = "Unknown Artist";
            String title = String.valueOf(p);
            String album = "N/A";
            String genre = "N/A";
            int duration = 0;

            try {
                AudioFile audioFile = AudioFileIO.read(p.toFile());
                Tag tag = audioFile.getTag();
                AudioHeader header = audioFile.getAudioHeader();

                artist = tag.getFirst(FieldKey.ARTIST);
                title = tag.getFirst(FieldKey.TITLE);
                album = tag.getFirst(FieldKey.ALBUM);
                genre = tag.getFirst(FieldKey.GENRE);
                duration = header.getTrackLength();

                if (artist == null || artist.isEmpty() || artist.equals("null")){
                    artist = "Unknown Artist";
                }

                if (title == null || title.isEmpty() || title.equals("null")){
                    title = String.valueOf(p);
                }

                if (album == null || album.isEmpty() || album.equals("null")){
                    album = "N/A";
                }

                if (genre == null || genre.isEmpty() || genre.equals("null")){
                    genre = "---";
                }

                if (duration == 0){
                    File file = p.toFile();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = audioInputStream.getFormat();
                    long frames = audioInputStream.getFrameLength();
                    double durationInSeconds = (double) frames / format.getFrameRate();

                    duration = (int) durationInSeconds;
                }



            } catch (CannotReadException e) {
                e.printStackTrace();
            } catch (TagException e) {
                e.printStackTrace();
            } catch (InvalidAudioFrameException e) {
                e.printStackTrace();
            } catch (ReadOnlyFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }

            int id = (int) (Math.random() * 100);
                returnList.add(new SongModel(id, title, artist, album, duration, genre, p.toString()));
        }

        return returnList;
    }
    
    

}

