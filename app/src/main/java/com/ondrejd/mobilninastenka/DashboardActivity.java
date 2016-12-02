package com.ondrejd.mobilninastenka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity with dashboard with messages.
 * @todo Save dashboard hash if messages are downloaded successfully.
 * @todo Change name of the activity (should be "Dashboard - {Name of the dashboard}").
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

    ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Get dashboard code from Intent
        Intent intent = getIntent();
        dashboardHash = intent.getStringExtra(DASHBOARD_HASH);
        //Initialize messageList
        messageList = new ArrayList<>();
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

                    // Getting JSON Array node
                    JSONArray messages = jsonObj.getJSONArray("messages");

                    // looping through All Contacts
                    for (int i = 0; i < messages.length(); i++) {
                        //parse JSON object
                        JSONObject c = messages.getJSONObject(i);
                        DashboardMessage msg = new DashboardMessage(c);

                        if (msg.getTitle() == null) {
                            continue;
                        }
                        //messageList.add(message)
                        HashMap<String, String> message = new HashMap<>();
                        message.put("boardHash", msg.getBoardHash());
                        message.put("boardName", msg.getBoardName());
                        message.put("id", String.valueOf(msg.getId()));
                        message.put("title", msg.getTitle());
                        message.put("content", msg.getContent());
                        message.put("timestamp", msg.getTimestampAsDateString());
                        message.put("link", msg.getTitle());
                        //message.put("priority", priority);
                        //message.put("deleted", deleted);
                        message.put("expiration", msg.getExpirationAsDateString());
                        message.put("author", msg.getAuthor());
                        message.put("sent", msg.getSentAsDateString());

                        //add message to the list
                        messageList.add(message);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
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
                                "Couldn't get json from server. Check LogCat for possible errors!",
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

            //Move parsed JSON into the ListView
            ListAdapter adapter = new SimpleAdapter(
                    DashboardActivity.this,
                    messageList,
                    R.layout.simple_list_row,
                    new String[]{
                            //"notice-board-hash",
                            //"notice-board-name",
                            //"id",
                            "title",
                            "content",
                            "timestamp",
                            //"link",
                            //"priority",
                            //"deleted",
                            //"expiration",
                            "author",
                            "sent"
                    },
                    new int[]{
                            R.id.list_row_title,
                            R.id.list_row_content,
                            R.id.list_row_timestamp,
                            R.id.list_row_author,
                            R.id.list_row_sent
                    }
            );
            listView.setAdapter(adapter);
        }
    }
}
