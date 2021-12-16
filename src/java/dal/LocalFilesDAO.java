package dal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

import dal.db.EASVDatabase;
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

    /** //TODO: To be used for adding all songs in a directory in the settings panel if implemented.
     * Lists all files in the given path, and checks whether it's a directory
     * or a supported filetype (.wav or .mp3). The file's path is then stored as a Path object in
     * the returnlist. If it's a directory, the method is run again in inside that directory.
     * The method loops until there are no more subdirectories.
     * @param path The given Path to read files from.
     * @return A list containing a list of every file mp3 file and wav file
     * in the path's directory and subdirectories.
     */

    private List<Path> readAllFromDirectory(Path path) {
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
                readAllFromDirectory(Path.of(f.getPath()));
            }
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
        return extension.equals("mp3") || extension.equals("wav");
    }

    public List<SongModel> loadSongModels(List<Path> loadList)
    {
        List<SongModel> returnList = new ArrayList<>();
        for (Path p : loadList)
        {
            String artist = "Unknown Artist";
            String title = String.valueOf(p);
            String album = "N/A";
            String genre = "N/A";
            int duration = 0;

            try
            {
                AudioFile audioFile = AudioFileIO.read(p.toFile());
                Tag tag = audioFile.getTag();
                AudioHeader header = audioFile.getAudioHeader();

                if (tag != null)
                {
                    artist = tag.getFirst(FieldKey.ARTIST);
                    title = tag.getFirst(FieldKey.TITLE);
                    album = tag.getFirst(FieldKey.ALBUM);
                    genre = tag.getFirst(FieldKey.GENRE);
                }

                duration = header.getTrackLength();

                if (artist == null || artist.isEmpty() || artist.equals("null"))
                {
                    artist = "Unknown Artist";
                }

                if (title == null || title.isEmpty() || title.equals("null"))
                {
                    String titleExtension = p.toFile().getName();
                    title = titleExtension.substring(0, titleExtension.lastIndexOf("."));
                }

                if (album == null || album.isEmpty() || album.equals("null"))
                {
                    album = "N/A";
                }

                if (genre == null || genre.isEmpty() || genre.equals("null"))
                {
                    genre = "---";
                }

                if (duration == 0)
                {
                    File file = p.toFile();
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = audioInputStream.getFormat();
                    long frames = audioInputStream.getFrameLength();
                    double durationInSeconds = (double) frames / format.getFrameRate();

                    duration = (int) durationInSeconds;
                }
            }
            catch (CannotReadException e)
            {
                e.printStackTrace();
            }
            catch (TagException e)
            {
                e.printStackTrace();
            }
            catch (InvalidAudioFrameException e)
            {
                e.printStackTrace();
            }
            catch (ReadOnlyFileException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (UnsupportedAudioFileException e)
            {
                e.printStackTrace();
            }

            returnList.add(new SongModel(-1, title, artist, genre, album, duration, "local", p.toString()));
        }

        return returnList;
    }
}

