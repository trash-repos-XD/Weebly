package com.example.weebly;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.weebly.placeholder.Content;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import com.example.weebly.ui.main.SectionsPagerAdapter;
import com.example.weebly.databinding.ActivityMainBinding;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        new SendGetRequest(this).execute();
    }

    static class SendGetRequest extends AsyncTask<String, Void, String>{

        private Context ctx;

        public SendGetRequest (Context context){
            ctx = context;
        }

        private Exception exception;

        public String sendRequest(String targetURL) throws  IOException {

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
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }
        protected String doInBackground(String... urls) {
            String response ="";
            try {
                response = sendRequest("https://annie-api.azurewebsites.net/getWeekSchedule");
                return response;
            } catch ( IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String res) {
            Content.initItems(res);
            Toast.makeText(ctx, res, Toast.LENGTH_LONG).show();
        }
    }
}


