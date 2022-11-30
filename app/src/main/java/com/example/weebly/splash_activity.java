package com.example.weebly;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.weebly.helpers.CacheHelper;
import com.example.weebly.placeholder.Content;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class splash_activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String cached = CacheHelper.read(this, "schedules.json");

        if (cached == null || cached.length() == 0) {
            new SendGetRequest(this).execute();
        } else {
            toHome(getApplicationContext(), cached);
        }
    }

    protected void toHome(Context ctx, String schedules) {
        Content.initItems(schedules);
        startActivity(new Intent(ctx, MainActivity.class));
        finish();
    }

    class SendGetRequest extends AsyncTask<String, Void, String> {

        private final Context ctx;

        public SendGetRequest(Context context) {
            ctx = context;
        }

        public String sendRequest(String targetURL) throws IOException {

            URL url = new URL(targetURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return readStream(in);
            } finally {
                urlConnection.disconnect();
            }
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while (i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }

        protected String doInBackground(String... urls) {
            String response = "";
            try {
                response = sendRequest("https://annie-api.azurewebsites.net/getWeekSchedule");
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String res) {
            CacheHelper.create(ctx, res);
            toHome(ctx, res);
        }
    }
}