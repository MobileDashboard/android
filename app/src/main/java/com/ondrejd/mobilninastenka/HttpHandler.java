package com.ondrejd.mobilninastenka;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that connects and downloads messages from remote dashboard.
 */
public class HttpHandler {
    public static final String TAG = HttpHandler.class.getSimpleName();

    private String dashboardCode;

    HttpHandler(String dashboardCode) {
        this.dashboardCode = dashboardCode;
    }

    /**
     * Loads JSON as string from the server.
     */
    public String makeServiceCall(String aUrl) {
        String json = null;

        try {
            //Establish connection
            URL url = new URL(aUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Read the response
            InputStream is = new BufferedInputStream(conn.getInputStream());
            json = convertStreamToString(is);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
            //TODO Show error dialog (or toaster)!
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
            //TODO Show error dialog (or toaster)!
        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e.getMessage());
            //TODO Show error dialog (or toaster)!
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
            //TODO Show error dialog (or toaster)!
        }

        return json;
    }

    /**
     * Converts input stream to string.
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Show error dialog (or toaster)!
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO Show error dialog (or toaster)!
            }
        }

        return sb.toString();
    }
}
