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
        return db.filterEqualsParameter("artist", filterParameter);
    }

    public List<SongModel> filterEqualsAlbum(String filterParameter){
        return db.filterEqualsParameter("album", filterParameter);
    }


    public List<SongModel> filterEqualsGenre(String filterParameter){
        return db.filterEqualsParameter("genre", filterParameter);
    }


    public List<SongModel> filterEqualsArtistTitle(String filterParameter){
        List<SongModel> artistAndTitle = new ArrayList<>();
        artistAndTitle.addAll(db.filterEqualsParameter("artist", filterParameter));
        artistAndTitle.addAll(db.filterEqualsParameter("title", filterParameter));

        return artistAndTitle;
    }

    public List<SongModel> filterEqualsSearch(){
        return db.getAllSongs();
    }

    public List<String> allAvailableArtist(){
        ArrayList<String> returnList = new ArrayList();
        returnList.addAll(db.allAvailableByParameter("artist"));
        java.util.Collections.sort(returnList);


        for (int i = 1; i<returnList.size(); i++) {
            if (returnList.get(i).equals(returnList.get(i-1))){
                returnList.remove(i);
            }
        } return returnList;
    }

    public List<String> allAvailableAlbums(){
        ArrayList<String> returnList = new ArrayList();
        returnList.addAll(db.allAvailableByParameter("album"));
        java.util.Collections.sort(returnList);


        for (int i = 1; i<returnList.size(); i++) {
            if (returnList.get(i).equals(returnList.get(i-1))){
                returnList.remove(i);
            }
        } return returnList;
    }


    public List<String> allAvailableGenre(){
        ArrayList<String> returnList = new ArrayList();
        returnList.addAll(db.allAvailableByParameter("genre"));
        java.util.Collections.sort(returnList);


        for (int i = 1; i<returnList.size(); i++) {
            if (returnList.get(i).equals(returnList.get(i-1))){
                returnList.remove(i);
            }
        } return returnList;
    }


    public List<String> allAvailableTitleArtist(){
        ArrayList<String> returnList = new ArrayList();
        returnList.addAll(db.allAvailableByParameter("artist"));
        returnList.addAll(db.allAvailableByParameter("title"));
        java.util.Collections.sort(returnList);


        for (int i = 1; i<returnList.size(); i++) {
            if (returnList.get(i).equals(returnList.get(i-1))){
                returnList.remove(i);
            }
        } return returnList;
    }


}
