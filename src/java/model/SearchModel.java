package model;

import be.MusicModel;

import java.util.List;

public class SearchModel {

    public MusicModel getObjectFromText(List<MusicModel> inputList, String search) {
        for (MusicModel m : inputList) {
            if (m.toString().equals(search)) {
                return m;
            }
        }

        return null;
    }
}
