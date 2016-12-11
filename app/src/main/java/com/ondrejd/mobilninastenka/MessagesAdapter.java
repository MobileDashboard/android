package com.ondrejd.mobilninastenka;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom list adapter with dashboard messages.
 * @link https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class MessagesAdapter extends ArrayAdapter<Message> {
    /**
     * Constructor.
     * @param context Context in which is adapter used.
     * @param messages List of messages.
     */
    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        Message message = getItem(position);
        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_row, parent, false);
        }

        LinearLayout row = (LinearLayout) convertView.findViewById(R.id.list_row);

        if (message.getPriority() == Message.PRIORITY_EXTRA) {
            row.setBackgroundColor(Color.parseColor("#f44336"));
        }
        else if (message.getPriority() == Message.PRIORITY_NORMAL) {
            row.setBackgroundColor(Color.parseColor("#ffd740"));
        }
        else if (message.getPriority() == Message.PRIORITY_LOW) {
            row.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

        //Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.list_row_title);
        TextView tvTimestamp = (TextView) convertView.findViewById(R.id.list_row_timestamp);
        TextView tvContent = (TextView) convertView.findViewById(R.id.list_row_content);
        TextView tvSent = (TextView) convertView.findViewById(R.id.list_row_sent);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.list_row_author);
        //Populate the data into the template view using the data object
        tvTitle.setText(message.getTitle());
        tvTimestamp.setText(message.getTimestampAsDateString());
        tvContent.setText(message.getContent());
        tvSent.setText(message.getSentAsDateString());
        tvAuthor.setText(message.getAuthor());
        //Return the completed view to render on screen
        return convertView;
    }
}
