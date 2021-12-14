package bll;

import be.SongModel;
import dal.db.EASVDatabase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchManager
{
    EASVDatabase db;

    public SearchManager() {
        db = new EASVDatabase();
    }

    public ListProperty<SongModel> getAll(){
        return new SimpleListProperty<SongModel>(db.getAllSongs());
    }

    public ListProperty<SongModel> filter(String firstProperty, String filterParameter)
    {
        if (filterParameter == null || filterParameter.isEmpty())
        {
            return getAll();
        }

        return db.filterEqualsParameter(firstProperty, "NONE", filterParameter);
    }

    public ListProperty<SongModel> filter(String firstProperty, String secondProperty, String filterParameter)
    {
        if (filterParameter == null || filterParameter.isEmpty())
        {
            return getAll();
        }

        return db.filterEqualsParameter(firstProperty, secondProperty, filterParameter);
    }

    public List<String> getAllByParameter(String... params)
    {
        List<String> inputList = new ArrayList<>();

        for (String param: params)
        {
            inputList.addAll(db.getUniqueValuesIn(param));
        }

        return inputList.stream().sorted().collect(Collectors.toList());
    }
}
