package com.example.weebly.helpers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CacheHelper {

    private static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String read(Context context, String fileName) {
        try {
            Date lastModified = new Date(new File(context.getFilesDir(), fileName).lastModified());
            Date lastSunday = Date.from(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).atStartOfDay(ZoneId.systemDefault()).toInstant());
            long fromLastSunday = getDifferenceDays(lastSunday, lastModified);

            if (fromLastSunday < 0) {
                return null;
            }

            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean create(Context context, String jsonString) {
        String FILENAME = "schedules.json";
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (IOException fileNotFound) {
            return false;
        }

    }
}