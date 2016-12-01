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
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private final static String TAG = DashboardActivity.class.getSimpleName();

    public static String DASHBOARD_CODE = "dashboard-code";

    //URL of dashboard server
    private static String url = "http://mobilninastenka.cz/api/";

    //Hold's proper hash code of a dashboard
    private String dashboardCode;

    private ProgressDialog progressDialog;
    private ListView listView;

    private ArrayAdapter<String> listAdapter;
    ArrayList<HashMap<String, String>> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Get dashboard code from Intent
        Intent intent = getIntent();
        dashboardCode = intent.getStringExtra(DASHBOARD_CODE);
        //Initialize messageList
        messageList = new ArrayList<>();
        //Initialize listView
        listView = (ListView) findViewById(R.id.activity_dashboard);
        //Get messages
        new GetMessages().execute();
        //Access dashboard
        //Dashboard dashboard = new Dashboard(dashboardCode);
        //Get and render messages
        //renderMessages(dashboard.getMessages());
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
            //TODO Use properly localized string!
            progressDialog.setMessage("Please, wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler(dashboardCode);

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

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
                        String noticeBoardHash = c.getString("notice-board-hash");
                        String noticeBoardName = c.getString("notice-board-name");
                        String id = c.getString("id");
                        String title = c.getString("title");
                        String content = c.getString("content");
                        //int timestamp = c.getInt("timestamp");
                        String link = c.getString("link");
                        //int priority = c.getInt("priority");
                        //boolean deleted = c.getBoolean("deleted");
                        //int expiration = c.getInt("expiration");
                        String author = c.getString("author");
                        //int sended = c.getInt("sended");

                        //temporary hash map for single message
                        /**
                         * @todo Use {@link DashboardMessage} object instead of {@link HashMap}!
                         */
                        HashMap<String, String> message = new HashMap<>();
                        message.put("boardHash", noticeBoardHash);
                        message.put("boardName", noticeBoardName);
                        message.put("id", id);
                        message.put("title", title);
                        message.put("content", content);
                        //message.put("timestamp", timestamp);
                        message.put("link", link);
                        //message.put("priority", priority);
                        //message.put("deleted", deleted);
                        //message.put("expiration", expiration);
                        message.put("author", author);
                        //message.put("sended", sended);

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
                            //"timestamp",
                            //"link",
                            //"priority",
                            //"deleted",
                            //"expiration",
                            "author",
                            //"sended"
                    },
                    new int[]{
                            R.id.list_row_title,
                            R.id.list_row_content,
                            R.id.list_row_author
                    }
            );
            listView.setAdapter(adapter);
        }
    }

    /**
     * Renders messages.
     * @param messages List of {@link DashboardMessage}.
     * @todo Better rrendering!
     */
    private void renderMessages(List<DashboardMessage> messages) {
        ArrayList<String> messagesList = new ArrayList<String>();

        for(DashboardMessage message : messages) {
            String msg = message.getTitle() + "\n" + message.getText() + "\n" +
                    "[" + message.getAuthor() + "/" + message.getCreated() + "/" + message.getUpdated() + "]" +
                    "\n" + "\n";
            messagesList.add(msg);
        }

        listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_row, messagesList);

        listView.setAdapter(listAdapter);
    }
}
