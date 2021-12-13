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

    public List<SongModel> getAll(){
        return db.getAllSongs();
    }

    public List<SongModel> filter(String firstProperty, String filterParameter)
    {
        return db.filterEqualsParameter(firstProperty, "NONE", filterParameter);
    }

    public List<SongModel> filter(String firstProperty, String secondProperty, String filterParameter)
    {
        return db.filterEqualsParameter(firstProperty, secondProperty, filterParameter);
    }

    public List<String> getAllByParameter(String... params)
    {
        List<String> inputList = new ArrayList<>();

        for (String param: params)
        {
            inputList.addAll(db.allAvailableByParameter(param));
        }

        return inputList.stream().sorted().collect(Collectors.toList());
    }
}
