package com.example.sneha.androidservices;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class StartedService extends IntentService {

    public StartedService() {
        super("StartedService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Object[] value =(Object[])intent.getExtras().get("urls");
        URL[] urls = (URL[])value;
        int count = urls.length;
        long totalBytesDownloaded = 0;
        for (int i = 0; i < count; i++) {
            totalBytesDownloaded += DownloadFile(urls[i]);
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
        getBaseContext().sendBroadcast(broadcastIntent);
    }

    public long DownloadFile(URL url) {
        long total = 0;
        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream("/sdcard/"+url.getPath().substring(url.getPath().lastIndexOf('/') + 1));

            byte data[] = new byte[1024];

            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }

}
