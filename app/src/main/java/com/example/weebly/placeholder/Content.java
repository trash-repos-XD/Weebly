package com.example.weebly.placeholder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Content {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<AnimeSched> ITEMS = new ArrayList<AnimeSched>();

    public static List<AnimeSched> getItemByDay(String day){
        return ITEMS;
    }

    private static final int COUNT = 25;
//
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(new AnimeSched("id", "Tensura", "ahahi"));
//        }
//    }
    public static void initItems(String jsonData){
        try{
            Log.e("THE JSON", new JSONArray(jsonData).getJSONObject(0).getJSONArray("schedules").toString() );

            JSONArray scheds = new JSONArray(jsonData).getJSONObject(0).getJSONArray("schedules");
//            addItem(new AnimeSched(scheds.getJSONObject(0)));

//            for (int i=0; i < scheds.length(); i++) {
//                addItem(new AnimeSched(scheds.getJSONObject(i)));
//            }
        }catch (JSONException e){
            Log.e("JSON EXCEPTION",e.toString() );
        }
//        try{
//            addItem(new AnimeSched(new JSONObject(jsonData)));
//
//        }catch (JSONException e){
//            Log.e("JSON EXCEPTION",e.toString() );
//        }
    }


    private static void addItem(AnimeSched item) {
        ITEMS.add(item);
    }


    public static class AnimeSched {
        public final String maUrl;
        public final int id;
        public final String thumbnail;
        public final String name;
        public final double score;
        public final double popularity;
        public final String synopsis;
        public final String[] genres;
        public final String trailer;
        public final String trailerUrl;

        public AnimeSched(JSONObject parsedResponse) throws JSONException {
            Log.e("INIT", parsedResponse.getString("name"));
            this.id = parsedResponse.getInt("id");
            this.maUrl = "";
            this.thumbnail="";
            this.name=parsedResponse.getString("name");
            this.score=10;
            this.popularity=10;
            this.synopsis="";
            this.genres= new String[]{"Action", "Adventure"};
            this.trailer="";
            this.trailerUrl="";
        }
    }
}