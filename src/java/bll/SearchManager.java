package bll;

import be.SongModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchManager
{
    public SearchManager() {
    }

    public ListProperty<SongModel> getAll(){
        return new SimpleListProperty<SongModel>(DataManager.selectedPlaylist().get().getSongs());
    }

    public ListProperty<SongModel> filter(String firstProperty, String filterParameter)
    {
        if (filterParameter == null || filterParameter.isEmpty())
        {
            return getAll();
        }

        return DataManager.filterEqualsParameter(firstProperty, "NONE", filterParameter);
    }

    public ListProperty<SongModel> filter(String firstProperty, String secondProperty, String filterParameter)
    {
        if (filterParameter == null || filterParameter.isEmpty())
        {
            return getAll();
        }

        return DataManager.filterEqualsParameter(firstProperty, secondProperty, filterParameter);
    }

    public List<String> getAllByParameter(String... params)
    {
        List<String> inputList = new ArrayList<>();

        for (String param: params)
        {
            inputList.addAll(DataManager.getUniqueValuesIn(param));
        }

        return inputList.stream().sorted().collect(Collectors.toList());
    }
}
