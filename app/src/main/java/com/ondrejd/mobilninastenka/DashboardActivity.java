package com.ondrejd.mobilninastenka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity with dashboard with messages.
 * @todo Save dashboard hash if messages are downloaded successfully.
 * @todo Change name of the activity (should be "Dashboard - {Name of the dashboard}").
 * @todo URL of dashboard server should be taken from the settings!
 */
public class DashboardActivity extends AppCompatActivity {
    private final static String TAG = DashboardActivity.class.getSimpleName();

    public static String DASHBOARD_HASH = "dashboard-hash";

    //URL of dashboard server
    private static String url = "http://mobilninastenka.cz/api/%s/messages";

    //Hold's proper hash of a dashboard
    private String dashboardHash;

    private ProgressDialog progressDialog;
    private ListView listView;

    ArrayList<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Get dashboard code from Intent
        Intent intent = getIntent();
        dashboardHash = intent.getStringExtra(DASHBOARD_HASH);
        //Initialize messageList
        messageList = new ArrayList<Message>();
        //Initialize listView
        listView = (ListView) findViewById(R.id.activity_dashboard);
        //Get messages
        new GetMessages().execute();
    }

    /**
     * Async task class to get dashboard messages (JSON via HTTP).
     */
    private class GetMessages extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Show progress dialog
            progressDialog = new ProgressDialog(DashboardActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.dashboard_activity_waiting_msg));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url.replace("%s", dashboardHash));

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray messages = jsonObj.getJSONArray("messages");

                    messageList = Message.fromJson(messages);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    R.string.dashboard_activity_json_parse_err,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                R.string.dashboard_activity_server_parse_err,
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Dismiss progress dialog
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            MessagesAdapter adapter = new MessagesAdapter(DashboardActivity.this, messageList);
            listView.setAdapter(adapter);
        }
    }
}
