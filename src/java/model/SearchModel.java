package model;

import be.MusicModel;
import be.SongModel;

import java.util.List;

public class SearchModel {

    public MusicModel getObjectFromText(List<SongModel> inputList, String search) {
        for (MusicModel m : inputList) {
            if (m.toString().equals(search)) {
                return m;
            }
        }

        return null;
    }
}
