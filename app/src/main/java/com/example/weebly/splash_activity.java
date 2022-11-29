package com.example.weebly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.weebly.placeholder.Content;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SendGetRequest(this).execute();
    }

    class SendGetRequest extends AsyncTask<String, Void, String> {

        private Context ctx;

        public SendGetRequest(Context context) {
            ctx = context;
        }

        private Exception exception;

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
            Content.initItems(res);
            startActivity(new Intent(ctx, MainActivity.class));
            finish();
        }
    }
}