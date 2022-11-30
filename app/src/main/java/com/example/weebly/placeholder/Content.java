package com.example.weebly.placeholder;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Content {

    public static final List<AnimeSched> ITEMS_mon = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_tue = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_wed = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_thu = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_fri = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_sat = new ArrayList<AnimeSched>();
    public static final List<AnimeSched> ITEMS_sun = new ArrayList<AnimeSched>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<AnimeSched> getItemByDay(String day) {
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
            JSONArray schedules = new JSONArray(jsonData);
            for (int i = 0; i < schedules.length(); i++) {
                JSONObject daySched = schedules.getJSONObject(i);
                JSONArray scheds = daySched.getJSONArray("schedules");
                for (int j = 0; j < scheds.length(); j++) {
                    addItem(new AnimeSched(scheds.getJSONObject(j)), daySched.getString("day").substring(0, 3).toLowerCase());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
//            Log.e("JSON EXCEPTION", e.);
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
        public final String genres;
        public final String trailer;
        public final String trailerUrl;

        public AnimeSched(JSONObject parsedResponse) throws JSONException {
            JSONArray parsedGenres = parsedResponse.getJSONArray("genres");

            String genres = "";
            String synopsis = parsedResponse.getString("synopsis");
            int synopsisLength = 150;


            if (synopsis.equals("null")) {
                synopsis = "No Synopsis";
            } else if (synopsis.length() > synopsisLength) {
                synopsis = synopsis.substring(0, synopsisLength) + "...";
            }

            for (int i = 0; i < parsedGenres.length(); i++) {
                String genre = parsedGenres.getString(i);
                if (i == parsedGenres.length() - 1) {
                    genres += genre + ".";
                    break;
                }
                genres += genre + ", ";
            }

            this.id = String.valueOf(parsedResponse.getInt("id"));

            this.maUrl = "";
            this.thumbnail = parsedResponse.getString("thumbnail");
            this.name = parsedResponse.getString("name");
            this.score = 10;
            this.popularity = 10;
            this.synopsis = synopsis;
            this.genres = genres;
            this.trailer = "";
            this.trailerUrl = "";
        }
    }
}