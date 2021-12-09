package be;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MusicModel {
    /**
     * A filler class that
     */
    private static final String TYPE = "MusicModel";

    public MusicModel() {

    }

    public String getType(){
        return TYPE;
    }

    @Override
    public String toString()
    {
        //return new Gson().toJson(this);
        return "unkown musicmodel";
    }


}
