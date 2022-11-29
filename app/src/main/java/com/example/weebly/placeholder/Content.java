package com.example.weebly.placeholder;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

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
    public static final List<AnimeSched> ITEMS_mon = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_tue = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_wed = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_thu = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_fri = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_sat = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_sun = new ArrayList<AnimeSched>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<AnimeSched> getItemByDay(String day) {
        Log.e("D DAY", day);
        switch (day.toLowerCase()) {
            case "sun":
                return ITEMS_sun;
            case "mon":
                return ITEMS_mon;
            case "tue":
                return ITEMS_tue;
            case "wed":
                return ITEMS_wed;
            case "thu":
                return ITEMS_thu;
            case "fri":
                return ITEMS_fri;
            case "sat":
                return ITEMS_sat;
            default:
                return new ArrayList<>();
        }
    }

    public static void initItems(String jsonData) {
        try {
            Log.e("THE JSON", new JSONArray(jsonData).getJSONObject(0).getJSONArray("schedules").toString());

            JSONArray schedules = new JSONArray(jsonData);
            for (int i = 0; i < schedules.length(); i++) {
                JSONObject daySched = schedules.getJSONObject(i);
                JSONArray scheds = daySched.getJSONArray("schedules");
                for (int j = 0; j < scheds.length(); j++) {
                    addItem(new AnimeSched(scheds.getJSONObject(j)), daySched.getString("day").substring(0, 3).toLowerCase());
                }
            }
        } catch (JSONException e) {
            Log.e("JSON EXCEPTION", e.toString());
        }
    }


    private static void addItem(AnimeSched item, String day) {
        switch (day) {
            case "sun":
                ITEMS_sun.add(item);
                break;
            case "mon":
                ITEMS_mon.add(item);
                break;
            case "tue":
                ITEMS_tue.add(item);
                break;
            case "wed":
                ITEMS_wed.add(item);
                break;
            case "thu":
                ITEMS_thu.add(item);
                break;
            case "fri":
                ITEMS_fri.add(item);
                break;
            case "sat":
                ITEMS_sat.add(item);
                break;
        }
    }


    public static class AnimeSched {
        public final String maUrl;
        public final String id;
        public final String thumbnail;
        public final String name;
        public final double score;
        public final double popularity;
        public final String synopsis;
        public final String[] genres;
        public final String trailer;
        public final String trailerUrl;

        public AnimeSched(JSONObject parsedResponse) throws JSONException {
            this.id = String.valueOf(parsedResponse.getInt("id"));
            this.maUrl = "";
            this.thumbnail = "";
            this.name = parsedResponse.getString("name");
            this.score = 10;
            this.popularity = 10;
            this.synopsis = "";
            this.genres = new String[]{"Action", "Adventure"};
            this.trailer = "";
            this.trailerUrl = "";
        }
    }
}