package com.example.weebly.placeholder;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Content {

    public static final List<AnimeSched> ITEMS_mon = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_tue = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_wed = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_thu = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_fri = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_sat = new ArrayList<>();
    public static final List<AnimeSched> ITEMS_sun = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<AnimeSched> getItemByDay(String day) {
        List<AnimeSched> toReturn;
        switch (day.toLowerCase()) {
            case "sun":
                toReturn = ITEMS_sun;
                break;
            case "mon":
                toReturn = ITEMS_mon;
                break;
            case "tue":
                toReturn = ITEMS_tue;
                break;
            case "wed":
                toReturn = ITEMS_wed;
                break;
            case "thu":
                toReturn = ITEMS_thu;
                break;
            case "fri":
                toReturn = ITEMS_fri;
                break;
            case "sat":
                toReturn = ITEMS_sat;
                break;
            default:
                toReturn = new ArrayList<>();
                break;
        }

        Collections.sort(toReturn, (obj1, obj2) -> obj2.score.compareToIgnoreCase(obj1.score));

        return toReturn;
    }

    public static void initItems(String jsonData) {
        try {
            JSONArray schedules = new JSONArray(jsonData);
            for (int i = 0; i < schedules.length(); i++) {
                JSONObject daySched = schedules.getJSONObject(i);
                JSONArray scheds = daySched.getJSONArray("schedules");
                for (int j = 0; j < scheds.length(); j++) {
                    AnimeSched theSched = new AnimeSched(scheds.getJSONObject(j));
                    Log.e("SCORE", theSched.score);
                    if (theSched.score.equals("null")) {
                        continue;
                    }
                    addItem(theSched, daySched.getString("day").substring(0, 3).toLowerCase());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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


    public static class AnimeSched implements Serializable {
        public final String malUrl;
        public final String id;
        public final String thumbnail;
        public final String name;
        public final String score;
        public final String popularity;
        public final String synopsis;
        public final String genres;
        public final String trailer;
        public final String trailerUrl;

        public AnimeSched(JSONObject parsedResponse) throws JSONException {
            JSONArray parsedGenres = parsedResponse.getJSONArray("genres");

            StringBuilder genres = new StringBuilder();
            String synopsis = parsedResponse.getString("synopsis");

            if (synopsis.equals("null")) {
                synopsis = "No Synopsis";
            }

            for (int i = 0; i < parsedGenres.length(); i++) {
                String genre = parsedGenres.getString(i);
                if (i == parsedGenres.length() - 1) {
                    genres.append(genre).append(".");
                    break;
                }
                genres.append(genre).append(", ");
            }

            this.id = String.valueOf(parsedResponse.getInt("id"));
            this.malUrl = parsedResponse.getString("malUrl");
            this.thumbnail = parsedResponse.getString("thumbnail");
            this.name = parsedResponse.getString("name");
            this.score = parsedResponse.getString("score");
            this.popularity = parsedResponse.getString("popularity");
            this.synopsis = synopsis;
            this.genres = genres.toString();
            this.trailer = parsedResponse.getString("trailer");
            this.trailerUrl = parsedResponse.getString("trailerUrl");
        }
    }
}