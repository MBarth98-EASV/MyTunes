package bll;

import be.SongModel;
import dal.db.EASVDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchManager {
    EASVDatabase db;

    public SearchManager() {
        db = new EASVDatabase();
    }

    public List<SongModel> filterEqualsArtist(String filterParameter){
        return db.filterEqualsParameter("artist", "NONE", filterParameter);
    }

    public List<SongModel> filterEqualsAlbum(String filterParameter){
        return db.filterEqualsParameter("album", "NONE", filterParameter);
    }


    public List<SongModel> filterEqualsGenre(String filterParameter){
        return db.filterEqualsParameter("genre", "NONE", filterParameter);
    }


    public List<SongModel> filterEqualsArtistTitle(String filterParameter){
        List<SongModel> artistAndTitle = new ArrayList<>();
        artistAndTitle.addAll(db.filterEqualsParameter("artist", "title", filterParameter));

        return artistAndTitle;
    }

    public List<SongModel> filterEqualsSearch(){
        return db.getAllSongs();
    }

    public List<String> allAvailableArtist(){
        ArrayList<String> inputList = new ArrayList();
        inputList.addAll(db.allAvailableByParameter("artist"));

        List<String> returnList = inputList.stream().distinct().sorted().collect(Collectors.toList());

        return returnList;
    }

    public List<String> allAvailableAlbums(){
        ArrayList<String> inputList = new ArrayList();
        inputList.addAll(db.allAvailableByParameter("album"));

        List<String> returnList = inputList.stream().distinct().sorted().collect(Collectors.toList());

        return returnList;
    }


    public List<String> allAvailableGenre(){
        ArrayList<String> inputList = new ArrayList();
        inputList.addAll(db.allAvailableByParameter("genre"));

        List<String> returnList = inputList.stream().distinct().sorted().collect(Collectors.toList());

        return returnList;
    }


    public List<String> allAvailableTitleArtist(){
        ArrayList<String> inputList = new ArrayList();
        inputList.addAll(db.allAvailableByParameter("artist"));
        inputList.addAll(db.allAvailableByParameter("title"));

        List<String> returnList = inputList.stream().distinct().sorted().collect(Collectors.toList());

        return returnList;
    }


}
