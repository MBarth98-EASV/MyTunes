package dal.db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.SongModel;


import com.mpatric.mp3agic.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * the appropriate list. If its a directory, the method is run again in inside that directory.
     * The method loops until there are no more subdirectories.
     * @param path The given Path to read files from.
     * @return A HashMap containing a list of every file mp3 file, and one for every wav file
     * in the path's directory and subdirectories.
     */

    public HashMap<String, ArrayList<Path>> readAllFromSubDir(Path path) {
        ArrayList<Path> returnMp3List = new ArrayList<>();
        ArrayList<Path> returnWavList = new ArrayList<>();
        HashMap<String, ArrayList<Path>> returnMap = new HashMap<>();
        returnMap.put("mp3", returnMp3List);
        returnMap.put("wav", returnWavList);

        File filePath = path.toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForFileType(f.getName(), "mp3")) {
                returnMp3List.add(f.toPath());
            }
            else if (checkForFileType(f.getName(), "wav")) {
                returnWavList.add(f.toPath());
            }
            else if (f.isDirectory()) {
                readAllFromSubDir(Path.of(f.getPath()));
            }
        }
        returnMap.put("mp3", returnMp3List);
        returnMap.put("wav", returnWavList);
        return returnMap;
    }


    /**
     * Read all files in the current directory and directories.
     * If the file is of a supported filetype (.wav or .mp3), it is added to
     * the appropriate List, stored inside a HashMap. If it is a directory, readAllFromSubDir
     * is run.
     * @return A HashMap containing a list of every mp3 file and a list of every wav file in a directory.
     */
    public HashMap<String, List<Path>> readAllFromCurDirectory() {
        ArrayList<Path> returnMp3List = new ArrayList<>();
        ArrayList<Path> returnWavList = new ArrayList<>();
        HashMap<String, List<Path>> returnMap = new HashMap<>();
        returnMap.put("mp3", returnMp3List);
        returnMap.put("wav", returnWavList);


        File filePath = loadDirectory().toFile();
        File[] listOfFiles = filePath.listFiles();

        ArrayList<File> allLocalFilePaths = new ArrayList<>();
        allLocalFilePaths.addAll(List.of(listOfFiles));

        for (File f : allLocalFilePaths) {

            if (checkForFileType(f.getName(), "mp3")) {
                returnMp3List.add(f.toPath());
            }
            else if (checkForFileType(f.getName(), "wav")){
                returnWavList.add(f.toPath());
            }
            else if (f.isDirectory()) {
                Map<String, ArrayList<Path>> subDirMap = readAllFromSubDir(Path.of(f.getPath()));
                returnMp3List.addAll(subDirMap.get("mp3"));
                returnWavList.addAll(subDirMap.get("wav"));
            }
        }
        returnMap.put("mp3", returnMp3List);
        returnMap.put("wav", returnWavList);
        return returnMap;
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
    public HashMap<String, ArrayList<Path>> loadAllExternalSongs() {
        ArrayList<Path> returnMp3List = new ArrayList<>();
        ArrayList<Path> returnWavList = new ArrayList<>();
        HashMap<String, ArrayList<Path>> returnMap = new HashMap<>();


        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(songPath)))) {
            String line;
            while ((line = br.readLine()) != null) {

                String songPath = line;
                if (checkForFileType(songPath, "mp3")) {
                    returnMp3List.add(Path.of(songPath));
                }
                else if (checkForFileType(songPath, "wav")){
                    returnWavList.add(Path.of(songPath));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        returnMap.put("mp3", returnMp3List);
        returnMap.put("wav", returnWavList);
        return returnMap;
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

    /**
     * Checks whether or not a file is of the wanted filetype.
     * @param fileName: The file to check.
     * @param fileType: The filetype to check for.
     * @return true if the file has the given fileType extension
     */
    private Boolean checkForFileType(String fileName, String fileType) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        if (extension.equals(fileType)){
            return true;
        } else return false;
    }


    private List<SongModel> loadAllLocalSongs() {
        ArrayList<Path> mp3List = new ArrayList<>();
        ArrayList<Path> wavList = new ArrayList<>();
        mp3List.addAll(readAllFromCurDirectory().get("mp3"));
        mp3List.addAll(loadAllExternalSongs().get("mp3"));
        wavList.addAll(readAllFromCurDirectory().get("wav"));
        wavList.addAll(loadAllExternalSongs().get("wav"));

        String artist = null;
        String title = null;
        String album = null;
        String genre = null;
        int duration = 0;

        ArrayList<SongModel> returnList = new ArrayList<>();

        returnList.addAll(loadAllMp3AsSong(mp3List,artist,title,album,genre,duration));

        returnList.addAll(loadAllWavAsSong(wavList,artist,title,album,genre,duration));

        return returnList;
        }


        private List<SongModel> loadAllMp3AsSong(List<Path> list, String artist, String title, String album, String genre, int duration) {
            ArrayList<SongModel> returnList = new ArrayList<>();

            for (Path p : list) {
                try {
                    Mp3File mp3file = new Mp3File(p);
                    if (mp3file.hasId3v2Tag()) {
                        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                        artist = id3v2Tag.getArtist();
                        title = id3v2Tag.getTitle();
                        album = id3v2Tag.getAlbum();
                        genre = id3v2Tag.getGenreDescription();
                        duration = id3v2Tag.getDataLength();


                    } else if (mp3file.hasId3v1Tag()) {
                        ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                        artist = id3v1Tag.getArtist();
                        title = id3v1Tag.getTitle();
                        album = id3v1Tag.getAlbum();
                        genre = id3v1Tag.getGenreDescription();
                        duration = 0;
                    }


                    if (artist == null) {
                        artist = "Unknown Artist";
                    }

                    if (title == null) {
                        title = String.valueOf(p);
                    }

                    if (album == null) {
                        album = "N/A";
                    }

                    if (genre == null) {
                        genre = "N/A";
                    }

                    if (duration == 0) {
                        duration = (int) mp3file.getLengthInSeconds();
                    }

                    int id = (int) (Math.random() * 100);
                    returnList.add(new SongModel(id, title, artist, duration, genre, p.toString()));

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

    private List<SongModel> loadAllWavAsSong(List<Path> list, String artist, String title, String album, String genre, int duration) {
        ArrayList<SongModel> returnList = new ArrayList<>();

        for (Path p : list) {

            artist = "Unkown Artist";
            title = String.valueOf(p);
            album = "N/A";
            genre = "N/A";
            duration = 0;


            try {
                File file = p.toFile();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                double durationInSeconds = (frames+0.0) / format.getFrameRate();

                duration = (int) durationInSeconds;

            } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }


            int id = (int) (Math.random() * 100);
                returnList.add(new SongModel(id, title, artist, duration, genre, p.toString()));
        }

        return returnList;
    }



        public static void main(String[] args) {
        LocalFilesDAO localFilesDAO = new LocalFilesDAO();
        ArrayList<SongModel> l = new ArrayList<>(localFilesDAO.loadAllLocalSongs());
        for (SongModel s : l){
            System.out.println(s);
        }
    }
}

